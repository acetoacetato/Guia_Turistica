package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Colecciones.MapaComentarios;



public class Lugar {

	private String id;
	private String nombreLocal;
	private Direccion dir;
	private float lat;
	private float lng;
	private String categoria;
	private String descripcion;
	
	private MapaComentarios comentariosMap;
	
	
	/**
	 * Constructor por defecto, se crea en una dirección arbiraria.
	 * @throws SQLException
	 */
	public Lugar() throws SQLException {
		id = "96747421";
		dir = new Direccion();
		lat = -33.5641197f;
		lng = -70.5575697f;
		comentariosMap = new MapaComentarios(id);
		categoria = "default";
	}
	
	
	
	/**
	 * Constructor de lugar a partir de parámetros dados.
	 * @param id : el id del lugar a crear.
	 * @param dir : la dirección (nombre de calle + número) del lugar.
	 * @param comuna : comuna del lugar a agregar.
	 * @param region : región del lugar a agregar.
	 * @param pais : país del lugar a agregar.
	 * @param lat : latitud del lugar.
	 * @param lng : longitud del lugar.
	 * @param nombre : nombre del local.
	 * @param descripcion : descripción del local.
	 * @param categoria : categoría en la que puede entrar el lugar.
	 * @throws SQLException
	 */
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
	
	/**
	 * Constructor de lugar a partir de parámetros dados, esta sobrecarga recibe un arreglo de Strings para la dirección en vez de cada uno por separado.
	 * @param id : el id del lugar.
	 * @param name : el nombre del local.
	 * @param dir : String[] de la dirección, de tamaño 4 y cuyos valores deben ser : nombre de la calle + número, comuna, región, país.
	 * @param categoria : categoría del local.
	 * @param lat : latitud del lugar.
	 * @param lng : longitud del lugar.
	 * @param desc : descripción del local.
	 * @throws SQLException
	 */
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
	
	/**
	 * Constructor de lugar a partir de un ResultSet.
	 * @param rs ResultSet generado por una consulta SQL.
	 * @throws SQLException
	 */
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
	

	
		
		
	
	
	/**
	 * Genera y retorna un String[] con los parámetros de esta instancia de lugar.
	 * @return un arreglo de Strings con id, nombre del local, dirección(nombre de calle + número), comuna, región, país, latitud, longitud, categoría y descripción, en ese orden.
	 */
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
	
	/**
	 * Actualiza la información del lugar. Sólo es actualizable el nombre del local, la categoría y la descripción.
	 * @param nombre : Nuevo nombre del lugar.
	 * @param cat : Nueva categoría del lugar.
	 * @param desc : nueva descripción del lugar.
	 */
	public void actualizar(String nombre, String cat, String desc) {
		setNombreLocal(nombre);
		setCategoria(cat);
		setDescripcion(desc);		
	}


	/**
	 * Modifica un comentario de un usuario en el lugar.
	 * @param coment : el nuevo comentario.
	 * @param points : el nuevo puntaje.
	 * @param usr : el usuario al cual hay que modificar su comentario.
	 * @throws SQLException
	 */
	public void modificar(String coment, String points, String usr) throws SQLException {
		comentariosMap.modificar(coment,points,usr );
		
	}


	/**
	 * Elimina un comentario del sistema a partir de su instancia.
	 * @param comentario : La instancia del comentario a eliminar.
	 */
	public void eliminarComentario(Comentario comentario) {
		comentariosMap.eliminar(comentario.getUsr());
		
	}


	/**
	 * Elimina un comentario a partir del nombre del usuario que hizo dicho comentario en este lugar.
	 * @param usr : el nombre del usuario que hizo el comentario.
	 */
	public void eliminarComentario(String usr) {
		comentariosMap.eliminar(usr);
		
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


	

	
	
	
	
	
	
}



