package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;

import Colecciones.MapaComentariosUsuario;

public class Usuario extends CuentaUsuario {
	

  /**
   * Constructor crea al usuario a partir del nombre y su clave.
   * @param usuario : nombre de usuario.
   * @param password : contraseña.
   * @throws SQLException
   */
  public Usuario ( String usuario, String password ) throws SQLException  {
		super ( usuario, password );
	}

  	/**
  	 * Constructor de un usuario a partir de un resultset (El resultado de una consulta SQL).
  	 * @param rs : el resultset de una consulta de usuarios.
  	 * @throws SQLException
  	 */
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
