package Colecciones;


import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Interfaces.Reportable;
import excepciones.PlaceAlreadyTakenException;
import excepciones.PlaceException;
import main.java.Busqueda;
import main.java.Comentario;
import main.java.DbHandler;
import main.java.Lugar;
import ventanas.VentanaReporte;

public class MapaZona implements Reportable {

	private Hashtable<String, MapaCategorias> mapita;
	
	public MapaZona() throws SQLException {
		mapita = new Hashtable<String, MapaCategorias>();
		importar();
	}
	
	
	/**
	 * Agrega un lugar a la colección.
	 * @param id : id del lugar a agregar.
	 * @param nombre : nombre del lugar a agregar.
	 * @param dir : String[] conteniendo la dirección del lugar, se asume que es de largo 4 y contiene los valores en el orden de: nombre de calle + número, comuna, región, país.
	 * @param cat : categoría del lugar a agregar.
	 * @param lat : latitud del lugar.
	 * @param lng : longitud del lugar.
	 * @param desc : descripción del lugar.
	 * @return Una instancia del lugar agregado.
	 * @throws SQLException
	 * @throws PlaceAlreadyTakenException En caso de que el lugar ya esté en el Mapa, se lanza la excepción.
	 */
	public Lugar agregar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc ) throws SQLException, PlaceAlreadyTakenException {
		String zona = dir[1];
		
		MapaCategorias m = mapita.get(zona);
		if(m != null) {
			return m.agregar(id, nombre, dir, cat, lat, lng, desc);
		}
				
		m = new MapaCategorias(zona);
		mapita.put(zona, m);
		return m.agregar(id, nombre, dir, cat, lat, lng, desc);
		
		
	}
	
	
	/**
	 * Modifica un lugar del sistema, sólo se puede modificar el nombre del local, la categoría a la que pertenece y la descripción de este.
	 * @param id : id del lugar a modificar.
	 * @param nombre : nuevo nombre del lugar.
	 * @param cat : nueva categoría del lugar.
	 * @param desc : nueva descripción del lugar.
	 * @return La instancia del lugar modificado.
	 * @throws PlaceException En caso de que el lugar no exista en el Mapa, se lanza la excepción.
	 * @throws SQLException
	 */
	public Lugar modificar(String id, String nombre,  String cat, String desc) throws PlaceException, SQLException{
		
		String zona = obtenerZona(id);
		if(zona == null)
			throw new PlaceException("El lugar no existe en el sistema.");
		MapaCategorias m = mapita.get(zona);
		if(m == null) 
			throw new PlaceException("Fallo al encontrar la zona del lugar.");
		
		return m.modificar(id, nombre, cat,  desc);
	}
	
	
	/**
	 * Obtiene la zona a la que perteneze un lugar determinado.
	 * @param id : id del lugar.
	 * @return String de la zona(comuna) a la que pertenece el lugar.
	 * @throws PlaceException En caso de que el lugar no se encuentre en el Mapa, se lanza la excepción.
	 */
	public String obtenerZona(String id)throws PlaceException{
		ArrayList<MapaCategorias> l = new ArrayList<MapaCategorias>(mapita.values());
		
		for(MapaCategorias m : l) {
			Lugar lugar = m.buscarLugar(id);
			if(lugar != null)
				return lugar.getComuna();
		}
		
		return null;
	}
	
	
	/**
	 *Elimina un lugar del Mapa.
	 * @param id : el id del lugar eliminado.
	 * @return Una referencia al lugar eliminado.
	 */
	public Lugar eliminarLugar(String id) {
		ArrayList<MapaCategorias> l = new ArrayList<MapaCategorias>(mapita.values());
		Lugar lugar;
		for(MapaCategorias m : l) {
			if( (lugar = m.eliminarLugar(id)) != null )
				return lugar;
		}
		
		return null;
	}
	
	
	/**
	 * Busca y retorna un lugar en el mapa.
	 * @param idLugar : el id del lugar a buscar.
	 * @return La instancia del lugar encontrado.
	 * @throws PlaceException En caso de que el lugar no exista en el sistema, se lanza la excepción.
	 */
	public Lugar buscarLugar(String idLugar) throws PlaceException{
		ArrayList<MapaCategorias> l = new ArrayList<MapaCategorias>(mapita.values());
		
		for(MapaCategorias m : l) {
			Lugar lugar = m.buscarLugar(idLugar);
			if(lugar!=null)
				return lugar;
		}
		
		throw new PlaceException("El la zona no se encuentra en el sistema.");
	}
	
	
	/**
	 * Obtiene todos los lugares que pertenezcan a una zona(comuna) y categoría determinada.
	 * @param cat : categoría de los lugares a buscar.
	 * @param zona : zona(comuna) de los lugares a buscar.
	 * @return Un arrayList<Lugar> con los lugares coincidentes.
	 */
	public ArrayList<Lugar> obtenerLugares(String cat, String zona){
		MapaCategorias m = mapita.get(zona);
		if(m == null)
			return new ArrayList<Lugar>();
		return m.obtenerLugares(cat);
	}
	
	
	/**
	 * Carga el mapa con los lugares que estén en la base de datos.
	 * @throws SQLException
	 */
	private void importar() throws SQLException  {
		DbHandler db = new DbHandler();
		String[] zonas = db.zonas();
		for(String s : zonas){
			mapita.putIfAbsent(s , new MapaCategorias(s));
		}		
		
	}


	@Override
	public void generarReporte(String path) {
		Document pdf = new Document();
		
		FileOutputStream archivo;
		try {
			archivo = new FileOutputStream(path);
		
			PdfWriter.getInstance(pdf, archivo);
		
			pdf.open();
		
			pdf.addAuthor("Aplicación guía turística.");
			pdf.addCreationDate();
			
			Paragraph parrafo = new Paragraph();
			
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Reporte de zonas actuales", catFont));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Reporte generado por : " + System.getProperty("usr.nombre") + ", " + new Date(), smallBold));
			parrafo.add(new Paragraph(" "));
			
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Lista de zonas (comunas) donde residen los lugares de la base de datos:", smallBold));
			
			
			Set<String> zonas = mapita.keySet();
			int i = 1;
			
			for(String s : zonas) {
				parrafo.add(new Paragraph(" "));
				parrafo.add(new Chunk(i + ") " , redFont));
				parrafo.add(new Chunk(s + ".\n", smallBold));
				i++;
			}
			
			pdf.add(parrafo);
			pdf.close();
			
		
		}catch(DocumentException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Abre una ventana de reporte de acuerdo a la búsqueda.
	 * @param b : busqueda determinada, si es tipo "Zonas" se creará reporte de todas las zonas en el sistema, en caso contrario, debe ser de tipo "Categorias" y en parámetros una zona específica.
	 * @throws PlaceException: si el lugar no se encuentra, se lanza la excepción.
	 */
	public void reporte(Busqueda b) throws PlaceException {
		if( b.getTipo().equals("Zonas")) {
			VentanaReporte v = new VentanaReporte(this);
			v.setVisible(true);
			return;
		}else {
			String zona = b.getParametro();
			MapaCategorias m = mapita.get(zona);
			if(m == null)
				throw new PlaceException("La zona seleccionada es incorrecta.");
			m.reporte(b);
			return;
		}
		
		
		
	}
	
	/**
	 * Muestra todas las zonas en el sistema.
	 * @return Un String[] con todas las zonas que existen en el sistema.
	 */
	public String[] zonas() {
		ArrayList<String> l = new ArrayList<String>(mapita.keySet());
		String[] s = new String[(l.size())];
		for(int i= 0 ; i<l.size() ; i++) {
			s[i] = l.get(i);
		}
		return s;
	}
	
	
	@Override
	public String reportePantalla() {
		
		
		String s =  "Reporte de zonas actuales\n"
					+ "Reporte generado por : " + System.getProperty("usr.nombre") + ", " + new Date() + "\n"
					+ "\n\n"
					+ "Lista de zonas (comunas) donde residen los lugares de la base de datos:\n\n";
		
		Set<String> zonas = mapita.keySet();
		int i = 1;
		
		for(String s2 : zonas) {
			s = s.concat(i + ") " + s2 + ".\n");
			i++;
		}
		return s;
		
	}

	public void eliminarComentario(Comentario comentario) throws PlaceException {
		
		System.out.println(comentario.getPlaceId());
		
		Lugar l = buscarLugar(comentario.getPlaceId());
		
		l.eliminarComentario(comentario);
		
		
	}

	/**
	 * Elimina los comentarios de un usuario determinado en todos los lugares que existan en el mapa.
	 * @param usr : usuario dueño de los comentarios a eliminar.
	 * @throws SQLException
	 */
	public void eliminarComentario(String usr) throws SQLException {
		
		ArrayList<MapaCategorias> l = new ArrayList<MapaCategorias>(mapita.values());
		
		for(MapaCategorias m : l) {
			m.eliminarComentario(usr);
		}
		
	}




	

}
