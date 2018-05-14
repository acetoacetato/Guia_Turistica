package excepciones;

public class UserRegisterFailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserRegisterFailureException(String msg) {
		super (msg);
	}
}
