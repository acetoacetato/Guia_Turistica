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
import ventanas.VentanaInicioSesion;
/**
 * 
 * @author acetoacetato
 *
 */
public class SistemaMapa {
	private DbHandler db;
	private CuentaUsuario usuario;
	private boolean admin;
	private MapaZona lugares;
	private MapaUsuarios usuarios;
	private MapApi api;
	
	
	/**
	 * Constructor por defecto.
	 * @throws SQLException
	 */
	public SistemaMapa() throws SQLException {
		lugares = new MapaZona();
		usuarios = new MapaUsuarios();
		db = new DbHandler();
		api = new MapApi();
	}
	
	/**
	 * Verifica que la combinación usuario/contraseña dados existe, y de ser así, se inicia la sesión en el sistema con ese usuario.
	 * @param usr : el nombre de usuario.
	 * @param pass : la contraseña.
	 * @throws UserCheckException En caso de que la combinación sea inexistente, se lanza la excepción.
	 * @throws SQLException
	 */
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
	
	
	/**
	 * Cierra sesión del sistema.
	 */
	public void cerrarSesion() {
		System.clearProperty("usr.nombre");
		admin = false;
		usuario = null;
		VentanaInicioSesion v = new VentanaInicioSesion(this);
		v.setVisible(true);
	}
	
	/**
	 * A partir de una búsqueda, se abre una ventana de reporte.
	 * @param b : la búsqueda determinada.
	 * @throws PlaceException en caso de que el lugar no se encuentre, se lanza la excepción.
	 */
	public void generarReporte(Busqueda b) throws PlaceException {
		switch(b.getTipo()) {
		
			case "Usuarios":
				usuarios.reporte(b);
				break;
			default:
				lugares.reporte(b);
				break;
		}
	}

	/**
	 * Retorna todas las zonas(comunas) disponibles actualmente en el sistema.
	 * @return Un arreglo de Strings que contiene las zonas disponibles.
	 */
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
	
	
	/**
	 * Elimina un lugar del sistema.
	 * @param id : el id del lugar a eliminar.
	 * @throws SQLException
	 */
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
	

	/**
	 * Busca y retorna un lugar del sistema a partir de su dirección formal.
	 * La dirección formal es la combinación de nombre de la calle + número de la casa + comuna 
	 * @param lugar : la dirección formal del lugar.
	 * @return Una referencia al lugar encontrado.
	 * @throws SQLException
	 * @throws ApiException En caso de que no se pueda encontrar el lugar a partir de la dirección formal, se lanza la excepción.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public Lugar obtenerLugarPorDireccion(String lugar) throws SQLException, ApiException, InterruptedException, IOException{
		
			try {
				String id = api.idLugar(lugar);
				Lugar l = lugares.buscarLugar(id);
				return l;
			}catch(PlaceException e) {
				return api.buscaLugar(lugar);
			}
		
		
	}
	
	/**
	 * Retorna si el usuario que inició sesión es administrador o no.
	 * @return Booleano con valor true si el usuario es administrador y false si no lo es.
	 */
	public boolean getAdmin() {
		return admin;
	}
	
	
	/**
	 * Retorna el nombre de usuario.
	 * @return String del nombre del usuario.
	 */
	public String getNombreUsuario() {
		return usuario.getNombreUsuario();
	}
	
	/**
	 * Retorna información de la cuenta.
	 * @return Un String de información relevante como si es administrador o no.
	 */
	public String getInfoCuenta() {
		return usuario.informacionCuenta();
	}

	/**
	 * Modifica un comentario del usuario iniciado en un determinado lugar.
	 * @param l : el lugar donde pertenece el comentario.
	 * @param comentAct : el nuevo contenido del comentario.
	 * @param points : la nueva puntuación del comentario.
	 * @throws SQLException
	 */
	public void modificar(Lugar l, String comentAct, String points) throws SQLException {

			l.modificar(comentAct, points, usuario.getNombreUsuario());
			usuario.modificar(comentAct, points, l.getId());
		
	}

	
	/**
	 * Obtiene un lugar, si es que está en el sistema, por el id de este.
	 * @param id : id del lugar a buscar.
	 * @return Una referencia al lugar coincidente.
	 * @throws PlaceException En caso de que el lugar no se encuentre en el sistema, se lanza la excepción.
	 */
	public Lugar obtenerLugarPorId(String id) throws PlaceException{
		return lugares.buscarLugar(id);
		
 	}

	/**
	 * Elimina un comentario del sistema.
	 * @param comentario : Referencia del comentario a eliminar.
	 * @throws PlaceException : en caso de que el lugar ya no se encuentre en el sistema, se lanza la excepción.
	 */
	public void eliminarComentario(Comentario comentario) throws PlaceException {
		
		usuarios.eliminarComentario(comentario);
		lugares.eliminarComentario(comentario);
		db.eliminar(comentario);
		
		
		
	}

	/**
	 * Busca y retorna un usuario buscado.
	 * @param usr : el nombre del  usuario a buscar.
	 * @return Una referencia al usuario buscado.
	 * @throws UserFindException En cado de que l usuario no se encuentre registrado en el sistema, se lanza la excepción.
	 */
	public CuentaUsuario buscarUsuario(String usr) throws UserFindException {
		return usuarios.buscar(usr);
	}

	
	/**
	 * Elimina un usuario del sistema.
	 * @param usr : el nombre del usuario a eliminar.
	 * @throws SQLException
	 */
	public void eliminarUsuario(String usr) throws SQLException {
		
		usuarios.eliminar(usr);
		lugares.eliminarComentario(usr);
		db.eliminarUsuario(usr);
		
	}

	
	/**
	 * Modifica un usuario del sistema, sólo se puede modificar la contraseña y si es administrador.
	 * @param id : el nombre del usuario.
	 * @param passwd : la nueva contraseña del usuario.
	 * @param adm : true para ser administrador, false, para ser usuario común.
	 * @throws SQLException
	 */
	public void modificarUsuario(String id, String passwd, boolean adm) throws SQLException {
		usuarios.modificar(id, passwd, adm);
		db.modificarUsuario(id, passwd, adm);
	}


}
