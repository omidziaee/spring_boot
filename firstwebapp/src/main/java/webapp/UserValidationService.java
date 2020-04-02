package webapp;

public class UserValidationService {
	public boolean isValid(String username, String password) {
		if(username.equals("omidziaee1362") && password.equals("omidan")) {
			return true;
		} else {
			return false;
		}
	}

}
