package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Colecciones.MapaComentarios;
import Interfaces.Reportable;



public class Lugar implements Reportable {

	private String id;
	private String nombreLocal;
	private Direccion dir;
	private float lat;
	private float lng;
	private String categoria;
	private String descripcion;
	
	private MapaComentarios comentariosMap;
	
	
		
	public Lugar() throws SQLException {
		id = "96747421";
		dir = new Direccion();
		lat = -33.5641197f;
		lng = -70.5575697f;
		comentariosMap = new MapaComentarios(id);
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
				 float lat,
				 float lng,
				 String nombre,
				 String descripcion,
				 String categoria) throws SQLException {
		this.id = id;
		this.dir = new Direccion(dir, comuna, region, pais);
		this.lat = lat;
		this.lng = lng;
		this.nombreLocal = nombre;
		this.descripcion = descripcion;
		this.categoria = categoria;
		
		comentariosMap = new MapaComentarios(id);
	}
	
	

	/*
	 * Sobrecarga para agregar a la DB, la usa VentanaAgregar
	 * 
	 * */
	public Lugar(String id, String name, String[] dir, String categoria, float lat, float lng, String desc) throws SQLException {
		this.id = id;
		this.dir = new Direccion(dir);
		this.lat = lat;
		this.lng = lng;		
		this.nombreLocal = name;
		this.categoria = categoria;
		this.descripcion = desc;
		comentariosMap = new MapaComentarios(id);
		
		
	}
	
	
	/*
	 * Sobrecarga para crear un lugar a partir de un ResulSet, asume que est� en posici�n
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
		comentariosMap = new MapaComentarios(id);
	}
	

	
		
		
	
	
	
	public String[] arreglo() {
		String[] s = new String[10];
		s[0] = this.getId();
		s[1] = this.getNombreLocal();
		s[2] = this.getDireccionPpal();
		s[3] = this.getComuna();
		s[4] = this.getRegion();
		s[5] = this.getPais();
		s[6] = String.valueOf(this.getLat());
		s[7] = String.valueOf(this.getLng());
		s[8] = this.getCategoria();
		s[9] = this.getDescripcion();
		return s;
	}
	
	
	
	
	/*
	 * Getters
	 * */
	
	public String getNombreLocal() {
		return nombreLocal;
	}


	public void setNombreLocal(String nombreLocal) {
		this.nombreLocal = nombreLocal;
	}


	public int getPuntuacion() {
		return comentariosMap.rating();
	}




	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public ArrayList<Comentario> getComentarios() {
		return comentariosMap.valores();
	}


	
	public String getId() {
		return id;
	}
	
	public String[] getDir() {
		return dir.getDirArray();
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
	
	public float getLat() {
		return lat;
	}
	
	public float getLng() {
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
	
	public void setLat(float lat) {
		this.lat = lat;
	}
	
	public void setLng(float lng) {
		this.lng = lng;
	}
	
	public void setCategoria(String cat) {
		this.categoria = cat;
	}


	@Override
	public void generarReporte(String path) {
		
	}


	@Override
	public String reportePantalla() {
		return null;
	}


	
	public void reporte(Busqueda b) {
		
	}


	public void actualizar(String nombre, String cat, String desc) {
		setNombreLocal(nombre);
		setCategoria(cat);
		setDescripcion(desc);		
	}


	public void modificar(String comentAct, String points, String ussr) throws SQLException {
		comentariosMap.modificar(comentAct,points,ussr );
		
	}


	public void eliminarComentario(Comentario comentario) {
		comentariosMap.eliminar(comentario.getUsr());
		
	}


	public void eliminarComentario(String usr) {
		comentariosMap.eliminar(usr);
		
	}
	
	
	
	
	
}



