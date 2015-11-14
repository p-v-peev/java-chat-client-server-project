package ChatObjects;

import java.io.Serializable;

public class SignInObject implements Serializable {

	private static final long serialVersionUID = -7433559904344762273L;
	
	private String email = null;
	private String password = null;
	private String firstName = null;
	private String lastName = null;
	private String phoneNumber = null;
	private String address = null;
	private byte[] avatar = null;
	
	public SignInObject(String email, String password, String firstName, String lastName, String phoneNumber, String address, byte[] avatar) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.avatar = avatar;
	}
	
	//Get user's email
	public String getEmail(){
		return email;
	}
	
	//Get user's password
	public String getPassword(){
		return password;
	}
	
	//Get user's first name
	public String getFirstName(){
		return firstName;
	}
	
	//Get user's last name
	public String getLastName(){
		return lastName;
	}
	
	//Get user's phone number
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	//Get user's address
	public String getAddress(){
		return address;
	}
	
	//Get user's avatar
	public byte[] getAvatar(){
		return avatar;
	}
}
