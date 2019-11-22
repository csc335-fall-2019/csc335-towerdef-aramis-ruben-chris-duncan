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
import java.util.ArrayList;
import java.util.List;

import game.TowerDefenseView;
import javafx.application.Platform;

public class PeerToPeerSocket implements Runnable{
	private List<ServerSocket> servers;
	private List<Socket> activeConnections;
	private List<Query> currentConnections;
	private String host;
	private User user;
	private Query from;
	
	public PeerToPeerSocket() throws IOException {
		this("localhost",6881);
	}
	
	public PeerToPeerSocket(String host, int offset) throws IOException {
		from = new Query(host, offset);
		this.host = host;
		servers = new ArrayList<ServerSocket>();
		currentConnections = new ArrayList<Query>();
		for(int i = offset;i<offset+5;i++) {
			try {
				servers.add(new ServerSocket(i));
			}catch(Exception ex) {
				ex.printStackTrace();
				continue;
			}
		}
		activeConnections = new ArrayList<Socket>();
	}
	
	@Override
	public void run() {
		while(true) {
			List<ServerSocket> toRemove = new ArrayList<ServerSocket>();
			for(ServerSocket server: servers) {
				try {
					server.setSoTimeout(100);
					Socket s = server.accept();
					ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
					out.writeObject(new Message(from, new Query(host, server.getLocalPort()),user!=null?user.getUsername():""));
					activeConnections.add(s);
					toRemove.add(server);
				} catch (SocketTimeoutException e) {
					// TODO Auto-generated catch block
					continue;
				} catch(IOException e) {
					return;
				}
				catch(Exception ex) {
					List<Socket> connections = new ArrayList<Socket>();
					for(Socket con: activeConnections) {
						try {
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
			for(Socket s: activeConnections) {
				try {
					ObjectInputStream in = new ObjectInputStream(s.getInputStream());
					Object obj = in.readObject();
					if(obj instanceof Message){
						Message message = (Message)obj;
						if(verifyQuery(message.getQuery(), s)) {
							System.out.println(message.getMessage()+" from "+message.getFrom().getDesiredHost());
							ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
							out.writeObject(new AckMessage(from, message.getFrom()));
						}else {
							System.out.println("Message: ,"+message.getMessage()+"Desired host and port: "+message.getQuery().getDesiredHost()+" "+message.getQuery().getDesiredPort()+". Current host and port: "+host+" "+s.getPort()+" "+s.getLocalPort());
							for(Socket socket: activeConnections) {
								if(socket.equals(s)) {
									continue;
								}
								ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
								Query query = message.getQuery();
								if(query.getDesiredHostName()!=null&&query.getDesiredHostName().length()>0) {
									// Check if we can update the query with the correct data.
									for(Query q: currentConnections) {
										if(q.getDesiredHostName()!=null&&q.getDesiredHostName().equals(query.getDesiredHostName())) {
											if(q.getDesiredHost()!=null&&q.getDesiredHost().length()==0) {
												q.setDesiredHost(query.getDesiredHost());
											}
											if(q.getDesiredPort()==0) {
												q.setDesiredPort(query.getDesiredPort());
											}
											if(query.getDesiredHost()!=null&&query.getDesiredHost().length()==0) {
												query.setDesiredHost(q.getDesiredHost());
											}
											if(query.getDesiredPort()==0) {
												query.setDesiredPort(q.getDesiredPort());
											}
										}
									}
								}
								out.writeObject(message);
							}
						}
					}else if(obj instanceof AckMessage) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								TowerDefenseView.MESSAGE_RECEIVED.showAndWait();
							}
						});
					}
				}catch(Exception ex) {
					continue;
				}
			}
		}
	}
	
	public boolean verifyQuery(Query q, Socket s) {
		boolean isDesiredHost = false;
		if(q.getDesiredHostName()!=null&&q.getDesiredHostName().length()>0) {
			isDesiredHost = user.getUsername().equals(q.getDesiredHostName());
		}else {
			isDesiredHost = q.getDesiredHost().equals(host);
		}
		int desiredPort = q.getDesiredPort();
		boolean isDesiredPort = desiredPort == s.getPort()||desiredPort==s.getLocalPort();
		return isDesiredHost&&isDesiredPort;
	}
	
	public void connect(String host, int port, Thread callback) throws Exception{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for(Socket s: activeConnections) {
					if(s.getPort()==port&&(s.getInetAddress().getHostName().equals(host)||s.getInetAddress().getHostAddress().equals(host))) {
						callback.start();
						return;
					}
				}
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
					callback.start();
					return;
				}
				try {
					ObjectInputStream in = new ObjectInputStream(s.getInputStream());
					Message init = (Message)in.readObject();
					currentConnections.add(init.getQuery());
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
	
	public void connect(String hostname, Thread callback) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				int port = 0;
				String host = "";
				for(Query q:currentConnections) {
					if(q.getDesiredHostName()!=null&&q.getDesiredHostName().equals(hostname)) {
						port = q.getDesiredPort();
						host = q.getDesiredHost();
					}
				}
				for(Socket s: activeConnections) {
					if(s.getPort()==port&&(s.getInetAddress().getHostName().equals(host)||s.getInetAddress().getHostAddress().equals(host))) {
						callback.start();
						return;
					}
				}
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
					callback.start();
					return;
				}
				try {
					ObjectInputStream in = new ObjectInputStream(s.getInputStream());
					Message init = (Message)in.readObject();
					currentConnections.add(init.getQuery());
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
	
	public void sendMessage(String hostname, String message) {
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Query query = new Query(hostname);
				Message m = new Message(from, query, message);
				List<Socket> toRemove = new ArrayList<Socket>();
				for(Socket s: activeConnections) {
					try {
						ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
						out.writeObject(m);
					}catch(Exception ex) {
						toRemove.add(s);
					}
				}
				for(Socket s: toRemove) {
					try {
						servers.add(new ServerSocket(s.getLocalPort()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				activeConnections.removeAll(toRemove);
			}
		});
		connect(host, thread2);
	}
	
	public void sendMessage(String hostTo, int port, String message) throws Exception {
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Query query = new Query(hostTo, port);
				Message m = new Message(from, query, message);
				List<Socket> toRemove = new ArrayList<Socket>();
				for(Socket s: activeConnections) {
					try {
						ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
						out.writeObject(m);
					}catch(Exception ex) {
						toRemove.add(s);
					}
				}
				for(Socket s: toRemove) {
					try {
						servers.add(new ServerSocket(s.getLocalPort()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				activeConnections.removeAll(toRemove);
			}
		});
		connect(hostTo, port, thread2);
	}
	
	public boolean login(String username, String password) throws IOException {
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
	            		this.user = testUser;
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
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(users));
		out.writeObject(user);
		out.close();
		this.user = user;
		return true;
	}
}
