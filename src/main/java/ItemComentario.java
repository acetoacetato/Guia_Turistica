package main.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import excepciones.PlaceException;
import ventanas.VentanaComentarios;

public class ItemComentario {
	
	protected JLabel user;
	protected JTextPane comentarios;
	protected JScrollPane scroll1;
	protected JScrollPane scroll2;
	protected JTextField puntuacion;
	protected JButton btnEliminar = null;
	

	
	public ItemComentario(Comentario comentario,  int X, int Y) {
		
		item(comentario, X, Y);
	}
	
	public ItemComentario(Comentario comentario,  int X, int Y, SistemaMapa sis, Lugar l, JFrame jf) {
		
		item(comentario, X, Y);
		btnEliminar = new JButton();
		btnEliminar.setBounds( X + user.getText().length() + 90, Y, 42, 20 );
		btnEliminar.setText("X");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sis.eliminarComentario(comentario);
					JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente el comentario.",
							"Exito eliminando.",
                            JOptionPane.OK_OPTION);
					VentanaComentarios vc = new VentanaComentarios(l, sis);
					jf.setVisible(false);
					vc.setVisible(true);
					
					
				} catch (PlaceException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), 
							"No se pudo eliminar el comentario.",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		
		
	}
	
	private void item(Comentario comentario,  int X, int Y) {
		// se crea el label del comentario con el nombre del usuario.
				user = new JLabel(comentario.getUsr() + ":");
				
				
				puntuacion = new JTextField(""+comentario.getPt());
				
				//se crea un scroll negando el horizontal para que baje la ventana en caso de sobrepasar
				//los limites del campo de texto vertical.
				scroll1 = new JScrollPane();
				
				
				//se crea un textPane donde recibe el comentario del usuario X
				//se le niega que el usuario que est� logueado pueda editar los comentarios de otros usuarios
				//se le setea el scroll1 a comentarios para el tema de la barra
				comentarios = new JTextPane();
				comentarios.setEditable(false);
				
				
				comentarios.setText(comentario.getCom());
				puntuacion.setEditable(false);
				
				scroll1.setViewportView(comentarios);
				scroll2 = new JScrollPane();
				scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				
				
				
				//se le dan las coordenadas a los labels, y los textpane correspondientes
				user.setBounds(X+25, Y, 250, 30);
				puntuacion.setBounds(X, Y, 30, 30);
				scroll1.setBounds(X, Y+50, 250, 100);
	}
	
	
	public void agregarEnPanel(JPanel p) {
		p.add(getUser());
		p.add(getScroll1());
		p.add(getPuntuacion());
		if(btnEliminar != null) {
			p.add(btnEliminar);
		}

	}


	public JTextField getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(JTextField puntuacion) {
		this.puntuacion = puntuacion;
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
