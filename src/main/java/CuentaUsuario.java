package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Colecciones.MapaComentariosUsuario;

public abstract class CuentaUsuario {
	private String nombreUsuario;
	private String contrasena;

	protected MapaComentariosUsuario comentariosMap;
	
	public CuentaUsuario( String usuario, String contrasena ) {
		this.setNombreUsuario( usuario );
		this.setContrasena( contrasena );
	}

  public CuentaUsuario(ResultSet rs) throws SQLException {
	  setNombreUsuario(rs.getString("nombre"));
	  setContrasena(rs.getString("contrasena"));
  }

public abstract String tipoCuenta();

  public String getNombreUsuario() {
		return nombreUsuario;
	}

  public String informacionCuenta() {
    return "Cuenta " + getNombreUsuario();
  }

  public void destroy() {
	  nombreUsuario = null;
	  contrasena = null;
  }
  
  public void setNombreUsuario( String nombreUsuario ) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena( String contrasena ) {
		this.contrasena = contrasena;
	}
  
  public void modificar(String comentAct, String points, String lug) throws SQLException {
		comentariosMap.modificar(comentAct,points,lug );
		
	}
  public ArrayList<Comentario> getComentarios() {
		return comentariosMap.valores();
	}

}
	
