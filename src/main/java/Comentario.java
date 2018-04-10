package main.java;

public class Comentario {
	private String userId;
	private String placeId;
	private String comentario;
	private float puntuacion;
	
	
	
	
	public Comentario(String usr) {
		userId = usr;
		comentario = "Aún no comentas este lugar";
		puntuacion = 0.0f;
	}
	
	public Comentario(String usr, String com, float pt) {
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
