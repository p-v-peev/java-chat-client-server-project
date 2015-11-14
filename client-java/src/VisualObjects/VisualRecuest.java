package VisualObjects;

import UserInterface.AvatarMaker;
import UserInterface.ChatWindow;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ChatObjects.RecuestAcceptedObject;
import ChatObjects.RecuestDataObject;import ChatObjects.RecuestDeclinedObject;


public class VisualRecuest extends HBox {
	
	public VisualRecuest(RecuestDataObject recuest) {
		super(30);
		setStyle("-fx-background-color: #EDFDFF; -fx-min-width: 1500px; -fx-alignment: center-left;");
		getChildren().add(new AvatarMaker(recuest.getAvatar(), 25, 1.5));
		
		Text names = new Text(recuest.getFirstName() + " " + recuest.getLastName());
		Text address = new Text(recuest.getAddress());
		Button accept = new Button("Accept");
		Button decline = new Button("Decline");
		
		accept.setOnMouseClicked(e -> {ChatWindow.getWriter().writeToOutput(new RecuestAcceptedObject(ChatWindow.getUserID(), recuest.getID()));
		accept.setVisible(false);
		decline.setVisible(false);
		});
		
		decline.setOnMouseClicked(e -> {ChatWindow.getWriter().writeToOutput(new RecuestDeclinedObject(ChatWindow.getUserID(), recuest.getID()));
		accept.setVisible(false);
		decline.setVisible(false);
		});
		
		getChildren().addAll(names, address, accept, decline);
		
	}
}
