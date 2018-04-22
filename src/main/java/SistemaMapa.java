package main.java;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.google.maps.errors.ApiException;

import excepciones.PlaceAlreadyTakenException;
import excepciones.PlaceException;
import excepciones.UserCheckException;
import excepciones.UserRegisterFailureException;
import excepciones.WithoutNextException;

public class SistemaMapa {
	private Hashtable<String, ArrayList<Lugar>> mapaCatLugares;
	private DbHandler db;
	private CuentaUsuario usuario;
	private boolean admin;
	private MapaComentarios comentarios;
	
	
	public SistemaMapa() throws SQLException {
		mapaCatLugares = new Hashtable<String, ArrayList<Lugar>>();
		db = new DbHandler();
		
	}
	
	public void iniciarSesion(String usr, String pass) throws UserCheckException, SQLException{

		usuario = db.iniciarSesion(usr, pass);
		admin = usuario.tipoCuenta().equals("Administrador");
	}
	
	public void registrar(String usr, String pass) throws UserRegisterFailureException {
		db.registrarUsuario(usr, pass);
	}
	
	public void registrar(Lugar l) throws SQLException, PlaceAlreadyTakenException {
		db.ingresarLugar(l);
	}
	
	public void cargarLugares(String cat) throws SQLException {
		mapaCatLugares = db.cargaLugares(cat);
	}
	
	public ArrayList<Lugar> obtenerLugares(String cat){
		return mapaCatLugares.get(cat);
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
