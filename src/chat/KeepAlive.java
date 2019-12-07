package chat;

import java.io.Serializable;

/**
 * Sends a message that the connection is still alive to prevent blocking by the ObjectInputStream.
 * @author Aramis
 *
 */
public class KeepAlive implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5342285374111859987L;
	
	
}
