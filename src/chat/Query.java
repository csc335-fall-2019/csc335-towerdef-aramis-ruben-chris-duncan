package chat;

import java.io.Serializable;

public class Query implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3975675938231942126L;

	private String desiredHost;
	
	private int desiredPort;
	
	private String desiredHostName;
	
	public Query(String desiredHostName) {
		this.desiredHostName = desiredHostName;
	}
	
	public Query(String desiredHost, int desiredPort) {
		this.desiredHost = desiredHost;
		this.desiredPort = desiredPort;
	}
	
	public void setDesiredPort(int p) {
		desiredPort = p;
	}
	
	public void setDesiredHost(String h) {
		desiredHost = h;
	}
	
	public void setDesiredHostName(String h) {
		desiredHostName = h;
	}
	
	public String getDesiredHostName() {
		return desiredHostName;
	}
	
	public String getDesiredHost() {
		return desiredHost;
	}
	
	public int getDesiredPort() {
		return desiredPort;
	}
}
