package excepciones;

public class WithoutNextException extends Exception {

	public WithoutNextException() {
		super("no existe p�gina siguiente");
	}
}
