package ChatObjects;

import java.io.Serializable;

public class MessageSeenObject implements Serializable {
	
	private static final long serialVersionUID = 2766058884435436255L;
	
	private int senderID;
	private int recipientID;
	
	public MessageSeenObject(int sender, int recipient) {
		senderID = sender;
		recipientID = recipient;
	}
	
	public int getSenderID(){
		return senderID;
	}
	
	public int getRecipientID(){
		return recipientID;
	}

}
