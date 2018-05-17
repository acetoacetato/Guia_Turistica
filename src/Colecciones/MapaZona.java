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
	 * Agrega un Lugar a la colección
	 * @param a
	 * @param bs
	 * @throws SQLException 
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
	
	public Lugar modificar(String id, String nombre,  String cat, String desc) throws PlaceException, SQLException{
		
		String zona = obtenerZona(id);
		if(zona == null)
			throw new PlaceException("El lugar no existe en el sistema.");
		MapaCategorias m = mapita.get(zona);
		if(m == null) 
			throw new PlaceException("Fallo al encontrar la zona del lugar.");
		
		return m.modificar(id, nombre, cat,  desc);
	}
	
	public String obtenerZona(String id){
		ArrayList<MapaCategorias> l = new ArrayList<MapaCategorias>(mapita.values());
		
		for(MapaCategorias m : l) {
			Lugar lugar = m.buscarLugar(id);
			if(lugar != null)
				return lugar.getComuna();
		}
		
		return null;
	}
	
	public Lugar eliminarLugar(String id) {
		ArrayList<MapaCategorias> l = new ArrayList<MapaCategorias>(mapita.values());
		Lugar lugar;
		for(MapaCategorias m : l) {
			if( (lugar = m.eliminarLugar(id)) != null )
				return lugar;
		}
		
		return null;
	}
	
	public Lugar buscarLugar(String idLugar) {
		ArrayList<MapaCategorias> l = new ArrayList<MapaCategorias>(mapita.values());
		
		for(MapaCategorias m : l) {
			Lugar lugar = m.buscarLugar(idLugar);
			if(lugar!=null)
				return lugar;
		}
		
		return null;
	}
	
	public void agregar(String a) {
		
	}

	public void quitar(Object o) {
		// TODO Auto-generated method stub

	}

	
	public void modificar(Object o) {
		// TODO Auto-generated method stub

	}
	
	public ArrayList<Lugar> obtenerLugares(String cat, String zona){
		MapaCategorias m = mapita.get(zona);
		if(m == null)
			return new ArrayList<Lugar>();
		return m.obtenerLugares(cat);
	}
	
	
	
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




	

}
