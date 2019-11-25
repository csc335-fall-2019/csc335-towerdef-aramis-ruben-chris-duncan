package chat;

import java.io.Serializable;

public class AckMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3878864033800198219L;

	private Sender to;
	
	private Sender from;
	
	public AckMessage(Sender to, Sender sender) {
		this.to = to;
		this.from = sender;
	}
}
