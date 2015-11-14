package ServerConnector;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import ChatObjects.EmailNotFreeObject;
import ChatObjects.ExitObject;
import ChatObjects.FriendObject;
import ChatObjects.MessageObject;
import ChatObjects.MessageSeenObject;
import ChatObjects.RecuestDataObject;
import ChatObjects.SearchResultObject;
import ChatObjects.UserFoundObject;
import ChatObjects.UserNotFoundObject;
import Handlers.TextInputHandler;
import Program.Program;
import UserInterface.ChatWindow;
import UserInterface.LogInClass;
import VisualObjects.Header;
import VisualObjects.VisualFrend;
import VisualObjects.VisualMessageWindow;
import VisualObjects.VisualSearch;
import VisualObjects.VisualUser;

public class ReadingThread extends Thread{
	
	private ObjectInputStream input = null;
	private Socket userConnection = null;
	private ChatWindow window = null;
	private LogInClass logInWindow = null;
	
	public ReadingThread(Socket connection, ChatWindow reference, LogInClass logIn) throws IOException {
		userConnection = connection;
		input = new ObjectInputStream(userConnection.getInputStream());
		window = reference;
		logInWindow = logIn;
	}
	
	public void run(){
		Object inputObject = null;
		
		while (!((inputObject = readFromInput()) instanceof ExitObject)) {
			
			if(inputObject == null)
				break;
			else if (inputObject instanceof MessageObject) {
				MessageObject message =  (MessageObject) inputObject;
				setMessage(message);
			}else if (inputObject instanceof UserFoundObject) {
				UserFoundObject user = (UserFoundObject) inputObject;
				setHeader(user);
			}else if (inputObject instanceof FriendObject) {
				FriendObject friend = (FriendObject) inputObject;
				addFriends(friend);
			}else if (inputObject instanceof MessageSeenObject) {
				MessageSeenObject messageSeen = (MessageSeenObject) inputObject;
				setMessageSeen(messageSeen.getSenderID(), messageSeen.getRecipientID());
			}else if (inputObject instanceof UserNotFoundObject) {
				setWrongEmailOrPass();
			}else if (inputObject instanceof EmailNotFreeObject) {
				Program.clearSignIn();
			}else if (inputObject instanceof SearchResultObject) {
				SearchResultObject result = (SearchResultObject) inputObject;
				setSearchResult(result);
			}
			else if (inputObject instanceof RecuestDataObject) {
				RecuestDataObject recuestData = (RecuestDataObject) inputObject;
				setRecuest(recuestData);
			}
		}
	}
	
	private Object readFromInput(){
		try {
			return input.readObject();
		}catch (EOFException e){
			return null;
		}catch (SocketException e){
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void closeInput() throws IOException{
		if(input != null)
			input.close();
	}
	
	private void setHeader(UserFoundObject user){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ChatWindow.setUserID(user.getID());
				window.setHeader(new Header(window, new VisualUser(user)));
				window.setUser(user);
				
				if (logInWindow != null) {
					logInWindow.hide();
					logInWindow = null;
				}
				Program.hideSignIn();
					window.show();
			}
		});
	}
	
	private void addFriends(FriendObject friend){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				VisualFrend visualFriend = new VisualFrend(friend);
				VisualMessageWindow visualMessage = new VisualMessageWindow(friend);
				ChatWindow.getNotVisualFriends().add(friend);
				ChatWindow.getFriendList().add(visualFriend);
				window.getFriends().getChildren().add(visualFriend);
				
				ChatWindow.getVisualMessagePane().add(visualMessage);
				
				window.addBorder();
				window.getCenterPane().getChildren().add(visualMessage);
				window.getCenterPane();
				AnchorPane.setTopAnchor(visualMessage, 30.0);
				window.getCenterPane();
				AnchorPane.setLeftAnchor(visualMessage, 30.0);
				window.getCenterPane();
				AnchorPane.setBottomAnchor(visualMessage, 30.0);
				window.getCenterPane();
				AnchorPane.setRightAnchor(visualMessage, 30.0);
				new TextInputHandler(window, ChatWindow.getWriter(),  visualMessage.getMessageInput(),  visualMessage.getID());
			}
		});
	}
	
	private void setMessage(MessageObject message){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				window.setMessage(message);
			}
		});
	}
	
	private void setMessageSeen(int senderID, int recipientId){
		Platform.runLater(new Runnable() {
			public void run() {
				window.setSeen(senderID, recipientId);
			}
		});
	}
	
	private void setWrongEmailOrPass(){
		Platform.runLater(new Runnable() {
			public void run() {
				logInWindow.setWrongEmailOrPasswordVisble(true);
				logInWindow.clearFields();
			}
		});
	}
	
	private void setSearchResult(SearchResultObject result){
		if (ChatWindow.getSearch() != null) {
			Platform.runLater(new Runnable() {
				public void run() {
					ChatWindow.getSearch().getResults().getChildren().add(new VisualSearch(result));
				}
			});
		}
	}
	
	private void setRecuest(RecuestDataObject recuestData){
		Platform.runLater(new Runnable() {
			
			public void run() {
				window.setRecuest(recuestData);
				window.getHeader().setNotifyVisible(true);
			}
		});
	}
}
