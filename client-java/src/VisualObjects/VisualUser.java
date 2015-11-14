package VisualObjects;

import ChatObjects.UserFoundObject;
import UserInterface.AvatarMaker;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class VisualUser extends HBox {

	public VisualUser(UserFoundObject user) {
		super(5, new AvatarMaker(user.getAvatar(), 30, 2));
		setStyle("-fx-alignment: center-left;");
		
		Text name = new Text(user.getFirstName() + " " + user.getLastName());
		
		setEfects(name);
		
		getChildren().addAll(name);
		autosize();
	}
	
	private void setEfects(Node text){
		text.setStyle("-fx-fill: #0078FF");
	}
}
