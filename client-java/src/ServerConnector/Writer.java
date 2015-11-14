package ServerConnector;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Writer {
	
	private Socket userConnection = null;
	private ObjectOutputStream output = null;
	
	public Writer(Socket connection) throws IOException {
		userConnection = connection;
		output = new ObjectOutputStream(userConnection.getOutputStream());
	}
	
	public void writeToOutput(Object outputObject){
		try {
			output.writeObject(outputObject);
		} catch (IOException e) {
			System.out.println("Съобщението не е изпратено");
			e.printStackTrace();
		}
	}
	
	public void closeOutput() throws IOException{
		if(output != null)
			output.close();
	}
}
