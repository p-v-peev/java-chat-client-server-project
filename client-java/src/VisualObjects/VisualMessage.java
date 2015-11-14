package VisualObjects;

import UserInterface.AvatarMaker;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class VisualMessage extends HBox {
	public VisualMessage(AvatarMaker avatar, String message) {
		super(5, avatar);
		Text messageText = new Text(message);
		getChildren().add(messageText);
		setStyle("-fx-background-color: #EDFDFF; -fx-min-width: 1500px; -fx-alignment: center-left;");
		
	}
}
