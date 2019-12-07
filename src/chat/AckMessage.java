package chat;

import java.io.Serializable;


public class AckMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3878864033800198219L;

	private Query to;
	
	private Query from;
	
	public AckMessage(Query to, Query from) {
		this.to = to;
		this.from = from;
	}
}
