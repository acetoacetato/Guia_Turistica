package main.java;

public class Administrador extends Usuario {

	public Administrador ( String usuario, String password ) {
		super ( usuario, password );
	}
 
	public String tipoCuenta() {
		return "Administrador";
	}

	public String informacionCuenta() {
		return "Cuenta Administrador " + this.getNombreUsuario();
	}

}
