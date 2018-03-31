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
		
		//se realiza la búsqueda del lugar a partir del string 'lugar'
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
	
	
	
	/*
	
	public static void main(String[] args) throws ApiException, InterruptedException, IOException, SQLException {
		
		
		BufferedReader lector = new BufferedReader( new InputStreamReader(System.in) );
		String lugar;
		
		
		System.out.println("Ingrese lugar: ");
		lugar = lector.readLine();
	
		
		//abrimos un contexto para usar la api con la key generada
		GeoApiContext context = new GeoApiContext.Builder()
		    .apiKey("AIzaSyAXUXkUsFImgP_B1pIMco8PrRhefSO1oJ8")
		    .build();
		
		//obtenemos los resultados de la búsqueda del lugar y el contexto
		GeocodingResult[] results =  GeocodingApi.geocode(context,lugar).await();
		
		//creamos un Gson para manipular los resultados que entrega la busqueda
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		//dividimos la direccion del primer resultado en varios strings
		//y los ingresamos en nuestro array 'direccion'
		String[] direccion = gson.toJson(results[0].formattedAddress ).split(",");
		
		//imprimimos la dirección
		System.out.println("la dirección : ");
		for(int i=0 ; i < direccion.length ; i++) {
			direccion[i] = direccion[i].replaceAll("\"","");
			System.out.print(direccion[i] + " , ");
		}
		
		//imprimimos la ubicación en términos de longitud y latitud, esta se entrega por result[0].geometry.location.(lat/lng)
		System.out.println(  "\n la ubicacion : " 
							 + gson.toJson(results[0].geometry.location.lat 
							 + " , " 
							 + gson.toJson(results[0].geometry.location.lng)
						 ));
		
		
		//consultamos si se desea agregar la dirección a la base de datos
		System.out.println("Está seguro que quiere ingresar el lugar a la base de datos? [Y/n] : ");
		if( lector.readLine().matches("y")) {
			
			//se abre la conección a la base de datos
			MysqlDataSource dbLugares = new MysqlDataSource();
			dbLugares.setUser("sql10226436");
			dbLugares.setPassword("SS1Yd6sPyi");
			dbLugares.setServerName("sql10.freemysqlhosting.net");
			Connection coneccion = dbLugares.getConnection();
			
			//statement para hacer las consultas
			Statement stmt = coneccion.createStatement();
			
			ResultSet rs = null;
			stmt.executeQuery("use sql10226436");
			
			try {
				//stmt.executeUpdate("insert into sql10226436.Lugar (casa, comuna, region, pais, latitud, longitud)" + " values ('AAAAAAAno','valparaíso', 'Región de valparaíso', 'Chile', -33.4, -71.6 );");
				
				//se usa smt.executeUpdate() para hacer modificaciones a la base de datos, los querys normales se usan con stmt.executeQuery()
				stmt.executeUpdate("insert into sql10226436.Lugar (id, casa, comuna, region, pais, latitud, longitud)" 
									+ " values (" + "'"
									+ gson.toJson(results[0].placeId).replaceAll("\"","") + "', '"
									+ direccion[0] + "', '" 
									+ direccion[1] + "', '" 
									+ direccion[2] + "', '" 
									+ direccion[3] + "', "
									+ gson.toJson(results[0].geometry.location.lat).toString() + ", "
									+ gson.toJson(results[0].geometry.location.lng).toString()+ ");"
									
						);
				
				
				
				//en caso de que la ubicación ya haya sido ingresada anteriormente, se maneja la excepción 
			}catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
				System.out.println("el lugar ya existe en la base de datos");
			}catch(Exception e) {
				System.out.println(e + "\nNo se ha podido ingresar el lugar.\n\n");
				
			}
			
			
			
			
			
			
			/*while(rs.next()) {
				System.out.println(rs.getString("casa"));
			}
			
			if(rs != null)
				rs.close();
			stmt.close();
			coneccion.close();
		}*/
		
		/*
		 * Conección a la base de datos
		 * */
		
		/*
		
		MysqlDataSource dato = new MysqlDataSource ();
		dato.setUser("root");
		dato.setPassword("%4c3t04c3t4t0%");
		dato.setServerName("127.0.0.1");
		Connection coneccioncita = dato.getConnection();
		System.out.println("falló");

		Statement stmt = coneccioncita.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("CREATE TABLE examplee (id INT, data VARCHAR(100));");
		}catch(Exception e) {
			System.out.println("falló");
		}
		*/
		
		
	

}


