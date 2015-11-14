package Handlers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ChatObjects.MessageObject;
import ServerConnector.Writer;
import UserInterface.ChatWindow;

public class TextInputHandler {

	public TextInputHandler(ChatWindow window, Writer writer, TextField area, int recipientID) {
		
		area.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER) {
					String text = area.getText();
					area.clear();
					if (!(text.length() == 0)) {
						writer.writeToOutput(new MessageObject(recipientID, text, ChatWindow.getUserID()));
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								window.setMyMessage(new MessageObject(recipientID, text, ChatWindow.getUserID()));
							}
						});
					}
				}
			}
		});
		
	}
	
}
