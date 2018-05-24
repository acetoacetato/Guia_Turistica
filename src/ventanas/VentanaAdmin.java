package ventanas;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.SistemaMapa;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaAdmin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SistemaMapa sistema;


	/**
	 * Create the frame.
	 */
	public VentanaAdmin(SistemaMapa sis) {
		setResizable(false);
		setTitle("Admin");
		sistema = sis;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenido, " + sis.getNombreUsuario());
		lblNewLabel.setBounds(5, 5, 282, 20);
		contentPane.add(lblNewLabel);
		
		JButton btnAgregar = new JButton("Administrar Lugares");
		btnAgregar.setSize(btnAgregar.getPreferredSize());
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				VentanaAdminLugares ventanaAdmL = new VentanaAdminLugares(sistema);
				ventanaAdmL.setVisible(true);
				
			} 
		});
		btnAgregar.setBounds(118, 75, 186, 29);
		contentPane.add(btnAgregar);
		
		JButton btnCerrarSesin = new JButton("Cerrar Sesi\u00F3n");
		btnCerrarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				setVisible(false);
				VentanaInicioSesion ventanaInicio = new VentanaInicioSesion(sistema);
				ventanaInicio.setVisible(true);
				
				
			}
		});
		btnCerrarSesin.setBounds(145, 199, 142, 29);
		contentPane.add(btnCerrarSesin);
		
		JButton btnAdministrarUsuarios = new JButton("Administrar Usuarios");
		btnAdministrarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaAdminUsuarios vUsr = new VentanaAdminUsuarios(sistema);
				setVisible(false);
				vUsr.setVisible(true);
			}
		});
		btnAdministrarUsuarios.setBounds(118, 126, 186, 27);
		contentPane.add(btnAdministrarUsuarios);
	}
}
