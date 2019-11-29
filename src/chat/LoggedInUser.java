package chat;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoggedInUser{
	private volatile ObservableList<Chat> openChats;
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
		System.out.println("Added message: "+m.getFrom().getPort());
		Chat addedChat = new Chat(user, m.getFrom());
		addMessageOnMainThread(addedChat);
		addedChat.addMessage(m);
	}
	
	public void addMessageOnMainThread(Chat c) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				openChats.add(c);
			}
		});
	}
	
	public void addOwnMessage(Message m) throws IOException, NoSuchAlgorithmException {
		System.out.println(m.getMessage());
		for(Chat c: openChats) {
			if(c.getRecipient()!=null&&c.getRecipient().getUser().matches(m.getFrom().getUser())) {
				c.addMessage(m);
				return;
			}
		}
		System.out.println("Own message: "+m.getQuery().getDesiredPort());
		Chat addedChat = new Chat(user, new Sender(m.getQuery().getDesiredHostName(), m.getQuery().getDesiredHost(), m.getQuery().getDesiredPort()));
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
