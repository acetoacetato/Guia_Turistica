package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.maps.errors.ApiException;

import excepciones.PlaceAlreadyTakenException;
import main.java.CuentaUsuario;
import main.java.DbHandler;
import main.java.Lugar;
import main.java.MapApi;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionEvent;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class VentanaAgregar extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTextField txtDireccion;
	private JTextField txtComuna;
	private JTextField txtRegion;
	private JTextField txtPais;
	private JTextField txtLatitud;
	private JTextField txtLongitud;
	private JTextField txtId;
	private JTextField txtCategoria;
	private JButton btnVolver;
	DbHandler db;
	CuentaUsuario usr;


	/**
	 * Create the frame.
	 */
	public VentanaAgregar(DbHandler database, CuentaUsuario cta) {
		
		db = database;
		usr = cta;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 651);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtBuscar = new JTextField();
		txtBuscar.setText("Buscar");
		txtBuscar.setBounds(15, 16, 375, 38);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MapApi mapita = new MapApi();
				Lugar lugarcito = new Lugar();
				try {
					lugarcito = mapita.buscaLugar(txtBuscar.getText());
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				txtId.setText(lugarcito.getId());
				txtDireccion.setText(lugarcito.getDireccionPpal());
				txtComuna.setText(lugarcito.getComuna());
				txtRegion.setText(lugarcito.getRegion());
				txtPais.setText(lugarcito.getPais());
				txtLatitud.setText(  String.valueOf(lugarcito.getLat()) );
				txtLongitud.setText( String.valueOf(lugarcito.getLng()) );
				
				
				
				
			}
		});
		btnNewButton.setBounds(417, 21, 115, 29);
		contentPane.add(btnNewButton);
		
		txtDireccion = new JTextField();
		txtDireccion.setText("direccion");
		txtDireccion.setBounds(51, 137, 146, 26);
		contentPane.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		txtComuna = new JTextField();
		txtComuna.setText("Comuna");
		txtComuna.setBounds(51, 193, 146, 26);
		contentPane.add(txtComuna);
		txtComuna.setColumns(10);
		
		txtRegion = new JTextField();
		txtRegion.setText("Regi\u00F3n");
		txtRegion.setBounds(51, 261, 146, 26);
		contentPane.add(txtRegion);
		txtRegion.setColumns(10);
		
		txtPais = new JTextField();
		txtPais.setText("Pa\u00EDs");
		txtPais.setBounds(51, 330, 146, 26);
		contentPane.add(txtPais);
		txtPais.setColumns(10);
		
		txtLatitud = new JTextField();
		txtLatitud.setEditable(false);
		txtLatitud.setText("Latitud");
		txtLatitud.setBounds(51, 401, 146, 26);
		contentPane.add(txtLatitud);
		txtLatitud.setColumns(10);
		
		txtLongitud = new JTextField();
		txtLongitud.setEditable(false);
		txtLongitud.setText("Longitud");
		txtLongitud.setBounds(51, 466, 146, 26);
		contentPane.add(txtLongitud);
		txtLongitud.setColumns(10);
		
		JTextPane txtpnDescripcion = new JTextPane();
		txtpnDescripcion.setText("Descripci\u00F3n");
		txtpnDescripcion.setBounds(596, 58, 326, 417);
		contentPane.add(txtpnDescripcion);
		
		JComboBox<String> catComboBox = new JComboBox<String>();
		catComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"categor\u00EDa", "comida", "dormir", "vida_nocturna", "atracciones"}));
		
		catComboBox.setBounds(243, 91, 158, 26);
		contentPane.add(catComboBox);
		
		JButton botonDb = new JButton("Ingresar a DB");
		
		botonDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
							
				String[] dir = new String[5];
				dir[0] = txtDireccion.getText().trim().toLowerCase();
				dir[1] = txtComuna.getText().trim().toLowerCase();
				dir[2] = txtRegion.getText().trim().toLowerCase();
				dir[3] = txtPais.getText().trim().toLowerCase();
				
				if(dir[0].equals("") || dir[0].equals("direccion") ||
					dir[1].equals("") || dir[1].equals("comuna") || 
					dir[2].equals("") || dir[2].equals("region") ||
					dir[3].equals("") || dir[3].equals("pais") ||
					txtLatitud.getText().equals("") ||
					txtLongitud.getText().equals("") ||
					catComboBox.getSelectedItem().equals("Categoría") || 
					txtpnDescripcion.getText().equals("") ||
					txtpnDescripcion.getText().equals("Descripci\\u00F3n")					
					) {
					JOptionPane.showMessageDialog(null, "Llene todos los campos porfavor.", 
							"No se pudo ingresar a la base de datos",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Double lat, lng;
				try {
					lng = Double.parseDouble(txtLatitud.getText());
					lat = Double.parseDouble(txtLongitud.getText());
					
				}catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Latitud y longitud deben ser un número", 
							"No se pudo ingresar a la base de datos",
                            JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Lugar l = new Lugar(txtId.getText(),
							dir,
							catComboBox.getSelectedItem().toString(),
						    Double.parseDouble( txtLatitud.getText() ),
						    Double.parseDouble( txtLongitud.getText() )						    
						    );
				System.out.println(l.getCategoria());
				
				try {
					db.ingresarLugar(l);
				} catch (SQLException  e) {
					JOptionPane.showMessageDialog(null, e.toString(), 
							"No se pudo ingresar a la base de datos",
                            JOptionPane.ERROR_MESSAGE);
				} catch(PlaceAlreadyTakenException e) {
					JOptionPane.showMessageDialog(null, e.toString(), 
							"No se pudo ingresar a la base de datos",
                            JOptionPane.ERROR_MESSAGE);
				}
				
				JOptionPane.showMessageDialog(null, "Se ha registrado el lugar.", 
						"Registro completo",
                        JOptionPane.OK_OPTION);
				
				return;
			}
		});
		botonDb.setBounds(317, 260, 158, 27);
		contentPane.add(botonDb);
		
		txtId = new JTextField();
		txtId.setText("ID");
		txtId.setBounds(51, 539, 301, 26);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				VentanaAdmin ventanaAdm = new VentanaAdmin(db, cta);
				ventanaAdm.setVisible(true);
			}
		});
		btnVolver.setBounds(864, 555, 115, 29);
		contentPane.add(btnVolver);


		

		
		
		
		

			
			
		
		
		
	}
}
