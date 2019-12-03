package chat;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
			System.out.println("Recipient: "+c.getRecipient().getUser()+" sent from: "+m.getFrom().getUser());
			if(c.getRecipient()!=null&&c.getRecipient().getHost().equals(m.getFrom().getHost())&&c.getRecipient().getPort()==m.getFrom().getPort()) {
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
		for(Chat c: openChats) {
			System.out.println("Own message host: "+c.getRecipient().getHost()+":"+c.getRecipient().getPort()+" sent from: "+m.getQuery().getDesiredHost()+":"+m.getQuery().getDesiredPort());
		if(c.getRecipient()!=null&&c.getRecipient().getHost().equals(m.getQuery().getDesiredHost())&&c.getRecipient().getPort()==m.getQuery().getDesiredPort()) {
				c.addMessage(m);
				return;
			}
		}
		System.out.println("Own message: "+m.getQuery().getDesiredPort());
		System.out.println(m.getQuery().getDesiredHostName());
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
