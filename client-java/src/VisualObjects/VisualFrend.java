package VisualObjects;

import UserInterface.AvatarMaker;
import ChatObjects.FriendObject;
import Handlers.VisualFriendHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class VisualFrend extends GridPane{
	
	private int ID;
	private ImageView newMessage = null;
	private ImageView messageSeen = null;
	
	public VisualFrend(FriendObject friend) {
		super();
		setHgap(5);
		//setMaxSize(200.0, 150.0);
		ID = friend.getID();
		
		setStyle("-fx-alignment: center; -fx-background-color: #1A1A1A; -fx-border-radius: 3px;");
		AvatarMaker avatar = new AvatarMaker(friend.getAvatar(), 20, 1.5);
		
		newMessage = new ImageView(new Image(getClass().getResourceAsStream("new.png")));
		newMessage.setVisible(false);
		messageSeen = new ImageView(new Image(getClass().getResourceAsStream("seen.png")));
		messageSeen.setVisible(false);
		
		Text name = new Text(friend.getFirstName() + " " + friend.getLastName());
		
		setTextStyle(name);
		
		add(avatar, 0, 0);
		add(name, 1, 0);
		add(newMessage, 2, 0);
		add(messageSeen, 2, 0);
		
		new VisualFriendHandler(ID, this);
		
	}
	
	private void setTextStyle(Node text){
		text.setStyle("-fx-fill: #EAEAEA;");
	}
	
	
	public void setNewMessageVisible(boolean visible){
		newMessage.setVisible(visible);
	}
	
	public boolean getNewMessageVisible(){
		return newMessage.isVisible();
	}
	
	public void setMessageSeen(boolean visible){
		messageSeen.setVisible(visible);
	}
	
	public int getID(){
		return ID;
	}
	
}
