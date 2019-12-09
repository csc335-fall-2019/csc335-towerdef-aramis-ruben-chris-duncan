package game;
import java.io.File;
import java.io.FileNotFoundException;
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
import network.AbilityCardUsedMessage;
import network.DamageOtherMessage;
import network.StatIncreaseMessage;
import network.TowerDefenseMoveMessage;
import network.TowerDefenseTurnMessage;
import network.TowerPlacedMessage;
import network.TowerUpgradedMessage;
import network.TurnFinishedMessage;
import viewable.Viewable;
import viewable.cards.Card;
import viewable.cards.abilityCards.AbilityCard;
import viewable.cards.towers.TowerCard;
import viewable.gameObjects.Map;
import viewable.gameObjects.Market;
import viewable.gameObjects.Minion;
import viewable.gameObjects.Player;
import viewable.gameObjects.Tower;
import viewable.gameObjects.TowerType;
import viewable.mapObjects.Path;

public class TowerDefenseController {
	private TowerDefenseBoard board;
	
	private volatile boolean isRunning = true;
	
	private volatile boolean hasConnected = false;
	
	private volatile ServerSocket server;
	
	private volatile Socket socket;
	
	private volatile ObjectOutputStream out;
	
	private ObservableList<SocketAddress> possibleConnections;
	
	private TowerDefenseTurnMessage currentTurn;
	
	private Player currentPlayer;
	
	private Player otherPlayer;
	
	private volatile boolean minionsFinished;
	
	public TowerDefenseController(TowerDefenseView view) throws IOException {
		board = new TowerDefenseBoard(view, this);
		currentPlayer = new Player(this);
		otherPlayer = new Player(this);
		minionsFinished = false;
		currentTurn = new TowerDefenseTurnMessage();
		setServer(getNextAvailableHostPort());
		possibleConnections = FXCollections.observableArrayList(new ArrayList<SocketAddress>());
	}

	public Map getBoard() {
		// TODO Auto-generated method stub
		return board.getBoard();
	}
	
	public void handleMessage(TowerDefenseTurnMessage message) {
		List<TowerDefenseMoveMessage> moves = message.getMoves();
		for(int i =0;i<moves.size();i++) {
			TowerDefenseMoveMessage move = moves.get(i);
			handleMove(move);
		}
	}
	
	private void handleMove(TowerDefenseMoveMessage move) {
		Platform.runLater(()->{
			if(move instanceof TowerPlacedMessage) {
				TowerPlacedMessage m = (TowerPlacedMessage)move;
				int col = getBoard().getBoard().length-1-m.getCol();
				int row = getBoard().getBoard()[0].length-1-m.getRow();
				addTower(row, col, m.getTower());
			}else if(move instanceof AbilityCardUsedMessage) {
				AbilityCardUsedMessage a = (AbilityCardUsedMessage)move;
				useAbilityCardOther((AbilityCard)a.getCard());
			}else if(move instanceof DamageOtherMessage) {
				DamageOtherMessage d = (DamageOtherMessage)move;
				currentPlayer.damageTaken(d.getAmount());
			}
		});
	}
	
	public void endTurn() {
		currentPlayer.setComplete(true);
		Thread thread = new Thread(()-> {
			board.triggerMinions();
			while(!minionsFinished) {
				System.out.println("waiting");
			}
			try {
				out.writeObject(new TurnFinishedMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(!otherPlayer.isFinished()) {
				System.out.println(minionsFinished);
			}
			
			minionsFinished = false;
			try {
				out.writeObject(currentTurn);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			currentTurn = new TowerDefenseTurnMessage();
			Platform.runLater(()->{
				try {
					board.getMarket().repopulateForSale();
					currentPlayer.discardHand();
					currentPlayer.drawCards(5);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			currentPlayer.setComplete(false);
			otherPlayer.setComplete(false);
		});
		thread.start();
	}
	
	public void addTower(int row, int col, TowerType type) {
		board.addTower(row, col, type);
		currentTurn.addMove(new TowerPlacedMessage(type, row, col));
	}
	
	public void	useAbilityCard(AbilityCard card) {
		card.ability(currentPlayer);
		currentTurn.addMove(new AbilityCardUsedMessage(card));
		currentPlayer.addToDiscard(card);
	}
	
	private void useAbilityCardOther(AbilityCard card) {
		card.ability(otherPlayer);
		otherPlayer.addToDiscard(card);
	}
	
	public void useTowerCard(int row, int col) {
		if(getBoard().getBoard()[col][row][0] instanceof Path) {
			return;
		}
		if(currentPlayer.getSelectedCard()==null||!(currentPlayer.getSelectedCard() instanceof TowerCard)) {
			return;
		}
		TowerType vals = null;
		for(TowerType t: TowerType.values()) {
			if(t.getTower() == ((TowerCard) currentPlayer.getSelectedCard()).getTower()) {
				vals = t;
			}
		}
		if(vals==null) {
			return;
		}
		addTower(row, col, vals);
		currentPlayer.addToDiscard(currentPlayer.getSelectedCard());
		currentPlayer.setSelectedCard(null);
	}
	
	public boolean canUpgrade(int row, int col) {
		if(getBoard().getBoard()[col][row][0]==null) {
			return false;
		}
		if(!(getBoard().getBoard()[col][row][0] instanceof Tower)) {
			return false;
		}
		if(currentPlayer.getSelectedCard()==null||!(currentPlayer.getSelectedCard() instanceof TowerCard)) {
			return false;
		}
		TowerCard tCard = (TowerCard)currentPlayer.getSelectedCard();
		return tCard.getTower().isAssignableFrom(getBoard().getBoard()[col][row][0].getClass());
	}
	
	public void upgradeTower(Tower t, int row, int col) {
		((TowerCard)currentPlayer.getSelectedCard()).Upgrade(t);
		currentPlayer.addToDiscard(currentPlayer.getSelectedCard());
		currentPlayer.setSelectedCard(null);
		currentTurn.addMove(new TowerUpgradedMessage(row, col));
	}
	
	public void damageOther(int amount) {
		// send message to other player to take damage.
		currentTurn.addMove(new DamageOtherMessage(amount));
	}
	public void killMinion(Minion minion) {
		currentPlayer.increaseGold(minion.getReward());
		currentTurn.addMove(new StatIncreaseMessage(0, minion.getReward()));
	}
	
	public void setSelectedCard(Card card) {
		currentPlayer.setSelectedCard(card);
	}
	
	public void setOtherPlayerFinished(boolean b) {
		otherPlayer.setComplete(b);
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
	
	public Player getPlayer() {
		return currentPlayer;
	}
	
	public Player getOtherPlayer() {
		return otherPlayer;
	}
	
	private void setOtherPlayer(Player p) {
		otherPlayer = p;
	}
	
	public Market getMarket() {
		return board.getMarket();
	}
	
	public void setBoard(Map m) {
		board.setBoard(m);
	}
	
	public void setMinionsFinished(boolean b) {
		minionsFinished = b;
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
