package ChatObjects;

import java.io.Serializable;

public class RecuestDataObject implements Serializable {
	
	private static final long serialVersionUID = -6656274201835771351L;
	
	private int fromID;
	private String firstName = null;
	private String lastName = null;
	private String address = null;
	private byte[] avatar = null;
	
	public RecuestDataObject(int fromID, String firstName, String lastName, String address, byte[] avatar) {
		this.fromID = fromID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.avatar = avatar;
	}
	
	public int getID(){
		return fromID;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getAddress(){
		return address;
	}
	
	public byte[] getAvatar(){
		return avatar;
	}
	
}
