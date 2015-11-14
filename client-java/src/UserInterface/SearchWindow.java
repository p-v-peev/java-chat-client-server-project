package UserInterface;

import ChatObjects.SearchObject;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchWindow extends Stage{
	
	private VBox results = null;

	public SearchWindow() {
		
		setResizable(false);
		setTitle("Circuit\u2122");
		getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
		
		Label logo = new Label("Circuit\u2122", new ImageView(new Image(getClass().getResourceAsStream("logo.png"))));
		logo.setStyle("-fx-text-fill: #0078FF");
		HBox header = new HBox(logo);
		header.setStyle("-fx-alignment: center-left; -fx-background-color: #1A1A1A;");
	
		TextField firstname = new TextField();
		TextField lastName = new TextField();
		TextField address = new TextField();
		Button search = new Button("Search");
		
		firstname.setPromptText("Enter first name");
		lastName.setPromptText("Enter last name");
		address.setPromptText("Enter address");
		search.setOnMouseClicked(e ->{
			if (firstname.getText().length() != 0 || lastName.getText().length() != 0 || address.getText().length() != 0) {
				ChatWindow.getWriter().writeToOutput(new SearchObject(firstname.getText(), lastName.getText(), address.getText()));
			}
		});
		
		HBox input = new HBox(5, firstname, lastName, address, search);
		input.setStyle(" -fx-min-width: 800px; -fx-alignment: top-center; -fx-background-color: #1A1A1A; -fx-max-height: 20px;");
		
		results = new VBox(2);
		ScrollPane list = new ScrollPane(results);
		list.setStyle("-fx-background: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 0px; -fx-background-radius: 0px; -fx-hbar-policy: never; -fx-Vbar-policy: always;");
		
		GridPane root = new GridPane();
		root.add(header, 0, 0);
		root.add(input, 0, 1);
		root.add(list, 0, 2);

		
		Scene scene = new Scene(root, 700, 490);
		scene.getStylesheets().add(LogInClass.class.getResource("LogInStyle.css").toExternalForm());
		setScene(scene);
		sizeToScene();
		show();
		
	}
	
	public VBox getResults(){
		return results;
	}
}
