package ChatObjects;

import java.io.Serializable;

public class RecuestAcceptedObject implements Serializable {

	private static final long serialVersionUID = -5476996050232371136L;
	
	private int userID;
	private int senderID;
	
	public RecuestAcceptedObject(int userID, int senderID) {
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
