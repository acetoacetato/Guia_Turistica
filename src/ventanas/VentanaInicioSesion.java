package ventanas;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import excepciones.UserCheckException;
import main.java.SistemaMapa;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;




/***
 * 
 * @author acetoacetato
 * 
 * Cuentas para iniciar sesión:
 * 
 * 	administrador:
 * 		nombre: admin
 * 		contraseña: toor
 * 
 * 	cuenta normal:
 * 		nombre: alen
 * 		contraseña: alen1
 *
 */


public class VentanaInicioSesion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField campoPass;
	private JTextField campoUsuario;
	private SistemaMapa sistema;


	
	//constructor que se llama al inicio de la aplicaci�n
	public VentanaInicioSesion(SistemaMapa sis) {
			sistema = sis;
			setResizable(false);
			ventanita();
	}

	
	
	public void ventanita() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("Registrarse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				VentanaRegistro ventanaReg = new VentanaRegistro(sistema);
				ventanaReg.setVisible(true);
				
			}
		});
		button.setBounds(306, 5, 117, 27);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial Black", Font.PLAIN, 13));
		button.setBackground(new Color(102, 153, 255));
		
		contentPane.add(button);
		/*
		 * Bot�n para iniciar sesi�n
		 * */
		JButton button_1 = new JButton("Ingresar");
		button_1.addActionListener(new ActionListener() {
			
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				if(!verificarCampos()) {
					JOptionPane.showMessageDialog(null, "Nombre de usuario y/o contrase�a vac�os o contienen car�cteres \' o \"", 
							"Error en inicio",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				//se obtienen los datos de los campos de texto y contrase�a
				String usr = campoUsuario.getText().trim();
				String pass = campoPass.getText().trim();
				
				try {
					//ResultSet rs = db.iniciarSesion(usr, pass);
					//creamos la cuenta de usuario con usr, pass y el booleano de si es usuario o administrador
					
					
					sistema.iniciarSesion(usr, pass);
					setVisible(false);
				}catch (UserCheckException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), 
								"Error en inicio",
	                            JOptionPane.ERROR_MESSAGE);
						return;
			
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), 
							"Error en inicio",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			}
		});
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("Arial Black", Font.PLAIN, 13));
		button_1.setBackground(new Color(102, 153, 255));
		button_1.setBounds(148, 205, 106, 23);
		contentPane.add(button_1);
		
		campoPass = new JPasswordField();
		campoPass.setBounds(148, 146, 106, 26);
		contentPane.add(campoPass);
		
		campoUsuario = new JTextField();
		campoUsuario.setBounds(148, 104, 106, 26);
		contentPane.add(campoUsuario);
		campoUsuario.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(64, 107, 69, 20);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(43, 149, 96, 20);
		contentPane.add(lblContrasea);
	}
	
	
	 
	 //verifica que los campos no contienen ' ni " ni est�n vacios
	 public boolean verificarCampos() {
		 String usr = campoUsuario.getText().trim();
		 @SuppressWarnings("deprecation")
		 String pass = campoPass.getText().trim();
		 if( usr.contains("\'\"") || usr.equals("") || pass.contains("\'\"") || usr.equals(""))
			 return false;
		 return true;
	 }
	 
	 
	 //limpia los campos
	 public void limpiarCampos() {
		 campoUsuario.setText("");
		 campoPass.setText("");
	 }

}


