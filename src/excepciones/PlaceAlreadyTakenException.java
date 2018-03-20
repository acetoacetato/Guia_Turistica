package excepciones;

public class PlaceAlreadyTakenException extends Exception {

	public PlaceAlreadyTakenException() {
		super("El lugar ya existe en la base de datos.");
	}
}
