package ventanas;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.maps.errors.ApiException;

import excepciones.FieldCheckException;
import excepciones.PlaceAlreadyTakenException;
import excepciones.PlaceException;
import main.java.Lugar;
import main.java.SistemaMapa;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

public class VentanaAdminLugares extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTextField txtDireccion;
	private JTextField txtComuna;
	private JTextField txtRegion;
	private JTextField txtPais;
	private JTextField txtLatitud;
	private JTextField txtLongitud;
	private JTextField txtId;
	private JButton btnVolver;
	private JTextField txtNombre;
	private JButton btnEliminarLugar;
	private JTextField txtDB;
	private JTextPane txtpnDescripcion;
	private JComboBox<String> catComboBox;
	
	
	private SistemaMapa sistema;
	private JButton btnVerLugar;

	
	
	
	/**
	 * Create the frame.
	 */
	public VentanaAdminLugares(SistemaMapa sis) {
		setTitle("Administración de lugares.");
		
		sistema = sis;
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 652);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtBuscar = new JTextField();
		txtBuscar.setText("Buscar");
		txtBuscar.setBounds(15, 16, 375, 38);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		JButton btnNewButton = new JButton("Autocompletar");
		
		btnNewButton.setBounds(417, 21, 175, 29);
		contentPane.add(btnNewButton);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(161, 136, 146, 26);
		contentPane.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		txtComuna = new JTextField();
		txtComuna.setBounds(161, 192, 146, 26);
		contentPane.add(txtComuna);
		txtComuna.setColumns(10);
		
		txtRegion = new JTextField();
		txtRegion.setBounds(161, 260, 146, 26);
		contentPane.add(txtRegion);
		txtRegion.setColumns(10);
		
		txtPais = new JTextField();
		txtPais.setBounds(161, 329, 146, 26);
		contentPane.add(txtPais);
		txtPais.setColumns(10);
		
		txtLatitud = new JTextField();
		txtLatitud.setEditable(false);
		txtLatitud.setBounds(161, 400, 146, 26);
		contentPane.add(txtLatitud);
		txtLatitud.setColumns(10);
		
		txtLongitud = new JTextField();
		txtLongitud.setEditable(false);
		txtLongitud.setBounds(161, 465, 146, 26);
		contentPane.add(txtLongitud);
		txtLongitud.setColumns(10);
		
		txtpnDescripcion = new JTextPane();
		txtpnDescripcion.setBounds(364, 364, 246, 127);
		contentPane.add(txtpnDescripcion);
		
		catComboBox = new JComboBox<String>();
		catComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"categor\u00EDa", "comida", "dormir", "vida_nocturna", "atracciones"}));
		
		catComboBox.setBounds(401, 96, 158, 26);
		contentPane.add(catComboBox);
		
		JButton botonDb = new JButton("Ingresar a DB");
		
		botonDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					agregar();
				}catch(FieldCheckException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), 
							"No se pudo ingresar a la base de datos0",
                            JOptionPane.ERROR_MESSAGE);
					return;
				} catch (SQLException  e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), 
							"No se pudo ingresar a la base de datos1",
                            JOptionPane.ERROR_MESSAGE);
					return;
					
				} catch(PlaceAlreadyTakenException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), 
							"No se pudo ingresar a la base de datos2",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				JOptionPane.showMessageDialog(null, "Se ha registrado el lugar.", 
						"Registro completo",
                        JOptionPane.OK_OPTION);
				
				return;
			}
		});
		botonDb.setBounds(783, 266, 175, 27);
		contentPane.add(botonDb);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(161, 538, 301, 26);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				VentanaInicioSesion ventanaInicio = new VentanaInicioSesion(sistema);
				ventanaInicio.setVisible(true);
				
			}
		});
		btnVolver.setBounds(864, 555, 115, 29);
		contentPane.add(btnVolver);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(161, 77, 146, 26);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		
		
		btnEliminarLugar = new JButton("Eliminar lugar");
		btnEliminarLugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					eliminar();
					limpiarCampos();
					JOptionPane.showMessageDialog(null,"Se ha eliminado correctamente el lugar.", 
							"Eliminado correctamente",
							JOptionPane.OK_OPTION);
					return;
				}catch(SQLException e) {
					JOptionPane.showMessageDialog(null,e.getMessage(), 
							"Error al eliminar",
							JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
		});
		btnEliminarLugar.setBounds(783, 405, 175, 29);
		contentPane.add(btnEliminarLugar);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(60, 83, 69, 20);
		contentPane.add(lblNombre);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setBounds(60, 142, 86, 20);
		contentPane.add(lblDireccin);
		
		JLabel lblComuna = new JLabel("Comuna:");
		lblComuna.setBounds(60, 198, 69, 20);
		contentPane.add(lblComuna);
		
		JLabel lblRegin = new JLabel("Regi\u00F3n:");
		lblRegin.setBounds(60, 266, 69, 20);
		contentPane.add(lblRegin);
		
		JLabel lblPas = new JLabel("Pa\u00EDs:");
		lblPas.setBounds(60, 335, 69, 20);
		contentPane.add(lblPas);
		
		JLabel lblLatitud = new JLabel("Latitud:");
		lblLatitud.setBounds(60, 406, 69, 20);
		contentPane.add(lblLatitud);
		
		JLabel lblLonngitud = new JLabel("Lonngitud:");
		lblLonngitud.setBounds(60, 471, 86, 20);
		contentPane.add(lblLonngitud);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(60, 544, 49, 20);
		contentPane.add(lblId);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setBounds(364, 328, 98, 20);
		contentPane.add(lblDescripcin);
		
		JLabel lblEnBaseDe = new JLabel("En base de datos:");
		lblEnBaseDe.setBounds(658, 25, 135, 20);
		contentPane.add(lblEnBaseDe);
		
		txtDB = new JTextField();
		txtDB.setEditable(false);
		txtDB.setBounds(812, 22, 49, 26);
		contentPane.add(txtDB);
		txtDB.setColumns(10);

		

		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Lugar lugarcito;
				try {
					lugarcito = new Lugar();
					lugarcito = sistema.obtenerLugarPorDireccion(txtBuscar.getText());
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} 
				if( lugarcito.getNombreLocal() != null && !( lugarcito.getNombreLocal().equals("") ) ) {
					txtDB.setText("si");
				}else
					txtDB.setText("no");
			
				catComboBox.setSelectedItem(lugarcito.getCategoria());
				txtNombre.setText(lugarcito.getNombreLocal());
				txtpnDescripcion.setText(lugarcito.getDescripcion());
				txtId.setText(lugarcito.getId());
				txtDireccion.setText(lugarcito.getDireccionPpal());
				txtComuna.setText(lugarcito.getComuna());
				txtRegion.setText(lugarcito.getRegion());
				txtPais.setText(lugarcito.getPais());
				txtLatitud.setText(  String.valueOf(lugarcito.getLat()) );
				txtLongitud.setText( String.valueOf(lugarcito.getLng()) );
				
				
				
				
			}
		});
		

			
		JButton btnAplicarModificacion = new JButton("Aplicar modificacion");
		btnAplicarModificacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
					try {
						modificar();
					} catch (SQLException | PlaceException | FieldCheckException  e) {
						JOptionPane.showMessageDialog(null,e.getMessage(), 
								"Fallo al actualizar.",
		                        JOptionPane.ERROR_MESSAGE);
						return;
					}
				
				JOptionPane.showMessageDialog(null, "se han realizado los cambios correctamente.", 
						"Cambios aplicados",
                        JOptionPane.ERROR_MESSAGE);
			}
		});
		btnAplicarModificacion.setBounds(783, 334, 175, 29);
		contentPane.add(btnAplicarModificacion);
		
		btnVerLugar = new JButton("Ver lugar");
		btnVerLugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!verificarCampos()) {
					JOptionPane.showMessageDialog(null,"presione el botón \"Autocompletar\" primero.", 
							"Fallo al ver copmentarios.",
	                        JOptionPane.ERROR_MESSAGE);
					return;
				}
				VentanaLugar v;
				try {
					v = new VentanaLugar(sistema.obtenerLugarPorId(txtId.getText()), sistema);
					v.setVisible(true);
				} catch (PlaceException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(), 
							"Fallo al ver lugar",
		                    JOptionPane.ERROR_MESSAGE);
				}
					
				
				
			}
		});
		btnVerLugar.setBounds(658, 57, 115, 27);
		contentPane.add(btnVerLugar);
		
		
		
	}
	
	
	private void agregar() throws FieldCheckException, SQLException, PlaceAlreadyTakenException{
		
		if(!verificarCampos()) 
			throw new FieldCheckException("No se han llenado todos los campos, presione bot�n autocompletar.");
		
		String[] dir = new String[4];
		dir[0] = txtDireccion.getText().trim().toLowerCase();
		dir[1] = txtComuna.getText().trim().toLowerCase();
		dir[2] = txtRegion.getText().trim().toLowerCase();
		dir[3] = txtPais.getText().trim().toLowerCase();
		
		String nombre = txtNombre.getText();
		String cat = (String) catComboBox.getSelectedItem();
		String desc = txtpnDescripcion.getText();
		String id = txtId.getText();
		
		float lat = Float.parseFloat(txtLatitud.getText());
		float lng = Float.parseFloat(txtLatitud.getText());
		
		sistema.agregar( id, nombre, dir, cat, lat, lng, desc );
		
	}
	
private void modificar() throws FieldCheckException, PlaceException, SQLException{
		
		if(!verificarCampos()) 
			throw new FieldCheckException("No se han llenado todos los campos, presione bot�n autocompletar.");
		
		String[] dir = new String[4];
		dir[0] = txtDireccion.getText().trim().toLowerCase();
		dir[1] = txtComuna.getText().trim().toLowerCase();
		dir[2] = txtRegion.getText().trim().toLowerCase();
		dir[3] = txtPais.getText().trim().toLowerCase();
		
		String nombre = txtNombre.getText();
		String cat = (String) catComboBox.getSelectedItem();
		String desc = txtpnDescripcion.getText();
		String id = txtId.getText();
		
		
		
		sistema.modificar( id, nombre, cat, desc );
		
	}
	
	public void eliminar() throws SQLException {
		String id = txtId.getText();;
		
		sistema.eliminarLugar(id);
	}

	public void limpiarCampos() {
		txtDireccion.setText("");
		txtNombre.setText("");
		txtComuna.setText("");
		txtRegion.setText("");
		txtPais.setText("");
		txtLatitud.setText("");
		txtLongitud.setText("");
		txtId.setText("");
		catComboBox.setSelectedIndex(0);
		txtpnDescripcion.setText("");
		txtDB.setText("");
	}
	
	public boolean verificarCampos(){
		try {
		if( txtDireccion.getText().trim().equals("") ||  
			txtNombre.getText().trim().equals("") ||
			txtComuna.getText().trim().equals("") ||
			txtRegion.getText().trim().equals("") ||
			txtPais.getText().trim().equals("") ||
			txtLatitud.getText().equals("") ||
			txtLongitud.getText().equals("") ||
			txtId.getText().equals("") ||
			txtpnDescripcion.getText().trim().equals("") ||
			catComboBox.getSelectedIndex() == 0
			)	return false;
		}catch(Exception e) {
			System.out.println("AAAAAAH: " + e.getMessage());
		}
		return true;
	}
	
}
