package chat;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Sender implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -937615790253733296L;

	private String username;
	
	private String host;
	
	private int port;
	
	public Sender(String username) throws UnknownHostException {
		this(username, InetAddress.getLocalHost().getHostAddress().toString(),0);
	}
	
	public Sender(String username, String host, int port) {
		this.username = username;
		this.host = host;
		this.port = port;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null||!(o instanceof Sender)) {
			return false;
		}
		Sender s = (Sender)o;
		if(s.getHost().equals(getHost())&&s.getPort()==getPort()&&s.getUser().equals(getUser())) {
			return true;
		}
		return false;
	}
	
	public String getUser() {
		return username==null?"":username;
	}
	
	public String getHost() {
		return host==null?"":host;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setUser(String user) {
		this.username = user;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
}
