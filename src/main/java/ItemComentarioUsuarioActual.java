package main.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ItemComentarioUsuarioActual extends ItemComentario {

	private JButton bttonActualizar;
	private JButton bttonComentUp;
	private JButton bttonComentDown;
	private SistemaMapa sistema;
	private Lugar lugar;
	
	
	/**
	 * Constructor por defecto del Item, el item contiene labels y paneles para representar el nombre del usuario, la puntuación del comentario y el comentario.
	 * Se crea el comentario avisando que no hay un comentario del usuario hecho.
	 * @param sis : Referencia a una instancia de SistemaMapa.
	 * @param X : coordenada x de donde se debe comenzar a construir el item.
	 * @param Y : coordenada y de donde se debe comenzar a construir el item.
	 * @param l : lugar de donde se está haciendo el comentario.
	 */
	public ItemComentarioUsuarioActual(SistemaMapa sis, int X, int Y, Lugar l) {
		super(new Comentario(sis), X, Y);
		lugar = l;
		sistema = sis;
		item();
	}
	
	
	/**
	 * Constructor de Item, el item contiene labels y paneles para representar el nombre del usuario, la puntuación del comentario y el comentario.
	 * @param sis : Referencia a una instancia de SistemaMapa.
	 * @param X : coordenada x de donde se debe comenzar a construir el item.
	 * @param Y : coordenada y de donde se debe comenzar a construir el item.
	 * @param l : lugar de donde se está haciendo el comentario.
	 */
	public ItemComentarioUsuarioActual(Comentario comentario, int X, int Y,SistemaMapa sis, Lugar l) {
		super(comentario, X, Y);
		lugar = l;
		sistema = sis;
		item();
	}
	
	
	/**
	 * Crea el item.
	 */
	private void item() {
		
		this.comentarios.setEditable(true);
		bttonActualizar = new JButton();
		bttonActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String comentAct= comentarios.getText();
				String points= puntuacion.getText();
				if(sistema == null)
					return;
				try {
					sistema.modificar(lugar, comentAct, points);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			
			}
		});
		
		bttonActualizar.setBounds(500, 300, 95, 20);
		bttonActualizar.setText("Comentar");
		
		bttonComentUp = new JButton();
		bttonComentUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				puntuacion.setText("1");
			}
		});
		
		bttonComentUp.setBounds(450, 150, 50, 20);
		bttonComentUp.setText("^");
		
		bttonComentDown = new JButton();
		bttonComentDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				puntuacion.setText("-1");
			}
		});
		
		bttonComentDown.setBounds(450, 170, 50, 20);
		bttonComentDown.setText("v");
	
	}
	
	
	@Override
	public void agregarEnPanel(JPanel p) {
		p.add(getUser());
		p.add(getScroll1());
		p.add(getPuntuacion());
		p.add(getBttonActualizar());
		p.add(getBttonComentUp());
		p.add(getBttonComentDown());

	}
	
	
	
	
	public JButton getBttonActualizar() {
		return bttonActualizar;
	}
	public void setBttonActualizar(JButton bttonActualizar) {
		this.bttonActualizar = bttonActualizar;
	}
	public JButton getBttonComentUp() {
		return bttonComentUp;
	}
	public void setBttonComentUp(JButton bttonComentUp) {
		this.bttonComentUp = bttonComentUp;
	}
	public JButton getBttonComentDown() {
		return bttonComentDown;
	}
	public void setBttonComentDown(JButton bttonComentDown) {
		this.bttonComentDown = bttonComentDown;
	}

	
	
}
