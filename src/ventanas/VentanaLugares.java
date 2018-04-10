package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.CuentaUsuario;
import main.java.DbHandler;
import main.java.ItemLugar;
import main.java.Lugar;

public class VentanaLugares extends JFrame {

	private JPanel contentPane;
	
	//los items generados 
	private ItemLugar[] itememes;
	
	//el arrayList de lugares a mostrar
	private ArrayList<Lugar> lugarcitos;
	private CuentaUsuario usuario;
	
	//en caso que exista al menos un lugar para hacer la página sgte, existeNext es true
	private boolean existeNext;
	private int act;
	private int categoria;
	private DbHandler db;
	

	
	public VentanaLugares(String titulo, ArrayList<ArrayList<Lugar>> listaLugares, CuentaUsuario usr, int actual, int cat, DbHandler baseDatos) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle(titulo);
		
		//se guardan las referencias necesarias
		existeNext = true;
		db = baseDatos;
		usuario = usr;
		act = actual;
		categoria = cat;
		lugarcitos = listaLugares.get(categoria);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		itememes = new ItemLugar[5];
		int  x = 40, y = 20, z = 0;
		
		
		//se crean 5 items y se agregan al panel
		for(int i = 0 ; i < 5 ; i++) {
			
			//se verifica que quede un lugar o más, si no es así se avisa que no existe sgte página y se dejan de crear items
			if(act == lugarcitos.size()) {
				existeNext = false;
				act--;
				break;
			}
			
			//se crea un item con el lugar, las coordenadas donde debe crearse  y una referencia al usuario, para pasarlo en caso de que se vean los comentarios del lugar
			itememes[i] = new ItemLugar(lugarcitos.get(act), x, y, z, usuario, db);
			
			//se cambian las coordenadas para el sgte item a generar
			y+=40;
			z+=41;
			
			//se adhieren los elementos del item al panel
			contentPane.add(itememes[i].getNombreLocal());
			contentPane.add(itememes[i].getRating());
			contentPane.add(itememes[i].getBtnLugar());
			contentPane.add(itememes[i].getFondo());
			
			//se aumenta el índice del lugar actual al generar
			act++;
			
			
			
		}
		
		//si existe al menos un lugar más a mostrar, se crea un botón para la sgte página y se adhiere al panel
		if(existeNext) {
			JButton btnNext = new JButton("next");
			btnNext.setBounds(370, 220, 45, 20);
			
			btnNext.addActionListener(new ActionListener() {
				//al presionarse, se crea un ventanaLugares con los parámetros actuales
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					VentanaLugares vtnLugar = new VentanaLugares(titulo, listaLugares, usr, act, categoria, db);
					vtnLugar.setVisible(true);
				}
			});
			
			contentPane.add(btnNext);
		}
		
		//botón para volver al menú de categorías
		JButton btnVolverMenu = new JButton("Volver");
		btnVolverMenu.setBounds(10, 210, 100, 40);
		contentPane.add(btnVolverMenu);
		btnVolverMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaCategorias ventanaCat = new VentanaCategorias(db, usuario, listaLugares);
				try {
					finalize();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
			
		});
		
		//si el actual es mayor a 9, entonces se puede volver una página atrás
		if(act>9) {
			
			//se crea el botón atrás y se lo agrega al panel
			JButton btnBack = new JButton();
			btnBack.setBounds(19, 220, 45, 20);
			
			//al presionarse el botón, se crea una ventanaLugares con actual-10, que sería el actual de la página anterior
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					VentanaLugares vtnLugar = new VentanaLugares(titulo, listaLugares, usr, act-10, categoria, db);
					vtnLugar.setVisible(true);
					
				}
			});
			
			contentPane.add(btnBack);
		}
		
		//
		
	}

}
