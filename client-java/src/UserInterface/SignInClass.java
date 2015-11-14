package UserInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ChatObjects.AuthenticateObject;
import ChatObjects.ExitObject;
import ChatObjects.SignInObject;
import Program.Program;
import ServerConnector.Writer;

public class SignInClass extends Stage {

	private TextField email = null;
	private PasswordField password = null;
	private TextField firstName = null;
	private TextField lastName = null;
	private TextField phoneNumber = null;
	private TextField address = null;
	private Writer signInWriter = null;
	private Text text = null;
	private File avatar;
	
	public SignInClass(Writer writer) {
		
		setResizable(false);
		setTitle("Circuit\u2122");
		getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
		
		signInWriter = writer;
		
		setOnCloseRequest(e -> {
			signInWriter.writeToOutput(new ExitObject());
			Program.killReader();
			});
		
		text = new Text("We already have user with this email");
		text.setFill(Color.CRIMSON);
		text.setVisible(false);
		
		email = new TextField();
		password = new PasswordField();
		email.setPromptText("Enter your e-mail");
		password.setPromptText("Enter your password");
		
		email.setOnMouseClicked(e -> text.setVisible(false));
		password.setOnMouseClicked(e -> text.setVisible(false));
		
		firstName = new TextField();
		lastName = new TextField();
		phoneNumber = new TextField();
		address = new TextField();
		
		firstName.setPromptText("Enter your first name");
		lastName.setPromptText("Enter your last name");
		phoneNumber.setPromptText("Enter your phone *optional");
		address.setPromptText("Enter your address *optional");
		
		Button signIn = new Button("Sign in");
		Button chooseAvatar = new Button("Choose avatar");
		Label logo = new Label("Circuit\u2122", new ImageView(new Image(getClass().getResourceAsStream("logo.png"))));
		chooseAvatar.setStyle("-fx-min-width: 200px");
		signIn.setStyle("-fx-min-width: 200px");
		logo.setStyle("-fx-text-fill: #0078FF");
		
		signIn.setOnMouseClicked(e -> tryToSignIn());
		
		avatar = new File("default.png");
		
		chooseAvatar.setOnMouseClicked(e ->{ 
			FileChooser chooser =  new FileChooser();
			chooser.setTitle("Select photo for your avatar");
			chooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
			File choosedFile = chooser.showOpenDialog(this);
			if (choosedFile != null) {
				avatar = choosedFile;
			}
		});
		
		GridPane controlsGrid = new GridPane();
		controlsGrid.setHgap(10);
		controlsGrid.setVgap(10);
		
		controlsGrid.add(text, 0, 0);
		controlsGrid.add(email, 0, 1);
		controlsGrid.add(password, 0, 2);
		controlsGrid.add(firstName, 0, 3);
		controlsGrid.add(lastName, 0, 4);
		controlsGrid.add(phoneNumber, 0, 5);
		controlsGrid.add(address, 0, 6);
		controlsGrid.add(chooseAvatar, 0, 7);
		controlsGrid.add(signIn, 0, 8);
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
	}
	
	public void setEmailNotFree(boolean visible){
		text.setVisible(visible);
	}
	
	public void clearFields(){
		email.clear();
		password.clear();
	}
	
	private void tryToSignIn(){
		
		String emailText = email.getText();
		String passwordText = password.getText();
		String firstNameText = firstName.getText();
		String lastNameText = lastName.getText();
		String phonetext = phoneNumber.getText();
		String addresstext = address.getText();
		
		byte[] avatarBytes = new byte[(int)avatar.length()];
		try {
			FileInputStream avatarStream=new FileInputStream(avatar);
			avatarStream.read(avatarBytes,0,avatarBytes.length);
			avatarStream.close();
		} catch (IOException e) {
		}
		
		if (emailText.length() != 0 && passwordText.length() != 0 && firstNameText.length() != 0 && lastNameText.length() != 0) {
		signInWriter.writeToOutput(new AuthenticateObject());	
		signInWriter.writeToOutput(new SignInObject(emailText, passwordText, firstNameText, lastNameText, phonetext, addresstext, avatarBytes));
		}
		

	}
}
