package main.java;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/*
 * Clase que maneja el icono del mapa, crea la url con los parámetros necesarios y guarda la imágen generada
 * 
 * 		Utiliza la api de mapas estáticos de google:
 * 
 * 			https://developers.google.com/maps/documentation/static-maps/intro
 * 
 * */
public class MapHandler {

	//key de api, necesaria para poder usar la api de mapas estáticos de google
	private static String apiKey = "AIzaSyC4ClGpPgudrfyIWrO528j9DxXLiLQ3KlU";
	
	//el tamaño de la imágen generada
	private static String size = "410x297" ;
	
	//genera la url inicial, con la key de la api y el tamaño
	private String dirIn = "https://maps.googleapis.com/maps/api/staticmap?key=" 
						   + apiKey 
						   + "&size=" 
						   + size 
						   + "&zoom=18&maptype=roadmap";
	private String dir;

	
	private ImageIcon map;
	
	
	//constructor de pruebas, centrado en el ibc
	public MapHandler() throws IOException {
			dir = dirIn
				  + "center=IBC,+valparaiso";
		URL url;
		
		//se crea una url con la direccion completa
    	url = new URL(dir);
    	
    	//se lee la imagen que entrega la url y se crea el ícono con esta
		map = new ImageIcon( ImageIO.read(url) );
	}
	
	
	//constructor con un string de direccion determinado, se asume que es info suficiente para que se genere el mapa
	public MapHandler(String direccion) throws IOException {
		
		//se genera la url, que no puede contener espacios(se reemplazan con +)
		//ni acentos, que se reemplazan por sus homólogos sin ellos ni ñ que se remplaza por n
		 dir = dirIn
			   + "&center=" 
			   + direccion.replaceAll(" ", "+")
			   			  .replaceAll("á", "a")
			   			  .replaceAll("é", "e")
			   			  .replaceAll("í", "i")
			   			  .replaceAll("ó", "o")
			   			  .replaceAll("ú", "u")
			   			  .replaceAll("ñ", "n");
		URL url;
		
		//se genera la url con la direccion 
    	url = new URL(dir);
    	
    	//se obtiene la imágen que entrega la url y se guarda en map
		map = new ImageIcon( ImageIO.read(url) );
	}
	
	
	
	
	
	public ImageIcon getMapa() {
		return this.map;
	}
	
	public String getUrl() {
		return this.dir;
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
