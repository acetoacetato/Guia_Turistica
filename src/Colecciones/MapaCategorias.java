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

public class MapaCategorias implements Reportable {

	private Hashtable<String, MapaLugares> mapaCat;
	private String zonita;
	public MapaCategorias(String zona) throws SQLException {
		zonita = zona;
		mapaCat = new Hashtable<String, MapaLugares>();
		importar(zona);
	}
	
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
	 * @throws PlaceException
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
	
	
	
	public Lugar eliminarLugar(String id) {
		ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		Lugar lugar;
		for(MapaLugares m : l) {
			if( (lugar = m.eliminarLugar(id)) != null )
				return lugar;
		}
		
		return null;
	}

	public Lugar buscarLugar(String idLugar) throws PlaceException {
		ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		
		for(MapaLugares m : l) {
			Lugar lugar = m.buscarLugar(idLugar);
			if(lugar != null)
				return lugar;
		}
		
		throw new PlaceException("La categoría del lugar no se encuentra en el sistema.");
	}
	
	public void quitar(Object o) {
		return;   

	}

	public void modificar(Object o) {

	}
	
	public ArrayList<Lugar> obtenerLugares(String cat){
		MapaLugares m = mapaCat.get(cat);
		if(m == null)
			return new ArrayList<Lugar>();
		return m.obtenerLugares();
	}
	
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
			parrafo.add(new Paragraph("Reporte de categorías actuales.", catFont));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Reporte generado por : " + System.getProperty("usr.nombre") + ", " + new Date(), smallBold));
			parrafo.add(new Paragraph(" "));
			
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Lista de categorías donde residen los lugares de la base de datos en la zona de " + zonita + ": ", smallBold));
			
			
			Set<String> zonas = mapaCat.keySet();
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

	
	public void reporte(Busqueda b) {
		
		if(b.getTipo().equals("Categorias")) {
			VentanaReporte v = new VentanaReporte(this);
			v.setVisible(true);
			return;
		}else {
			String s = b.getParametro();
			MapaLugares m = mapaCat.get(s);
			m.reporte(b);
			
		}
		
			
		
		
		
		
	}

	public void eliminarComentario(String usr) throws SQLException {
		ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		
		for(MapaLugares m : l) {
			m.eliminarComentario(usr);
		}
	}

}
