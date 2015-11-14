package ChatObjects;

import java.io.Serializable;

public class MessageObject implements Serializable {
	
	private static final long serialVersionUID = -7467019688895161719L;
	
	private int recipientID;
	private String message = null;
	private int senderID;
	
	public MessageObject(int recipientID, String message, int senderID) {
		this.recipientID = recipientID;
		this.message = message;
		this.senderID = senderID;
	}
	
	//Get the recipient for this message
	public int getrecipientID(){
		return recipientID;
	}
	
	//Get message
	public String getMessage(){
		return message;
	}
	
	//Get sender of this message
	public int getSenderID(){
		return senderID;
	}
}
