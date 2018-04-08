package main.java;

import java.awt.ScrollPane;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class ItemComentario {
	
	private JLabel user;
	private JTextPane comentarios;
	private JTextPane tuComen;
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	
	public ItemComentario(Comentario comentario, CuentaUsuario usr, int X, int Y) {
		
		// se crea el label del comentario con el nombre del usuario.
		user = new JLabel(comentario.getUsr() + ":");
		
		//se crea un scroll negando el horizontal para que baje la ventana en caso de sobrepasar
		//los limites del campo de texto vertical.
		scroll1 = new JScrollPane();
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//se crea un textPane donde recibe el comentario del usuario X
		//se le niega que el usuario que esté logueado pueda editar los comentarios de otros usuarios
		//se le setea el scroll1 a comentarios para el tema de la barra
		comentarios = new JTextPane();
		comentarios.setEditable(false);
		scroll1.setViewportView(comentarios);
		comentarios.setText(comentario.getCom());
		
		//lo mismo que el scroll1
		scroll2 = new JScrollPane();
		scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		//lo mismo que el campo de texto "comentarios", la diferencia, este si se puede editar
		//pues es el comentario del usuario logueado en ese momento
		//se le setea el scroll2
		tuComen = new JTextPane();
		scroll2.setViewportView(tuComen);
		
		//se le dan las coordenadas a los labels, y los textpane correspondientes
		user.setBounds(X, Y, 50, 30);
		scroll1.setBounds(X, Y, 250, 100);
		scroll2.setBounds(X+300, Y, 250, 100);
	}
	



	public JLabel getUser() {
		return user;
	}

	public void setUser(JLabel user) {
		this.user = user;
	}

	public JTextPane getComentarios() {
		return comentarios;
	}

	public void setComentarios(JTextPane comentarios) {
		this.comentarios = comentarios;
	}

	public JTextPane getTuComen() {
		return tuComen;
	}

	public void setTuComen(JTextPane tuComen) {
		this.tuComen = tuComen;
	}

	public JScrollPane getScroll1() {
		return scroll1;
	}

	public void setScroll1(JScrollPane scroll1) {
		this.scroll1 = scroll1;
	}

	public JScrollPane getScroll2() {
		return scroll2;
	}

	public void setScroll2(JScrollPane scroll2) {
		this.scroll2 = scroll2;
	}
	
	
	
	
}
