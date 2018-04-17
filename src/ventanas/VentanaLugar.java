package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.CuentaUsuario;
import main.java.DbHandler;
import main.java.Lugar;
import main.java.MapHandler;
import main.java.SistemaMapa;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;


//implementa Runnnable para usar threads
public class VentanaLugar extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtRating;
	private JButton comentario;
	private Lugar l;
	private SistemaMapa sistema;
	
	public VentanaLugar(Lugar l, SistemaMapa sis) {
		sistema = sis;
		//se guarda referencia al lugar a mostrar
		this.l = l;
		setBounds(100, 100, 800, 601);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//se crea e inicia el hilo que carga el mapa
		Thread t = new Thread(this);
		t.start();
		
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
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(22, 172, 300, 131);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		JLabel lblRating = new JLabel("Rating:");
		lblRating.setBounds(22, 102, 90, 26);
		contentPane.add(lblRating);
		
		comentario = new JButton("\u00A1Revisa los comentarios de nuestros usuarios!");
		comentario.setHorizontalTextPosition(SwingConstants.CENTER);
		comentario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//se abre una ventana con el título de "Comentarios", la lista de comentarios, la cuenta de usuario
				//y la base de datos
				VentanaComentarios ventanaCom = new VentanaComentarios("Comentarios", l, sistema);
				ventanaCom.setVisible(true);
			}
		});
		comentario.setBounds( 22, 331, 300, 44);
		contentPane.add(comentario);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setBounds(94, 33, 146, 26);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		txtNombre.setText(l.getNombreLocal());
		
		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setBounds(94, 65, 375, 26);
		contentPane.add(txtDireccion);
		txtDireccion.setColumns(10);
		txtDireccion.setText(l.getDireccionPpal() + ", " + l.getComuna());
		
		txtRating = new JTextField();
		txtRating.setEditable(false);
		txtRating.setBounds(94, 102, 146, 26);
		contentPane.add(txtRating);
		txtRating.setColumns(10);
		txtRating.setText( Integer.toString( l.getPuntuacion() ) );
		
		textArea.setText( l.getDescripcion());
		
		
	}
	
	
	/*
	 * Método que carga el mapa en un hilo diferente
	 * 
	 * */
	public void run() {
		
		
		MapHandler mapita;
		try {
			//se deja un aviso de cargando mapa
			JLabel cargando = new JLabel("Cargando mapa...");
			cargando.setBounds(453, 134, 410, 297);
			contentPane.add(cargando);
			
			//se crea un MapHandler con la dirección del lugar, más la gomuna, para una búsqueda efectiva
			mapita = new MapHandler(l.getDireccionPpal() + ", " + l.getComuna());
			
			//se crea un label con el ImageIcon del mapa
			JLabel lblMapa = new JLabel(mapita.getMapa());
			lblMapa.setBounds(353, 134, 410, 297);
			
			//se quita el label de cargando y se agrega el del mapa
			contentPane.remove(cargando);
			contentPane.add(lblMapa);
			
			//se manda a re pintar el JFrame
			repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
