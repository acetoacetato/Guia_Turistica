package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.Comentario;
import main.java.ItemComentario;
import main.java.ItemComentarioUsuarioActual;
import main.java.Lugar;
import main.java.SistemaMapa;

public class VentanaComentarios extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	//los items generados
	private ItemComentario[] itemCom;
	
	//

	private Lugar lugar;
	
	//en caso de que existan al menos un comentario para hacer la p�gina sgte, existeNext es true
	private boolean existeNext;
	private int act;
	private String t;
	private int i = 0;
	private ItemComentarioUsuarioActual comUsr;
	private ArrayList<Comentario> listaComentarios;
	private SistemaMapa sistema;
	private JFrame estaVentana;
	
	
	
	public VentanaComentarios( Lugar l, SistemaMapa sis, int actual, ItemComentarioUsuarioActual usrC, ArrayList<Comentario> comentarios) {
		estaVentana = this;
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		t = "Comentarios";
		existeNext = true;
		sistema = sis;
		act = actual;
		lugar = l;
		comUsr = usrC;
		listaComentarios = comentarios;
		
		if(!sistema.getAdmin()) {
			contentPane.add(comUsr.getUser());
			contentPane.add(comUsr.getScroll1());
			contentPane.add(comUsr.getPuntuacion());
			contentPane.add(comUsr.getBttonActualizar());
			contentPane.add(comUsr.getBttonComentDown());
			contentPane.add(comUsr.getBttonComentUp());
		}
		cargarVentana();
		setResizable(false);
		

	}

	public VentanaComentarios(Lugar l, SistemaMapa sis ) {
		estaVentana = this;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		t = "Comentarios";
		existeNext = true;
		sistema = sis;
		act = 0;
		lugar = l;
		listaComentarios = lugar.getComentarios();
		if(sistema.getAdmin()) {
			comUsr = null;
			cargarVentana();
			return;
		}
		
		
		for (int i = 0 ; i < l.getComentarios().size() ; i++) {
			
			Comentario c = listaComentarios.get(i);
			if(c.getUsr().equals(sistema.getNombreUsuario())) {
				comUsr = new ItemComentarioUsuarioActual(c, 500, 150, sistema, lugar);
				listaComentarios.remove(i);
				comUsr.agregarEnPanel(contentPane);
				cargarVentana();
				return;
			}
			
		}
		comUsr =new ItemComentarioUsuarioActual(sistema, 500, 150, lugar);
		comUsr.agregarEnPanel(contentPane);
		cargarVentana();
		
	}

private void cargarVentana() {
	
	
	setBounds(100, 100, 800, 600);

	setTitle(t);
	
	insertarComentarios();
	
	
	
	if(existeNext && act < listaComentarios.size()) {
		JButton btnNext = new JButton("Siguiente");
		btnNext.setBounds(650, 490, 150, 20);
		
		btnNext.addActionListener(new ActionListener() {
			//al presionarse, se crea un ventanaLugares con los par�metros actuales
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaComentarios vtnCom = new VentanaComentarios(lugar, sistema, act, comUsr, listaComentarios);
				vtnCom.setVisible(true);
			}
		});
		
		contentPane.add(btnNext);
	}
	
	//bot�n para volver al men� de categor�as
	JButton btnVolverLugar = new JButton("Volver");
	btnVolverLugar.setBounds(300, 480, 100, 40);
	contentPane.add(btnVolverLugar);
	btnVolverLugar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			VentanaLugar ventanaCat = new VentanaLugar(lugar, sistema);
			ventanaCat.setVisible(true);
			
		}
		
	});
	
//si el actual es mayor a 5, entonces se puede volver una p�gina atr�s
	if(act>3) {

		//se crea el bot�n atr�s y se lo agrega al panel
		JButton btnBack = new JButton("Atr�s");
		btnBack.setBounds(19, 490, 150, 20);
		int nuevoAct = act - (i+1) - (act - (i+1)) %3;
		//al presionarse el bot�n, se crea una ventanaComentarios con actual-6, que ser�a el actual de la p�gina anterior
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				VentanaComentarios vtnComentario = new VentanaComentarios(lugar, sistema, nuevoAct, comUsr, listaComentarios);
				vtnComentario.setVisible(true);
						
					}
				});
				
				contentPane.add(btnBack);
			}
}

protected void insertarComentarios() {
	itemCom = new ItemComentario[3];
	int  x = 40, y = 20;
	
	int i=0;
	
	for (i = 0 ; i < 3 ; i++) {
		
		if(act == listaComentarios.size()) {
			existeNext = false;
			
			break;
		}
		Comentario com = listaComentarios.get(act);
		if(com.getUsr().equals(sistema.getNombreUsuario())) {
			act++;
			i--;
			continue;
		}
		
		
		itemCom[i] = (comUsr != null)? new ItemComentario( com , x, y) : new ItemComentario(com, x, y, sistema, lugar, estaVentana );
		y+=150;
		itemCom[i].agregarEnPanel(contentPane);
		act++;
	}
}


}