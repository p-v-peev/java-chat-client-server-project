package ChatObjects;

import java.io.Serializable;

public class RecuestObject implements Serializable {

	private static final long serialVersionUID = -8024998560495289764L;
	
	private int senderID;
	private int recipientID;
	
	public RecuestObject(int senderID, int recipientID) {
		this.senderID = senderID;
		this.recipientID = recipientID;
	}
	
	public int getSenderID(){
		return senderID;
	}
	
	public int getRecipientID(){
		return recipientID;
	}
}
