package UserInterface;

import ChatObjects.AuthenticateObject;
import ChatObjects.ExitObject;
import ChatObjects.LogInObject;
import Program.Program;
import ServerConnector.Writer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogInClass extends Stage{
	
	private TextField email = null;
	private PasswordField password = null;
	private Writer logInWriter = null;
	private Text text = null;
	
	public LogInClass(Writer writer) {
		
		setResizable(false);
		setTitle("Circuit\u2122");
		getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
		
		logInWriter = writer;
		
		setOnCloseRequest(e -> {
			logInWriter.writeToOutput(new ExitObject());
			Program.killReader();
			});
		
		text = new Text("wrong email/password");
		text.setFill(Color.CRIMSON);
		text.setVisible(false);
		
		email = new TextField();
		password = new PasswordField();
		email.setPromptText("Enter your e-mail");
		password.setPromptText("Enter your password");
		
		email.setStyle("-fx-prompt-text-fill: #4F4F4F");
		password.setStyle("-fx-prompt-text-fill: #4F4F4F");
		
		email.setOnMouseClicked(e -> text.setVisible(false));
		password.setOnMouseClicked(e -> text.setVisible(false));
		
		Button logIn = new Button("Log in");
		Button signIn = new Button("Sign in");
		Label logo = new Label("Circuit\u2122", new ImageView(new Image(getClass().getResourceAsStream("logo.png"))));
		logIn.setStyle("-fx-min-width: 100px");
		signIn.setStyle("-fx-min-width: 100px");
		logo.setStyle("-fx-text-fill: #0078FF");
		
		logIn.setOnMouseClicked(e -> tryToLogIn());
		signIn.setOnMouseClicked(e -> Program.makeSignIn());
		
		GridPane controlsGrid = new GridPane();
		controlsGrid.setHgap(10);
		controlsGrid.setVgap(10);
		
		controlsGrid.add(text, 0, 0, 2, 1);
		controlsGrid.add(email, 0, 1, 2, 1);
		controlsGrid.add(password, 0, 2, 2, 1);
		controlsGrid.add(logIn, 0, 3);
		controlsGrid.add(signIn, 1	, 3);
		controlsGrid.setStyle("-fx-alignment: center");
		
		HBox header = new HBox(logo);
		header.setStyle("-fx-alignment: center-left; -fx-background-color: #1A1A1A;");
		
		BorderPane root = new BorderPane();
		root.setTop(header);
		root.setCenter(controlsGrid);
		//root.setStyle("-fx-background-color: #383838");
		
		Scene scene = new Scene(root, 700, 490);
		scene.getStylesheets().add(LogInClass.class.getResource("LogInStyle.css").toExternalForm());
		setScene(scene);
		sizeToScene();
		show();
	}
	
	private void tryToLogIn(){
		String emailtext = email.getText();
		String passwordText = password.getText();
		
		if (emailtext.length() != 0 && passwordText.length() != 0) {
			logInWriter.writeToOutput(new AuthenticateObject());
			logInWriter.writeToOutput(new LogInObject(emailtext, passwordText));
		}
	}

	public void setWrongEmailOrPasswordVisble(boolean visible){
		text.setVisible(visible);
	}
	
	public void clearFields(){
		email.clear();
		password.clear();
	}
}
