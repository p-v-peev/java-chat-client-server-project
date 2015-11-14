package MemoryClean;

import java.util.ArrayList;

import javafx.application.Platform;
import UserInterface.ChatWindow;
import VisualObjects.VisualMessageWindow;

public class Cleaner extends Thread{
	
	public void run(){
		while (true) {
			
			ArrayList<VisualMessageWindow> pane = ChatWindow.getVisualMessagePane();
			
			if (pane != null) {
					Platform.runLater(new Runnable() {
						public void run() {
							for (int i = 0; i < pane.size(); i++) {
								VisualMessageWindow current = pane.get(i);
								int last = current.getMessagesBox().getChildren().size() - 10;
								if (last >0) {
									current.getMessagesBox().getChildren().remove(0, last);	
								}
							}
						}
					});
			}
			
			try {
				sleep(30000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

