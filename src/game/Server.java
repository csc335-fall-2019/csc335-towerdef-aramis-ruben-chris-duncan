package game;
/**
 * Server.java
 * 
 * Opens the ports for the server side of the networking and maintains the
 * connection for as long as the program is running.
 * 
 * Usage instructions:
 * 
 * Construct Server
 * Server server= new Server(host, port, controller, lostModal, tieModal)
 * 
 * Other useful methods:
 * client.run()
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Platform;

public class Server implements Runnable{
	
	// Field variables for Client object
	private String host;
	
	private int port;
	
	private TowerDefenseController controller;
	
	private Runnable lostModal;
	
	private Runnable tieModal;
	
	/**
     * Purpose: Initializes a Server object.
     * 
     * @param host - the name of the host to connect to.
     * 
     * @param port - the port to connect through
     * 
     * @param controller - a Connect4Controller object to have reference back
     * to the controller
     * 
     * @param lostModal - a modal to display if the client player has lost
     * 
     * @param tieModal - a modal to display if the client player has tied
     * 
     * @return None.
     */
	public Server(String h, int p, TowerDefenseController c, Runnable lostModal, Runnable tieModal) {
		host = h;
		port = p;
		controller = c;
		this.lostModal = lostModal;
		this.tieModal = tieModal;
	}
	
	/**
     * Purpose: Opens the socket to establish a connection and waits for a
     * client to connect. Maintains the connection until the program is ended.
     * 
     * @param None.
     *  
     * @return None.
     */
	@Override
	public void run() {
		ObjectInputStream in = null;
		ServerSocket server = null;
		try {
			controller.setServer(new ServerSocket());
			server = controller.getServer();
		} catch (IOException e1) {
			// Failed to connect to outgoing ports.
			e1.printStackTrace();
			return;
		}
		try {
			server.bind(new InetSocketAddress(host,port));

			try {
				Socket socket = server.accept();
				controller.setSocket(socket);
				controller.setConnected(true);
				controller.setRunning(true);
				controller.setOut(new ObjectOutputStream(socket.getOutputStream()));
				in = new ObjectInputStream(socket.getInputStream());
				while(controller.isRunning()) {
					try {
						TowerDefenseTurnMessage move = (TowerDefenseTurnMessage)in.readObject();
//						if(!controller.isLegalMove(move)) {
//							System.out.println(move.getRow()+" "+move.getColumn());
//							throw new IndexOutOfBoundsException("Illegal Move.");
//						}
						controller.handleMessage(move);
					}catch (ClassNotFoundException e) {
						controller.setRunning(false);
					} catch(IndexOutOfBoundsException e) {
						System.out.println("Illegal Move");
					}catch (Exception e) {
						controller.setRunning(false);
					}
				}
				socket.close();
				server.close();
			} catch (IOException e) {
				// Failed to accept.
				e.printStackTrace();
				try {
					server.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if(controller.getSocket()!=null) {
						controller.getSocket().close();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (IOException e) {
			// Failed to connect to specific passed in port.
			e.printStackTrace();
		}
	}
}
