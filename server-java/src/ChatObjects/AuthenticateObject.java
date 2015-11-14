package ChatObjects;

import java.io.Serializable;

public class AuthenticateObject implements Serializable {

	private static final long serialVersionUID = 8524500061405638489L;

	private final long controlValue;
	
	public AuthenticateObject() {
		controlValue = 3412412141L;
	}
	
	public long getControlValue(){
		return controlValue;
	}
}
