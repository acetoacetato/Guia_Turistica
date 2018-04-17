package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.CuentaUsuario;
import main.java.DbHandler;
import main.java.Lugar;
import main.java.SistemaMapa;
import main.java.Usuario;
import main.java.VentanaCampos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VentanaUsuario extends JFrame implements VentanaCampos {

	private JPanel contentPane;
	
	private SistemaMapa sistema;
	//comboBox para elegir las zonas a las que ingresar
	private JComboBox<String> zonaBox;

	/**
	 * Create the frame.
	 */
	public VentanaUsuario(SistemaMapa sis) {
		
		sistema = sis;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//mensaje de bienvenida al usuario
		JLabel lblBienvenida = new JLabel("Bienvenido/a, " + sistema.getNombreUsuario() + "!");
		lblBienvenida.setBounds(15, 16, 170, 20);
		contentPane.add(lblBienvenida);
		
		//se inicializa el comboBox que contiene las ubicaciones disponibles
		zonaBox = new JComboBox<String>();
		zonaBox.setModel(new DefaultComboBoxModel<String>(new String[] {"zona", "Valpara\u00EDso", "Quilpu\u00E9", "Vi\u00F1a del mar"}));
		zonaBox.setBounds(118, 138, 170, 26);
		contentPane.add(zonaBox);
		
		
		JLabel lbldndeDeseasBuscar = new JLabel("\u00BFD\u00F3nde deseas buscar?");
		lbldndeDeseasBuscar.setBounds(26, 91, 203, 20);
		contentPane.add(lbldndeDeseasBuscar);
		
		//botón para realizar una búsqueda de los lugares en una zona determinada
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!verificarCampos()) {
					JOptionPane.showMessageDialog(null,"No se ha seleccionado una zona.", 
							"Error al buscar",
                            JOptionPane.INFORMATION_MESSAGE);
					
					
					return;
				}
				
				try {
					/*
					 * se crea un ArrayList con las categorías, cada espacio de este es un ArrayList de lugares
					 */
					
					sistema.cargarLugares((String)zonaBox.getSelectedItem());
					setVisible(false);
					
					//se inicia una ventana de categorías
					VentanaCategorias ventanaCat = new VentanaCategorias(sistema);
					ventanaCat.setVisible(true);
					
				} catch (SQLException e) {
					// En caso que falle la conexión con la base de datos, no hay ucho que hacer
					System.out.println("no se pudo ejecutar :/");
					e.printStackTrace();
					return;
				}
				
			}
		});
		btnBuscar.setBounds(130, 199, 115, 29);
		contentPane.add(btnBuscar);
		
		//botón para volver al menú de inicio de sesión
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				VentanaInicioSesion ventanaInicio = new VentanaInicioSesion();
				ventanaInicio.setVisible(true);
			}
		});
		btnVolver.setBounds(298, 199, 115, 29);
		contentPane.add(btnVolver);
	}
	
	//verifica que se haya seleccionado una zona, en caso contrario retorna false
	public boolean verificarCampos() {
		String s = (String)zonaBox.getSelectedItem();
		if(s.equals("zona"))
			return false;
		return true;
	}
	
	//limpia el comboBox de zona hasta el inicio
	public void limpiarCampos() {
		zonaBox.setSelectedIndex(0);
	}
	
}
