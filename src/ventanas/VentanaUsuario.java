package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.CuentaUsuario;
import main.java.DbHandler;
import main.java.Lugar;
import main.java.Usuario;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VentanaUsuario extends JFrame {

	private JPanel contentPane;

	

	/**
	 * Create the frame.
	 */
	public VentanaUsuario(DbHandler db, CuentaUsuario cta) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBienvenida = new JLabel("Bienvenido, " + cta.getNombreUsuario() + "!");
		lblBienvenida.setBounds(15, 16, 170, 20);
		contentPane.add(lblBienvenida);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Valpara\u00EDso", "Quilpu\u00E9", "Vi\u00F1a del mar"}));
		comboBox.setBounds(118, 138, 170, 26);
		contentPane.add(comboBox);
		
		JLabel lbldndeDeseasBuscar = new JLabel("\u00BFD\u00F3nde deseas buscar?");
		lbldndeDeseasBuscar.setBounds(26, 91, 203, 20);
		contentPane.add(lbldndeDeseasBuscar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ArrayList<ArrayList <Lugar>> listaCat = db.cargaLugares((String)comboBox.getSelectedItem());
					setVisible(false);
					VentanaCategorias ventanaCat = new VentanaCategorias(db, cta, listaCat);
					ventanaCat.setVisible(true);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("no se pudo ejecutar :/");
					e.printStackTrace();
				}
				
			}
		});
		btnBuscar.setBounds(130, 199, 115, 29);
		contentPane.add(btnBuscar);
	}
}
