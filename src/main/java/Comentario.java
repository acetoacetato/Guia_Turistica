package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comentario {
	private int id;
	private String userId;
	private String placeId;
	private String comentario;
	private float puntuacion;
	
	
	
	
	public Comentario(SistemaMapa sis) {
		userId = sis.getNombreUsuario();
		id = -20;
		comentario = "Aún no comentas este lugar";
		puntuacion = 0.0f;
	}
	
	public Comentario(int idCom, String usr, String com, float pt) {
		id = idCom;
		userId = usr;
		comentario = com;
		puntuacion = pt;
	}
	
	public Comentario(ResultSet rs) throws SQLException {
		id = rs.getInt("id");
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
	
	public int getId() {
		return id;
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
	
	
	
	
	
}
