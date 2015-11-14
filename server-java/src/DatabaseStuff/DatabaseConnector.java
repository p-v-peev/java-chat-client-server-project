package DatabaseStuff;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ChatObjects.EmailNotFreeObject;
import ChatObjects.FriendObject;
import ChatObjects.MessageObject;
import ChatObjects.RecuestAcceptedObject;
import ChatObjects.RecuestDataObject;
import ChatObjects.RecuestDeclinedObject;
import ChatObjects.SearchObject;
import ChatObjects.SearchResultObject;
import ChatObjects.UserFoundObject;
import Server.Server;
import Server.User;

public class DatabaseConnector {
	
	private Connection connection = null;
	
	public DatabaseConnector() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/circuit", "server", "server");
		System.out.println("Потребителя се свърза с базата данни");
	}
	
	//Close Connection with database
	public void closeConnection() throws SQLException{
		if(!connection.isClosed())
			connection.close();
	}
	
	//Method that check for such an user in database and send users info if there is such an user
	public boolean login(User reference, String email, String password) throws SQLException{
	
		PreparedStatement statement = connection.prepareStatement("SELECT ID, firstName, lastName, avatar FROM users WHERE email = ? AND password = ?;");
		
		statement.setString(1, email);
		statement.setString(2, password);
		
		ResultSet result = statement.executeQuery();
		
		if (result.next()){
			reference.writeToOutput(new UserFoundObject(result.getInt("ID"), result.getString("firstName"), result.getString("lastName"), result.getBytes("avatar")));
			reference.setID(result.getInt("ID"));
			return true;
		}
		else{
			return false;
		}
	}
	
	//Method that update info for user IP and last date 
	public void updateDataAndIP(int ID, String IP) throws SQLException{
		
			PreparedStatement statement = connection.prepareStatement("UPDATE users SET lastLogin = CURDATE(), IP = ? WHERE ID = ?;");
			
			statement.setString(1, IP);
			statement.setInt(2, ID);
			statement.executeUpdate();
		}
	
	//Method that sends all of the friend for this user
	public void sendFriends(User reference, int ID) throws SQLException{
		
		PreparedStatement statement = connection.prepareStatement("SELECT u.ID, u.firstname, u.lastname, u.avatar FROM users u WHERE u.ID IN(SELECT friendID FROM friends f WHERE f.userID = ?);");
	
		statement.setInt(1, ID);
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			reference.writeToOutput(new FriendObject(result.getInt("ID"), result.getString("firstName"), result.getString("lastName"), result.getBytes("avatar")));
		}
	}
	
	//Method that add to undelivered messages some message
	public void addToUndelivered(MessageObject message) throws SQLException{
		PreparedStatement statement = connection.prepareStatement("INSERT INTO undelivered(userID, message, senderID) VALUES(?, ?, ?);");
		
		statement.setInt(1, message.getrecipientID());
		statement.setString(2, message.getMessage());
		statement.setInt(3, message.getSenderID());
		
		statement.executeUpdate();
	}
	
	//Method that sends undelivered messages to this user
	public void sendMessages(User reference, int ID) throws SQLException{
		
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM undelivered WHERE userID = ?;");
		
		statement.setInt(1, ID);
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			reference.writeToOutput(new MessageObject(result.getInt("userID"), result.getString("message"), result.getInt("senderID")));
		}
		
	}
	
	//Method that sign in some user
	public void signIn(User reference ,String email, String password, String firstName, String lastName, String phoneNumber, String address, byte[] avatar, String IP) throws SQLException, IOException {
		
		PreparedStatement statement = connection.prepareStatement("SELECT ID FROM users WHERE email = ?;");
		
		statement.setString(1, email);
		
		ResultSet result = statement.executeQuery();
		
		if (result.next()){
			reference.writeToOutput(new EmailNotFreeObject());
			return;
		}
		
		statement = connection.prepareStatement("INSERT INTO users(email, password, firstName, lastName, phoneNumber, address, avatar, lastLogin, IP)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, CURDATE(), ?);");
		
		statement.setString(1, email);
		statement.setString(2, password);
		statement.setString(3, firstName);
		statement.setString(4, lastName);
		statement.setString(5, phoneNumber);
		statement.setString(6, address);
		statement.setBytes(7, avatar);
		statement.setString(8, IP);
		
		statement.executeUpdate();
		
		statement = connection.prepareStatement("SELECT ID, firstName, lastName, avatar FROM users WHERE email = ? AND password = ?;");
		
		statement.setString(1, email);
		statement.setString(2, password);
		
		result = statement.executeQuery();
		
		if (result.next()){
			reference.writeToOutput(new UserFoundObject(result.getInt("ID"), result.getString("firstName"), result.getString("lastName"), result.getBytes("avatar")));
			reference.setID(result.getInt("ID"));
		}
	}
	
	public void deleteMessageFromDatabase(int userID, int senderID) throws SQLException{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM undelivered WHERE userID = ? AND senderID = ?;");
		
		statement.setInt(1, userID);
		statement.setInt(2, senderID);
	
		statement.executeUpdate();
	}
	
	public void searchUsers(User reference, SearchObject search) throws SQLException{
		
		PreparedStatement statement = null;
		String firstName = search.getFirstName();
		String lastName = search.getLastName();
		String address = search.getAddress();
		
		if (firstName.length() != 0 && lastName.length() == 0 && address.length() == 0) {
			statement = connection.prepareStatement("SELECT ID, firstName, lastName, avatar, address FROM users WHERE firstName = ?;");
			statement.setString(1, firstName);
		}else if (firstName.length() == 0 && lastName.length() != 0 && address.length() == 0) {
			statement = connection.prepareStatement("SELECT ID, firstName, lastName, avatar, address FROM users WHERE lastName = ?;");
			statement.setString(1, lastName);
		}else if (firstName.length() == 0 && lastName.length() == 0 && address.length() != 0) {
			statement = connection.prepareStatement("SELECT ID, firstName, lastName, avatar, address FROM users WHERE address = ?;");
			statement.setString(1, address);
		}else if (firstName.length() != 0 && lastName.length() != 0 && address.length() == 0) {
			statement = connection.prepareStatement("SELECT ID, firstName, lastName, avatar, address FROM users WHERE firstName = ? AND lastName = ?;");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
		}else if (firstName.length() == 0 && lastName.length() != 0 && address.length() != 0) {
			statement = connection.prepareStatement("SELECT ID, firstName, lastName, avatar, address FROM users WHERE lastName = ? AND address = ?;");
			statement.setString(1, lastName);
			statement.setString(2, address);
		}else if (firstName.length() != 0 && lastName.length() == 0 && address.length() != 0) {
			statement = connection.prepareStatement("SELECT ID, firstName, lastName, avatar, address FROM users WHERE firstName = ? AND address = ?;");
			statement.setString(1, firstName);
			statement.setString(2, address);
		}else if (firstName.length() != 0 && lastName.length() != 0 && address.length() != 0) {
			statement = connection.prepareStatement("SELECT ID, firstName, lastName, avatar, address FROM users WHERE firstName = ? AND lastName = ? AND address = ?;");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, address);
		}
		
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			reference.writeToOutput(new SearchResultObject(result.getInt("ID"), result.getString("firstName"), result.getString("lastName"), result.getString("address"), result.getBytes("avatar")));
		}
	}
	
	public void addToRecuests(int recipientID, int senderID) throws SQLException{
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO requests(fromID, toID) VALUES(?, ?);");
		
		statement.setInt(1, senderID);
		statement.setInt(2, recipientID);
		statement.executeUpdate();
		
		statement = connection.prepareStatement("SELECT ID, firstName, lastName, address, avatar FROM users WHERE ID = ? ");
		statement.setInt(1, senderID);
		ResultSet result = statement.executeQuery();
		
		if (result.next()) {
			Server.sendMessage(new RecuestDataObject(senderID, result.getString("firstName"), result.getString("lastName"), result.getString("address"), result.getBytes("avatar")), recipientID);
		}
	}	
	
	public void sendRecuests(User reference, int userID) throws SQLException{
		
		PreparedStatement statement = connection.prepareStatement("SELECT ID, firstName, lastName, address, avatar FROM users WHERE ID IN(SELECT fromID FROM  requests WHERE toID = ?);");
		statement.setInt(1, userID);
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			reference.writeToOutput(new RecuestDataObject(result.getInt("ID"), result.getString("firstName"), result.getString("lastName"), result.getString("address"), result.getBytes("avatar")));
		}
	}
	
	public void addToFriends(User reference, RecuestAcceptedObject recuest) throws SQLException{
		PreparedStatement statement = connection.prepareStatement("INSERT INTO friends(userID, friendID) VALUES(?, ?);");
		statement.setInt(1,recuest.getUser());
		statement.setInt(2, recuest.getSender());
		statement.executeUpdate();
		statement.setInt(1,recuest.getSender());
		statement.setInt(2, recuest.getUser());
		statement.executeUpdate();
		
		statement = connection.prepareStatement("DELETE FROM requests WHERE fromID = ? AND toID = ?");
		statement.setInt(1, recuest.getSender());
		statement.setInt(2, recuest.getUser());
		statement.executeUpdate();
		
		statement = connection.prepareStatement("SELECT ID, firstName, lastname, avatar FROM users WHERE ID = ?");
		statement.setInt(1, recuest.getSender());
		
		ResultSet result = statement.executeQuery();
		if (result.next()) {
			reference.writeToOutput(new FriendObject(result.getInt("ID"), result.getString("firstName"), result.getString("lastName"), result.getBytes("avatar")));
		}
		
		statement = connection.prepareStatement("SELECT ID, firstName, lastname, avatar FROM users WHERE ID = ?");
		statement.setInt(1, recuest.getUser());
		
		result = statement.executeQuery();
		if (result.next()) {
			Server.sendMessage(new FriendObject(result.getInt("ID"), result.getString("firstName"), result.getString("lastName"), result.getBytes("avatar")), recuest.getSender());
		}
	}
	
	public void deleteRecuest(RecuestDeclinedObject decline) throws SQLException{
		PreparedStatement statement = connection.prepareStatement("DELETE FROM requests WHERE fromID = ? AND toID = ?");
		statement.setInt(1, decline.getSender());
		statement.setInt(2, decline.getUser());
		statement.executeUpdate();
	}
}

