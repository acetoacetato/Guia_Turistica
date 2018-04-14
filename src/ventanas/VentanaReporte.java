package ventanas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import main.java.CuentaUsuario;
import main.java.Lugar;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaReporte extends JFrame {

	private JPanel contentPane;
	JFrame estaVentana;
	
	//path por defecto, queda en la raiz del proyecto
	private String documentPath = "texto.pdf";
	private JTextPane textPane;

	/**
	 * Create the frame.
	 */
	
	public VentanaReporte(ArrayList<Lugar> l, CuentaUsuario usr) {
		
		//se guarda referencia al JFrame para su utilización al guardar el PDF
		estaVentana = this;
		setBounds(100, 100, 575, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(5, 5, 549, 409);
		
		//Se establece el formato de la fecha que se escribirá en el documento
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
		
		//se obtiene el usuario que se agregará al documento
		String nombre = usr.informacionCuenta();
		
		//se obtiene el StyledDocument del textPane para agregar cosas
		StyledDocument document = (StyledDocument) textPane.getDocument();
	    try {
	    	
	    	//se inserta la primera linea del reporte
			document.insertString(document.getLength(), nombre, null);
			
			//se inserta la segunda linea del reporte
			document.insertString(document.getLength(), "\n\n  Fecha de creación:\n\t    " + format.format(new Date()), null);
			
			//se inserta la categoría buscada
			document.insertString(document.getLength(), "\nCategoría:\n " + l.get(0).getCategoria(), null);
			
			
			document.insertString(document.getLength(), "\nLugares Buscados:", null);
			
			//se insertan todos los lugares encontrados en ese parámetro de busqueda
			for(Lugar i : l) {
				document.insertString(document.getLength(), "\n\t" + i.getNombreLocal() + ", " + i.getComuna() + ", " + i.getRegion() + ", " + i.getPais(), null);
			}
			
			
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
	    
	    
	    contentPane.add(textPane);
	    
	    JButton btnGuardarReporte = new JButton("Guardar Reporte");
	    btnGuardarReporte.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		//se crea y manda la pantalla de elección de la ruta dónde se guardará el archivo
	    		JFileChooser path = new JFileChooser();
	    		int option = path.showSaveDialog(estaVentana);
	    		
	    		//en caso de que se pueda crear el archivo, se crea el archivo
	    		//se agrega la extensión .pdf en caso de ser necesario y se manda a escrbir el contenido
	    		//del textPane en el archivo de la ruta seleccionada 
	    		if(option == JFileChooser.APPROVE_OPTION) {
	    			File f = path.getSelectedFile();
	    			documentPath = f.toString();
	    			if(!documentPath.endsWith(".pdf"))
	    				documentPath = documentPath.concat(".pdf");
	    			escribeDocumento();
	    		}
	    			
	    	}
	    });
	    btnGuardarReporte.setBounds(406, 440, 129, 23);
	    contentPane.add(btnGuardarReporte);
	}
	
	public void escribeDocumento() {

		
		//se crea una instancia de Document
	    Document pdf = new Document();
	    
	    //se crea una instancia de escritura de archivo
    	FileOutputStream archivo; 
		try {
			
			//se abre el archivo en modo escritura
			archivo = new FileOutputStream(documentPath);
			
			//se crea una instancia de escritura del pdf
			PdfWriter.getInstance(pdf, archivo);
			
			//se abre el archivo
	    	pdf.open();
	    	
	    	//se agrega un párrafo con el contenido del textPane
	    	pdf.add(new Paragraph(textPane.getText()));
	    	
	    	//se agrega el autor y la fecha de creación al documento
	    	pdf.addAuthor("Aplicación guia turística");
	    	pdf.addCreationDate();
	    	
	    	//se cierra el documento
	    	pdf.close();
	    	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
