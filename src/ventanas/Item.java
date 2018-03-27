package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import main.java.CuentaUsuario;
import main.java.Lugar;
import main.java.Usuario;

public class Item {

	private JLabel nombreLocal;
	private JLabel rating;
	private JButton btnLugar;
	private int corX;
	private int corY;
	public JLabel getNombreLocal() {
		return nombreLocal;
	}

	public void setNombreLocal(JLabel nombreLocal) {
		this.nombreLocal = nombreLocal;
	}

	public JLabel getRating() {
		return rating;
	}

	public void setRating(JLabel rating) {
		this.rating = rating;
	}

	public JButton getBtnLugar() {
		return btnLugar;
	}

	public void setBtnLugar(JButton btnLugar) {
		this.btnLugar = btnLugar;
	}

	private Lugar place;
	private CuentaUsuario usrAccnt;
	
	public Item (Lugar lugar, int X, int Y, CuentaUsuario usr){
		
		nombreLocal = new JLabel(lugar.getNombreLocal());
		rating = new JLabel( Integer.toString(lugar.getPuntuacion()) );
		place = lugar;
		usrAccnt = usr;
		btnLugar = new JButton ();
		
		btnLugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaLugar vtnLugar = new VentanaLugar(place, usrAccnt);
				vtnLugar.setVisible(true);
			}
		});
		
		
		this.corX = X;
		this.corY = Y;
		
		nombreLocal.setBounds(X, Y, 40, 10);
		rating.setBounds(X+50, Y, 20, 10);
		btnLugar.setBounds(X+100, Y, 10, 10);
		
	}
	
	
	
		
}
