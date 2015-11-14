package ChatObjects;

import java.io.Serializable;

public class FriendObject implements Serializable {

	private static final long serialVersionUID = 8797732113713738348L;

	private int ID;
	private String firstName = null;
	private String lastName = null;
	private byte[] avatar = null;
	
	public FriendObject(int ID, String firstName, String lastName, byte[] avatar) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.avatar = avatar;
	}
	
	//Get user's ID
		public int getID(){
			return ID;
		}
	
	//Get user's first name
	public String getFirstName(){
		return firstName;
	}
	
	//Get user's last name
	public String getLastName(){
		return lastName;
	}
	
	//Get user's avatar
	public byte[] getAvatar(){
		return avatar;
	}
}
