package chat;

/**
 * Stores the current chat objects.
 * @author Aramis
 *
 */
public class ChatViewModel {
	private String selectedUser;
	
	public ChatViewModel() {
		selectedUser = "";
	}
	
	public String getSelectedUser() {
		return selectedUser;
	}
	
	public void setSelectedUser(String u) {
		selectedUser = u;
	}
}
