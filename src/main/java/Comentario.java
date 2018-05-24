package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comentario {
	private String userId;
	private String placeId;
	private String comentario;
	private float puntuacion;
	
	
	
	/**
	 * Crea el comentario por defecto, asume que el usuario que creó ese comentario es el que inició sesión en la aplicación.
	 * @param sis : instancia de SistemaMapa
	 */
	public Comentario(SistemaMapa sis) {
		userId = sis.getNombreUsuario();
		comentario = "A�n no comentas este lugar";
		puntuacion = 0.0f;
	}
	
	/**
	 * Crea el comentario dados parámetros.
	 * @param idLugar : id del lugar donde se crea el comentario.
	 * @param usr : nombre del usuario que comenta.
	 * @param com : comentario.
	 * @param pt : puntuación del comentario.
	 */
	public Comentario( String idLugar, String usr, String com, float pt) {
		userId = usr;
		placeId = idLugar;
		comentario = com;
		puntuacion = pt;
	}
	
	/**
	 * Crea el comentario a partir del resultado de una consulta.
	 * @param rs : ResultSet producto de una consulta SQL.
	 * @throws SQLException
	 */
	public Comentario(ResultSet rs) throws SQLException {
		userId = rs.getString("id_usuario");
		placeId = rs.getString("id_lugar");
		comentario = rs.getString("comentario");
		puntuacion = rs.getInt("puntuacion");
	}
	
	
	
	public String getUsr() {
		return userId;
	}
	
	public String getCom() {
		return comentario;
	}
	
	public float getPt() {
		return puntuacion;
	}
	
	public void setUsr(String usr) {
		userId = usr;
	}
	
	public void setCom(String com) {
		comentario = com;
	}
	
	public void setPt(float pt) {
		puntuacion = pt;
	}
	
	public String getPlaceId() {
		return placeId;
	}
	
	public void setPlaceId(String p) {
		placeId = p;
	}
	
	
	
}
