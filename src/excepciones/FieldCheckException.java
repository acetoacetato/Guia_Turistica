package excepciones;

public class FieldCheckException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FieldCheckException(String m) {
		super("Fallo al obtener los campos:" + m);
	}
}
