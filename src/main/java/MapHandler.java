package main.java;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MapHandler {

	private static String apiKey = "AIzaSyC4ClGpPgudrfyIWrO528j9DxXLiLQ3KlU";
	private static String size = "410x297" ;//412x299
	private String dirIn = "https://maps.googleapis.com/maps/api/staticmap?key=" 
						   + apiKey 
						   + "&size=" 
						   + size 
						   + "&zoom=18&maptype=roadmap";
	private String dir;

	
	private ImageIcon map;
	
	
	
	public MapHandler() throws IOException {
			dir = dirIn
				  + "center=IBC,+valparaiso";
		URL url;
    	url = new URL(dir);
		map = new ImageIcon( ImageIO.read(url) );
	}
	
	public MapHandler(String direccion) throws IOException {
		 dir = dirIn
			   + "&center=" 
			   + direccion.replaceAll(" ", "+")
			   			  .replaceAll("á", "a")
			   			  .replaceAll("é", "e")
			   			  .replaceAll("í", "i")
			   			  .replaceAll("ó", "o")
			   			  .replaceAll("ú", "u");
		URL url;
    	url = new URL(dir);
    	System.out.println(url);
		map = new ImageIcon( ImageIO.read(url) );
	}
	
	
	
	
	
	public ImageIcon getMapa() {
		return this.map;
	}
	
	public String getUrl() {
		return this.dir;
	}
	
	public void setMapa(String dir) throws IOException {
		dir = dirIn+dir.replaceAll(" ","+");
		URL url = new URL(dir);
		map = new ImageIcon(ImageIO.read(url));
	}
	
	
	
	
}









/*
public static ImageIcon cargaMapa() throws IOException {
	
		dir = "https://maps.googleapis.com/maps/api/staticmap?"
				  + "center="
				  + direccion
				  //+"&size=317x250"
				  + "&size="
				  + size
				  + "&zoom=18"
				  + "&maptype=roadmap"
				  + "&key=" + apiKey;
	
	
	URL url = new URL(path);
	BufferedImage imagen = ImageIO.read(url);
	return new ImageIcon(imagen);
			
}

public static ImageIcon cargaMapa(String direccion) throws IOException {
	
		dir = "https://maps.googleapis.com/maps/api/staticmap?"
				  + "center="
				  + direccion
				  //+"&size=317x250"
				  + "&size=" + size
				  + "&zoom=18"
				  + "&maptype=roadmap"
				  + "&key=AIzaSyC4ClGpPgudrfyIWrO528j9DxXLiLQ3KlU";
	
	
	URL url = new URL(path);
	BufferedImage imagen = ImageIO.read(url);
	return new ImageIcon(imagen);
			
}*/


/*public static ImageIcon cargaMapa(String dir, String[] puntos) throws IOException {
	//	TODO: AGREGAR EL PROCESO DE PONER PUNTOS EN LA URL
	String path = "https://maps.googleapis.com/maps/api/staticmap?"
				  + "center="
				  + dir
				  //+"&size=317x250"
				  + "&size="
				  + size
				  + "&zoom=18"
				  + "&maptype=roadmap"
				  + "&key=AIzaSyC4ClGpPgudrfyIWrO528j9DxXLiLQ3KlU";
	
	
	URL url = new URL(path);
	BufferedImage imagen = ImageIO.read(url);
	return new ImageIcon(imagen);
			
}*/
