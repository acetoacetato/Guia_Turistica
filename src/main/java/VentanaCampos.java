package main.java;

//interfaz utilizada por ventanas que tienen campos completables por usuarios
public interface VentanaCampos {
	
	//verifica que todos los campos están completados correctamente
	public boolean verificarCampos();
	
	//vacía el contenido de todos los campos de la ventana
	public void limpiarCampos();
}
