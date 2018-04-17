package ventanas;

import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import main.java.CuentaUsuario;
import main.java.DbHandler;
import main.java.Lugar;
import main.java.SistemaMapa;


public class VentanaCategorias extends JFrame {
	
	private JPanel contentPane;
	private SistemaMapa sistema;

	
	
	public VentanaCategorias(SistemaMapa sis) {
		
		setTitle("main user");
		sistema = sis;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 283);
				
		Container contentPane = getContentPane();
		this.setVisible(true);
		
	/*
	 * 	{ "atraccion", "dormir", "comida", "vida_nocturna" }
	 * */
		
		//Los respectivos botones con su posición en x,y y tamaño respectivamente
		JButton botonAtracciones = new JButton();
		botonAtracciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//se abre una ventana con el título de atracciones, la lista de lugares, la cuenta de usuario,
				//el index de inicio donde comienza a mostrar los lugares (siempre en un principio 0)
				//el index de la categoría de la ventana y la base de datos para volver de ahí
				VentanaLugares ventanaAtrac = new VentanaLugares( "atraccion", sistema, 0);
				ventanaAtrac.setVisible(true);
				
			}
		});
		
		botonAtracciones.setBounds(50, 110, 60, 60);
		
		JButton botonDormir = new JButton();
		botonDormir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//se abre una ventana con el título de "Dónde dormir", la lista de lugares, la cuenta de usuario,
				//el index de inicio donde comienza a mostrar los lugares (siempre en un principio 0)
				//el index de la categoría de la ventana y la base de datos para volver de ahí
				VentanaLugares ventanaHot = new VentanaLugares("domir", sistema, 0);
				ventanaHot.setVisible(true);
				
			}
		});
		botonDormir.setBounds(160, 110, 60, 60);
		
		
		JButton botonComida = new JButton();
		botonComida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaLugares ventanaRest = new VentanaLugares("comida", sistema, 0);
				ventanaRest.setVisible(true);
				
			}
		});
		botonComida.setBounds(270, 110, 60, 60);
		
		JButton botonVidaNoc = new JButton();
		botonVidaNoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaLugares ventanaNoc = new VentanaLugares("vida_nocturna", sistema, 0);
				ventanaNoc.setVisible(true);
				
			}
		});		
		botonVidaNoc.setBounds(380, 110, 60, 60);
		
		
		
		//Busca las imagenes respectivas para las categorias
		ImageIcon imagen = new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/Atracciones.png"));
		ImageIcon imagen2 = new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/Hoteles.png"));
		ImageIcon imagen3 = new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/Restaurantes.png"));
		ImageIcon imagen4 = new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/Vida_Nocturna.png"));
		
		//Setea las imagenes en las dimesiones del botón
		Icon icon1 = new ImageIcon(imagen.getImage().getScaledInstance(botonAtracciones.getWidth(), botonAtracciones.getHeight(), Image.SCALE_DEFAULT));
		Icon icon2 = new ImageIcon(imagen2.getImage().getScaledInstance(botonDormir.getWidth(), botonDormir.getHeight(), Image.SCALE_DEFAULT));
		Icon icon3 = new ImageIcon(imagen3.getImage().getScaledInstance(botonComida.getWidth(), botonComida.getHeight(), Image.SCALE_DEFAULT));
		Icon icon4 = new ImageIcon(imagen4.getImage().getScaledInstance(botonVidaNoc.getWidth(), botonVidaNoc.getHeight(), Image.SCALE_DEFAULT));
		
		//Setea un icono al botón
		botonAtracciones.setIcon(icon1);
		botonDormir.setIcon(icon2);
		botonComida.setIcon(icon3);
		botonVidaNoc.setIcon(icon4);
		
		JLabel lblPregunta = new JLabel(" \u00BFQu\u00E9 deseas hacer hoy?");
		lblPregunta.setForeground(new Color(255, 0, 255));
		lblPregunta.setBounds(50, 42, 320, 31);
		lblPregunta.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		
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
		lblVidaNocturna.setBounds(360, 180, 108, 14);
		lblVidaNocturna.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		JLabel lblRestaurantes = new JLabel("Restaurantes");
		lblRestaurantes.setForeground(new Color(153, 51, 153));
		lblRestaurantes.setBounds(255, 180, 100, 14);
		lblRestaurantes.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaUsuario ventanaUsr = new VentanaUsuario(sistema);
				setVisible(false);
				ventanaUsr.setVisible(true);
			}
		});
		btnVolver.setBounds(406, 210, 89, 23);
		getContentPane().add(btnVolver);
		
		
		
		
	    //se crea el mensaje de bienvenida al usuario
		JLabel lblBienvenida = new JLabel(sistema.getNombreUsuario() + ",");
		lblBienvenida.setForeground(new Color(255, 0, 255));
		lblBienvenida.setBounds(10, 11, 360, 31);
		lblBienvenida.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		
		
		//se agregan los labels 
		contentPane.add(lblBienvenida);
		contentPane.add(lblAtracciones);
		contentPane.add(lblHoteles);
		contentPane.add(lblVidaNocturna);
		contentPane.add(lblRestaurantes);
		contentPane.add(lblPregunta);
		
		//se setea un layout vacío
		contentPane.setLayout(null);
		
		//se agregan los botones
	    contentPane.add(botonAtracciones);
		contentPane.add(botonDormir);
		contentPane.add(botonComida);
	    contentPane.add(botonVidaNoc);
		
		//se crea y agrega el fondo 
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon(VentanaUsuario.class.getResource("/Imagenes/fondo1.png")));
		lblFondo.setBounds(0, 0, 505, 245);
		getContentPane().add(lblFondo);
		
		
		
	}
}
