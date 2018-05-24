package excepciones;

public class UserFindException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public UserFindException() {
		super("No se encontró el usuario especificado.");
	}
	
}
