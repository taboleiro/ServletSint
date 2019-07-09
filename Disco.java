package p2;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Collections;

public class Disco {
	String titulo;
	String idd;
	String interprete;
	String idiomas;
	
	public Disco(){}

	public Disco(String titulo, String idd, String interprete, String idiomas) {
		this.titulo = titulo;
		this.idd = idd;
		this.interprete = interprete;
		this.idiomas = idiomas;
	}
	
	public static ArrayList<Disco> ordenarPorTitulo(ArrayList<Disco> lista) {
		TreeMap<String ,Disco> discos = new TreeMap<String, Disco>();
		int tiempo = 0; 
		for(int i = 0; i < lista.size(); i++) {
			discos.put(lista.get(i).getTitulo(), lista.get(i));
		}
		lista.clear();
		for(String key : discos.keySet()){
			lista.add(discos.get(key));
		}
		return lista;
	}

	public static ArrayList<Disco> ordenarLista(ArrayList<Disco> lista)throws IOException{	
		ArrayList<Disco> sortedList = new ArrayList<Disco>();
		LinkedList<String> interpretes = new LinkedList<String>();
		LinkedList<String> discos = new LinkedList<String>();
		int index = 0;
		for(int i = 0; i < lista.size(); i++){
			interpretes.add(lista.get(i).getInterprete());
		}
		Collections.sort(interpretes);
		//SintNumP2.escribir("n interpretes: "+interpretes.size());
		for(int i = 0; i < interpretes.size(); i++){
			for(int j = 0; j < lista.size(); j++){
				if(lista.get(j).getInterprete() == interpretes.get(i)){
					index = j;
					discos.add(lista.get(j).getTitulo());
				}
			}
			Collections.sort(discos);

			//SintNumP2.escribir("n discos: "+discos.size());
			if(discos.size() == 1){
				sortedList.add(lista.get(index));
			}else{
				for(int j = 0; j < discos.size(); j++){
					if(lista.get(j).getTitulo() == discos.get(i)){
						sortedList.add(lista.get(j));
					}
				}
			}
			discos.clear();
		}
		return sortedList;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getIdd() {
		return idd;
	}
	public void setIdd(String idd) {
		this.idd = idd;
	}
	public String getInterprete() {
		return interprete;
	}
	public void setInterprete(String interprete) {
		this.interprete = interprete;
	}
	public String getIdiomas() {
		return idiomas;
	}
	public void setIdiomas(String idiomas) {
		this.idiomas = idiomas;
	}
}
