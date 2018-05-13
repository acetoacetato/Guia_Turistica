package Interfaces;

//interfaz utilizada por ventanas que tienen campos completables por usuarios
public interface VentanaCampos {
	
	//verifica que todos los campos est�n completados correctamente
	public boolean verificarCampos();
	
	//vac�a el contenido de todos los campos de la ventana
	public void limpiarCampos();
}
