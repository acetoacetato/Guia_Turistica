package main.java;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.mysql.jdbc.jdbc2.optional.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


/*
 * Clase que maneja la api de google de geolocalización
 * 
 * 		https://developers.google.com/maps/documentation/geocoding/intro
 * 		https://github.com/googlemaps/google-maps-services-java
 * 	
 * */

public class MapApi{
	
	//contexto para buscar los lugares
	private GeoApiContext contexto;
	//lugar que contiene todo la información que se retornará al usar buscaLugar() 
	private Lugar lugar;
	
	
	//constructor por defecto, inicializa el contexto con la key de la api
	//e inicializa el lugar con sus valores por defecto
	public MapApi() {
		contexto = new GeoApiContext.Builder()
				   .apiKey("AIzaSyAXUXkUsFImgP_B1pIMco8PrRhefSO1oJ8")
				   .build();
		lugar = new Lugar();
	}
	
	
	//función que busca el lugar a partir de un string
	public Lugar buscaLugar(String lugar) throws ApiException, InterruptedException, IOException {
		
		//se realiza la búsqueda del lugar a partir del string 'lugar' y se toma el primer resultado
		GeocodingResult resultado =  GeocodingApi.geocode(contexto, lugar).await()[0];
		
		//se crea un Gson para manejar el resultado
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		//se crea un arreglo de strings con los parámetros del formattedAdres
		//el formato es: "direccion, comuna, region, pais"
		String[] direccion = gson.toJson(resultado.formattedAddress)
							 .replaceAll(", ", ",")
							 .replaceAll("\"", "")
							 .split(",") ;
		
		//se cambian los valores de 'lugar' por los del resultado de búsqueda y se retorna el objeto lugar
		this.lugar.setId(resultado.placeId);
		this.lugar.setDir(direccion);
		this.lugar.setLat( resultado.geometry.location.lat );
		this.lugar.setLng( resultado.geometry.location.lng );
		
		
							 
		return this.lugar;
	}
	
	
	
	
		
	

}


