package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.Busqueda;
import main.java.ItemLugar;
import main.java.Lugar;
import main.java.SistemaMapa;

public class VentanaLugares extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	//los items generados 
	private ItemLugar[] itememes;
	
	//el arrayList de lugares a mostrar
	private ArrayList<Lugar> lugarcitos;
	
	//en caso que exista al menos un lugar para hacer la p�gina sgte, existeNext es true
	private boolean existeNext;
	private int act;	
	private int i;
	private String categoria;
	private SistemaMapa sistema;
	private String zona;
	
	public VentanaLugares(String cat, String zona, SistemaMapa sis) {
		sistema = sis;
		categoria = cat;
		this.zona = zona;
		act = 0;
		lugarcitos = sis.obtenerLugares(cat,  zona);
		
		ventanita();
		
	}
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public VentanaLugares(String cat, SistemaMapa sis, int actual, ArrayList<Lugar> lugarcitos, String zona) {
		
		this.zona = zona;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle(cat);
		
		//se guardan las referencias necesarias
		
		sistema = sis;
		act = actual;
		categoria = cat;
		this.lugarcitos = lugarcitos;
		
		ventanita();
		
	}
	
	
	private void ventanita() {
		existeNext = true;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setBounds(100, 100, 405, 300);
		itememes = new ItemLugar[5];
		int  x = 40, y = 20, z = 0;
		i=0;
		
		
		//se crean 5 items y se agregan al panel
		for(i = 0 ; i < 5 ; i++) {
			if(lugarcitos==null || lugarcitos.isEmpty()) {
				JLabel lblVacio = new JLabel("no hay lugares en esta categor�a. :c");
				lblVacio.setBounds(80, 40, 500, 100);
				contentPane.add(lblVacio);
				volverMenu();
				return;
				
			}
			//se verifica que quede un lugar o m�s, si no es as� se avisa que no existe sgte p�gina y se dejan de crear items
			if(act == lugarcitos.size()) {
				existeNext = false;
				act--;
				break;
			}
			
			//se crea un item con el lugar, las coordenadas donde debe crearse  y una referencia al usuario, para pasarlo en caso de que se vean los comentarios del lugar
			itememes[i] = new ItemLugar(lugarcitos.get(act), x, y, z, sistema);
			
			//se cambian las coordenadas para el sgte item a generar
			y+=40;
			z+=41;
			
			//se adhieren los elementos del item al panel
			contentPane.add(itememes[i].getNombreLocal());
			contentPane.add(itememes[i].getRating());
			contentPane.add(itememes[i].getBtnLugar());
			contentPane.add(itememes[i].getFondo());
			
			//se aumenta el �ndice del lugar actual al generar
			act++;
			
			
			
		}
		
		JButton btnReporte = new JButton("Generar Reporte");
		btnReporte.setBounds(100, 230, 180, 15);
		btnReporte.addActionListener(new ActionListener() {
		
				public void actionPerformed(ActionEvent e) {
					//String[] s = {zona, categoria};
					String[] s = {zona};
					sistema.generarReporte(new Busqueda("Zonas", s) );
					//VentanaReporte ventanitaR = new VentanaReporte(lugarcitos, sistema);
					//ventanitaR.setVisible(true);
					
				}
			
		});
		
		
		
		contentPane.add(btnReporte);
		
		//si existe al menos un lugar m�s a mostrar, se crea un bot�n para la sgte p�gina y se adhiere al panel
		if(existeNext) {
			JButton btnNext = new JButton("siguiente");
			btnNext.setBounds(330, 240, 90, 20);
			
			btnNext.addActionListener(new ActionListener() {
				//al presionarse, se crea un ventanaLugares con los par�metros actuales
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					VentanaLugares vtnLugar = new VentanaLugares(categoria, sistema, act, lugarcitos, zona);
					vtnLugar.setVisible(true);
				}
			});
			
			contentPane.add(btnNext);
		}
		volverMenu();
		
		
		//si el actual es mayor a 9, entonces se puede volver una p�gina atr�s
		if(act>5) {
			
			//se crea el bot�n atr�s y se lo agrega al panel
			JButton btnBack = new JButton("atr�s");
			btnBack.setBounds(19, 240, 70, 20);
			
			//al presionarse el bot�n, se crea una ventanaLugares con actual-10, que ser�a el actual de la p�gina anterior
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					setVisible(false);
					
					int nuevoAct = act - (i+1) - (act - (i+1)) %5;
					VentanaLugares vtnLugar = new VentanaLugares(categoria, sistema, nuevoAct, lugarcitos, zona);
					vtnLugar.setVisible(true);
					
				}
			});
			
			contentPane.add(btnBack);
		}
	}
	
	private void volverMenu() {
		//bot�n para volver al men� de categor�as
				JButton btnVolverMenu = new JButton("Volver a categorias");
				btnVolverMenu.setBounds(100, 260, 180, 15);
				contentPane.add(btnVolverMenu);
				btnVolverMenu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						VentanaCategorias ventanaCat = new VentanaCategorias(sistema);
						ventanaCat.setVisible(true);
						
					}
					
					
					
				});
	}

}
