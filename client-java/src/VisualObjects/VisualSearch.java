package VisualObjects;

import ChatObjects.RecuestObject;
import ChatObjects.SearchResultObject;
import UserInterface.AvatarMaker;
import UserInterface.ChatWindow;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class VisualSearch extends HBox {
	public VisualSearch(SearchResultObject result) {
		super(30);
		setStyle("-fx-background-color: #EDFDFF; -fx-min-width: 1500px; -fx-alignment: center-left;");
		getChildren().add(new AvatarMaker(result.getAvatar(), 25, 1.5));
		Text names = new Text(result.getFirstName() + " " + result.getLastName());
		Text address = new Text(result.getAddress());
		Text sended = new Text("Recuest is sended");
		sended.setStyle("-fx-fill: #43f955");
		sended.setVisible(false);
		Button sendRequest = new Button("Send request");
		sendRequest.setOnMouseClicked(e -> {ChatWindow.getWriter().writeToOutput(new RecuestObject(ChatWindow.getUserID(), result.getID()));
		sendRequest.setVisible(false);
		sended.setVisible(true);
		});
		getChildren().addAll(names, address, sendRequest, sended);
	}
}
