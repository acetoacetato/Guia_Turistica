package ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class VentanaAtracciones extends JFrame {

	JLabel labelTitulo;
	private JPanel contentPane;
	JScrollPane scrollPane;
	private JLabel lblNewLabel;


	VentanaAtracciones(){
		
		setTitle("atracciones");
		setSize(521, 283);
		//pone la ventana en el Centro de la pantalla;
		setLocationRelativeTo(null);
		//setLayout(null);
		iniciarComponentes();
	}
	
	private void iniciarComponentes() {
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 10, 521, 200);
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		
		labelTitulo = new JLabel();
		labelTitulo.setText("Contenedor JSCROLLPANE");
		labelTitulo.setBounds(128, 49, 85, 68);

		
		contentPane.setPreferredSize(new Dimension (500, 240));
		
		scrollPane.setViewportView(contentPane);
		
		contentPane.add(labelTitulo);
		
	}

}
