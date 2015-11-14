package UserInterface;

import java.util.ArrayList;

import ChatObjects.ExitObject;
import ChatObjects.FriendObject;
import ChatObjects.MessageObject;
import ChatObjects.MessageSeenObject;
import ChatObjects.RecuestDataObject;
import ChatObjects.UserFoundObject;
import Program.Program;
import ServerConnector.Writer;
import VisualObjects.Header;
import VisualObjects.VisualFrend;
import VisualObjects.VisualMessage;
import VisualObjects.VisualMessageWindow;
import VisualObjects.VisualRecuest;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ChatWindow extends Stage {
	
	private static int userID;
	private static ArrayList<FriendObject> friendsList = null;
	private static ArrayList<VisualFrend> visualFrinds  = null;
	private static ArrayList<VisualMessageWindow> visualMessagePane = null;
	private UserFoundObject user = null;
	private static Writer userWriter = null;
	private BorderPane root = null;
	private AnchorPane center = null;
	private ScrollPane scroll = null;
	private VBox friends = null;
	private Scene scene = null;
	private static SearchWindow search = null;
	private static VisualMessageWindow currentMessageMindow = null;
	private Header header = null;
	private RecuestWindow recuest = null;
	
	public ChatWindow(Writer writer) {
		
		setTitle("Circuit\u2122");
		getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
		setMinWidth(650);
		setMinHeight(600);
		setMaxWidth(850);
		setMaxHeight(650);
		
		userWriter = writer;
		
		setOnCloseRequest(e -> {
			userWriter.writeToOutput(new ExitObject());
			Program.killReader();
			});
		
		visualFrinds = new ArrayList<VisualFrend>();
		visualMessagePane = new ArrayList<VisualMessageWindow>();
		friendsList = new ArrayList<FriendObject>();
		
		center = new AnchorPane();
		center.setId("center");
		center.setStyle(" -fx-alignment: center;");
		root = new BorderPane(center);
		friends = new VBox(0);
		friends.setStyle("-fx-background-color: #000000;");
		friends.setMinWidth(150);
		addBorder();
		scroll = new ScrollPane();
		scroll.setContent(friends);
		scroll.autosize();
		scroll.setStyle("-fx-background: #1A1A1A; -fx-border-color: #1A1A1A; -fx-hbar-policy: never; -fx-min-width: 150px;");
		root.setLeft(scroll);
		root.getStylesheets().add(ChatWindow.class.getResource("style.css").toExternalForm());
		scene = new Scene(root); 
		setScene(scene);
	}
	
	public BorderPane getRoot(){
		return root;
	}
	
	public AnchorPane getCenterPane(){
		return center;
	}
	
	public VBox getFriends(){
		return friends;
	}
	
	public void addBorder(){
		friends.getChildren().add(new Rectangle(friends.getWidth(), 5));
	}
	
	public static ArrayList<VisualFrend> getFriendList(){
		return visualFrinds;
	}
	
	public static ArrayList<VisualMessageWindow> getVisualMessagePane(){
		return visualMessagePane;
	}
	
	public static ArrayList<FriendObject> getNotVisualFriends(){
		return friendsList;
	}

	public static int getUserID() {
		return userID;
	}

	public static void setUserID(int userID) {
		ChatWindow.userID = userID;
	}
	
	public void setUser(UserFoundObject user){
		this.user = user;
	}
	
	public UserFoundObject getUser(){
		return user;
	}
	
	public void setMessage(MessageObject message){
		for (int i = 0; i < visualFrinds.size(); i++) {
			if (visualFrinds.get(i).getID() == message.getSenderID()) {
				VisualFrend friend = visualFrinds.get(i);
				VisualMessageWindow messageWindow = visualMessagePane.get(i);
				
				if (currentMessageMindow == null || currentMessageMindow.getID() != message.getSenderID()) {
					friend.setNewMessageVisible(true);
				}
				else if ( currentMessageMindow.getID() == message.getSenderID()) {
					userWriter.writeToOutput(new MessageSeenObject(userID, visualFrinds.get(i).getID()));
				}
				
				friend.setMessageSeen(false);
				messageWindow.getMessagesBox().getChildren().add(new VisualMessage(new AvatarMaker(friendsList.get(i).getAvatar(), 10, 1), message.getMessage()));
				break;
			}
		}
	}
	
	public void setMyMessage(MessageObject message){
		for (int i = 0; i < visualFrinds.size(); i++) {
			
			if (visualFrinds.get(i).getID() == message.getrecipientID()) {
				VisualMessageWindow messageWindow = visualMessagePane.get(i);
				visualFrinds.get(i).setMessageSeen(false);
				messageWindow.getMessagesBox().getChildren().add(new VisualMessage(new AvatarMaker(user.getAvatar(), 10, 1), message.getMessage()));
				break;
			}
		}
	}

	public static void showMessage(int ID) {
		
		for (int i = 0; i < visualFrinds.size(); i++) {
			
			if (visualFrinds.get(i).getID() == ID) {
				VisualFrend friend = visualFrinds.get(i);
				VisualMessageWindow messageWindow = visualMessagePane.get(i);
				
				currentMessageMindow = messageWindow;
				
				if (friend.getNewMessageVisible()) {
					userWriter.writeToOutput(new MessageSeenObject(userID, visualFrinds.get(i).getID()));
				}
				
				friend.setNewMessageVisible(false);
				friend.setMessageSeen(false);
				messageWindow.setVisible(true);
				messageWindow.toFront();
				
				break;
			}
		}
	}
	
	public  static Writer getWriter(){
		return userWriter;
	}
	
	public void setSeen(int senderID, int recipientID){
		for (int i = 0; i < visualFrinds.size(); i++) {
			if (visualFrinds.get(i).getID() == senderID){
				VisualFrend friend = visualFrinds.get(i);
				
				friend.setNewMessageVisible(false);
				friend.setMessageSeen(true);
				
				
			}
		}
	}

	public static SearchWindow getSearch() {
		return search;
	}

	public static void setSearch(SearchWindow search) {
		ChatWindow.search = search;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
		root.setTop(header);
	}
	
	public RecuestWindow getRecuest(){
		return recuest;
	}
	public void setRecuest(RecuestDataObject recuestData){
		
		if (recuest == null) {
			recuest = new RecuestWindow();
		}
		
		recuest.getRecuests().getChildren().add(new VisualRecuest(recuestData));
	}
	
}