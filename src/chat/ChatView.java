package chat;

import java.awt.Paint;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ChatView{
	private static Stage MESSAGE_RECEIVED; 
	private static LoggedInUser user;
	
	public Stage create(int portNum) throws IOException {
		Stage primaryStage = new Stage();
		// TODO Auto-generated method stub
		MESSAGE_RECEIVED = new Stage();
		BorderPane textPane = new BorderPane();
		Text label = new Text();
		label.setText("Received");
		textPane.getChildren().add(label);
		MESSAGE_RECEIVED.setScene(new Scene(textPane, 100,100));
		MESSAGE_RECEIVED.initModality(Modality.WINDOW_MODAL);
		MESSAGE_RECEIVED.initOwner(primaryStage);
		PeerToPeerSocket p2p = new PeerToPeerSocket(portNum);
		Thread thread = new Thread(p2p);
		thread.start();
		BorderPane pane = createChatBottom(p2p);
		primaryStage.setScene(new Scene(pane, 400, 400));
		primaryStage.setTitle(portNum+"");
		return primaryStage;
	}
	
	private BorderPane createChatBottom(PeerToPeerSocket p2p) {
		BorderPane box = new BorderPane();
		HBox hbox = new HBox();
		ChatViewModel c = new ChatViewModel();
		TextField text = new TextField();
		TextField address = new TextField();
		TextField port = new TextField();
		TextField name = new TextField();
		VBox input = createMessageStack(text, name, address, port);
		Button send = new Button();
		send.setText("Send");
		send.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					if(text.getText().length()==0) {
						e.consume();
						return;
					}
					if(c.getSelectedUser().length()>0||name.getText().length()>0) {
						p2p.sendMessage(c.getSelectedUser().length()>0?c.getSelectedUser():name.getText(), text.getText());
					}else {
						p2p.sendMessage(address.getText(),Integer.parseInt(port.getText()), text.getText());
					}
					if(box.getCenter()!=null) {
						return;
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Button login = new Button();
		login.setText("Login");
		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					if(p2p.login("Tester","Fuck you")) {
						hbox.getChildren().remove(login);
						hbox.getChildren().add(input);
						hbox.getChildren().add(send);
						user = p2p.getUser();

						ListView<Chat> view = new ListView<Chat>();
						ListView<Message> messages = new ListView<Message>();
						messages.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
			                @Override 
			                public ListCell<Message> call(ListView<Message> list) {
			                    return new MessageCell();
			                }
			            });
						view.setItems(p2p.getUser().getOpenChats());
						view.setCellFactory(new Callback<ListView<Chat>, ListCell<Chat>>() {
			                @Override 
			                public ListCell<Chat> call(ListView<Chat> list) {
			                    return new ChatCell(address, port);
			                }
			            });
						view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Chat>() {

							@Override
							public void changed(ObservableValue arg0, Chat old, Chat newVal) {
								// TODO Auto-generated method stub
								if(newVal==null) {
									return;
								}
								messages.setItems(newVal.getMessages());
								box.setCenter(messages);
								//c.setSelectedUser("localhost");
								//System.out.println(newVal.getUser().getUsername());
								HBox backButton = createBackButton(box, view, input, send);
								box.setTop(backButton);
								HBox h = new HBox();
								h.getChildren().add(text);
								h.getChildren().add(send);
								box.setBottom(h);
							}
						});

						box.setCenter(view);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		hbox.getChildren().add(login);
		box.setBottom(hbox);
		box.setPickOnBounds(false);
		return box;
	}
	
	private HBox createBackButton(BorderPane pane, ListView<Chat> view, VBox input, Button connect) {
		// TODO Auto-generated method stub
		HBox box = new HBox();
		Button goBack = new Button();
		goBack.setText("<-");
		goBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				view.getSelectionModel().clearSelection();
				pane.setCenter(view);
				pane.setTop(null);
				HBox h = new HBox();
				h.getChildren().addAll(input, connect);
				pane.setBottom(h);
			}
		});
		box.getChildren().add(goBack);
		return box;
	}

	private VBox createMessageStack(TextField text, TextField name, TextField address, TextField port) {
		VBox box = new VBox();
		HBox h = new HBox();
		h.getChildren().add(name);
		Button b = new Button();
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(h.getChildren().contains(name)) {
					name.setText("");
					h.getChildren().remove(name);
					h.getChildren().add(0,port);
					h.getChildren().add(0,address);
				}else {
					h.getChildren().add(0,name);
					h.getChildren().remove(address);
					h.getChildren().remove(port);
					address.setText("");
					port.setText("");
				}
			}
		});
		h.getChildren().add(b);
		box.getChildren().add(h);
		box.getChildren().add(text);
		return box;
		
	}

	
	static class ChatCell extends ListCell<Chat> {
		private TextField address;
		private TextField port;
		
		public ChatCell(TextField address, TextField port) {
			this.address = address;
			this.port = port;
		}
        @Override
        public void updateItem(Chat item, boolean empty) {
            super.updateItem(item, empty);
            Text text = new Text();
            if (item != null) {
                address.setText(item.getRecipient()==null?"":item.getRecipient().getHost());
                port.setText(item.getRecipient()==null?"":item.getRecipient().getPort()+"");
                text.setText(item.getRecipient().getUser());
                setGraphic(text);
            }
        }
    }
	
	class MessageCell extends ListCell<Message>{
		@Override
		public void updateItem(Message item, boolean empty) {
			super.updateItem(item, empty);
			VBox box = new VBox();
			HBox text = new HBox();
			text.setAlignment(Pos.BOTTOM_RIGHT);
			Text textBox = new Text();
			if(item!=null) {
				if(item.getFrom().getUser().equals(user.getUser().getUsername())) {
					text.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
				}
				textBox.setText(item.getMessage());
				text.getChildren().add(textBox);
				box.getChildren().addAll(text);
				setGraphic(box);
			}
		}
	}
}
