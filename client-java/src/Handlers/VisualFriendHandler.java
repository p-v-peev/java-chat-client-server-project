package Handlers;

import UserInterface.ChatWindow;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class VisualFriendHandler {
	public VisualFriendHandler(int ID, Node visualFriend) {
		
		visualFriend.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				ChatWindow.showMessage(ID);
			}
		});
		
	}
}
