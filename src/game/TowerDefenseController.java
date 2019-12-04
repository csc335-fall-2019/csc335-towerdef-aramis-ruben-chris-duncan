package game;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import viewable.Viewable;
import viewable.gameObjects.Market;
import viewable.gameObjects.Tower;
import viewable.gameObjects.TowerType;

public class TowerDefenseController {
	private TowerDefenseBoard board;
	
	private volatile boolean isRunning = false;
	
	private volatile boolean hasConnected = false;
	
	private volatile ServerSocket server;
	
	private volatile Socket socket;
	
	private volatile ObjectOutputStream out;
	
	private List<TowerDefenseMoveMessage> moves;
	
	public TowerDefenseController(TowerDefenseView view) throws FileNotFoundException {
		board = new TowerDefenseBoard(view);
		moves = new ArrayList<TowerDefenseMoveMessage>();
	}

	public Viewable[][][] getBoard() {
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
	
	public Market getMarket() {
		return board.getMarket();
	}
	
	// Getter for isRunning.
	public boolean isRunning() {
		return isRunning;
	}
	
	// Setter for isRunning.
	public void setRunning(boolean b) {
		isRunning = b;
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
