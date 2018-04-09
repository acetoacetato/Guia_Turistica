package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.CuentaUsuario;
import main.java.DbHandler;
import main.java.ItemComentario;
import main.java.ItemLugar;
import main.java.Lugar;

public class VentanaComentarios extends JFrame {
	
	private JPanel contentPane;
	
	//los items generados
	private ItemComentario[] itemCom;
	
	//
	
	private CuentaUsuario usuario;
	private Lugar lugar;
	private DbHandler db;
	
	//en caso de que existan al menos un comentario para hacer la página sgte, existeNext es true
	private boolean existeNext;
	private int act;
	private int coment;
		
	private String t;
	
	
	
	
	public VentanaComentarios( String titulo, Lugar l, CuentaUsuario usr, DbHandler baseDatos, int actual) {
		
		t = titulo;
		existeNext = true;
		db = baseDatos;
		act = actual;
		usuario = usr;
		lugar = l;
		cargarVentana();
		

	}

	public VentanaComentarios(String titulo, Lugar l, CuentaUsuario usr, DbHandler baseDatos, int actual, boolean esPrimera) {
		t = titulo;
		existeNext = true;
		db = baseDatos;
		act = actual;
		usuario = usr;
		lugar = l;
		lugar.cargarComentarios();
		cargarVentana();
	}

private void cargarVentana() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 800, 601);
	setTitle(t);
	
	
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	itemCom = new ItemComentario[5];
	int  x = 40, y = 20;
	
	System.out.println(act);
	for (int i = 0 ; i < 3 ; i++) {
		
		if(act == lugar.getComentarios().size()) {
			existeNext = false;
			
			break;
		}
		
		itemCom[i] = new ItemComentario( lugar.getComentarios().get(act) , usuario, x, y);
		y+=150;
		System.out.print(itemCom[i].getUser());
		contentPane.add(itemCom[i].getUser());
		contentPane.add(itemCom[i].getScroll1());
		//contentPane.add(itemCom[i].getComentarios());
		//contentPane.add(itemCom[i].getScroll2());
		//contentPane.add(itemCom[i].getTuComen());
		contentPane.add(itemCom[i].getPuntuacion());
		act++;
	}
	
	
	if(existeNext && act < lugar.getComentarios().size()) {
		JButton btnNext = new JButton("Siguiente");
		btnNext.setBounds(650, 490, 150, 20);
		
		btnNext.addActionListener(new ActionListener() {
			//al presionarse, se crea un ventanaLugares con los parámetros actuales
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaComentarios vtnCom = new VentanaComentarios(t, lugar, usuario, db, act);
				vtnCom.setVisible(true);
			}
		});
		
		contentPane.add(btnNext);
	}
	
	//botón para volver al menú de categorías
	JButton btnVolverLugar = new JButton("Volver");
	btnVolverLugar.setBounds(300, 480, 100, 40);
	contentPane.add(btnVolverLugar);
	btnVolverLugar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			VentanaLugar ventanaCat = new VentanaLugar(lugar, usuario);
			ventanaCat.setVisible(true);
			try {
				finalize();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	});
	
//si el actual es mayor a 5, entonces se puede volver una página atrás
	if(act>5) {
				
		//se crea el botón atrás y se lo agrega al panel
		JButton btnBack = new JButton("Atrás");
		btnBack.setBounds(19, 490, 150, 20);
				
		//al presionarse el botón, se crea una ventanaComentarios con actual-6, que sería el actual de la página anterior
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaComentarios vtnComentario = new VentanaComentarios(t, lugar, usuario, db, act-6);
				vtnComentario.setVisible(true);
						
					}
				});
				
				contentPane.add(btnBack);
			}		
}

}