package main.java;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.google.maps.errors.ApiException;

import Colecciones.MapaUsuarios;
import Colecciones.MapaZona;
import excepciones.PlaceAlreadyTakenException;
import excepciones.PlaceException;
import excepciones.UserCheckException;
import excepciones.UserFindException;
import excepciones.UserRegisterFailureException;
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
	
	public void generarReporte(Busqueda b) throws PlaceException {
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
	 * @throws SQLException 
	 */
	public void agregar(String usr, String pass) throws UserRegisterFailureException, SQLException {
		usuarios.agregar(usr, pass);
		db.registrarUsuario(usr, pass);
	}
	
	/**
	 * Agrega un nuevo lugar al sistema y lo retorna.
	 * @param id : el id del lugar a agregar.
	 * @param nombre : el nombre del local a agregar.
	 * @param dir : dirección del local.
	 * @param cat : categoría del local.
	 * @param lat : latitud de ubicación del local.
	 * @param lng : longitud de ubicación del local.
	 * @param desc : descripción del local.
	 * @return la instancia del lugar agregado en caso de que se agregue, null en caso contrario
	 * @throws SQLException
	 * @throws PlaceAlreadyTakenException
	 */
	public Lugar agregar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc ) throws PlaceAlreadyTakenException, SQLException {
		Lugar l = lugares.agregar(id, nombre, dir, cat, lat, lng, desc );
		
		System.out.println("se pasó la excepción por la raja");
		db.ingresarLugar(l);
		return l;
	}
	
	/**
	 * Actualiza la información de un lugar
	 * @param id : el id del lugar a modificar.
	 * @param nombre : el nuevo nombre del local.
	 * @param cat : nueva tegoría del local.
	 * @param desc : nueva descripción del local.
	 * @throws SQLException 
	 */
	public void modificar(String id, String nombre, String cat, String desc) throws PlaceException, SQLException {
		Lugar l = lugares.modificar(id, nombre, cat, desc);	
		db.actualizarLugar(l);
	}
	
	public void eliminarLugar(String id) throws SQLException {
		Lugar l = lugares.eliminarLugar(id);
		db.eliminar(l);
	}
	
	/**
	 * Busca y retorna todos los lugares que coincidan con la categoría y zona dadas.
	 * @param cat : La categoría a buscar.
	 * @param zona : La zona (comuna) a buscar.
	 * @return ArrayList<Lugar>
	 */
	public ArrayList<Lugar> obtenerLugares(String cat, String zona) {
		return lugares.obtenerLugares(cat, zona);
	}
	
	
	
	
	
	
	
	
	
	
	public Lugar obtenerLugarPorDireccion(String lugar) throws SQLException, ApiException, InterruptedException, IOException{
		
			try {
				String id = api.idLugar(lugar);
				Lugar l = lugares.buscarLugar(id);
				return l;
			}catch(PlaceException e) {
				return api.buscaLugar(lugar);
			}
		
		
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

	public void modificar(Lugar l, String comentAct, String points) throws SQLException {

			l.modificar(comentAct, points, usuario.getNombreUsuario());
			usuario.modificar(comentAct, points, l.getId());
		
	}

	public Lugar obtenerLugarPorId(String id) throws PlaceException{
		return lugares.buscarLugar(id);
		
 	}

	public void eliminarComentario(Comentario comentario) throws PlaceException {
		
		usuarios.eliminarComentario(comentario);
		lugares.eliminarComentario(comentario);
		db.eliminar(comentario);
		
		
		
	}

	public CuentaUsuario buscarUsuario(String usr) throws UserFindException {
		return usuarios.buscar(usr);
	}

	public void eliminarUsuario(String usr) throws SQLException {
		
		usuarios.eliminar(usr);
		lugares.eliminarComentario(usr);
		db.eliminarUsuario(usr);
		
	}

	public void modificarUsuario(String id, String passwd, boolean adm) throws SQLException {
		usuarios.modificar(id, passwd, adm);
		db.modificarUsuario(id, passwd, adm);
	}


}
