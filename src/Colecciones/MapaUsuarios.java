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
	

	
	public void agregar(String usr, String pass) throws UserRegisterFailureException, SQLException {
		CuentaUsuario cta = new Usuario(usr, pass);
		if(mapaUser.putIfAbsent(cta.getNombreUsuario(), cta) != null)
			throw new UserRegisterFailureException("El usuario ya se encuentra registrado.");
		
	}
	
	public void agregar(String usr, String pass, boolean adm) throws UserRegisterFailureException, SQLException {
		CuentaUsuario cta = (adm)? new Administrador(usr, pass) : new Usuario(usr, pass);
		if(mapaUser.putIfAbsent(cta.getNombreUsuario(), cta) != null)
			throw new UserRegisterFailureException("El usuario ya se encuentra registrado.");
	}
	
	public void quitar(String usr) {
		mapaUser.remove(usr);
	}

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

	public void reporte(Busqueda b) {
		
		VentanaReporte v = new VentanaReporte(this);
		v.setVisible(true);
		return;
		
	}

	private void importar() throws SQLException {
		DbHandler db = new DbHandler();
		ResultSet rs = db.usuarios();
		while(rs.next()){
			CuentaUsuario user = (rs.getBoolean("adm"))? new Administrador(rs) : new Usuario(rs);
			mapaUser.putIfAbsent(user.getNombreUsuario(), user);
		}
	}



	public void eliminarComentario(Comentario comentario) {
		CuentaUsuario usr = mapaUser.get(comentario.getUsr());
		usr.eliminarComentario(comentario);
	}



	public CuentaUsuario buscar(String usr) throws UserFindException {
		CuentaUsuario cta = mapaUser.get(usr);
		if(cta == null)
			throw new UserFindException();
		return cta;
	}



	public void eliminar(String usr) {

		mapaUser.remove(usr);
		
	}
	
	

}
