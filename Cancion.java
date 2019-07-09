package p2;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.io.IOException;
import java.util.ArrayList;

public class Cancion {
	String titulo;
	String idc;
	String descripcion;
	String premios;
	String genero;
	String duracion;

	public Cancion(){}

	public Cancion(String titulo, String idc, String descripcion, ArrayList<String> premios, String genero, String duracion){ 
		this.titulo =  titulo;
		this.idc = idc;
		this.descripcion = descripcion;
		this.premios = "";
		for(int i = 0; i < premios.size(); i++){
			this.premios = this.premios + ", " + premios.get(i);
		}
		this.genero = genero;
		this.duracion = duracion;
	}
	/*
	public Cancion(String titulo, String idc, String genero, String duracion){
		this.titulo =  titulo;
		this.idc = idc;
		this.genero = genero;
		this.duracion = duracion;
	}	*/

	public Cancion(String titulo, String idc, String descripcion, String genero){
		this.titulo =  titulo;
		this.idc = idc;
		this.genero = genero;
		this.descripcion = descripcion;
	}

	public Cancion(String titulo, String descripcion, ArrayList<String> premios){
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.premios = "";
		for(int i = 0; i < premios.size(); i++){
			this.premios = this.premios + " " + premios.get(i);
		}
	}
	
	public static ArrayList<Cancion> ordenarPorGenero(ArrayList<Cancion> lista)throws IOException{	
		ArrayList<Cancion> sortedList = new ArrayList<Cancion>();
		ArrayList<String> genero = new ArrayList<String>();
		int index = 0;
		String aux = "";
		int comp1, comp2 = 0;
		for(int i = 0; i < lista.size(); i++){
			aux = lista.get(i).getGenero() + lista.get(i).getTitulo();
			genero.add(aux);
		}
		Collections.sort(genero);
		//SintNumP2.escribir("n interpretes: "+interpretes.size());
		for(int i = 0; i < genero.size(); i++){
			//SintNumP2.escribir("genero: " + genero.get(i));
			for(int j = 0; j < lista.size(); j++){
				if(genero.get(i).contains(lista.get(j).getTitulo())){
					//SintNumP2.escribir("Metemos cancion en la lista");
					sortedList.add(lista.get(j));
				}
			}
		}	
		//SintNumP2.escribir("Numero de canciones ordenadas: "+ sortedList.size());
		return sortedList;
	}
	
	public static ArrayList<Cancion> ordenarPorTitulo(ArrayList<Cancion> lista) {
		TreeMap<String,Cancion> canciones = new TreeMap<String, Cancion>();
		int tiempo = 0; 
		for(int i = 0; i < lista.size(); i++) {
			canciones.put(lista.get(i).getTitulo(), lista.get(i));
		}
		lista.clear();
		for(String key : canciones.keySet()){
			lista.add(canciones.get(key));
		}
		Collections.reverse(lista);
		return lista;
	}
	
	
	
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPremios() {
		return premios;
	}
	public void setPremios(String premios) {
		this.premios = premios;
	}
	public String getIdc() {
		return idc;
	}
	public void setIdc(String idc) {
		this.idc = idc;
	}
}
