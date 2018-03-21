package ventanas;

import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import main.java.CuentaUsuario;
import main.java.DbHandler;


public class VentanaUsuario extends JFrame {
	
	private JPanel contentPane;
	private DbHandler db;
	private CuentaUsuario usr;
	
	
	public VentanaUsuario(DbHandler database, CuentaUsuario cta) {
		
		setTitle(" ");
		db = database;
		usr = cta;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 283);
				
		Container contentPane = getContentPane();
		this.setVisible(true);
		
		
		//Los respectivos botones con su posición en x,y y tamaño respectivamente
		JButton boton1 = new JButton();
		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaAtracciones ventanaAtrac = new VentanaAtracciones();
				ventanaAtrac.setVisible(true);
				
			}
		});
		
		boton1.setBounds(50, 110, 60, 60);
		
		JButton boton2 = new JButton();
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaHoteles ventanaHot = new VentanaHoteles();
				ventanaHot.setVisible(true);
				
			}
		});
		boton2.setBounds(160, 110, 60, 60);
		
		JButton boton3 = new JButton();
		boton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaVidaNoc ventanaVida = new VentanaVidaNoc();
				ventanaVida.setVisible(true);
				
			}
		});
		boton3.setBounds(380, 110, 60, 60);
		
		JButton boton4 = new JButton();
		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaRestaurant ventanaRest = new VentanaRestaurant();
				ventanaRest.setVisible(true);
				
			}
		});
		boton4.setBounds(270, 110, 60, 60);
		
		//Busca las imagenes respectivas para las categorias
		ImageIcon imagen = new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/Atracciones.png"));
		ImageIcon imagen2 = new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/Hoteles.png"));
		ImageIcon imagen3 = new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/Restaurantes.png"));
		ImageIcon imagen4 = new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/Vida_Nocturna.png"));
		
		//Setea las imagenes en las dimesiones del botón
		Icon icon1 = new ImageIcon(imagen.getImage().getScaledInstance(boton1.getWidth(), boton1.getHeight(), Image.SCALE_DEFAULT));
		Icon icon2 = new ImageIcon(imagen2.getImage().getScaledInstance(boton2.getWidth(), boton2.getHeight(), Image.SCALE_DEFAULT));
		Icon icon3 = new ImageIcon(imagen3.getImage().getScaledInstance(boton3.getWidth(), boton3.getHeight(), Image.SCALE_DEFAULT));
		Icon icon4 = new ImageIcon(imagen4.getImage().getScaledInstance(boton4.getWidth(), boton4.getHeight(), Image.SCALE_DEFAULT));
		
		//Setea un icono al botón
		boton1.setIcon(icon1);
		boton2.setIcon(icon2);
		boton3.setIcon(icon3);
		boton4.setIcon(icon4);
		
		JLabel lblNewLabel = new JLabel(" \u00BFQu\u00E9 deseas hacer hoy?");
		lblNewLabel.setForeground(new Color(255, 0, 255));
		lblNewLabel.setBounds(91, 42, 299, 31);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		
		JLabel lblAtracciones = new JLabel("Atracciones");
		lblAtracciones.setForeground(new Color(153, 51, 153));
		lblAtracciones.setBounds(42, 180, 94, 14);
		lblAtracciones.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		JLabel lblHoteles = new JLabel("Hoteles");
		lblHoteles.setForeground(new Color(153, 51, 153));
		lblHoteles.setBounds(165, 180, 76, 14);
		lblHoteles.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		JLabel lblVidaNocturna = new JLabel("Vida Nocturna");
		lblVidaNocturna.setForeground(new Color(153, 51, 153));
		lblVidaNocturna.setBounds(250, 180, 108, 14);
		lblVidaNocturna.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		JLabel lblRestaurantes = new JLabel("Restaurantes");
		lblRestaurantes.setForeground(new Color(153, 51, 153));
		lblRestaurantes.setBounds(370, 180, 87, 14);
		lblRestaurantes.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		
		contentPane.setLayout(null);
		contentPane.add(lblNewLabel);
		
	    contentPane.add(boton1);
		contentPane.add(boton2);
		contentPane.add(boton3);
	    contentPane.add(boton4);
		
		JLabel lblDasd = new JLabel("Bienvenido " + cta.getNombreUsuario());
		lblDasd.setForeground(new Color(255, 0, 255));
		lblDasd.setBounds(10, 11, 360, 31);
		lblDasd.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		
		contentPane.add(lblDasd);

		
		contentPane.add(lblAtracciones);
		contentPane.add(lblHoteles);
		contentPane.add(lblVidaNocturna);
		contentPane.add(lblRestaurantes);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/fondo1.png")));
		lblNewLabel_1.setBounds(0, 0, 505, 245);
		getContentPane().add(lblNewLabel_1);
		
		
		
	}
}
