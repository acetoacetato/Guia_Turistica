package main.java;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JFrame;

import com.google.maps.errors.ApiException;

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
	
	
	public SistemaMapa() throws SQLException {
		lugares = new MapaZona();
		usuarios = new MapaUsuarios();
		db = new DbHandler();		
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
	
	
	public void agregar(String usr, String pass) throws UserRegisterFailureException {
		usuarios.agregar(usr, pass);
		db.registrarUsuario(usr, pass);
	}
	
	public void agregar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc ) throws SQLException, PlaceAlreadyTakenException {
		lugares.agregar(id, nombre, dir, cat, lat, lng, desc );
	}
	
	public void modificar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc) {
		lugares.modificar(id, nombre, dir, cat, lat, lng, desc);
	}
	
	public void eliminarLugar(String id) {
		lugares.eliminarLugar(id);
	}
	
	public ArrayList<Lugar> obtenerLugares(String cat, String zona){
		return lugares.obtenerLugares(cat, zona);
	}
	
	
	
	
	
	
	
	
	
	
	//TODO: re hacer esto
	public Lugar obtenerLugar(String lugar) throws SQLException, ApiException, InterruptedException, IOException {
		
		return db.autoCompletaLugar(lugar);
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
