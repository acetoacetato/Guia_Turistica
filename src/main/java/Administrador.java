package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrador extends CuentaUsuario {

	public Administrador ( String usuario, String password ) throws SQLException{
		super ( usuario, password );
	}
 
	public Administrador(ResultSet rs) throws SQLException {
		super(rs);
	}
	
	public String tipoCuenta() {
		return "Administrador";
	}
	
	@Override
	public String informacionCuenta() {
		return "Cuenta Administrador " + this.getNombreUsuario();
	}

}
