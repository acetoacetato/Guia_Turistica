package excepciones;

public class UserFindException extends Exception {

	public UserFindException() {
		super("No se encontró el usuario especificado.");
	}
	
}
