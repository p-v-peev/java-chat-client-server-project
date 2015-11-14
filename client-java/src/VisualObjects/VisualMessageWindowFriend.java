package VisualObjects;

import ChatObjects.FriendObject;
import UserInterface.AvatarMaker;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class VisualMessageWindowFriend extends HBox {
	
	public VisualMessageWindowFriend(FriendObject friend) {
		super(5);
		setStyle("-fx-alignment: center-left; -fx-background-color: #1A1A1A; -fx-border-color: #0078FF;");
		
		AvatarMaker avatarMaker = new AvatarMaker(friend.getAvatar(), 20, 1);
		
		Text name = new Text(friend.getFirstName() + " " + friend.getLastName());
		
		setEfects(name);
		
		getChildren().addAll(avatarMaker, name);
		autosize();
	}
	
	private void setEfects(Node label){
		label.setStyle("-fx-fill: #EAEAEA; -fx-font-size: 12px;");
	}
}
