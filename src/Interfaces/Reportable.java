package Interfaces;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;


public interface Reportable {
	public static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    public static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    public static final Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    public static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

	public void generarReporte(String path);
	public String reportePantalla();
	//public void reporte(Busqueda b);
	
}
