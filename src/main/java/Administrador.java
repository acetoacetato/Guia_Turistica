package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrador extends Usuario {

	public Administrador ( String usuario, String password ) {
		super ( usuario, password );
	}
 
	public Administrador(ResultSet rs) throws SQLException {
		super(rs);
	}
	
	public String tipoCuenta() {
		return "Administrador";
	}

	public String informacionCuenta() {
		return "Cuenta Administrador " + this.getNombreUsuario();
	}

}
