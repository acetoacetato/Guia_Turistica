package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;

import Colecciones.MapaComentariosUsuario;

public class Usuario extends CuentaUsuario {
	

  public Usuario ( String usuario, String password ) throws SQLException {
		super ( usuario, password );
		comentariosMap= new MapaComentariosUsuario(getNombreUsuario());
	}

  	public Usuario(ResultSet rs) throws SQLException {
  		super(rs);
  		comentariosMap= new MapaComentariosUsuario(getNombreUsuario());
  	}
  	
	public String tipoCuenta() {
		return "Usuario";
	}
	
	@Override
	public String informacionCuenta() {
		return "Cuenta Usuario " + this.getNombreUsuario();
	}
	
	
	
	

}
