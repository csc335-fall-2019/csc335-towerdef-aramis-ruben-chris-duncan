package chat;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoggedInUser{
	private ObservableList<Chat> openChats;
	private User user;
	
	public LoggedInUser(User u) {
		openChats = FXCollections.observableList(new ArrayList<Chat>());
		user = u;
	}
	
	public void addChat(Message m) {
		for(Chat c: openChats) {
			if(c.getRecipient()!=null&&c.getRecipient().getUser().matches(m.getFrom().getUser())) {
				c.addMessage(m);
				return;
			}
		}
		Chat addedChat = new Chat(user, m.getFrom());
		addMessageOnMainThread(addedChat);
	}
	
	public void addMessageOnMainThread(Chat c) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				openChats.add(c);
			}
		});
	}
	
	public void addOwnMessage(Message m) throws IOException {
		for(Chat c: openChats) {
			if(c.getRecipient()!=null&&c.getRecipient().getUser().matches(m.getFrom().getUser())) {
				c.addMessage(m);
				return;
			}
		}
		Chat addedChat = new Chat(new User(m.getFrom().getUser(),""), m.getFrom());
		addMessageOnMainThread(addedChat);
		addedChat.addMessage(m);
	}
	
	public User getUser() {
		return user;
	}
	
	public ObservableList<Chat> getOpenChats() {
		return openChats;
	}
	
	public void removeChat(Chat c) {
		if(c==null||!openChats.contains(c)) {
			return;
		}
		openChats.remove(c);
	}
}
