package chat;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.TowerDefenseView;
import javafx.application.Platform;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;

public class PeerToPeerSocket implements Runnable{
	private volatile List<ServerSocket> servers;
	private volatile List<Socket> activeConnections;
	private volatile List<Sender> currentConnections;
	private Map<Socket, Object[]> mapConnections;
	private String host;
	private LoggedInUser user;
	private Sender from;
	private Thread failedGeneric;
	
	public PeerToPeerSocket() throws IOException {
		this("localhost",6881);
	}
	
	public PeerToPeerSocket(String host, int offset) throws IOException {
		this.host = host;
		mapConnections = new HashMap<Socket, Object[]>();
		servers = new ArrayList<ServerSocket>();
		currentConnections = new ArrayList<Sender>();
		for(int i = offset;i<offset+5;i++) {
			try {
				servers.add(new ServerSocket(i));
			}catch(Exception ex) {
				ex.printStackTrace();
				continue;
			}
		}
		activeConnections = new ArrayList<Socket>();
		failedGeneric = new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						
					}
				});
			}
		});
	}
	
	@Override
	public void run() {
		while(true) {
			checkServers();
			checkSockets();
			System.out.println("Listening");
		}
	}
	
	private void checkServers() {
		List<ServerSocket> toRemove = new ArrayList<ServerSocket>();
		for(ServerSocket server: servers) {
			try {
				server.setSoTimeout(100);
				Socket s = server.accept();
				System.out.println("Accepted");
				// If connection is accepted, send the initial user information.
				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(s.getInputStream());
				out.writeObject(new Message(from, new Query(host, server.getLocalPort()),""));
				
				// Get the response object which should inform us about the other PC.
				Message res = (Message)in.readObject();
				Sender mesFrom = res.getFrom();
				mesFrom.setHost(s.getInetAddress().getHostAddress());
				mesFrom.setPort(s.getPort());
				currentConnections.add(res.getFrom());
				activeConnections.add(s);
				mapConnections.put(s, new Object[] {out, in});
				
				// Ensure we don't try to connect to this server again.
				toRemove.add(server);
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				continue;
			} catch(IOException e) {
				e.printStackTrace();
				return;
			}
			catch(Exception ex) {
				ex.printStackTrace();
				// In the case that something unexpected happens, check to make sure that the sockets all connect.
				List<Socket> connections = new ArrayList<Socket>();
				for(Socket con: activeConnections) {
					try {
						// Read the next byte in the stream to ensure that the stream is still connected.
						con.getInputStream().mark(1);
						if(con.getInputStream().read()==-1) {
							servers.add(new ServerSocket(con.getLocalPort()));
							continue;
						}else {
							con.getInputStream().reset();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						try {
							servers.add(new ServerSocket(con.getLocalPort()));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						continue;
					}
					connections.add(con);
				}
				activeConnections = connections;
			}
		}
		servers.removeAll(toRemove);
	}
	
	private void checkSockets() {
		for(Socket s: activeConnections) {
			try {
				// Try to read in an object.
				ObjectInputStream in = (ObjectInputStream)(mapConnections.get(s)[1]);
				if(in.available()==0) {
					System.out.println("Stream "+s.getPort()+" has no objects to read.");
					continue;
				}
				Object obj = in.readObject();
				System.out.println(obj);
				
				// Handle the message case.
				if(obj instanceof Message){
					try {
						handleMessage(obj, s);
					}catch(IOException ex) {
						ex.printStackTrace();
					}
				}else if(obj instanceof AckMessage) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
						}
					});
				}
			}catch(Exception ex) {
				continue;
			}
		}
	}
	
	public void handleMessage(Object obj, Socket s) throws IOException {
		Message message = (Message)obj;
		// Can we verify that the message is meant for us?
		if(verifyQuery(message.getQuery(),s)) {
			user.addChat(message);
			if(!currentConnections.contains(message.getFrom())) {
				
			}
			//ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			// Write an acknowledge message to the user we received the message from.
			// [TODO] make this write the message directly to the user, ie connect to the user and then write the message.
			//out.writeObject(new AckMessage(from, message.getFrom()));
			currentConnections.add(message.getFrom());
		}else {
			System.out.println("???");
			// Add the person we got this message from to the list of current known connections.
			currentConnections.add(message.getFrom());
			for(Socket socket: activeConnections) {
				if(socket.equals(s)) {
					continue;
				}
				
				// Try to update useful data, like usernames and whatnot with the new data.
				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
				Query query = message.getQuery();
//				if(query.getDesiredHostName()!=null&&query.getDesiredHostName().length()>0) {
//					// Check if we can update the query with the correct data.
//					for(Sender q: currentConnections) {
//						if(q.getUser()!=null&&q.getDesiredHostName().equals(query.getDesiredHostName())) {
//							if(q.getDesiredHost()!=null&&q.getDesiredHost().length()==0) {
//								q.setDesiredHost(query.getDesiredHost());
//							}
//							if(q.getDesiredPort()==0) {
//								q.setDesiredPort(query.getDesiredPort());
//							}
//							if(query.getDesiredHost()!=null&&query.getDesiredHost().length()==0) {
//								query.setDesiredHost(q.getDesiredHost());
//							}
//							if(query.getDesiredPort()==0) {
//								query.setDesiredPort(q.getDesiredPort());
//							}
//						}else if(query.getDesiredHost().equals(q.getDesiredHost())&&query.getDesiredPort()==q.getDesiredPort()) {
//							if(q.getDesiredHostName()!=null&&!q.getDesiredHostName().equals(query.getDesiredHostName())) {
//								query.setDesiredHostName(q.getDesiredHostName());
//							}
//						}
//					}
//				}
				// Send the message on.
				out.writeObject(message);
			}
		}
	}

	/**
	 * This method checks to make sure that the message is either meant for the user or for host.
	 * @param q the query.
	 * @param s the socket that the connection is held on.
	 * @return
	 */
	public boolean verifyQuery(Query q, Socket s) {
		boolean isDesiredHost = false;
		// Username takes preference. 
		// [TODO] verify that the user we're connecting to has the correct salt and whatnot.
		if(q.getDesiredHostName()!=null&&q.getDesiredHostName().length()>0) {
			isDesiredHost = user.getUser().getUsername().equals(q.getDesiredHostName());
		}else {
			isDesiredHost = q.getDesiredHost().equals(host);
		}
		boolean isDesiredPort = q.getDesiredPort()==s.getLocalPort()||q.getDesiredPort()==s.getPort();
		System.out.println(isDesiredHost+" "+isDesiredPort);
		return isDesiredHost&&isDesiredPort;
	}
	
	/**
	 * This method connects to the desired host and port.
	 * @param host The ip address.
	 * @param port The port number.
	 * @param callback A thread to run upon completion of the connection.
	 * @param failed A thread to run if the connection failed.
	 * @throws Exception If IOException occurs.
	 */
	public void connect(String host, int port, Thread callback, Thread failed) throws Exception{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for(Socket s: activeConnections) {
					// Do we already have this connection stored?
					if(s.getPort()==port&&(s.getInetAddress().getHostName().equals(host)||s.getInetAddress().getHostAddress().equals(host))) {
						callback.start();
						return;
					}
				}
				// Poll for 10 seconds to ensure that the server we try to connect to is atleast open once.
				Socket s = null;
				long timer = System.currentTimeMillis();
				while(timer+10000>System.currentTimeMillis()) {
					try {
						s = new Socket(host, port);
						break;
					}catch(Exception ex) {
						continue;
					}
				}
				if(s==null) {
					System.out.println("Couldn't connect.");
					failed.start();
					return;
				}else {
					System.out.println("Connected");
				}
				try {
					// Try to read in the connection data.
					ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(s.getInputStream());
					Message init = (Message)in.readObject();
					currentConnections.add(init.getFrom());
					mapConnections.put(s, new Object[] {out, in});
					out.writeObject(new Message(from, new Query(init.getFrom().getUser()),""));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				activeConnections.add(s);
				callback.start();
			}
		});
		thread.start();
	}
	
	public void connect(String hostname, Thread callback, Thread failed) throws Exception {
		int port = 0;
		String host = "";
		for(Sender q:currentConnections) {
			if(q.getUser()!=null&&q.getUser().equals(hostname)) {
				port = q.getPort();
				host = q.getHost();
			}
		}
		connect(host, port, callback, failed);
	}
	
	public void sendMessage(String hostname, String message) throws Exception {
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				if(user!=null) {
					try {
						user.addOwnMessage(new Message(from, new Query(hostname), message));
					} catch (IOException | NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Query query = new Query(hostname);
				Message m = new Message(from, query, message);
				for(Socket s: activeConnections) {
					try {
						ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
						out.writeObject(m);
						System.out.println("Message sent");
					}catch(Exception ex) {
						continue;
					}
				}
			}
		});
		connect(host, thread2, failedGeneric);
	}
	
	public void sendMessage(String hostTo, int port, String message) throws Exception {
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				if(user!=null) {
					Query to = new Query(hostTo, port);
					for(Sender q: currentConnections) {
						if(to.getDesiredHost().equals(q.getUser())) {
							to.setDesiredHostName(q.getUser());
						}
					}
					Message m = new Message(from, to, message);
					try {
						user.addOwnMessage(m);
					} catch (IOException | NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Query query = new Query(hostTo, port);
				Message m = new Message(from, query, message);
				for(Socket s: activeConnections) {
					try {
						if(s.getInetAddress().getHostAddress().equals(s.getInetAddress().getHostAddress())&&s.getPort()==port) {
							ObjectOutputStream out = (ObjectOutputStream)(mapConnections.get(s)[0]);
							out.writeObject(m);
							System.out.println("Message sent");
						}
					}catch(Exception ex) {
						continue;
					}
				}
			}
		});
		connect(hostTo, port, thread2, failedGeneric);
	}
	
	public boolean login(String username, String password) throws IOException, NoSuchAlgorithmException {
		File users = new File("users.txt");
		users.createNewFile();
		User user = new User(username, password);
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(users));
	        while (true) {
	            User testUser = (User)(in.readObject());
	            if(testUser.getUsername().equals(username)) {
	            	if(testUser.checkPassword(password)) {
	            		this.user = new LoggedInUser(testUser);
	            		System.out.println(this.user);
	            		from = new Sender(testUser.getUsername());
	            		return true;
	            	}
	            	return false;
	            }
	        }
	    } catch (EOFException ignored) {
	        // as expected
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        if (in != null)
	            in.close();
	    }
		this.user = new LoggedInUser(user);
		System.out.println(this.user);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(users));
		out.writeObject(user);
		out.close();
		from = new Sender(user.getUsername());
		return true;
	}
	
	public LoggedInUser getUser() {
		return user;
	}
	
	public void setUser(LoggedInUser u) {
		user = u;
	}
}
