package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Colecciones.MapaComentariosUsuario;

public abstract class CuentaUsuario {
	private String nombreUsuario;
	private String contrasena;

	protected MapaComentariosUsuario comentariosMap;
	
	
	/**
	 * 
	 * @param usuario
	 * @param contrasena
	 * @throws SQLException
	 */
	public CuentaUsuario( String usuario, String contrasena ) throws SQLException {
		this.setNombreUsuario( usuario );
		this.setContrasena( contrasena );
		comentariosMap = new MapaComentariosUsuario(usuario);
	}

	/**
	 * Constructor de una cuenta a partir de un ResultSet
	 * @param rs : Resultado de una consulta SQL.
	 * @throws SQLException
	 */
	public CuentaUsuario(ResultSet rs) throws SQLException {
	  setNombreUsuario(rs.getString("nombre"));
	  setContrasena(rs.getString("contrasena"));
	}

	/**
	 * Retorna el tipo de cuenta.
	 * @return Retorna un String, con el tipo de cuenta. 
	 */
  	public abstract String tipoCuenta();


  	public String getNombreUsuario() {
		return nombreUsuario;
	}

  	
  	/**
  	 * Obtiene cierta información de la cuenta.
  	 * @return Un string que conecta "cuenta " + el nombre de usuario.
  	 */
  	public String informacionCuenta() {
	  return "Cuenta " + getNombreUsuario();
  	}

  	/**
  	 * Modifica un comentario.
  	 * @param comentAct : el comentario nuevo.
  	 * @param points : el nuevo puntaje.
  	 * @param lug : el lugar de donde se hizo el comentario.
  	 * @throws SQLException
  	 */
  	public void modificar(String comentAct, String points, String lug) throws SQLException {
		comentariosMap.modificar(comentAct,points,lug );
		
	}
  	
  	/**
  	 * obtiene todos los comentarios hechos por el usuario.
  	 * @return Un ArrayList<Comentario> con todos los comentarios del usuario.
  	 */
  	public ArrayList<Comentario> getComentarios() {
  		return comentariosMap.valores();
	}

  	
  	/**
  	 * Elimina un comentario.
  	 * @param comentario : una instancia del comentario a eliminar.
  	 */
  	public void eliminarComentario(Comentario comentario) {
	

		comentariosMap.eliminar(comentario.getPlaceId());
	
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
  

}
	
