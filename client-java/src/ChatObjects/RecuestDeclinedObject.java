package ChatObjects;

import java.io.Serializable;

public class RecuestDeclinedObject implements Serializable {

	private static final long serialVersionUID = 2518890276041593432L;
	
	private int userID;
	private int senderID;
	
	public RecuestDeclinedObject(int userID, int senderID) {
		this.userID = userID;
		this.senderID = senderID;
	}
	
	public int getUser(){
		return userID;
	}
	
	public int getSender(){
		return senderID;
	}
}
