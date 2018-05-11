package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario extends CuentaUsuario {

  public Usuario ( String usuario, String password ) {
		super ( usuario, password );
	}

  	public Usuario(ResultSet rs) throws SQLException {
  		super(rs);
  	}
  	
	public String tipoCuenta() {
		return "Usuario";
	}

	public String informacionCuenta() {
		return "Cuenta Usuario " + this.getNombreUsuario();
	}

}
