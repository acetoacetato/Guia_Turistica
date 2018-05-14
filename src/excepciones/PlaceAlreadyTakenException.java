package excepciones;

public class PlaceAlreadyTakenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlaceAlreadyTakenException() {
		super("El lugar ya existe en la base de datos.");
	}
}
