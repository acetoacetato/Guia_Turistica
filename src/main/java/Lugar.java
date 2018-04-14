package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;



public class Lugar {

	private String id;
	private String nombreLocal;
	private Direccion dir;
	private double lat;
	private double lng;
	private ImageIcon local;
	private ImageIcon mapa;
	private int puntuacion;
	private String categoria;
	private String descripcion;
	
	private Hashtable<String, Comentario> comentariosMap;
	
	
	public String getNombreLocal() {
		return nombreLocal;
	}


	public void setNombreLocal(String nombreLocal) {
		this.nombreLocal = nombreLocal;
	}


	public int getPuntuacion() {
		return puntuacion;
	}


	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Hashtable<String, Comentario> getComentarios() {
		return comentariosMap;
	}


	public void setComentarios(ArrayList<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	//lista de comentarios al lugar
	private ArrayList<Comentario> comentarios;
	
	public Lugar() {
		id = "96747421";
		dir = new Direccion();
		lat = -33.5641197;
		lng = -70.5575697;
		comentariosMap = new Hashtable<String, Comentario>();
		categoria = "default";
	}
	
	
	/*
	 * Sobrecarga para cargar de la DB, la usa DbHandler
	 * */
	
	public Lugar(String id,
				 String dir, 
				 String comuna,
				 String region,
				 String pais,
				 double lat,
				 double lng,
				 String nombre,
				 String descripcion,
				 String categoria) {
		this.id = id;
		this.dir = new Direccion(dir, comuna, region, pais);
		this.lat = lat;
		this.lng = lng;
		this.nombreLocal = nombre;
		this.descripcion = descripcion;
		this.puntuacion = 0;
		this.categoria = categoria;
		
		comentariosMap = new Hashtable<String, Comentario>();
	}
	
	/*
	 * Sobrecarga para agregar a la DB, la usa VentanaAgregar
	 * 
	 * */
	public Lugar(String id, String name, String[] dir, String categoria, double lat, double lng, String desc) {
		this.id = id;
		this.dir = new Direccion(dir);
		this.lat = lat;
		this.lng = lng;		
		this.nombreLocal = name;
		this.categoria = categoria;
		this.puntuacion = 0;
		this.descripcion = desc;
		comentariosMap = new Hashtable<String, Comentario>();
		
		
	}
	
	
	/*
	 * Sobrecarga para crear un lugar a partir de un ResulSet, asume que está en posición
	 * */
	public Lugar(ResultSet rs) throws SQLException {
		
		this.id = rs.getString("id");
		this.dir = new Direccion(rs.getString("casa"),
								 rs.getString("comuna"),
								 rs.getString("region"),
								 rs.getString("pais")
								);
		this.lat = rs.getFloat("latitud");
		this.lng = rs.getFloat("longitud");
		this.nombreLocal = rs.getString("nombre");
		this.descripcion = rs.getString("descripcion");
		this.categoria = rs.getString("categoria");
		comentariosMap = new Hashtable<String, Comentario>();
	}
	

	//carga los comentarios desde un lugar
	public void cargarComentarios() {
		
		try {
			DbHandler db = new DbHandler();
			ResultSet rs;
			rs = db.buscarComentarios(id);
			Comentario com;
			while(rs.next()) {
				com = new Comentario( rs.getInt("id"),
									  rs.getString("id_usuario"),
									  rs.getString("comentario"),
									  rs.getFloat("puntuacion")
									  );
				if(comentariosMap == null)
					System.out.println("tu papá es hombre");
				comentariosMap.put(com.getUsr(), com);
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
	
	public ArrayList<Comentario> getListaComentarios(){
		return new ArrayList<Comentario>(comentariosMap.values());
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



