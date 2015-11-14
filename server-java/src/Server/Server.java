package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	private static ServerSocket server = null;
	private static ArrayList<User> users = new ArrayList<>();
	
	public static void main(String[] args) {
		
		final int port = 12536;
		
		try {
			startServer(port);
			acceptUsers();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Немога да стартирам сървъра на този порт");
		}
	}
	
		
	private static void startServer(int port) throws IOException{
		server = new ServerSocket(port);
		System.out.println("Сървъра работи");
	}
	
	private static void acceptUsers(){
		
		while (true) {
			try {
				Socket socket = server.accept();
				User user = new User(socket);
				user.start();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Cant add this User");
			}
		}
	}
	
	//Add to users collection some user
	public synchronized static void addToUsers(User u){
		users.add(u);
	}
	
	//Remove from users collection some user
	public synchronized static void removeUser(User u){
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(u)) {
				users.remove(i);
				System.out.println("Потребителя е премахнат");
			}
		}
	}
	
	//Send message to some user
	public synchronized static void sendMessage(Object message, int ID){
		for (int i = 0; i < users.size(); i++) {
		
			if (users.get(i).getID() == ID) {
				users.get(i).writeToOutput(message);
				break;
			}
			
		}
	}
}
