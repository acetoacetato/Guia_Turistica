package main.java;


import java.io.*;
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

import ventanas.VentanaReporte;

public class MapaZona implements Reportable {

	private Hashtable<String, MapaCategorias> mapita;
	
	public MapaZona() throws SQLException {
		mapita = new Hashtable<String, MapaCategorias>();
		importar();
	}
	
	
	public void agregar(Object o) {
		// TODO Auto-generated method stub

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

	public void reporte(Busqueda b) {
		if( b.getTipo().equals("Zonas")) {
			VentanaReporte v = new VentanaReporte(this);
			v.setVisible(true);
			return;
		}else {
			String zona = b.getParametro();
			MapaCategorias m = mapita.get(zona);
			m.reporte(b);
			return;
		}
		
		
		
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
