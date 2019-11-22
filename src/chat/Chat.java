package chat;

import java.util.ArrayList;
import java.util.List;

public class Chat{
	private List<Message> messages;
	private User thisUser;
	private String recipient;
	
	public Chat(User user, String sendTo) {
		messages = new ArrayList<Message>();
		thisUser = user;
		recipient = sendTo;
	}
	
	public void addMessage(Message m) {
		messages.add(m);
	}
}
