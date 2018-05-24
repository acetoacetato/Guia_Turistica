package main.java;

public class Busqueda {
	
	String tipoBusqueda;
	String[] parametrosBusqueda;
	
	/**
	 * Constructor de la búsqueda por defecto, asume que es sólo un tipo.
	 * @param tipo : el tipo de búsqueda a realizar.
	 */
	public Busqueda(String tipo) {
		tipoBusqueda = tipo;
		parametrosBusqueda = new String[0];

	}
	
	/**
	 * Constructor de la búsqueda dado varios parámetros.
	 * @param tipo : tipo de búsqueda.
	 * @param param : String[] de parámetros.
	 */
	public Busqueda(String tipo, String[] param) {
		tipoBusqueda = tipo;
		parametrosBusqueda = param;
	}
	
	/**
	 * Constructor de la búsqueda dado varios parámetros.
	 * @param tipo : tipo de búsqueda.
	 * @param param : parámetros de búsqueda, se asume que cada parámetro está separado por "," y no hay espacios entre medio.
	 */
	public Busqueda(String tipo, String param) {
		tipoBusqueda = tipo;
		parametrosBusqueda = param.split(",");
	}
	
	public String getTipo() {
		return tipoBusqueda;
	}
	
	public void setTipo(String tipo) {
		tipoBusqueda = tipo;
	}
	
	public void setParametrosBusqueda(String busqueda) {
		parametrosBusqueda = busqueda.split(",");
	}
	
	public String getParametro() {
		String s = parametrosBusqueda[0];
		parametrosBusqueda[0] = null;
		comprimir();
		return s;
	}
	
	/**
	 * Elimina y comprime el primer elemento del String[] de parámetros.
	 */
	private void comprimir() {
		String[] s = parametrosBusqueda;
		for(int i=1 ; i < s.length ; i++) {
			s[i-1] = s[i];
		}
	}
	
}
