package excepciones;

public class FieldCheckException extends Exception{

	
	public FieldCheckException(String m) {
		super("Fallo al obtener los campos:" + m);
	}
}
