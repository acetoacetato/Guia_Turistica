package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import excepciones.PlaceException;
import excepciones.UserFindException;
import main.java.Busqueda;
import main.java.CuentaUsuario;
import main.java.SistemaMapa;

public class VentanaAdminUsuarios extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBusqueda;
	private JTextField txtId;
	private JTextField txtContrasea;
	private SistemaMapa sistema;
	private JCheckBox chckbxAdministrador;
	private JButton btnVolver;
	
	
	public VentanaAdminUsuarios(SistemaMapa sis) {
		sistema = sis;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtBusqueda = new JTextField();
		txtBusqueda.setText("Ingrese id de usuario");
		txtBusqueda.setBounds(37, 24, 169, 21);
		contentPane.add(txtBusqueda);
		txtBusqueda.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String campo = txtBusqueda.getText().trim();

				if(campo.equals("")) {
					JOptionPane.showMessageDialog(null, "porfavor, ingrese una búsqueda válida.", 
							"Error",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					CuentaUsuario cta = sistema.buscarUsuario(txtBusqueda.getText());
					
					txtId.setText(cta.getNombreUsuario());
					txtContrasea.setText(cta.getContrasena());
					chckbxAdministrador.setSelected(cta.tipoCuenta().equals("Administrador"));
					
				}catch(UserFindException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), 
							"Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}

			
		});
		btnBuscar.setBounds(228, 21, 105, 27);
		contentPane.add(btnBuscar);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setText("Id del usuario");
		txtId.setBounds(35, 105, 114, 21);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		txtContrasea = new JTextField();
		txtContrasea.setText("Contraseña");
		txtContrasea.setBounds(35, 154, 114, 21);
		contentPane.add(txtContrasea);
		txtContrasea.setColumns(10);
		
		chckbxAdministrador = new JCheckBox("Administrador");
		chckbxAdministrador.setBounds(35, 195, 114, 25);
		contentPane.add(chckbxAdministrador);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!verificarCampos()) {
					JOptionPane.showMessageDialog(null, "porfavor, ingrese una búsqueda válida.", 
							"Error",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				try {
					sistema.modificarUsuario(txtId.getText(), txtContrasea.getText(), chckbxAdministrador.isSelected());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), 
							"Error",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			}
		});
		btnModificar.setBounds(278, 126, 105, 27);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!verificarCampos()) {
					JOptionPane.showMessageDialog(null, "porfavor, ingrese una búsqueda válida.", 
							"Error",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					sistema.eliminarUsuario(txtId.getText());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),
							"Error",
                            JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		btnEliminar.setBounds(278, 169, 105, 27);
		contentPane.add(btnEliminar);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaAdmin vAdm = new VentanaAdmin(sistema);
				setVisible(false);
				vAdm.setVisible(true);
			}
		});
		btnVolver.setBounds(176, 234, 72, 21);
		contentPane.add(btnVolver);
		
		JButton btnReporte = new JButton("Reporte");
		btnReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sistema.generarReporte(new Busqueda("Usuarios"));
				} catch (PlaceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnReporte.setBounds(349, 234, 81, 24);
		contentPane.add(btnReporte);
	}
	
	private boolean verificarCampos() {
		
		String passwd = txtContrasea.getText().trim();
		String usr = txtId.getText().trim();
		
		
		return !passwd.equals("") || !usr.equals("");
		
		
		
	}
}
