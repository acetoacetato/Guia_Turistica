package main.java;

public class Direccion {
	
	private String particular;
	private String comuna;
	private String region;
	private String pais;
	
	
	public Direccion() {
		particular = "camilo henriquez 4142";
		comuna = "puente alto";
		region = "santiago";
		pais = "chile";
	}
	
	public Direccion(String[] dir) {
		particular = dir[0];
		comuna = dir[1];
		region = dir[2];
		pais = dir[3];
	}
	
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
