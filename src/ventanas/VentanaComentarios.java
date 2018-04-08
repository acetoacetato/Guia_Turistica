package ventanas;

import java.util.ArrayList;

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
		
	
	
	
	
	
	public VentanaComentarios( String titulo, Lugar l, CuentaUsuario usr, DbHandler baseDatos) {
		existeNext = true;
		db = baseDatos;
		usuario = usr;
		lugar = l;
		lugar.cargarComentarios();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		itemCom = new ItemComentario[5];
		int  x = 40, y = 20;
		
		
		
		for (int i = 0 ; i < 5 ; i++) {
			
			/*
			if(act == lugar.getComentarios().size()) {
				existeNext = false;
				act--;
				break;
			}
			*/
			System.out.println("caca");
			itemCom[i] = new ItemComentario( lugar.getComentarios().get(i) , usuario, x, y);
			y+=40;
			
			contentPane.add(itemCom[i].getUser());
			contentPane.add(itemCom[i].getScroll1());
			contentPane.add(itemCom[i].getComentarios());
			contentPane.add(itemCom[i].getScroll2());
			contentPane.add(itemCom[i].getTuComen());
			
			act++;
		}
		
	}

}
