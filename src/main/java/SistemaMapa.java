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
	
	
	public void registrar(String usr, String pass) throws UserRegisterFailureException {
		db.registrarUsuario(usr, pass);
	}
	
	public void registrar(Lugar l) throws SQLException, PlaceAlreadyTakenException {
		db.ingresarLugar(l);
	}
	
	
	
	public ArrayList<Lugar> obtenerLugares(String cat, String zona){
		return lugares.obtenerLugares(cat, zona);
	}
	
	
	
	
	public Lugar buscar(Lugar l) throws SQLException {
		return db.buscar(l);
	}
	
	public void eliminar(Lugar l) throws SQLException {
		db.eliminar(l);
	}
	
	public void modificar(Lugar l) throws SQLException, PlaceException {
		db.actualizarLugar(l);
	}
	
	public void modificar(String coment, Comentario c, Lugar l, String p) throws SQLException {
		db.modificarComentario(coment, c, l, p);
	}
	
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
