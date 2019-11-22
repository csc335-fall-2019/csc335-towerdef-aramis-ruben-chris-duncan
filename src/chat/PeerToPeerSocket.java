package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PeerToPeerSocket implements Runnable{
	private List<ServerSocket> servers;
	private List<Socket> activeConnections;
	private String host;
	
	public PeerToPeerSocket() throws IOException {
		this("localhost",6881);
	}
	
	public PeerToPeerSocket(String host, int offset) throws IOException {
		this.host = host;
		servers = new ArrayList<ServerSocket>();
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
		List<Object> history = new ArrayList<Object>();
		while(true) {
			List<ServerSocket> toRemove = new ArrayList<ServerSocket>();
			for(ServerSocket server: servers) {
				try {
					server.setSoTimeout(100);
					Socket s = server.accept();
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
								continue;
							}else {
								con.getInputStream().reset();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
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
						if(message.getQuery().getDesiredHost().equals(host)&&(message.getQuery().getDesiredPort()==s.getLocalPort())) {
							System.out.println(message.getMessage());
						}else {
							System.out.println("Message: ,"+message.getMessage()+"Desired host and port: "+message.getQuery().getDesiredHost()+" "+message.getQuery().getDesiredPort()+". Current host and port: "+host+" "+s.getPort()+" "+s.getLocalPort());
							for(Socket socket: activeConnections) {
								if(socket.equals(s)) {
									continue;
								}
								ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
								out.writeObject(message);
							}
						}
					}
				}catch(Exception ex) {
					continue;
				}
			}
		}
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
					return;
				}
				activeConnections.add(s);
				callback.start();
			}
		});
		thread.start();
	}
	
	public void sendMessage(String host, int port, String message) throws Exception {
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Query query = new Query(host, port);
				Message m = new Message(query, message);
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
		connect(host, port, thread2);
	}
	
	private boolean contains(List<Object> history, Object obj, Class cc) {
		for(Object o:history) {
			if(!cc.isInstance(o)) {
				continue;
			}
			if(o.equals(obj)) {
				return true;
			}
		}
		return false;
	}
}
