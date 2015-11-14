package ChatObjects;

import java.io.Serializable;

public class LogInObject implements Serializable {
	
	private static final long serialVersionUID = 9165339382174294786L;
	
	private String email = null;
	private String password = null;
	
	public LogInObject(String email, String password) {
		this.email = email;
		this.password = password;
	}

	//Get user's e-mail
	public String getEmail(){
		return email;
	}
	
	//Get user's password
	public String getPassword(){
		return password;
	}
}
