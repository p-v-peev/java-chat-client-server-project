package VisualObjects;

import ChatObjects.FriendObject;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VisualMessageWindow extends BorderPane{

	private int ID;
	private VBox messagesBox = null;
	private TextField messageInput = null;
	private HBox bottom = null;
	private ScrollPane messagesScroll = null;
	
	public VisualMessageWindow(FriendObject friend) {
		super();
		setStyle("-fx-max-width: 500px; -fx-background-color: #FFFFFF; -fx-border-width: 2px; -fx-border-color: #0078FF;");
		
		setVisible(false);
		
		VisualMessageWindowFriend visualFriend = new VisualMessageWindowFriend(friend);
		setTop(visualFriend);
		
		ID = friend.getID();
		
		messagesBox = new VBox(5);
		messagesScroll = new ScrollPane();
		messagesScroll.setContent(messagesBox);
		messagesScroll.setStyle("-fx-background: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 0px; -fx-background-radius: 0px; -fx-hbar-policy: never; -fx-Vbar-policy: always;");
		setCenter(messagesScroll);
		
		messageInput = new TextField();
		messageInput.setStyle("-fx-font-size: 13px; -fx-max-height: 20px; -fx-min-width:400px; -fx-background-radius: 0px;");
		
		ImageView emoticonIcon = new ImageView(new Image(getClass().getResourceAsStream("emoticonicon.png")));
		
		bottom = new HBox(5, messageInput, emoticonIcon);
		bottom.setStyle("-fx-alignment: center; -fx-background-radius: 0px;");
		setBottom(bottom);
		
	}
	
	public int getID(){
		return ID;
	}
	
	public VBox getMessagesBox()
	{
		return messagesBox;
	}
	
	public TextField getMessageInput(){
		return messageInput;
	}
}
