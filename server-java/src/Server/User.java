package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

import ChatObjects.AuthenticateObject;
import ChatObjects.EmailNotFreeObject;
import ChatObjects.ExitObject;
import ChatObjects.LogInObject;
import ChatObjects.MessageNotSavedObject;
import ChatObjects.MessageObject;
import ChatObjects.MessageSeenObject;
import ChatObjects.RecuestAcceptedObject;
import ChatObjects.RecuestDeclinedObject;
import ChatObjects.RecuestObject;
import ChatObjects.SearchObject;
import ChatObjects.SignInObject;
import ChatObjects.UserNotFoundObject;
import DatabaseStuff.DatabaseConnector;

public class User extends Thread {
	
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	private Socket userConnection = null;
	private DatabaseConnector connector = null;
	private int ID;
	
	public User(Socket connection) throws IOException{
		userConnection = connection;
		try {
			setStreams();
			connector = new DatabaseConnector();
		} catch (IOException e) {
			e.printStackTrace();
			userConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//Изпрати обект че нещо не е наред сбазата данни
			userConnection.close();
		}
	}
	
	//Set input and output streams for this user
	private void setStreams() throws IOException{
		input = new ObjectInputStream(userConnection.getInputStream());
		output = new ObjectOutputStream(userConnection.getOutputStream());
	}
	
	//Run method for this user
	public void run(){
		
		Object inputObject = null;
		
		if ((inputObject = readFromInput()) instanceof AuthenticateObject) {
			
			AuthenticateObject authentication = (AuthenticateObject) inputObject;
		
			if (authentication.getControlValue() == 3412412141L) {
				
				while (!((inputObject = readFromInput()) instanceof ExitObject)) {
					if (inputObject == null)
						continue;
					else if (inputObject instanceof MessageObject) {
						
						MessageObject message = (MessageObject) inputObject;
						Server.sendMessage(message, message.getrecipientID());
						
						try {
								connector.addToUndelivered(message);
							} catch (SQLException e) {
								e.printStackTrace();
								writeToOutput(new MessageNotSavedObject());
							}
						
					}else if (inputObject instanceof MessageSeenObject) {
						MessageSeenObject messageSeen = (MessageSeenObject) inputObject;
						
						try {
							connector.deleteMessageFromDatabase(messageSeen.getSenderID(), messageSeen.getRecipientID());
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
						Server.sendMessage(messageSeen, messageSeen.getRecipientID());
						
					}else if (inputObject instanceof LogInObject) {
						
						LogInObject login = (LogInObject) inputObject;
						
						try {
							if (connector.login(this, login.getEmail(), login.getPassword())){ //This LogIn object send to user user's data if there is such an user. 
								connector.updateDataAndIP(ID, userConnection.getInetAddress().getHostAddress());
								Server.addToUsers(this);
								connector.sendFriends(this, ID);//Send list of friends to this user
								connector.sendMessages(this, ID);//send undelivered messages to this user
								connector.sendRecuests(this, ID);//send friend recuests
							}else 
								writeToOutput(new UserNotFoundObject());
						} catch (SQLException e) {
							e.printStackTrace();
							writeToOutput(new UserNotFoundObject());
						}
					}else if (inputObject instanceof SignInObject) {
						SignInObject signin = (SignInObject) inputObject;
						
						try {
							connector.signIn(this, signin.getEmail(), signin.getPassword(), signin.getFirstName(), signin.getLastName(), signin.getPhoneNumber(), signin.getAddress(), signin.getAvatar(), userConnection.getInetAddress().getHostAddress());
							Server.addToUsers(this);
						} catch (SQLException e) {
							e.printStackTrace();
							writeToOutput(new EmailNotFreeObject());
						} catch (IOException e) {
							e.printStackTrace();
							writeToOutput(new EmailNotFreeObject());
						}
					}else if (inputObject instanceof SearchObject) {
						SearchObject search = (SearchObject) inputObject;
						try {
							connector.searchUsers(this, search);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}else if (inputObject instanceof RecuestObject) {
						RecuestObject recuest = (RecuestObject) inputObject;
						try {
							connector.addToRecuests(recuest.getRecipientID(), recuest.getSenderID());
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}else if (inputObject instanceof RecuestAcceptedObject) {
						RecuestAcceptedObject recuest = (RecuestAcceptedObject) inputObject;
						try {
							connector.addToFriends(this, recuest);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if (inputObject instanceof RecuestDeclinedObject) {
						RecuestDeclinedObject decline = (RecuestDeclinedObject) inputObject;
						try {
							connector.deleteRecuest(decline);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}// While loop
			}//Inner IF
		} //Outer IF
	
		System.out.println("Потребителя се дисконектна");
		Server.removeUser(this);
		closeStreams();
	}
	
	
	//Method that reads from input
	private Object readFromInput(){
		try {
			return input.readObject();
		}catch (SocketException e){
			return null;
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			Server.removeUser(this);
			closeStreams();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Възникна проблем при четенето на съобщението");
			return null;
		}
	}
	
	//Method that write to output stream
		public void writeToOutput(Object object){
			try {
				output.writeObject(object);
				output.flush();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Съобщението не е доставено");
			}
		}
	
	//Method that close all streams for this user
		private void closeStreams(){
			if(input != null)
				try {
					input.close();
					if(output != null)
						output.close();
					if(userConnection != null)
						userConnection.close();
					
					connector.closeConnection();
				
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Немога да затворя потоците");
					return;
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Немога да затворя връзката с базата данни");
					e.printStackTrace();
				}
		}
		
		//Get user's id
		public int getID(){
			return ID;
		}
		
		//Set user's id
		public void setID(int userID){
			ID = userID;
		}
		
}
