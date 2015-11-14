package UserInterface;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecuestWindow extends Stage {

	private VBox recuests = null;
	
	public RecuestWindow() {
		setResizable(false);
		setTitle("Circuit\u2122");
		getIcons().add(new Image(getClass().getResourceAsStream("logo.png")));
		
		Label logo = new Label("Circuit\u2122", new ImageView(new Image(getClass().getResourceAsStream("logo.png"))));
		logo.setStyle("-fx-text-fill: #0078FF");
		HBox header = new HBox(logo);
		header.setStyle("-fx-alignment: center-left; -fx-background-color: #1A1A1A; -fx-min-width: 800px;");
	
		recuests = new VBox(2);
		ScrollPane list = new ScrollPane(recuests);
		list.setStyle("-fx-background: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 0px; -fx-background-radius: 0px; -fx-hbar-policy: never; -fx-Vbar-policy: always;");
		
		GridPane root = new GridPane();
		root.add(header, 0, 0);
		root.add(list, 0, 1);

		
		Scene scene = new Scene(root, 700, 490);
		scene.getStylesheets().add(LogInClass.class.getResource("LogInStyle.css").toExternalForm());
		setScene(scene);
		sizeToScene();
	}
	
	public VBox getRecuests(){
		return recuests;
	}
}
