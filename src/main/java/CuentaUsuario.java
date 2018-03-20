package main.java;
public abstract class CuentaUsuario {
	private String nombreUsuario;
	private String contrasena;

	public CuentaUsuario( String usuario, String contrasena ) {
		this.setNombreUsuario( usuario );
		this.setContrasena( contrasena );
	}

  public abstract String tipoCuenta();

  public String getNombreUsuario() {
		return nombreUsuario;
	}

  public String informacionCuenta() {
    return "Cuenta Usuario " + getNombreUsuario();
  }

  public void destroy() {
	  nombreUsuario = null;
	  contrasena = null;
  }
  
  public void setNombreUsuario( String nombreUsuario ) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena( String contrasena ) {
		this.contrasena = contrasena;
	}

}
	
