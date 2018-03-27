package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.CuentaUsuario;
import main.java.Lugar;

public class VentanaLugares extends JFrame {

	private JPanel contentPane;
	private Item[] itememes;
	private ArrayList<Lugar> lugarcitos;
	private CuentaUsuario usuario;
	private boolean existeNext;
	private boolean existePrev;
	private int act;
	
	
	//TODO: borrar esto y morir
	public VentanaLugares(String t) {
		
	}
	
	public VentanaLugares(String titulo, ArrayList<Lugar> listaLugares, CuentaUsuario usr, int actual) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle(titulo);
		existeNext = true;
		usuario = usr;
		act = actual;
		lugarcitos = listaLugares;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		itememes = new Item[5];
		int  x = 20, y = 20;
		
		for(int i = 0 ; i < 5 ; i++) {
			System.out.println("actual = " + act + " ; size = " + lugarcitos.size());
			if(act == lugarcitos.size()) {
				existeNext = false;
				act--;
				break;
			}
			itememes[i] = new Item(lugarcitos.get(act), x, y, usuario);
			y+=15;
			contentPane.add(itememes[i].getNombreLocal());
			contentPane.add(itememes[i].getRating());
			contentPane.add(itememes[i].getBtnLugar());
			act++;
		}
		
		if(existeNext) {
			JButton btnNext = new JButton();
			btnNext.setBounds(100, 50, 20, 10);
			
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaLugares vtnLugar = new VentanaLugares(titulo, lugarcitos, usr, act);
					vtnLugar.setVisible(true);
				}
			});
			
			contentPane.add(btnNext);
		}
		
		if(act>9) {
			JButton btnNext = new JButton();
			btnNext.setBounds(200, 50, 20, 10);
			
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaLugares vtnLugar = new VentanaLugares(titulo, lugarcitos, usr, act-10);
					vtnLugar.setVisible(true);
				}
			});
			
			contentPane.add(btnNext);
		}
		
		
		
	}

}
