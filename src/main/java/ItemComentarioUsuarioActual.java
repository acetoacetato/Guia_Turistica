package main.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

import ventanas.VentanaLugar;

public class ItemComentarioUsuarioActual extends ItemComentario {

	private JButton bttonActualizar;
	private JButton bttonComentUp;
	private JButton bttonComentDown;
	private DbHandler db;
	private Lugar lugar;
	private Comentario com;
	public ItemComentarioUsuarioActual(Comentario comentario, int X, int Y,DbHandler dataBase, Lugar l) {
		super(comentario, X, Y);
		com = comentario;
		lugar = l;
		db = dataBase;
		this.comentarios.setEditable(true);
		bttonActualizar = new JButton();
		bttonActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String comentAct= comentarios.getText();
				String points= puntuacion.getText();
				try {
					
					if(db == null)
						return;
					db.modificarComentario(comentAct, com, lugar, points);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
			}
		});
		
		bttonActualizar.setBounds(500, 300, 60, 60);
		bttonActualizar.setText("Aceptar");
		
		bttonComentUp = new JButton();
		bttonComentUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				puntuacion.setText("1");
			}
		});
		
		bttonComentUp.setBounds(600, 300, 60, 60);
		bttonComentUp.setText("^");
		
		bttonComentDown = new JButton();
		bttonComentDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				puntuacion.setText("-1");
			}
		});
		
		bttonComentDown.setBounds(650, 300, 60, 60);
		bttonComentDown.setText("v");
	
		
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
