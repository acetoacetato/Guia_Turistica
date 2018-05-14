package excepciones;

public class WithoutNextException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WithoutNextException() {
		super("no existe p�gina siguiente");
	}
}
