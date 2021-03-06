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
	private SistemaMapa sistema;
	private Lugar place;
	
	/**
	 * Constructor del item, el item contiene un fondo, un label con el nombre del local, un label con el rating actual del local y un botón para poder entrar y ver detalles del lugar.
	 * @param lugar : el lugar al que se le está creando el item.
	 * @param X : coordenada x de donde se debe comenzar a construir el item.
	 * @param Y : coordenada y de donde se debe comenzar a construir el item.
	 * @param Z : coordenada de donde se debe comenzar a construir el item.
	 * @param sis : Referencia a una instancia de SistemaMapa, se usa para poder darle funcionalidad al botón.
	 */
	public ItemLugar (Lugar lugar, int X, int Y, int Z, SistemaMapa sis){
		
		sistema = sis;
		// se crea el label del lugar con el nombre del local/lugar de inter�s
		nombreLocal = new JLabel(lugar.getNombreLocal());
		
		//se crea un label con el rating del lugar
		rating = new JLabel( Integer.toString(lugar.getPuntuacion()) );
		
		//se guarda referencia al lugar
		place = lugar;
				
		//se crea el bot�n para ver el lugar con m�s detalle
		btnLugar = new JButton ();		
		btnLugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaLugar vtnLugar = new VentanaLugar(place, sistema);
				vtnLugar.setVisible(true);
			}
		});
		
		//se crea un icono para el fondo
		fondo = new JLabel("");
		fondo.setIcon(new ImageIcon(ItemLugar.class.getResource("/Imagenes/fondo2.png")));
		
		//se le dan las coordenadas al bot�n y labels correspondientes
		btnLugar.setBounds(X+300, Y, 30, 30);
		nombreLocal.setBounds(X, Y, 80, 30);
		rating.setBounds(X+150, Y, 20, 30);
		
		fondo.setBounds(5, Z, 450, 65);
		
		//se le pone el icono a bot�n para ver lugar en detalle
		imgn = new ImageIcon(ItemLugar.class.getResource("/Imagenes/lupa.jpg"));
		icon1 = new ImageIcon(imgn.getImage().getScaledInstance(btnLugar.getWidth(), btnLugar.getHeight(), Image.SCALE_DEFAULT));
		
		btnLugar.setIcon(icon1);
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
