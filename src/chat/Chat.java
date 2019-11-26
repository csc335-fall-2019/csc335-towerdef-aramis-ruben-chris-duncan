package chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3718984659768678611L;
	private ObservableList<Message> messages;
	private User thisUser;
	private Sender recipient;
	
	public Chat(User user, Sender sendTo) {
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
	
	public Sender getRecipient() {
		return recipient;
	}
	
	public User getUser() {
		return thisUser;
	}
}
