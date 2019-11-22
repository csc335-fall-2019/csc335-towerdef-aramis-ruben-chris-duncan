package chat;

import java.io.Serializable;

public class UpdateConnections implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1342678731454316895L;
	
	private String oldHost;
	
	private int oldPort;
	
	public UpdateConnections(String host, int port) {
		this.oldHost = host;
		this.oldPort = port;
	}
	
	public String getHost() {
		return oldHost;
	}
	
	public int getPort() {
		return oldPort;
	}
}
