package main.java;

public class Usuario extends CuentaUsuario {

  public Usuario ( String usuario, String password ) {
		super ( usuario, password );
	}

	public String tipoCuenta() {
		return "Usuario";
	}

	public String informacionCuenta() {
		return "Cuenta Usuario " + this.getNombreUsuario();
	}

}
