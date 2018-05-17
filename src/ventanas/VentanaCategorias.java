package ventanas;

import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Interfaces.VentanaCampos;
import excepciones.PlaceException;
import main.java.Busqueda;
import main.java.SistemaMapa;


public class VentanaCategorias extends JFrame implements VentanaCampos{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SistemaMapa sistema;
	private JComboBox<String> comboBox;
	
	private static String[] cat = { "atraccion", "dormir", "comida", "vida_nocturna" };
	
	public VentanaCategorias(SistemaMapa sis) {
		
		setTitle("Seleccione zona y categoría.");
		sistema = sis;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 505, 265);
		
		Container contentPane = getContentPane();
		this.setVisible(true);
		
	/*
	 * 	{ "atraccion", "dormir", "comida", "vida_nocturna" }
	 * */
		
		//Los respectivos botones con su posici�n en x,y y tama�o respectivamente
		JButton botonAtracciones = new JButton();
		botonAtracciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//se abre una ventana con el t�tulo de atracciones, la lista de lugares, la cuenta de usuario,
				//el index de inicio donde comienza a mostrar los lugares (siempre en un principio 0)
				//el index de la categor�a de la ventana y la base de datos para volver de ah�
				ventanaLugares(0);
				
			}
		});
		
		botonAtracciones.setBounds(50, 110, 60, 60);
		
		JButton botonDormir = new JButton();
		botonDormir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//se abre una ventana con el t�tulo de "D�nde dormir", la lista de lugares, la cuenta de usuario,
				//el index de inicio donde comienza a mostrar los lugares (siempre en un principio 0)
				//el index de la categor�a de la ventana y la base de datos para volver de ah�
				ventanaLugares(1);
				
			}
		});
		botonDormir.setBounds(160, 110, 60, 60);
		
		
		JButton botonComida = new JButton();
		botonComida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ventanaLugares(2);
				
				
			}
		});
		botonComida.setBounds(270, 110, 60, 60);
		
		JButton botonVidaNoc = new JButton();
		botonVidaNoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ventanaLugares(3);
				
			}
		});		
		botonVidaNoc.setBounds(380, 110, 60, 60);
		
		
		
		//Busca las imagenes respectivas para las categorias
		ImageIcon imagen = new ImageIcon(VentanaCategorias.class.getResource("/Imagenes/Atracciones.png"));
		ImageIcon imagen2 = new ImageIcon(VentanaCategorias.class.getResource("/Imagenes/Hoteles.png"));
		ImageIcon imagen3 = new ImageIcon(VentanaCategorias.class.getResource("/Imagenes/Restaurantes.png"));
		ImageIcon imagen4 = new ImageIcon(VentanaCategorias.class.getResource("/Imagenes/Vida_Nocturna.png"));
		
		//Setea las imagenes en las dimesiones del bot�n
		Icon icon1 = new ImageIcon(imagen.getImage().getScaledInstance(botonAtracciones.getWidth(), botonAtracciones.getHeight(), Image.SCALE_DEFAULT));
		Icon icon2 = new ImageIcon(imagen2.getImage().getScaledInstance(botonDormir.getWidth(), botonDormir.getHeight(), Image.SCALE_DEFAULT));
		Icon icon3 = new ImageIcon(imagen3.getImage().getScaledInstance(botonComida.getWidth(), botonComida.getHeight(), Image.SCALE_DEFAULT));
		Icon icon4 = new ImageIcon(imagen4.getImage().getScaledInstance(botonVidaNoc.getWidth(), botonVidaNoc.getHeight(), Image.SCALE_DEFAULT));
		
		//Setea un icono al bot�n
		botonAtracciones.setIcon(icon1);
		botonDormir.setIcon(icon2);
		botonComida.setIcon(icon3);
		botonVidaNoc.setIcon(icon4);
		
		JLabel lblPregunta = new JLabel(" \u00BFQu\u00E9 deseas hacer hoy?");
		lblPregunta.setForeground(new Color(255, 0, 255));
		lblPregunta.setBounds(160, 11, 320, 31);
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
				VentanaInicioSesion ventanaUsr = new VentanaInicioSesion(sistema);
				setVisible(false);
				ventanaUsr.setVisible(true);
			}
		});
		btnVolver.setBounds(406, 210, 89, 23);
		getContentPane().add(btnVolver);
		
		
		
		
	    //se crea el mensaje de bienvenida al usuario
		JLabel lblBienvenida = new JLabel(sistema.getNombreUsuario() + ",");
		lblBienvenida.setForeground(new Color(255, 0, 255));
		lblBienvenida.setBounds(12, 11, 360, 31);
		lblBienvenida.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		
		
		//se agregan los labels 
		contentPane.add(lblBienvenida);
		contentPane.add(lblAtracciones);
		contentPane.add(lblHoteles);
		contentPane.add(lblVidaNocturna);
		contentPane.add(lblRestaurantes);
		contentPane.add(lblPregunta);
		
		//se setea un layout vac�o
		contentPane.setLayout(null);
		
		//se agregan los botones
	    contentPane.add(botonAtracciones);
		contentPane.add(botonDormir);
		contentPane.add(botonComida);
	    contentPane.add(botonVidaNoc);
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("zona");
		for( String s : sistema.zonas() ) {
			comboBox.addItem(s);
		}
		comboBox.setBounds(67, 54, 274, 26);
		getContentPane().add(comboBox);
		
		JButton btnReporteZonas = new JButton("Reporte zonas");
		btnReporteZonas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					sistema.generarReporte(new Busqueda("Zonas"));
				} catch (PlaceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReporteZonas.setBounds(31, 206, 131, 27);
		getContentPane().add(btnReporteZonas);
		
		JButton btnReporteCategorias = new JButton("Reporte categorias");
		btnReporteCategorias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sistema.generarReporte(new Busqueda("Categorias", (String)comboBox.getSelectedItem()));
				} catch (PlaceException e1) {
					
					JOptionPane.showMessageDialog(null, e1.getMessage(), 
							"ERROR",
	                        JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnReporteCategorias.setBounds(188, 208, 167, 25);
		getContentPane().add(btnReporteCategorias);
		
		//se crea y agrega el fondo 
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon(VentanaCategorias.class.getResource("/Imagenes/fondo1.png")));
		lblFondo.setBounds(0, 0, 505, 245);
		getContentPane().add(lblFondo);
		
		
		
	}

	
	private void ventanaLugares(int categoria) {
		VentanaLugares ventanaRest = new VentanaLugares(cat[categoria], (String)comboBox.getSelectedItem(), sistema);
		ventanaRest.setVisible(true);
	}

	@Override
	public boolean verificarCampos() {
		int i = comboBox.getSelectedIndex();
		return (i != 0);
	}



	@Override
	public void limpiarCampos() {
		comboBox.setSelectedIndex(0);
		
	}
}
