package main.java;

public class Direccion {
	
	private String particular;
	private String comuna;
	private String region;
	private String pais;
	
	
	/**
	 * Constructor por defecto, centrado en una dirección arbitraria.
	 */
	public Direccion() {
		particular = "camilo henriquez 4142";
		comuna = "puente alto";
		region = "santiago";
		pais = "chile";
	}
	
	
	/**
	 * Constructor de una dirección, recibe un String[]
	 * @param dir : String de la dirección, debe ser de largo 4, con orden : nombre de calle + número, comuna, región, país.
	 */
	public Direccion(String[] dir) {
		particular = dir[0];
		comuna = dir[1];
		region = dir[2];
		pais = dir[3];
	}
	
	/**
	 * Constructor de una dirección, recibe los parámetros de esta.
	 * @param par : nombre de calle + número.
	 * @param com : comuna.
	 * @param reg : región.
	 * @param pa : país.
	 */
	public Direccion(String par, String com, String reg, String pa) {
		particular = par;
		comuna = com;
		region = reg;
		pais = pa;
	}
	
	
	/*
	 * Getters
	 * */
	
	public String getParticular() {
		return particular;
	}
	
	public String getComuna() {
		return comuna;
	}
	
	public String getRegion(){
		return region;
	}
	
	public String getPais() {
		return pais;
	}
	
	public String[] getDirArray() {
		String[] str = new String[4];
		str[0] = particular;
		str[1] = comuna;
		str[2] = region;
		str[3] = pais;
		
		return str;	
	}
	
	/*
	 * Setters
	 * */
	
	public void setParticular(String str) {
		particular = str;
	}
	
	public void setComuna(String str) {
		comuna = str;
	}
	
	public void setRegion(String str) {
		region = str;
	}
	
	public void setPais(String str) {
		pais = str;
	}
	
	public void setDir(String[] dir) {
		if(dir.length != 4)
			return;
		particular = dir[0];
		comuna = dir[1];
		region = dir[2];
		pais = dir[3];		
		
	}
	
}
