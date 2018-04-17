package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import excepciones.UserRegisterFailureException;
import main.java.DbHandler;
import main.java.SistemaMapa;
import main.java.VentanaCampos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaRegistro extends JFrame implements VentanaCampos {

	private JPanel contentPane;
	private JTextField campoUsr;
	private JPasswordField campoPass;
	private	SistemaMapa sistema;
	/**
	 * Create the frame.
	 */
	public VentanaRegistro(SistemaMapa sis) {
		
		sistema = sis;
		//se guarda la referencia al DbHandler
		
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
		
		//botón para ingresar al nuevo usuario a la base de datos
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				//si los campos contienen ' o ", o bien están vacíos, se manda error 
				if(!verificarCampos()) {
					JOptionPane.showMessageDialog(null, "Porfavor, ingrese usuario y contraseña, no pueden contener \" ni \'", 
							"Error al registrar",
                            JOptionPane.OK_OPTION);
					return;
				}
				
				String usr = campoUsr.getText().trim();
				String pass = campoPass.getText().trim();

				try {
					
					sistema.registrar(usr, pass);
					// se verifica que el usuario no está registrado
					JOptionPane.showMessageDialog (null, "Se ha registrado al nuevo usuario.", 
							"Registro completo",
                            JOptionPane.OK_OPTION);
					
					//retornar a la pantalla de inicio de sesión
					setVisible(false);
					VentanaInicioSesion ventanaInicio = new VentanaInicioSesion();
					ventanaInicio.setVisible(true);
					return;
					
					//en caso de que falle el registro se lanza el aviso
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
	
	
	public boolean verificarCampos() {
		String usr = campoUsr.getText();
		String pass = campoPass.getText();
		
		if(usr.equals("") || usr.contains("\"\'") ||
				pass.equals("") || usr.contains("\'\"")) 
			return false;
		
		return true;
	}
	
	public void limpiarCampos() {
		campoUsr.setText("");
		campoPass.setText("");
	}

}
