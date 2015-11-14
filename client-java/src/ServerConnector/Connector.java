package ServerConnector;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector {
	
	private Socket connection = null;
	
	public Connector(String host, int port) throws UnknownHostException, IOException {
		connection = new Socket(host, port);
	}
	
	public Socket getConnection(){
		return connection;
	}
	
	public void closeConnection() throws IOException{
		if (connection != null)
			connection.close();
	}
}
