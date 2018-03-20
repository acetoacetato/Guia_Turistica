package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import excepciones.UserRegisterFailureException;
import main.java.DbHandler;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaRegistro extends JFrame {

	private JPanel contentPane;
	private JTextField campoUsr;
	private JPasswordField campoPass;
	private DbHandler db;

	/**
	 * Create the frame.
	 */
	public VentanaRegistro(DbHandler database) {
		db = database;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel user = new JLabel("Usuario");
		user.setBounds(15, 39, 95, 20);
		contentPane.add(user);
		
		JLabel pass = new JLabel("Contrase\u00F1a");
		pass.setBounds(15, 75, 85, 20);
		contentPane.add(pass);
		
		campoUsr = new JTextField();
		campoUsr.setBounds(115, 36, 146, 26);
		contentPane.add(campoUsr);
		campoUsr.setColumns(10);
		
		campoPass = new JPasswordField();
		campoPass.setBounds(115, 72, 146, 26);
		contentPane.add(campoPass);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String usr = campoUsr.getText().trim();
				String pass = campoPass.getText().trim();
				if(usr.equals("") ||
					pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Porfavor, ingrese usuario y contraseña", 
							"Error al registrar",
                            JOptionPane.OK_OPTION);
					return;
				}
				
				if(pass.indexOf('\'') != -1 || usr.indexOf('\'') != -1 ) {
					JOptionPane.showMessageDialog(null, "Ni usuario ni contraseña pueden contener el caracter  ' .", 
							"Error al registrar",
                            JOptionPane.OK_OPTION);
					return;
				}
				

				try {
					ResultSet rs = db.verificarRegistro(usr);
					db.registrarUsuario(usr, pass);
					JOptionPane.showMessageDialog (null, "Se ha registrado al nuevo usuario.", 
							"Registro completo",
                            JOptionPane.OK_OPTION);
					
					//TODO: retornar a la pantalla de inicio de sesión
					setVisible(false);
					VentanaInicioSesion ventanaInicio = new VentanaInicioSesion(db);
					ventanaInicio.setVisible(true);
					return;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UserRegisterFailureException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), 
							"Registro incompleto",
                            JOptionPane.ERROR_MESSAGE);
					
					
					
				}
				
				
				
				
				
			}
		});
		btnRegistrarse.setBounds(133, 157, 115, 20);
		contentPane.add(btnRegistrarse);
	}

}
