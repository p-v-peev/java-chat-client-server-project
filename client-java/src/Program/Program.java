package Program;

import java.io.IOException;
import java.net.UnknownHostException;

import ServerConnector.Connector;
import ServerConnector.ReadingThread;
import ServerConnector.Writer;
import UserInterface.ChatWindow;
import UserInterface.LogInClass;
import UserInterface.SignInClass;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

	private static ReadingThread reader = null;
	private static Writer writer = null;
	private static ChatWindow window = null;
	private static LogInClass logIn = null;
	private static SignInClass signIn = null;
	
	
	@Override
	public void start(Stage primaryStage) {
		Connector connector;
		try {
			connector = new Connector("localhost", 12536);
			writer = new Writer(connector.getConnection());
			window = new ChatWindow(writer);
			logIn = new LogInClass(writer);
			signIn = new SignInClass(writer);
			reader = new ReadingThread(connector.getConnection(), window, logIn);
			reader.start();
			//Cleaner cleaner = new Cleaner();
			//cleaner.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void killReader(){
		reader.interrupt();
	}
	
	public static void makeSignIn(){
		logIn.hide();
		logIn = null;
		
		signIn = new SignInClass(writer);
		signIn.show();
	}
	
	public static void hideSignIn(){
		if (signIn != null) 
			signIn.hide();
	}
	
	public static void clearSignIn(){
		signIn.setEmailNotFree(true);
		signIn.clearFields();
	}
}
