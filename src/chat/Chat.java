package chat;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chat{
	private ObservableList<Message> messages;
	private User thisUser;
	private String recipient;
	
	public Chat(User user, String sendTo) {
		messages = FXCollections.observableList(new ArrayList<Message>());
		thisUser = user;
		recipient = sendTo;
	}
	
	public void addMessage(Message m) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				messages.add(m);
			}
		});
	}
	
	public ObservableList<Message> getMessages(){
		return messages;
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	public User getUser() {
		return thisUser;
	}
}
