package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.CuentaUsuario;
import main.java.Lugar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class VentanaLugar extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtRating;


	/**
	 * Create the frame.
	 */
	public VentanaLugar(Lugar l, CuentaUsuario usr) {
		setBounds(100, 100, 800, 601);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mapa");
		lblNewLabel.setBounds(579, 37, 159, 137);
		contentPane.add(lblNewLabel);
		
		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setBounds(22, 71, 90, 14);
		contentPane.add(lblDireccion);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(22, 39, 90, 14);
		contentPane.add(lblNombre);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(23, 148, 89, 14);
		contentPane.add(lblDescripcion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 172, 253, 131);
		contentPane.add(scrollPane);
		
		JTextPane txtpnDescripcion = new JTextPane();
		txtpnDescripcion.setEditable(false);
		scrollPane.setViewportView(txtpnDescripcion);
		
		JLabel lblNewLabel_1 = new JLabel("Rating:");
		lblNewLabel_1.setBounds(32, 106, 90, 26);
		contentPane.add(lblNewLabel_1);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setBounds(94, 33, 146, 26);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		txtNombre.setText(l.getNombreLocal());
		
		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setBounds(104, 65, 375, 26);
		contentPane.add(txtDireccion);
		txtDireccion.setColumns(10);
		txtDireccion.setText(l.getDireccionPpal() + ", " + l.getComuna());
		
		txtRating = new JTextField();
		txtRating.setEditable(false);
		txtRating.setBounds(94, 105, 146, 26);
		contentPane.add(txtRating);
		txtRating.setColumns(10);
		txtRating.setText( Integer.toString( l.getPuntuacion() ) );
		
		txtpnDescripcion.setText(l.getDescripcion());
		
		
		
	}
}
