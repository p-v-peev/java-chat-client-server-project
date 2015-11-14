package ChatObjects;

import java.io.Serializable;

public class UserFoundObject implements Serializable {
	
	private static final long serialVersionUID = 55884831308104601L;
	
	private int ID;
	private String firstName = null;
	private String lastName = null;
	private byte[] avatar = null;
	
	public UserFoundObject(int ID, String firstName, String lastName, byte[] avatar) {
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
