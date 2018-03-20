package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Lugar {

	private String id;
	private Direccion dir;
	private double lat;
	private double lng;
	private ImageIcon local;
	float puntuacion;
	private String categoria;
	
	//lista de comentarios al lugar
	private ArrayList<Comentario> comentarios;
	
	public Lugar() {
		id = "96747421";
		dir = new Direccion();
		lat = -33.5641197;
		lng = -70.5575697;
		comentarios = new ArrayList<Comentario>();
		categoria = "default";
	}
	
	
	public Lugar(String id) {
		ResultSet rs;
		DbHandler db;
		
		//se busca el lugar en la db por id
		try {
			db = new DbHandler();
			rs = db.buscarLugar(id);
			id = rs.getString("id");
			dir = new Direccion(rs.getString("casa"),
								rs.getString("comuna"),
								rs.getString("region"),
								rs.getString("pais"));
			lat = rs.getFloat("latitud");
			lng = rs.getFloat("longitud");
			categoria = rs.getString("categoria");
			puntuacion = db.puntuacionLugar(id);
			comentarios = new ArrayList<Comentario>();
			
		} catch (SQLException e) {
			
			this.id = "NO_EXISTE";	
		}		
		
		
	}
	
	public Lugar(String id,
				 String dir, 
				 String comuna,
				 String region,
				 String pais,
				 double lat,
				 double lng,
				 String categoria) {
		this.id = id;
		this.dir = new Direccion(dir, comuna, region, pais);
		this.lat = lat;
		this.lng = lng;		
		this.puntuacion = 0f;
		
		comentarios = new ArrayList<Comentario>();		
	}
	
	
	public Lugar(String id, String[] dir, String categoria, double lat, double lng) {
		this.id = id;
		this.dir = new Direccion(dir);
		this.lat = lat;
		this.lng = lng;		
		this.categoria = categoria;
		this.puntuacion = 0f;
		
		comentarios = new ArrayList<Comentario>();
		
	}
	

	
	private void cargarComentarios() {
		
		try {
			DbHandler db = new DbHandler();
			ResultSet rs;
			rs = db.buscarComentarios(id);
			Comentario com;
			while(rs.next()) {
				com = new Comentario( rs.getString("idUsuario"),
									  rs.getString("comentario"),
									  rs.getFloat("puntuacion")
									  );
				comentarios.add(com);
			}
		} catch (SQLException e) {
			return;
		}
	}
	
	
	public void destroy() {
		
	}
	
	
	/*
	 * Getters
	 * */
	
	public String getId() {
		return id;
	}
	
	public String getDireccionPpal() {
		return dir.getParticular();
	}
	
	public String getComuna() {
		return dir.getComuna();
	}
	
	public String getRegion() {
		return dir.getRegion();
	}
	
	public String getPais() {
		return dir.getPais();
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	/*
	 * Setters
	 * */
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setDirPpal(String dir) {
		this.dir.setParticular(dir);
	}
	
	public void setComuna(String com) {
		dir.setComuna(com);
	}
	
	public void setRegion(String reg) {
		dir.setRegion(reg);
	}
	
	public void setPais(String pais) {
		dir.setPais(pais);
	}
	
	public void setDir(String[] dir) {
		this.dir.setDir(dir);
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public void setCategoria(String cat) {
		this.categoria = cat;
	}
	
	
	
	
	
}
