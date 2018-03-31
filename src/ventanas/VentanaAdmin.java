package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.CuentaUsuario;
import main.java.DbHandler;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaAdmin extends JFrame {

	private JPanel contentPane;
	private DbHandler db;
	private CuentaUsuario usr;


	/**
	 * Create the frame.
	 */
	public VentanaAdmin(DbHandler database, CuentaUsuario cta) {
		setTitle("Admin");
		


		db = database;
		usr = cta;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenido, " + cta.getNombreUsuario());
		lblNewLabel.setBounds(5, 5, 282, 20);
		contentPane.add(lblNewLabel);
		
		JButton btnAgregar = new JButton("Administrar Lugares");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				VentanaAdminLugares ventanaAdmL = new VentanaAdminLugares(db, usr);
				ventanaAdmL.setVisible(true);
				
			} 
		});
		btnAgregar.setBounds(15, 84, 186, 29);
		contentPane.add(btnAgregar);
		
		JButton btnQuitar = new JButton("Administrar usuarios");
		btnQuitar.setToolTipText("");
		btnQuitar.setBounds(216, 84, 197, 29);
		contentPane.add(btnQuitar);
		
		JButton btnCerrarSesin = new JButton("Cerrar Sesi\u00F3n");
		btnCerrarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				usr = null;
				setVisible(false);
				VentanaInicioSesion ventanaInicio = new VentanaInicioSesion(db);
				ventanaInicio.setVisible(true);
				destroy();
				
			}
		});
		btnCerrarSesin.setBounds(145, 199, 142, 29);
		contentPane.add(btnCerrarSesin);
	}


	public void destroy() {
		contentPane = null;
		db = null;
		usr.destroy();
		usr = null;
	}
}
