package main.java;

import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 * Clase que maneja el icono del mapa, crea la url con los par�metros necesarios y guarda la im�gen generada
 * 
 * 		Utiliza la api de mapas est�ticos de google:
 * 
 * 			https://developers.google.com/maps/documentation/static-maps/intro
 * @author acetoacetato
 * */
public class MapHandler {

	//key de api, necesaria para poder usar la api de mapas est�ticos de google
	private static String apiKey = "AIzaSyC4ClGpPgudrfyIWrO528j9DxXLiLQ3KlU";
	
	//el tama�o de la im�gen generada
	private static String size = "410x297" ;
	
	//genera la url inicial, con la key de la api y el tama�o
	private String dirIn = "https://maps.googleapis.com/maps/api/staticmap?key=" 
						   + apiKey 
						   + "&size=" 
						   + size 
						   + "&zoom=18&maptype=roadmap";
	private String dir;

	
	private ImageIcon map;
	
	
	/**
	 * Constructor por defecto, coloca el mapa en el centro del ibc
	 * @throws IOException
	 */
	public MapHandler() throws IOException {
			dir = dirIn
				  + "center=IBC,+valparaiso,+chile";
		URL url;
		
		//se crea una url con la direccion completa
    	url = new URL(dir);
    	
    	//se lee la imagen que entrega la url y se crea el �cono con esta
		map = new ImageIcon( ImageIO.read(url) );
	}
	
	
	/**
	 * constructor con un string de direccion determinado, se asume que es info suficiente para que se genere el mapa
	 * @param direccion : dirección del lugar donde se centrará el mapa. 
	 * @throws IOException
	 */
	public MapHandler(String direccion) throws IOException {
		
		//se genera la url, que no puede contener espacios(se reemplazan con +)
		//ni acentos, que se reemplazan por sus hom�logos sin ellos ni � que se remplaza por n
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
    	
    	//se obtiene la im�gen que entrega la url y se guarda en map
		map = new ImageIcon( ImageIO.read(url) );
	}
	
	
	
	
	/**
	 * Retorna el mapa generado.
	 * @return Un ImageIcon con el mapa dentro.
	 */
	public ImageIcon getMapa() {
		return this.map;
	}
	
	/**
	 * Retorna la url del lugar.
	 * @return String del url del mapa.
	 */
	public String getUrl() {
		return this.dir;
	}
	
}









