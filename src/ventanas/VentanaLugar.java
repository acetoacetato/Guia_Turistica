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


	/**
	 * Create the frame.
	 */
	public VentanaLugar(Lugar l, CuentaUsuario usr) {
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mapa");
		lblNewLabel.setBounds(308, 21, 97, 82);
		contentPane.add(lblNewLabel);
		
		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setBounds(22, 71, 48, 14);
		contentPane.add(lblDireccion);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(22, 39, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(23, 148, 64, 14);
		contentPane.add(lblDescripcion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 173, 253, 77);
		contentPane.add(scrollPane);
		
		JTextPane txtpnDasssssssssssssssss = new JTextPane();
		scrollPane.setViewportView(txtpnDasssssssssssssssss);
		
		JLabel lblNewLabel_1 = new JLabel("Rating:");
		lblNewLabel_1.setBounds(22, 111, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("aaaaaaaaaa");
		lblNewLabel_2.setBounds(80, 39, 205, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel label = new JLabel("Pene");
		label.setBounds(80, 71, 205, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Pene");
		label_1.setBounds(80, 111, 205, 14);
		contentPane.add(label_1);
		
		
	}
}
