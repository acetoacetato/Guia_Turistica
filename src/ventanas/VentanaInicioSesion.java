package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import excepciones.UserCheckException;
import main.java.Administrador;
import main.java.CuentaUsuario;
import main.java.DbHandler;
import main.java.Usuario;
import main.java.VentanaCampos;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;




/*
 * 		OJO: NO SE PUEDE CONECTAR A LA BASE DE DATOS DESDE PUCV-PRO porque puertos bloqueados
 * 
 * 		Cuentas básicas
 * 	
 * 	Admin : user: acetoacetato
 * 			pass: cBc5536652
 * 
 * 	Usuario mortal:
 * 			user: marcelo
 * 			pass: marcelo1
 * 
 * 
 * 
 * */


public class VentanaInicioSesion extends JFrame implements VentanaCampos {

	private JPanel contentPane;
	private JPasswordField campoPass;
	private JTextField campoUsuario;
	private DbHandler db;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicioSesion frame = new VentanaInicioSesion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	
	//constructor que se llama al inicio de la aplicación
	public VentanaInicioSesion() {
		try {
			//establecemos conexión con la base de datos
			db = new DbHandler();
		} catch (SQLException e1) {
			
			//si no se puede conectar, entonces no se puede iniciar la aplicación
			JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos. Verifique que tiene una conexión a internet distinta y que el puerto 3306 esté abierto", 
					"No se pudo ingresar a la base de datos",
                    JOptionPane.ERROR_MESSAGE);
			System.exit(1);
			
		}
		ventanita();
	}
	
	//constructor que se llama al volver de los menus de usuario
	public VentanaInicioSesion(DbHandler database) {
		db = database;
		
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
				VentanaRegistro ventanaReg = new VentanaRegistro(db);
				ventanaReg.setVisible(true);
				
			}
		});
		button.setBounds(306, 5, 117, 27);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial Black", Font.PLAIN, 13));
		button.setBackground(new Color(102, 153, 255));
		
		contentPane.add(button);
		/*
		 * Botón para iniciar sesión
		 * */
		JButton button_1 = new JButton("Ingresar");
		button_1.addActionListener(new ActionListener() {
			
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				if(!verificarCampos()) {
					JOptionPane.showMessageDialog(null, "Nombre de usuario y/o contraseña vacíos o contienen carácteres \' o \"", 
							"Error en inicio",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				//se obtienen los datos de los campos de texto y contraseña
				String usr = campoUsuario.getText().trim();
				String pass = campoPass.getText().trim();
				
				try {
					ResultSet rs = db.iniciarSesion(usr, pass);
					//creamos la cuenta de usuario con usr, pass y el booleano de si es usuario o administrador
					
					CuentaUsuario cta = crearUsuario(usr, pass, rs.getBoolean("admin"));
					
					//si es admin, entonces se carga la ventana principal de admin
					if(cta.tipoCuenta().equals("Administrador")) {
						setVisible(false);
						limpiarCampos();
						VentanaAdmin ventanaAdm = new VentanaAdmin(db, cta);
						ventanaAdm.setVisible(true);
						return;
						
						//en caso contrario, se carga la del usuario
					}else {
						setVisible(false);
						limpiarCampos();
						VentanaUsuario ventanaUsr = new VentanaUsuario(db, cta);
						ventanaUsr.setVisible(true);
						return;
						
					}
						
					}catch (UserCheckException e) {
					// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e.getMessage(), 
								"Error en inicio",
	                            JOptionPane.ERROR_MESSAGE);
			
				} catch (SQLException e) {
					// TODO Auto-generated catch block
                    e.printStackTrace();
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
	
	 CuentaUsuario crearUsuario(String usr, String pass, boolean adm) {
		
		 if(adm) {
			 return new Administrador(usr, pass);
		 }else {
			 return new Usuario(usr, pass);
		 }
		 
	}
	 
	 //verifica que los campos no contienen ' ni " ni están vacios
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


