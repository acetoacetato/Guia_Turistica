package main.java;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JFrame;

import com.google.maps.errors.ApiException;

import Colecciones.MapaUsuarios;
import Colecciones.MapaZona;
import excepciones.PlaceAlreadyTakenException;
import excepciones.PlaceException;
import excepciones.UserCheckException;
import excepciones.UserRegisterFailureException;
import excepciones.WithoutNextException;
import ventanas.VentanaAdmin;
import ventanas.VentanaCategorias;

public class SistemaMapa {
	private DbHandler db;
	private CuentaUsuario usuario;
	private boolean admin;
	private MapaZona lugares;
	private MapaUsuarios usuarios;
	private MapApi api;
	
	public SistemaMapa() throws SQLException {
		lugares = new MapaZona();
		usuarios = new MapaUsuarios();
		db = new DbHandler();	
		api = new MapApi();
	}
	
	public void iniciarSesion(String usr, String pass)  throws UserCheckException, SQLException{

		usuario = db.iniciarSesion(usr, pass);
		admin = usuario.tipoCuenta().equals("Administrador");
		
		JFrame ventana;
		System.setProperty("usr.nombre", usuario.getNombreUsuario());
		if(admin) 
			ventana = new VentanaAdmin(this);
		else
			ventana = new VentanaCategorias(this);
		
		ventana.setVisible(true);
		
	}
	
	public void generarReporte(Busqueda b) {
		switch(b.getTipo()) {
		
			case "Usuarios":
				//TODO: reporte mapa usuarios
			case "Comentarios":
				//TODO: reporte mapa comentarios
			default:
				lugares.reporte(b);
		}
	}
	
	public String[] zonas() {
		return lugares.zonas();
	}
	
	/**
	 * Agrega un nuevo usuario al sistema, el usuario agregado es un usuario común
	 * @param usr : id del usuario a agregar
	 * @param pass : contraseña del usuario a agregar
	 * @throws UserRegisterFailureException 
	 */
	public void agregar(String usr, String pass) throws UserRegisterFailureException {
		usuarios.agregar(usr, pass);
	}
	
	/**
	 * Agrega un nuevo lugar al sistema.
	 * @param id : el id del lugar a agregar.
	 * @param nombre : el nombre del local a agregar.
	 * @param dir : dirección del local.
	 * @param cat : categoría del local.
	 * @param lat : latitud de ubicación del local.
	 * @param lng : longitud de ubicación del local.
	 * @param desc : descripción del local.
	 * @throws SQLException
	 * @throws PlaceAlreadyTakenException
	 */
	public void agregar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc ) throws SQLException, PlaceAlreadyTakenException {
		lugares.agregar(id, nombre, dir, cat, lat, lng, desc );
	}
	
	/**
	 * Actualiza la información de un lugar
	 * @param id : el id del lugar a modificar.
	 * @param nombre : el nuevo nombre del local.
	 * @param cat : nueva categoría del local.
	 * @param desc : nueva descripción del local.
	 */
	public void modificar(String id, String nombre, String cat, String desc) throws PlaceException {
		
		Lugar l = lugares.buscarLugar(id);
		if(l == null)
			throw new PlaceException("No existe el lugar");
		l.actualizar(nombre, cat, desc);
		lugares.eliminarLugar(id);
		
		
		
	}
	
	public void eliminarLugar(String id) {
		lugares.eliminarLugar(id);
	}
	
	public ArrayList<Lugar> obtenerLugares(String cat, String zona){
		return lugares.obtenerLugares(cat, zona);
	}
	
	
	
	
	
	
	
	
	
	
	//TODO: re hacer esto
	public Lugar obtenerLugar(String lugar) throws SQLException, ApiException, InterruptedException, IOException {
		String id = api.idLugar(lugar);
		
		Lugar l = lugares.buscarLugar(lugar);
		
		if(l !=  null) {
			System.out.println("aaaaaaaaaa: " + l.getNombreLocal());
			return l;
		}
		
		return api.buscaLugar(lugar);
	}
	
	public boolean getAdmin() {
		return admin;
	}
	
	public String getNombreUsuario() {
		return usuario.getNombreUsuario();
	}
	
	
	public String getInfoCuenta() {
		return usuario.informacionCuenta();
	}
}
