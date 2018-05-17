package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Colecciones.MapaComentariosUsuario;

public class Usuario extends CuentaUsuario {
	
	private MapaComentariosUsuario comentariosMap;

  public Usuario ( String usuario, String password ) throws SQLException {
		super ( usuario, password );
		comentariosMap= new MapaComentariosUsuario(getNombreUsuario());
	}

  	public Usuario(ResultSet rs) throws SQLException {
  		super(rs);
  		comentariosMap= new MapaComentariosUsuario(getNombreUsuario());
  	}
  	
	public String tipoCuenta() {
		return "Usuario";
	}
	
	@Override
	public String informacionCuenta() {
		return "Cuenta Usuario " + this.getNombreUsuario();
	}
	
	public ArrayList<Comentario> getComentarios() {
		return comentariosMap.valores();
	}
	
	public void modificar(String comentAct, String points, String lug) throws SQLException {
		comentariosMap.modificar(comentAct,points,lug );
		
	}

}
