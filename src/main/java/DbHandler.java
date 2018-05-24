package main.java;

import java.io.IOException;
import java.sql.*;

import java.util.ArrayList;

import com.google.maps.errors.ApiException;

import excepciones.PlaceAlreadyTakenException;
import excepciones.PlaceException;
import excepciones.UserCheckException;
import excepciones.UserRegisterFailureException;



public class DbHandler {
	
	//variables necesarias para realizar querys a la db
	private static Connection conexion = null;
	
	
	public DbHandler() {
		
		
			conexion = Db.getConexion();
		
		if(conexion == null)
			System.err.println("eerrrrroor");
		
		
		
		
		
	}
	
	public static Connection getConexion() {
		return conexion;
	}
	
	/**
	 * Retorna las zonas existentes en la base de datos.
	 * @return un String[] con las zonas existentes.
	 * @throws SQLException
	 */
	public String[] zonas() throws SQLException{
		CallableStatement cs = conexion.prepareCall("{CALL getZonas()}");
		ResultSet rs = cs.executeQuery();
		
		ArrayList<String> a = new ArrayList<String>();
		while(rs.next()) {
			a.add(rs.getString("zona"));
		}
		
		String[] s = new String[a.size()];
		
		for(int i=0 ; i < s.length ; i++) {
			s[i] = (String) a.get(i);
		}
		
		return s;
	}
	
	/**
	 * Retorna las categorías existentes en la base de datos.
	 * @return un ResultSet con las categorías existentes.
	 * @throws SQLException
	 */
	public ResultSet categorias() throws SQLException {
		CallableStatement cs = conexion.prepareCall("{CALL getCategorias()}");
		return cs.executeQuery();		
	}
	
	
	/**
	 * Retorna los lugares en la base de datos.
	 * @return un ResultSet con los lugares existentes.
	 * @throws SQLException
	 */
	public ResultSet lugares() throws SQLException {
		CallableStatement cs = conexion.prepareCall("{CALL getLugares()}");
		return cs.executeQuery();
	}
	
	/**
	 * Retorna los comentarios en la base de datos.
	 * @return un ResultSet con los comentarios existentes.
	 * @throws SQLException
	 */
	public ResultSet comentarios() throws SQLException{
		CallableStatement cs = conexion.prepareCall("{CALL getComentarios()}");
		return cs.executeQuery();
	}
	
	
	/**
	 * Retorna los usuarios en la base de datos.
	 * @return un ResultSet con los usuarios existentes.
	 * @throws SQLException
	 */
	public ResultSet usuarios() throws SQLException{
		CallableStatement cs = conexion.prepareCall("{CALL getUsuarios()}");
		return cs.executeQuery();
	}
	
	/**
	 * ingresamos un nuevo lugar a la DB, mySQL verifica si ya se ha ingresado
	 * @param l : lugar a ingresar.
	 * @return true si es que el lugar se ingresa, false si es que falla.
	 * @throws SQLException
	 * @throws PlaceAlreadyTakenException Si el lugar ya existe en la base de datos, se lanza la excepción.
	 */
	public boolean ingresarLugar(Lugar l) throws SQLException, PlaceAlreadyTakenException{
		CallableStatement cs = conexion.prepareCall("{CALL agregarLugar(?,?,?,?,?,?,?,?,?,?)}");
		String[] lugar = l.arreglo();
		
		for(int i = 0; i< lugar.length ; i++) {
			cs.setString(i+1, lugar[i]);
		}
		
		return cs.execute();
		
	}
	
	
	
	
	
	/**
	 * Registra el usuario en la base de datos, s�lo se pueden registrar usuarios normales. Recibe el nombre de usuario y la contrase�a
	 * @param usr el nombre de usuario
	 * @param pass la contraseña
	 * @throws UserRegisterFailureException si falla el ingreso al sistema.
	 */
	public void registrarUsuario(String usr, String pass) throws UserRegisterFailureException {
		
		Statement stmt;
		try {
			//se crea un statement para generar la consulta
			stmt = conexion.createStatement();
			
			//si el usuario ya est� registrado, se lanza la excepci�n UserRegisterFailureException
			verificarRegistro(usr);
			String query = "insert into Usuario (id, pass, admin) values" +
					"('" + usr 
					+ "','"+ pass + "', false);";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		
	}
	
	
	/***
	 * busca la cuenta que coincida con el inicio de sesion (usuario y password)
	 * @param usr
	 * @param pass
	 * @return
	 * @throws SQLException
	 * @throws UserCheckException
	 */
	public CuentaUsuario iniciarSesion(String usr, String pass) throws SQLException, UserCheckException {
		
		//se crea el statement para generar la consulta
		Statement stmt = conexion.createStatement();
		
		//se crea la consulta con el usuario y la contrase�a para verificar que el usuario existe
		String query = "select * from Usuario where id = '" 
				+ usr + "' and pass = '"
				+ pass + "';";
		
		//se ejecuta la consulta
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next()) {
			if(rs.getBoolean("admin"))
				return new Administrador(rs.getString("id"), rs.getString("pass"));
			else
				return new Usuario(rs.getString("id"), rs.getString("pass"));
		}
		//El ResultSet inicia en -1, rs.next() retorna true si existe l menos un resultado coincidente, por lo que si no hay coincidencias, se lanza un UserCheckException
		throw new UserCheckException("Combinaci�n usuario/contrase�a inv�lida.");
		
	}
	
	/**
	 * verifica si el usuario ya est� registrado
	 * @param usr el nombre de usuario.
	 * @return el ResultSet de los usuarios coincidentes.
	 * @throws SQLException 
	 * @throws UserRegisterFailureException en caso de que el usuario esté en la base de datos, se retorna la excepción.
	 */
	public ResultSet verificarRegistro(String usr) throws SQLException, UserRegisterFailureException{
		Statement stmt = conexion.createStatement();
		
		ResultSet rs = stmt.executeQuery("Select * from Usuario where id = '" + usr + "';");
		if(rs.next()) throw new UserRegisterFailureException("Usuario ya registrado.");
		return rs;
	}
	
	
	/**
	 * Actualiza la info del lugar
	 * @param l : el lugar a actualizar.
	 * @throws SQLException
	 * @throws PlaceException si el lugar no existe, entonces se lanza la exepción.
	 */
	public void actualizarLugar(Lugar l) throws SQLException, PlaceException {
		Statement stmt = conexion.createStatement();
		if (this.buscar(l) == null)
			throw new PlaceException("El lugar no existe en la base de datos.");
		
		stmt.executeUpdate("UPDATE Lugar "
				+ "SET nombre = '" + l.getNombreLocal() + "'"
				+ ", casa = '" + l.getDireccionPpal() + "'"
				+ ", comuna = '" + l.getComuna() + "'"
				+ ", region = '" + l.getRegion() + "'"
				+ ", pais = '" + l.getPais() + "'"
				+ ", latitud =" + l.getLat()
				+ ", longitud =" + l.getLng()
				+ ", categoria = '" + l.getCategoria() + "'"
				+ ", descripcion = '" + l.getDescripcion() + "'"
				+ "WHERE id = '" + l.getId() + "'");
	}
	
	/**
	 * Se busca un lugar por el nombre del local
	 * @param name : el nombre del lugar.
	 * @return el resultset del lugr coincidente,
	 * @throws SQLException
	 */
	public Lugar buscarLugar(String name) throws SQLException{
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("select * from Lugar where nombre = '" + name + "';");
		
		if(rs.next())
			return new Lugar(rs);
		
		else return null;

	}
	
	/**
	 * busca lugares que coincidan con una categor�a y ubicaci�n especificada por par�metro.
	 * @param cat categoría a buscar
	 * @param ubic ubicación(comuna) a buscar.
	 * @return  Retorna un ResultSet con todas las coincidencias
	 * @throws SQLException
	 */
	public ResultSet buscarLugar(String cat, String ubic) throws SQLException {
		Statement stmt = conexion.createStatement();
		String query = "SELECT * FROM Lugar WHERE categoria = '"
				 + cat + "' AND comuna = '" + ubic + "';";
		return stmt.executeQuery( query );
		
	}
	
	
	/**
	 * Genera un lugar, primero buscandolo en la api de google, y si el lugar ya existe en la base de datos.
	 * @param busqueda
	 * @return retorna los datos que hay en esta o, si es que no existe, los que hay en la base de datos de google.
	 * @throws SQLException
	 * @throws ApiException En caso de no encontrar el lugar en la api de google, lanza la excepción.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public Lugar autoCompletaLugar(String busqueda) throws SQLException, ApiException, InterruptedException, IOException {
		MapApi mapita = new MapApi();
		Lugar l;
		
		//se busca el lugar en la api de googe
		l = mapita.buscaLugar(busqueda);
		
		//Se guarda el id para buscar en la base de datos si el lugar ya existe en esta
		String id = l.getId();
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Lugar where id = '" + id + "';");
		
		//si ya existe, se retornan los datos que hay en la base de datos
		if(rs.next()) {
			return new Lugar(rs);
		}
		
		//en caso contrario, se retornan los datos que existen desde la api de google
		
			return l;
	}
	
	
	
	/**
	 * busca un lugar en la base de datos
	 * @param l : una instancia de Lugar con los parámetros a buscar.
	 * @return el lugar buscado.
	 * @throws SQLException
	 */
	public Lugar buscar(Lugar l) throws SQLException {
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Lugar WHERE id = '" + l.getId() + "'");
		if(rs.next()) 
			return new Lugar(rs);
		else return null;
	}
	
	
	
	/**
	 * elimina el lugar de la base de datos
	 * @param l : el lugar a eliminar.
	 * @throws SQLException
	 */
	public void eliminar(Lugar l) throws SQLException{
		Statement stmt = conexion.createStatement();
		stmt.executeUpdate("DELETE FROM Lugar WHERE id = '" + l.getId() + "'");
	}
	
	
	
	/**
	 * Busca los comentarios realizacons por un determinado usuario.
	 * @param idUsr : el nombre del usuario.
	 * @return
	 * @throws SQLException
	 */
	public ResultSet buscarComentariosUsuario(String idUsr) throws SQLException {
		Statement stmt = conexion.createStatement();
		return stmt.executeQuery("select * from Comentario where id_usuario = '" +  idUsr + "';");
	}
	
	
	/**
	 * verifica si un comentario de un usuario existe en un lugar determinado.
	 * @param idUsr : el nombre del usuario a buscar. 
	 * @param idLugar : el id del lugar donde se realizará la búsqueda.
	 * @return Retorna true si es que el comentario existe, false si es que no.
	 * @throws SQLException
	 */
	public boolean buscarComentario(String idUsr, String idLugar) throws SQLException{
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("Select * from Comentario where id_lugar = '" + idLugar + "' and id_usuario ='" + idUsr + "';");
		return rs.next();
	}
	
	
	/**
	 * Busca los comentarios de un lugar determinado.
	 * @param placeId el id del lugar a buscar.
	 * @return un ResultSet con los cometarios del lugar buscado.
	 * @throws SQLException
	 */
	public ResultSet buscarComentarios(String placeId) throws SQLException {
		Statement stmt = conexion.createStatement();
		return stmt.executeQuery("Select * from Comentario where id_lugar = '" + placeId + "';");
	}
	
	
	/**
	 * Calcula y retorna la puntuación actual de un determinado lugar.
	 * Las posibles puntuaciones de un comentario son 1 y -1,
	 * y el puntaje se calcula sumando todas las puntuaciones.
	 * La suma se hace mediante una consulta simple.
	 * 
	 * @param id : el id del lugar a calcular la puntuación.
	 * @return Un entero de la suma de las puntuaciones.
	 * @throws SQLException
	 */
	public int obtenerPuntuacion(String id) throws SQLException {
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT SUM(puntuacion) FROM Comentario WHERE id_lugar = '" + id + "';");
		if(rs.next()) 
			return rs.getInt("SUM(puntuacion)");
		else 
			return 0;
	}
	
	
	/**
	 * Modifica un comentario en la base de datos.
	 * @param coment El comentario nuevo
	 * @param c referencia al comentario a modificar.
	 * @param l El lugar donde pertenece el comentario.
	 * @param p la nueva puntuación.
	 * @throws SQLException
	 */
	public void modificarComentario(String coment, Comentario c, Lugar l, String p) throws SQLException {
		Statement stmt = conexion.createStatement();
		String query;
		if(buscarComentario(c.getUsr(), l.getId())) {
			query = "Update Comentario set comentario ='" + coment + "', puntuacion=" + p + " where id_usuario='" + c.getUsr() + "' and id_lugar='" + l.getId() + "';";
			
		}
		else {
			query = "Insert into Comentario (id_usuario, id_lugar, comentario, puntuacion) values( '" + c.getUsr() + "', '" + l.getId() + "', '" + coment + "', " + p+");";
			
		}
		
		stmt.executeUpdate(query);
		
	}
	
	
	/**
	 * Modifica el comntario dado, si no existe, entonces lo crea.
	 * @param coment El comentario nuevo
	 * @param c referencia al comentario a modificar.
	 * @param l El lugar donde pertenece el comentario.
	 * @param p la nueva puntuación.
	 * @throws SQLException
	 */
	public void modificarComentario(String coment, Comentario c, String idLugar, String p) throws SQLException {
		Statement stmt = conexion.createStatement();
		String query;
		if(buscarComentario(c.getUsr(), idLugar)) {
			query = "Update Comentario set comentario ='" + coment + "', puntuacion=" + p + " where id_usuario='" + c.getUsr() + "' and id_lugar='" + idLugar + "';";
			
		}
		else {
			query = "Insert into Comentario (id_usuario, id_lugar, comentario, puntuacion) values( '" + c.getUsr() + "', '" +idLugar+ "', '" + coment + "', " + p+");";
			
		}
		stmt.executeUpdate(query);
		
	}

	/**
	 * Elimina un comentario
	 * @param comentario el comentario a eliminar.
	 */
	public void eliminar(Comentario comentario) {
		String query = "DELETE FROM Comentario WHERE id_usuario = '" + comentario.getUsr() + "' AND id_lugar = '" + comentario.getPlaceId() + "';";
		Statement stmt;
		try {
			stmt = conexion.createStatement();
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Elimina un comentario dado el id del lugar y del usuario al que pertenece dicho comentario.
	 * @param id el id del lugar.
	 * @param usr el nombre de usuario.
	 * @throws SQLException
	 */
	public void eliminarComentario(String id, String usr) throws SQLException {

		
		String query = "DELETE FROM Comentario WHERE id_usuario = '" + usr + "' AND id_lugar = '" + id + "';" ;
		Statement stmt = conexion.createStatement();
		stmt.executeUpdate(query);
		
	}

	/**
	 * Modifica un usuario determinado, no se modifica el nombre.
	 * @param id el nombre de usuario
	 * @param passwd la nueva contraseña
	 * @param adm el permiso del usuario, true es administrador, false es usuario comun.
	 * @throws SQLException
	 */
	public void modificarUsuario(String id, String passwd, boolean adm) throws SQLException {

		String query = "UPDATE Usuario set pass = '" + passwd + "', admin = " + adm + " where id = '" + id + "';";
		Statement stmt = conexion.createStatement();
		stmt.executeUpdate(query);
		
	}

	
	/**
	 * Elimina un usuario de la base de datos.
	 * @param usr El nombre de usuario.
	 * @throws SQLException
	 */
	public void eliminarUsuario(String usr) throws SQLException {
		String query = "DELETE FROM Usuario WHERE id = '" + usr + "';" ;
		Statement stmt = conexion.createStatement();
		stmt.executeUpdate(query);		
	}
	
	
	
}
