package ventanas;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import Interfaces.Reportable;


import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaReporte extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JFrame estaVentana;
	
	//path por defecto, queda en la raiz del proyecto
	private String documentPath = "texto.pdf";
	private JTextPane textPane;
	/**
	 * Create the frame.
	 */
	
	public VentanaReporte(Reportable r) {
		
		setResizable(false);

		//se guarda referencia al JFrame para su utilizaci�n al guardar el PDF
		estaVentana = this;
		setBounds(100, 100, 575, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(5, 5, 549, 409);

		
		
		//se obtiene el StyledDocument del textPane para agregar cosas
		StyledDocument document = (StyledDocument) textPane.getDocument();
	    try {
	    	System.out.println(r.reportePantalla());
			document.insertString(document.getLength(), r.reportePantalla(), null);
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
	    
	    
	    contentPane.add(textPane);
	    
	    JButton btnGuardarReporte = new JButton("Guardar Reporte");
	    btnGuardarReporte.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		//se crea y manda la pantalla de elecci�n de la ruta d�nde se guardar� el archivo
	    		JFileChooser path = new JFileChooser();
	    		int option = path.showSaveDialog(estaVentana);
	    		
	    		//en caso de que se pueda crear el archivo, se crea el archivo
	    		//se agrega la extensi�n .pdf en caso de ser necesario y se manda a escrbir el contenido
	    		//del textPane en el archivo de la ruta seleccionada 
	    		if(option == JFileChooser.APPROVE_OPTION) {
	    			File f = path.getSelectedFile();
	    			documentPath = f.toString();
	    			if(!documentPath.endsWith(".pdf"))
	    				documentPath = documentPath.concat(".pdf");
	    			r.generarReporte(documentPath);
	    		}
	    			
	    	}
	    });
	    btnGuardarReporte.setBounds(406, 440, 129, 23);
	    contentPane.add(btnGuardarReporte);
	}
	
	
}
