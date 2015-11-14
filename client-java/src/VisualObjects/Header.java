package VisualObjects;

import UserInterface.ChatWindow;
import UserInterface.SearchWindow;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Header extends HBox {
	
	private ImageView  notify = null;
	
	public Header(ChatWindow window ,VisualUser user) {
		super(80);
		setStyle("-fx-background-color: #1A1A1A; -fx-alignment: center-left;");
		
		Label logo = new Label("Circuit\u2122", new ImageView(new Image(getClass().getResourceAsStream("logo.png"))));
		ImageView search = new ImageView(new Image(getClass().getResourceAsStream("search.png")));
		ImageView recuest = new ImageView(new Image(getClass().getResourceAsStream("recuest.png")));
		notify = new ImageView(new Image(getClass().getResourceAsStream("notify.png")));
		notify.setVisible(false);
		
		search.setOnMouseClicked(e -> {ChatWindow.setSearch(new SearchWindow());
		});
		
		recuest.setOnMouseClicked(e -> {notify.setVisible(false);
			if (window.getRecuest() != null) {
				window.getRecuest().show();
			}
		});
		
		AnchorPane recuestPane = new AnchorPane(recuest, notify);
		AnchorPane.setTopAnchor(recuest, 17.0);
		AnchorPane.setLeftAnchor(recuest, 5.0);
		AnchorPane.setTopAnchor(notify, 20.0);
		
		setEfects(logo);
		
		getChildren().addAll(logo, user, search, recuestPane);
	}
	
	private void setEfects(Label label){
		label.setStyle("-fx-text-fill: #0078FF");
	}
	
	public void setNotifyVisible(boolean visible){
		notify.setVisible(visible);
	}
}
