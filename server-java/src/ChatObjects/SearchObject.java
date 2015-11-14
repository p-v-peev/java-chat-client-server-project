package ChatObjects;

import java.io.Serializable;

public class SearchObject implements Serializable {

	private static final long serialVersionUID = 4230641053460510433L;
	
	private String firstName = null;
	private String lastName = null;
	private String address = null;
	
	public SearchObject(String firstName, String lastName, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
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
}
