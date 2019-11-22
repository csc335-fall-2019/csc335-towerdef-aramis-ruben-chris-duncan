package chat;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;

	private String message;
	
	private Query query;
	
	public Message(Query query, String message) {
		this.query = query;
		this.message = message;
	}
	
	public Query getQuery() {
		return query;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean equals(Object o) {
		if((o==null)||!(o instanceof Message)) {
			return false;
		}
		Message m = (Message)o;
		if(m.getMessage().equals(getMessage())) {
			return true;
		}
		return false;
	}
}
