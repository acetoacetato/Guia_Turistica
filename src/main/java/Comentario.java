package main.java;

public class Comentario {
	private int id;
	private String userId;
	private String placeId;
	private String comentario;
	private float puntuacion;
	
	
	
	
	public Comentario(String usr) {
		userId = usr;
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
