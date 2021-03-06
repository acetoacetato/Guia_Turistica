package Colecciones;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Interfaces.Reportable;
import excepciones.UserFindException;
import excepciones.UserRegisterFailureException;
import main.java.Administrador;
import main.java.Busqueda;
import main.java.Comentario;
import main.java.CuentaUsuario;
import main.java.DbHandler;
import main.java.Usuario;
import ventanas.VentanaReporte;

public class MapaUsuarios implements Reportable {
	
	private Hashtable<String, CuentaUsuario> mapaUser;
	
	
	
	public MapaUsuarios() throws SQLException{
		mapaUser = new Hashtable<String, CuentaUsuario>();
		importar();
	}
	
	/**
	 * Agrega un usuario normal (no administrador) al mapa.
	 * @param usr : nombre del usuario.
	 * @param pass : contraseña del usuario.
	 * @throws UserRegisterFailureException En caso de que el usuario ya exista en el mapa, lanza la excepción.
	 * @throws SQLException
	 */
	public void agregar(String usr, String pass) throws UserRegisterFailureException, SQLException {
		CuentaUsuario cta = new Usuario(usr, pass);
		if(mapaUser.putIfAbsent(cta.getNombreUsuario(), cta) != null)
			throw new UserRegisterFailureException("El usuario ya se encuentra registrado.");
		
	}
	
	
	/**
	 * Agrega un usuario con permisos extra.
	 * @param usr : nombre del usuario.
	 * @param pass : contraseña del usuario.
	 * @param adm : valor booleano, true para crear un administrador y false para un usuario normal.
	 * @throws UserRegisterFailureException En caso de que el usuario ya exista en el mapa, lanza la excepción.
	 * @throws SQLException
	 */
	public void agregar(String usr, String pass, boolean adm) throws UserRegisterFailureException, SQLException {
		CuentaUsuario cta = (adm)? new Administrador(usr, pass) : new Usuario(usr, pass);
		if(mapaUser.putIfAbsent(cta.getNombreUsuario(), cta) != null)
			throw new UserRegisterFailureException("El usuario ya se encuentra registrado.");
	}
	
	/**
	 * Quita un usuario del mapa.
	 * @param usr : nombre del usuario a eliminar.
	 */
	public void quitar(String usr) {
		mapaUser.remove(usr);
	}

	
	/**
	 * Modifica un usuario, no se modifica el nombre de usuario.
	 * @param usr : nombre del usuario a modificar.
	 * @param pass : nueva contraseña del usuario.
	 * @param adm : nuevo permiso, true para administrador y false para usuario normal.
	 * @throws SQLException
	 */
	public void modificar(String usr, String pass, boolean adm) throws SQLException {
		CuentaUsuario cta = (adm)? new Administrador(usr, pass) : new Usuario(usr, pass);
		mapaUser.put(usr, cta);
	}
	
	
	@Override
	public void generarReporte(String path) {
		Document pdf = new Document();
		
		FileOutputStream archivo;
		try {
			archivo = new FileOutputStream(path);
			
			PdfWriter.getInstance(pdf, archivo);
			
			pdf.open();
			
			pdf.addAuthor("Aplicación guía turística.");
			pdf.addCreationDate();
			
			Paragraph parrafo = new Paragraph();
			
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Reporte de Usuarios", catFont));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Reporte generado por : " + System.getProperty("usr.nombre") + ", " + new Date(), smallBold));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph(" "));
			parrafo.add(new Paragraph("Lista de Usuarios en la base de datos:", smallBold));
			
			Set<String> usuarios = mapaUser.keySet();
			int i = 1;
			for(String s : usuarios) {
				parrafo.add(new Paragraph(" "));
				parrafo.add(new Chunk(i + ") " + s , smallBold));
				parrafo.add(new Chunk(" (" + mapaUser.get(s).tipoCuenta() + ").\n", redFont));
				i++;
			}
			pdf.add(parrafo);
			pdf.close();
			
		}catch(DocumentException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	@Override
	public String reportePantalla() {
		
		String s =  "Reporte de usuarios actuales.\n"
				+ "Reporte generado por : " + System.getProperty("usr.nombre") + ", " + new Date() + "\n"
				+ "\n\n"
				+ "Lista de usuarios en la base de datos :\n\n";
	
		Set<String> zonas = mapaUser.keySet();
		int i = 1;
	
		for(String s2 : zonas) {
			s = s.concat(i + ") " + s2 + " (" + mapaUser.get(s2).tipoCuenta() + ").\n");
			i++;
		}
		return s;
		
	}

	/**
	 * Abre la pantalla de reporte de usuarios.
	 */
	public void reporte(Busqueda b) {
		
		VentanaReporte v = new VentanaReporte(this);
		v.setVisible(true);
		return;
		
	}

	/**
	 * ingresa todos los usuarios de la base de datos al sistema.
	 * @throws SQLException
	 */
	private void importar() throws SQLException {
		DbHandler db = new DbHandler();
		ResultSet rs = db.usuarios();
		while(rs.next()){
			CuentaUsuario user = (rs.getBoolean("adm"))? new Administrador(rs) : new Usuario(rs);
			mapaUser.putIfAbsent(user.getNombreUsuario(), user);
		}
	}


	/**
	 * Elimina un comentario.
	 * @param comentario comentario a eliminar.
	 */
	public void eliminarComentario(Comentario comentario) {
		CuentaUsuario usr = mapaUser.get(comentario.getUsr());
		usr.eliminarComentario(comentario);
	}


	/**
	 * Busca un usario y lo retorna.
	 * @param usr : el nombre del usuario a buscar.
	 * @return Una referencia al usuario buscado.
	 * @throws UserFindException : Si el usuario no se encuentra en el mapa, se lanza la excepción.
	 */
	public CuentaUsuario buscar(String usr) throws UserFindException {
		CuentaUsuario cta = mapaUser.get(usr);
		if(cta == null)
			throw new UserFindException();
		return cta;
	}


	/**
	 * Elimina un usuario del mapa.
	 * @param usr : nombre del usuario a eliminar.
	 */
	public void eliminar(String usr) {

		mapaUser.remove(usr);
		
	}
	
	

}
