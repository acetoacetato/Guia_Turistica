package main.java;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import ventanas.VentanaLugar;

public class ItemLugar {
	
	private Icon icon1;
	private JLabel fondo;
	private ImageIcon imgn;
	private JLabel nombreLocal;
	private JLabel rating;
	private JButton btnLugar;
	private int corX;
	private int corY;
	private int corZ;
	

	private Lugar place;
	private CuentaUsuario usrAccnt;
	
	public ItemLugar (Lugar lugar, int X, int Y, int Z, CuentaUsuario usr){
		
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
		btnLugar.setBounds(X+300, Y, 30, 30);
		
		imgn = new ImageIcon(ItemLugar.class.getResource("/Imagenes/lupa.jpg"));
	    icon1 = new ImageIcon(imgn.getImage().getScaledInstance(btnLugar.getWidth(), btnLugar.getHeight(), Image.SCALE_DEFAULT));
		
		btnLugar.setIcon(icon1);
		
		fondo = new JLabel("");
		fondo.setIcon(new ImageIcon(ItemLugar.class.getResource("/Imagenes/fondo2.png")));
		//getContentPane().add(fondo);
		
		
		this.corX = X;
		this.corY = Y;
		this.corZ = Z;
		
		nombreLocal.setBounds(X, Y, 80, 30);
		rating.setBounds(X+150, Y, 20, 30);
		
		fondo.setBounds(5, Z, 450, 65);
	}
	
	
	public JLabel getFondo() {
		return fondo;
	}
	
	public void setFondo(JLabel fondo) {
		this.fondo = fondo;
	}
	
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
	
	
		
}
