package main.java;

public class Busqueda {
	
	String tipoBusqueda;
	String[] parametrosBusqueda;
	
	public Busqueda(String tipo, String[] param) {
		tipoBusqueda = tipo;
		parametrosBusqueda = param;
	}
	
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
	
	private void comprimir() {
		String[] s = parametrosBusqueda;
		for(int i=1 ; i < s.length ; i++) {
			s[i-1] = s[i];
		}
	}
	
}
