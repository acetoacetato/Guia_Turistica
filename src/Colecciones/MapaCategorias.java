package Colecciones;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
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
import main.java.DbHandler;
import main.java.Lugar;
import ventanas.VentanaReporte;

/**
 * 
 * @author acetoacetato
 *
 */
public class MapaCategorias implements Reportable {

	
	private Hashtable<String, MapaLugares> mapaCat;
	private String zonita;
	
	
	/**
	 * Constructor que recibe un string de la zona (comuna) con la que se hará el mapa
	 * @param zona : la comuna de los lugares dónde se hará el mapa
	 * @throws SQLException
	 */
	public MapaCategorias(String zona) throws SQLException {
		zonita = zona;
		mapaCat = new Hashtable<String, MapaLugares>();
		importar(zona);
	}
	
	/**
	 * Agrega un lugar a partir de sus parámetros.
	 * 
	 * @param id : el id del lugar a ingresar.
	 * @param nombre : el nombre del local.
	 * @param dir : la dirección del local en formato String[], se considera de largo 4 y en orden: calle + numero, comuna, region, pais.
	 * @param cat : categoría del local.
	 * @param lat : latitud del lugar.
	 * @param lng : longitud del lugar.
	 * @param desc : descripción del local.
	 * @return Una referencia al lugar agregado.
	 * @throws SQLException
	 * @throws PlaceAlreadyTakenException : si el lugar ya está agregado en el mapa, se lanza la excepción.
	 */
	public Lugar agregar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc ) throws SQLException, PlaceAlreadyTakenException {
		
		MapaLugares m = mapaCat.get(cat);
		if(m != null) {
			return m.agregar(id, nombre, dir, cat, lat, lng, desc);
		}
		m = new MapaLugares(cat, dir[1]);
		mapaCat.put(cat, m);
		return m.agregar(id, nombre, dir, cat, lat, lng, desc);
		
	}
	
	
	/**
	 * Modifica un lugar del sistema
	 * @param id : id del lugar
	 * @param nombre : nuevo nombre del local
	 * @param cat : nueva categoria del local
	 * @param zona : nueva zona del local
	 * @param desc : nueva descripcion del local
	 * @throws PlaceException en caso de que el lugar no exista, se lanza la excepción.
	 * @throws SQLException
	 */
	public Lugar modificar(String id, String nombre, String cat, String desc ) throws PlaceException, SQLException {
		
		Lugar l = buscarLugar(id);
		
		if(l == null)
			throw new PlaceException("El lugar no existe en el sistema");
		
		l.actualizar(nombre, cat, desc);
		
		eliminarLugar(id);
		
		MapaLugares m = mapaCat.get(l.getCategoria());
		if(m == null) {
			m = new MapaLugares(l.getCategoria(), l.getComuna());
			mapaCat.put(l.getCategoria(), m);
		}
		
		m.agregar(l);
		
		return l;
		
		

	}
	
	
	/**
	 * Elimina un lugar del mapa.
	 * @param id : el id del lugar a eliminar.
	 * @return La referencia al lugar eliminado.
	 */
	public Lugar eliminarLugar(String id) {
		ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		Lugar lugar;
		for(MapaLugares m : l) {
			if( (lugar = m.eliminarLugar(id)) != null )
				return lugar;
		}
		
		return null;
	}

	
	/**
	 * Busca y retorna un lugar a partir de su id.
	 * @param idLugar : el id del lugar a buscar.
	 * @return La referencia al lugar buscado.
	 * @throws PlaceException En caso de que el lugar no exista, se lanza la excepción.
	 */
	public Lugar buscarLugar(String idLugar) throws PlaceException {
		ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		
		for(MapaLugares m : l) {
			Lugar lugar = m.buscarLugar(idLugar);
			if(lugar != null)
				return lugar;
		}
		
		throw new PlaceException("La categoría del lugar no se encuentra en el sistema.");
	}
	
	
	/**
	 * Encuentra y retorna todos los lugares que coincidan en una categoría dada.
	 * @param cat : la categoría que se busca.
	 * @return Un arrayList<Lugar> con todos los lugares coincidentes.
	 */
	public ArrayList<Lugar> obtenerLugares(String cat){
		MapaLugares m = mapaCat.get(cat);
		if(m == null)
			return new ArrayList<Lugar>();
		return m.obtenerLugares();
	}
	
	
	/**
	 * Importa todos los lugares de una zona determinada a la base de datos, agregándolos al mapa en sus categorías correspondientes.
	 * @param zona : la zona(comuna) de los lugares a importar.
	 * @throws SQLException En caso de que la conexión a la base de datos falle, se lanza la excepción.
	 */
	private void importar(String zona) throws SQLException {
		DbHandler db = new DbHandler();
		
		ResultSet rs = db.categorias();
		String s;
		
		while(rs.next()) {
			s = rs.getString("categoria");
			mapaCat.putIfAbsent(s, new MapaLugares(s, zona));
		}
	}
	
	@Override
	public void generarReporte(String path) {
		
		//se crea la instancia del documento.
		Document pdf = new Document();
		FileOutputStream archivo;
		try {
			
			//se abre el documento
			archivo = new FileOutputStream(path);
		
			PdfWriter.getInstance(pdf, archivo);
		
			pdf.open();
		
			//se agrega el autor y la fecha de creación.
			pdf.addAuthor("Aplicación guía turística.");
			pdf.addCreationDate();
			
			//se crea un nuevo párrafo, será el que contenga todo el texto.
			Paragraph parrafo = new Paragraph();
			
			//se agrega todo el contenido del documento.
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Reporte de categorías actuales.", catFont));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Reporte generado por : " + System.getProperty("usr.nombre") + ", " + new Date(), smallBold));
			parrafo.add(new Paragraph(" "));
			
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Lista de categorías donde hay lugares de la base de datos en la zona de " + zonita + ": ", smallBold));
			
			
			Set<String> zonas = mapaCat.keySet();
			int i = 1;
			
			//se agregan las categorías presentes en este mapa.
			for(String s : zonas) {
				parrafo.add(new Paragraph(" "));
				parrafo.add(new Chunk(i + ") " , redFont));
				parrafo.add(new Chunk(s + ".\n", smallBold));
				i++;
			}
			
			//se adhiere el párrafo al documento y se cierra.
			pdf.add(parrafo);
			pdf.close();
			
		
		}catch(DocumentException | FileNotFoundException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public String reportePantalla() {
		String s =  "Reporte de categorías actuales\n"
				+ "Reporte generado por : " + System.getProperty("usr.nombre") + ", " + new Date() + "\n"
				+ "\n\n"
				+ "Lista de categorías donde residen los lugares de la base de datos en la zona de " + zonita + ":\n\n";
	
		Set<String> zonas = mapaCat.keySet();
		int i = 1;
	
		for(String s2 : zonas) {
			s = s.concat(i + ") " + s2 + ".\n");
			i++;
		}
		return s;
	}

	
	/**
	 * Genera un reporte de las categorías.
	 * @param b : Busqueda.
	 */
	public void reporte(Busqueda b) {
		
		if(b.getTipo().equals("Categorias")) {
			VentanaReporte v = new VentanaReporte(this);
			v.setVisible(true);
			return;
		}
		
			
		
		
		
		
	}

	/**
	 * Elimina los comentarios de un usuario en todos los lugares presentes en el mapa.
	 * @param usr : el usuario al que hay que eliminar los comentarios.
	 * @throws SQLException
	 */
	public void eliminarComentario(String usr) throws SQLException {
		ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		
		for(MapaLugares m : l) {
			m.eliminarComentario(usr);
		}
	}

}
