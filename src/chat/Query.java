package chat;

import java.io.Serializable;

public class Query implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3975675938231942126L;

	private String desiredHost;
	
	private int desiredPort;
	
	public Query(String desiredHost, int desiredPort) {
		this.desiredHost = desiredHost;
		this.desiredPort = desiredPort;
	}
	
	public String getDesiredHost() {
		return desiredHost;
	}
	
	public int getDesiredPort() {
		return desiredPort;
	}
}
