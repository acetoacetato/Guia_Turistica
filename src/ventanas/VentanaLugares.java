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
import main.java.ItemLugar;
import main.java.Lugar;

public class VentanaLugares extends JFrame {

	private JPanel contentPane;
	private ItemLugar[] itememes;
	private ArrayList<Lugar> lugarcitos;
	private CuentaUsuario usuario;
	private boolean existeNext;
	private boolean existePrev;
	private int act;
	
	

	
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
		itememes = new ItemLugar[5];
		int  x = 40, y = 20, z = 0;
		
		
		
		for(int i = 0 ; i < 5 ; i++) {
			if(act == lugarcitos.size()) {
				existeNext = false;
				act--;
				break;
			}
			
			
			itememes[i] = new ItemLugar(lugarcitos.get(act), x, y, z, usuario);
			y+=40;
			z+=41;
			contentPane.add(itememes[i].getNombreLocal());
			contentPane.add(itememes[i].getRating());
			contentPane.add(itememes[i].getBtnLugar());
			
			contentPane.add(itememes[i].getFondo());
			
			act++;
			
			
			
		}
		
		if(existeNext) {
			JButton btnNext = new JButton();
			btnNext.setBounds(370, 220, 45, 20);
			
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaLugares vtnLugar = new VentanaLugares(titulo, lugarcitos, usr, act);
					vtnLugar.setVisible(true);
				}
			});
			
			contentPane.add(btnNext);
		}
		
		JButton btnVolverMenu = new JButton("Volver al menu anterior.");
		btnVolverMenu.setBounds(10, 210, 100, 40);
		contentPane.add(btnVolverMenu);
		
		if(act>9) {
			JButton btnBack = new JButton();
			btnBack.setBounds(19, 220, 45, 20);
			
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VentanaLugares vtnLugar = new VentanaLugares(titulo, lugarcitos, usr, act-10);
					vtnLugar.setVisible(true);
				}
			});
			
			contentPane.add(btnBack);
		}
		
		//
		
	}

}
