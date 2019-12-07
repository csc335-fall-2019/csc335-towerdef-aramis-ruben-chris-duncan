package game;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import viewable.gameObjects.Map;
import viewable.gameObjects.Market;
import viewable.gameObjects.TowerType;

public class TowerDefenseController {
	private TowerDefenseBoard board;
	
	private volatile boolean isRunning = true;
	
	private volatile boolean hasConnected = false;
	
	private volatile ServerSocket server;
	
	private volatile Socket socket;
	
	private volatile ObjectOutputStream out;
	
	private List<TowerDefenseMoveMessage> moves;
	
	private ObservableList<SocketAddress> possibleConnections;
	
	public TowerDefenseController(TowerDefenseView view) throws IOException {
		board = new TowerDefenseBoard(view);
		moves = new ArrayList<TowerDefenseMoveMessage>();
		setServer(getNextAvailableHostPort());
		possibleConnections = FXCollections.observableArrayList(new ArrayList<SocketAddress>());
	}

	public Map getBoard() {
		// TODO Auto-generated method stub
		return board.getBoard();
	}
	
	public void handleMessage(TowerDefenseTurnMessage message) {
		moves = message.getMoves();
		for(int i =0;i<moves.size();i++) {
			TowerDefenseMoveMessage move = moves.get(i);
		}
	}
	
	public void addTower(int row, int col, TowerType type) {
		board.addTower(row, col, type);
	}
	
	public void damageOther(int amount) {
		// send message to other player to take damage.
	}
	
	public ServerSocket getNextAvailableHostPort() throws IOException {
		ServerSocket s = new ServerSocket();
		int initial = 55000;
		while(true) {
			if(initial>55005) {
				throw new IndexOutOfBoundsException("No ports open.");
			}
			try {
				s.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), initial));
				break;
			}catch(Exception ex) {
				initial++;
				continue;
			}
		}
		return s;
	}
	
	public void scanPorts() throws IOException {
		try {
			if(!InetAddress.getLocalHost().getHostAddress().contains(".")) {
				return;
			}
			possibleConnections.clear();
			String[] command = new String[]{"arp","-a"};
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.directory(new File("./"));
			Process p=builder.start();
			Scanner scan = new Scanner(p.getInputStream());
			scan.nextLine();
			scan.nextLine();
			scan.nextLine();
			while(scan.hasNextLine()) {
				String list = scan.nextLine().trim();
				String address = list.split(" ")[0];
				Thread thread = new Thread(()-> {
					checkHost(address);
				});
				thread.start();
			}
			Thread thread = new Thread(()-> {
				try {
					checkHost(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			thread.start();
			scan.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkHost(String host) {
		try {
			if(!InetAddress.getByName(host).isReachable(100)) {
				System.out.println("Host: "+host+" unreachable...");
				return;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		for(int port = 55000;port<=55005&&isRunning;port++) {
			System.out.println("Checking "+host+":"+port);
			SocketAddress address = new InetSocketAddress(host, port);
			try {
				Socket s = new Socket(host, port);
				s.close();
				System.out.println(host+":"+port);
				Platform.runLater(()->{
					possibleConnections.add(address);
				});
			}catch(Exception ex) {
				continue;
			}
		}
	}
	
	public void startClient(String host, int port) {
		Thread thread = new Thread(new Client(host, port, this, null, null));
		thread.start();
	}
	
	public void startServer() {
		Thread thread = new Thread(new Server(this, null, null));
		System.out.println("Hosted on: "+server.getInetAddress().getHostAddress()+":"+server.getLocalPort());
		thread.start();
	}
	
	public ObservableList<SocketAddress> getPossibleConnections(){
		return possibleConnections;
	}
	
	public Market getMarket() {
		return board.getMarket();
	}
	
	public void setBoard(Map m) {
		board.setBoard(m);
	}
	
	// Getter for isRunning.
	public boolean isRunning() {
		return isRunning;
	}
	
	// Setter for isRunning.
	public void setRunning(boolean b) {
		isRunning = b;
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Getter for hasConnected.
	public boolean hasConnected() {
		return hasConnected;
	}
	
	// Setter for hasConnected.
	public void setConnected(boolean b) {
		hasConnected = b;
	}
	
	// Getter for serversocket.
	public ServerSocket getServer() {
		return server;
	}
	
	// Setter for serversocket.
	public void setServer(ServerSocket s) {
		server = s;
	}
	
	// Getter for socket.
	public Socket getSocket() {
		return socket;
	}
	
	// Setter for socket.
	public void setSocket(Socket s) {
		socket = s;
	}
	
	// Getter for output stream.
	public ObjectOutputStream getOut() {
		return out;
	}
	
	// Setter for output stream.
	public void setOut(ObjectOutputStream o) {
		out = o;
	}
}
