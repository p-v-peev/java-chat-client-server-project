package ChatObjects;

import java.io.Serializable;

public class SearchResultObject implements Serializable {
	
	private static final long serialVersionUID = 5999680083374196029L;
	
	private int ID;
	private String firstName = null;
	private String lastName = null;
	private String address = null;
	private byte[] avatar = null;
	
	public SearchResultObject(int ID, String firstName, String lastName,String address, byte[] avatar) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.avatar = avatar;
	}
	
	public int getID(){
		return ID;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String  getAddress(){
		return address;
	}
	
	public byte[] getAvatar(){
		return avatar;
	}
}
