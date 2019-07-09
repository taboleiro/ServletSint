package p2;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Enumeration;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Sint137P2 extends HttpServlet{
	static Element raiz;

	PrintWriter out;
	static String password = "taboleiro8";
	Boolean ini = true;

	LinkedHashMap<String, Document> filesXML = new LinkedHashMap<String, Document>();
	LinkedList<Element> MMLs = new LinkedList<Element>();

	ArrayList<String> langs = new ArrayList<String>();
	ArrayList<String> interpretes = new ArrayList<String>();
	ArrayList<Disco> discos = new ArrayList<Disco>();
	ArrayList<Cancion> canciones = new ArrayList<Cancion>();

	public static LinkedHashMap<String, ArrayList<String>> warnings = new LinkedHashMap<String, ArrayList<String>>();
	public static LinkedHashMap<String, ArrayList<String>> errores = new LinkedHashMap<String, ArrayList<String>>();
	public static LinkedHashMap<String, ArrayList<String>> erroresFatales = new LinkedHashMap<String,  ArrayList<String>>();


	static String dir = "http://gssi.det.uvigo.es/users/agil/public_html/SINT/18-19/";
	static String dirXML = "iml2001.xml";

	static URL dirSchema;

	boolean init = true;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String dirFile;
		if(init) {
			PrintWriter out = response.getWriter();
			try{
				URL dirServlet = new URL(request.getRequestURL().toString());
				escribir(dirServlet.toString());
				dirSchema = new URL(dirServlet, "iml.xsd");
			}catch(MalformedURLException e){

			}
			try{
				dirFile = dir + dirXML;
				escribir(dirFile);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(dirFile);
				doc.getDocumentElement().normalize();
				validateXML();
				init = false;
				//printTree(doc);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String pfase = request.getParameter("pfase");
		if(pfase == null){
			pfase = "01";
		}else{
			escribir(pfase);
		}
		if(request.getParameter("p") == null){
			Errores.errorWindow(response, "np", request.getParameter("auto"));
		}else if(!request.getParameter("p").equals(password)){
			Errores.errorWindow(response, "wp", request.getParameter("auto"));
		}else{
			switch(pfase){
				case "01":
				pantalla01(request,response);
				break;
				case "02":
				pantalla02(request,response);
				break;
				case "21":
				pantalla21(request,response);
				break;
				case "22":
				pantalla22(request,response);
				break;
				case "23":
				pantalla23(request,response);
				break;
				case "24":
				pantalla24(request,response);
				break;
				default:
				pantalla01(request,response);
				break;
			}
		}
	}


	/*********************************************************************************/
	/*********************************************************************************/
	/***************** DEFINICION DE LAS FUNCIONES DE PANTALLAS **********************/
	/*********************************************************************************/
	/*********************************************************************************/

	public void pantalla01(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			if(request.getParameter("auto") == null){
				out.println("<!Doctype html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel='stylesheet' type='text/css' href='iml.css'>");
				out.println("<title>Servicio de consultas de canciones</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<form method='GET' action='' accept-charset='utf-8'>");
				out.println("<input type='hidden' name='pfase'>");
				out.println("<input type='hidden' name='p' value="+request.getParameter("p")+" >");
				out.println("<h1> Servicio de consulta de canciones</h1>");
				out.println("<br>");
				out.println("<h2> Bienvenido a este servicio</h2>");
				out.println("<br>");
				out.println("<input type='submit' value='pulsa aqui para ver los ficheros erroneos' onclick='document.forms[0].pfase.value='02'>");
				out.println("<br>");
				out.println("<h3> Selecciona una consulta:</h3>");
				out.println("<br>");
				out.println("<input type='radio' checked='true' value=21> Consulta 1: Canciones de un interprete que duran menos de una dada");
				out.println("<br>");
				out.println("<input type='submit' value='enviar' onclick='document.forms[0].pfase.value=21'>");
				out.println("</form>");
				out.println("<br>");
				out.println("<div class='footer' name='nameAutor'>Pablo Táboas Rivas</div>");
				out.println("</body>");
				out.println("</html>");
			}else{
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				out.println("<?xml version='1.0' encoding='utf-8' ?>");
				out.println("<service>");
				out.println("<status>OK</status>");
				out.println("</service>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public void pantalla02(HttpServletRequest request,HttpServletResponse response) {
		try{
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			if(request.getParameter("auto") == null){
				out.println("<!Doctype html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel='stylesheet' type='text/css' href='iml.css'>");
				out.println("<title>Errores</title>");
				out.println("<body>");
				out.println("<form method='GET' action='' accept-charset='utf-8'>");
				out.println("<h1> Servicio de consulta de canciones</h2>");
				out.println("<ul>");
				out.println("<h2>Se han encontrado "+warnings.size()+" ficheros con warnings:</h2>");
				out.println("<br>");
				for(String key : warnings.keySet()) {
					out.println("<li type='disc'>"+key+"</li>");
					out.println("<ul>");
					for(int i = 0; i < warnings.get(key).size(); i++){
						out.println("<li type='circle'>"+warnings.get(key)+"</li>");
					}
					out.println("</ul>");
				}
				out.println("<h2>Se han encontrado "+errores.size()+" ficheros con errores:</h2>");
				out.println("<br>");
				for(String key : errores.keySet()) {
					out.println("<li type='disc'>"+key+"</li>");
					out.println("<ul>");
					for(int i = 0; i < errores.get(key).size(); i++){
						out.println("<li type='circle'>"+errores.get(key).get(i)+"</li>");
					}
					out.println("</ul>");
				}
				out.println("<h2>Se han encontrado "+erroresFatales.size()+" ficheros con errores fatales:</h2>");
				out.println("<br>");
				for(String key : erroresFatales.keySet()) {
					out.println("<li type='disc'>"+key+"</li>");
					out.println("<ul>");
					//out.println("len: "+erroresFatales.get(key).size());
					for(int i = 0; i < erroresFatales.get(key).size(); i++){
						out.println("<li type='circle'>"+erroresFatales.get(key).get(i)+"</li>");
					}
					out.println("</ul>");
				}
				out.println("</ul>");
				out.println("<br>");
				out.println("<input type='submit' id='atras' value='atras' onclick='document.forms[0].pfase.value=11'>");
				out.println("</form>");
				out.println("<div class='footer' name='nameAutor'>Pablo Táboas Rivas</div>");
				out.println("</body>");
				out.println("</html>");
			}else{
				String aux = "";
				response.setContentType("text/xml; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				out.println("<?xml version='1.0' encoding='utf-8' ?>");
				out.println("<errores>");
				out.println("	<warnings>");
				for(String key : warnings.keySet()) {
					out.println("		<warning>");
					out.println("			<file>"+key+"</file>");	
					for(int i = 0; i < warnings.get(key).size(); i++){
						aux += warnings.get(key).get(i)+"; ";
					}
					out.println("			<cause>"+aux+"</cause>");				
					out.println("		</warning>");					
				}
				out.println("	</warnings>");
				aux = "";
				out.println("	<errors>");
				for(String key : errores.keySet()) {
					out.println("		<error>");
					out.println("			<file>"+key+"</file>");	
					for(int i = 0; i < errores.get(key).size(); i++){
						aux += errores.get(key).get(i)+"; ";
					}
					out.println("			<cause>"+aux+"</cause>");				
					out.println("		</error>");					
				}
				out.println("	</errors>");
				aux = "";
				out.println("	<fatalerrors>");
				for(String key : erroresFatales.keySet()) {
					out.println("		<fatalerror>");
					out.println("			<file>"+key+"</file>");	
					for(int i = 0; i < erroresFatales.get(key).size(); i++){
						aux += erroresFatales.get(key).get(i)+"; ";
					}
					out.println("			<cause>"+aux+"</cause>");				
					out.println("		</fatalerror>");					
				}
				out.println("	</fatalerrors>");				
				out.println("</errores>");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return;
	}

	public void pantalla21(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			langs = getC2Langs();
			if(request.getParameter("auto") == null){
				out.println("<!Doctype html>");
				out.println("<html>");
				out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
				out.println("<title>Servicio de consultas de canciones</title>");
				out.println("<head>");
				out.println("<link rel='stylesheet' type='text/css' href='iml.css'>");
				out.println("</head>");
				out.println("<body>");
				out.println("<form method='GET' action='' accept-charset='utf-8'>");
				out.println("<input type='hidden' name='pfase'>");
				out.println("<input type='hidden' name='p' value="+request.getParameter("p")+" >");
				out.println("<h1> Servicio de consulta de información musical </h1>");
				out.println("<br>");
				out.println("<h2> Consulta 2. Fase 21: lista de idiomas </h2>");
				out.println("<br>");
				for(int i = 0; i < langs.size(); i++) {
					if (i == 0){
						out.println("<input type = 'radio' name = 'plang' value = "+langs.get(i)+" checked='true'>"+langs.get(i));
						out.println("<br>");
					}else{
						out.println("<input type = 'radio' name = 'plang' value = "+langs.get(i)+">"+langs.get(i));
						out.println("<br>");
					}
				}
				out.println("<h3> Seleccione un idioma</h3>");
				out.println("<br>");
				out.println("<input type='submit' id='enviar' value='enviar' onclick='document.forms[0].pfase.value=22'>");
				out.println("<input type='submit' id='atras' value='atras' onclick='document.forms[0].pfase.value=01'>");
				out.println("</form>");
				out.println("<br>");
				out.println("<div class='footer' id='nameAutor'>Pablo Táboas Rivas</div>");
				out.println("</body>");
				out.println("</html>");
			}else{
				response.setContentType("text/xml; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				out.println("<?xml version='1.0' encoding='utf-8' ?>");
				out.println("<langs>");
				for(int i = 0; i < langs.size(); i++) {
					out.println("	<lang>"+langs.get(i)+"</lang>");
				}
				out.println("</langs>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public void pantalla22(HttpServletRequest request,HttpServletResponse response) {
		try {
			if(request.getParameter("plang") == null){
				Errores.errorWindow(response, "lang", request.getParameter("auto"));
			}

			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			discos.clear();
			canciones = getC2Canciones(request.getParameter("plang"));
			if(request.getParameter("auto") == null){
				out.println("<!Doctype html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
				out.println("<link rel='stylesheet' type='text/css' href='iml.css'>");
				out.println("<title>Servicio de consultas de información musical</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<form method='GET' action='' accept-charset='utf-8'>");
				out.println("<input type='hidden' name='pfase'>");
				out.println("<input type='hidden' name='plang' value="+request.getParameter("plang")+">");
				out.println("<h1>Servicio de consultas de información musical</h1>");
				out.println("<h2> consulta 2:Fase 22: Canciones en el idioma '"+request.getParameter("plang")+"'</h2>");
				out.println("<input type='hidden' name='p' value="+request.getParameter("p")+" >");
				out.println("<br>");
				for(int i = 0; i < canciones.size(); i++) {
					if (i == 0){
						out.println("<input checked='true' type = 'radio' name = 'pgen' value = "+canciones.get(i).getGenero()+">"+(i+1)+".- <b>Título</b>: = '"+canciones.get(i).getTitulo()+"' --- <b>Género</b> = '"+canciones.get(i).getGenero()+"' --- <b>Descripción</b> = '"+canciones.get(i).getDescripcion()+"'");
						out.println("<br>");
					}else{
						out.println("<input type = 'radio' name = 'pgen' value = "+canciones.get(i).getGenero()+">"+(i+1)+".- <b>titulo</b>: = '"+canciones.get(i).getTitulo()+"' --- <b>Género</b> = '"+canciones.get(i).getGenero()+"' --- <b>Descripción</b> = '"+canciones.get(i).getDescripcion()+"'");
						out.println("<br>");
					}
				}
				out.println("<input type='submit' id='enviar' value='enviar' onclick='document.forms[0].pfase.value=23'>");
				out.println("<input type='submit' id='atras' value='atras' onclick='document.forms[0].pfase.value=21'>");
				out.println("<input type='submit' id='inicio' value='inicio' onclick='document.forms[0].pfase.value=01'>");
				out.println("<br>");
				out.println("<h3> Selecciona una canción:</h3>");
				out.println("</form>");
				out.println("<div class='footer' name='nameAutor'>Pablo Táboas Rivas</div>");
				out.println("</body>");
				out.println("</html>");
			}else{
				response.setContentType("text/xml; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				out.println("<?xml version='1.0' encoding='utf-8' ?>");
				out.println("<canciones>");
				for(int i = 0; i < canciones.size(); i++) {
					out.println("<cancion idc='"+canciones.get(i).getIdc()+"' genero='"+canciones.get(i).getGenero()+"' descripcion='"+canciones.get(i).getDescripcion().trim()+"'>"+canciones.get(i).getTitulo()+"</cancion>");				
				}
				out.println("</canciones>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e){
		}
		return;
	}

	public void pantalla23(HttpServletRequest request,HttpServletResponse response) {
		try {
			if(request.getParameter("plang") == null){
				Errores.errorWindow(response, "plang", request.getParameter("auto"));
			}else if(request.getParameter("pgen") == null){
				Errores.errorWindow(response, "pgen", request.getParameter("auto"));
			}else{
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				interpretes = getC2Interpretes(request.getParameter("plang"),request.getParameter("pgen"));
				if(request.getParameter("auto") == null){
					out.println("<!Doctype html>");
					out.println("<html>");
					out.println("<head>");
					out.println("<link rel='stylesheet' type='text/css' href='iml.css'>");
					out.println("<title>Servicio de consultas de canciones</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<form method='GET' action='' accept-charset='utf-8'>");
					out.println("<input type='hidden' name='pfase'>");
					out.println("<input type='hidden' name='plang' value="+request.getParameter("plang")+">");
					out.println("<input type='hidden' name='pgen' value="+request.getParameter("pgen")+">");
					out.println("<h1> consulta 2. Fase 23: intérpretes de canciones en '"+request.getParameter("plang")+"' y con el género '"+request.getParameter("pgen")+"'</h1>");
					out.println("<input type='hidden' name='p' value="+request.getParameter("p")+" >");
					out.println("<br>");
					out.println("<h3> Selecciona una canción:</h3>");
					out.println("<br>");
					for(int i = 0; i < interpretes.size(); i++) {
						if (i == 0){
							out.println("<input checked='true' type = 'radio' name = 'pint' value = '"+interpretes.get(i)+"'>"+(i+1)+".- "+interpretes.get(i));
							out.println("<br>");
						}else{
							out.println("<input type = 'radio' name = 'pint' value = '"+interpretes.get(i)+"'>"+(i+1)+".- "+interpretes.get(i));
							out.println("<br>");
						}
					}
					out.println("<input type='submit' id='enviar' value='enviar' onclick='document.forms[0].pfase.value=24'>");
					out.println("<input type='submit' id='atras' value='atras' onclick='document.forms[0].pfase.value=22'>");
					out.println("<input type='submit' id='inicio' value='inicio' onclick='document.forms[0].pfase.value=01'>");
					out.println("</form>");
					out.println("<br>");
					out.println("<div class='footer' name='nameAutor'>Pablo Táboas Rivas</div>");
					out.println("</body>");
					out.println("</html>");
				}else{
					response.setContentType("text/xml; charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					out = response.getWriter();
					out.println("<?xml version='1.0' encoding='utf-8' ?>");
					out.println("<interpretes>");
					for(int i = 0; i < interpretes.size(); i++) {
						out.println("<interprete>"+interpretes.get(i)+"</interprete>");
					}
					out.println("</interpretes>");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public void pantalla24(HttpServletRequest request,HttpServletResponse response) {
		try {
			if(request.getParameter("plang") == null){
				Errores.errorWindow(response, "plang", request.getParameter("auto"));
			}else if(request.getParameter("pgen") == null){
				Errores.errorWindow(response, "pgen", request.getParameter("auto"));
			}else if(request.getParameter("pint") == null){
				Errores.errorWindow(response, "pint", request.getParameter("auto"));
			}else{
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				ArrayList<Disco> discos = new ArrayList<Disco>();
				discos = getC2Resultado(request.getParameter("plang"), request.getParameter("pgen"), request.getParameter("pint"));
				if(request.getParameter("auto") == null){
					out.println("<!Doctype html>");
					out.println("<html>");
					out.println("<head>");
					out.println("<link rel='stylesheet' type='text/css' href='iml.css'>");
					out.println("<title>Servicio de consultas de información musical</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<form method='GET' action='' accept-charset='utf-8'>");
					out.println("<input type='hidden' name='pfase'>");
					out.println("<h1> Servicio de consulta de información musical</h1>");
					out.println("<h1> consulta 2: Fase 24: discos de canciones en '"+request.getParameter("plang")+"', con el género '"+request.getParameter("pgen")+"'y con intérpretes '"+request.getParameter("pint")+"'</h1>");
					out.println("<br>");
					out.println("<input type='hidden' name='p' value="+request.getParameter("p")+">");
					out.println("<input type='hidden' name='panio' value="+request.getParameter("plang")+">");
					out.println("<input type='hidden' name='pidd' value="+request.getParameter("pgen")+">");
					out.println("<input type='hidden' name='pidc' value="+request.getParameter("pint")+">");
					out.println("<h3> Estos son los discos:</h3>");
					out.println("<br>");
					for(int i = 0; i < discos.size(); i++) {
						out.println((i+1)+".- <b>Título</b>: = '"+discos.get(i).getTitulo()+"' --- <b>IDD</b> = '"+discos.get(i).getIdd()+"' --- <b>Idiomas</b> = '"+discos.get(i).getIdiomas()+"'");
						out.println("<br>");

					}
					out.println("<input type='submit' id='atras 'value='atras' onclick='document.forms[0].pfase.value=13'>");
					out.println("<input type='submit' id='inicio' value='inicio' onclick='document.forms[0].pfase.value=01'>");
					out.println("</form>");
					out.println("<br>");
					out.println("<div class='footer' name='nameAutor'>Pablo Táboas Rivas</div>");
					out.println("</body>");
					out.println("</html>");
				}else{
					response.setContentType("text/xml; charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					out = response.getWriter();
					out.println("<?xml version='1.0' encoding='utf-8' ?>");
					out.println("<discos>");
					for(int i = 0; i < discos.size(); i++) {
						out.println("<disco idd='"+discos.get(i).getIdd()+"' langs='"+discos.get(i).getIdiomas()+"'>"+discos.get(i).getTitulo()+"</disco>");
					}
					out.println("</discos>");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public ArrayList<String> getC2Langs()throws IOException{
		escribir("ORDENANDO IDIOMAS");
		ArrayList<String> langs = new ArrayList<String>();
		ArrayList<String> usedFiles = new ArrayList<String>();
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			for(String key : filesXML.keySet()){
				doc = filesXML.get(key);
				if(!usedFiles.contains(key)){
					usedFiles.add(key);
					NodeList discosXML = doc.getElementsByTagName("Disco");
					for(int i = 0; i < discosXML.getLength(); i++){
						escribir("entramos en disco nº "+ i);
						NodeList childNodes = discosXML.item(i).getChildNodes();
						if (discosXML.item(i).getAttributes().getNamedItem("langs") == null){
							if(!langs.contains(discosXML.item(i).getParentNode().getAttributes().getNamedItem("lang").getTextContent())){
								langs.add(discosXML.item(i).getParentNode().getAttributes().getNamedItem("lang").getTextContent());
							}
						}
						else{
							String idiomas[] = discosXML.item(i).getAttributes().getNamedItem("langs").getTextContent().split(" ");
							for(int i_idioma = 0; i_idioma < idiomas.length; i_idioma++){
								if(!langs.contains(idiomas[i_idioma])){
									langs.add(idiomas[i_idioma]);
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			escribir(e.toString());
			e.printStackTrace();
		}	
		Collections.sort(langs);
		return langs;
	}

	public ArrayList<Cancion> getC2Canciones (String lang)throws IOException{
		ArrayList<Cancion> cancionList= new ArrayList<Cancion>();
		ArrayList<String> usedFiles = new ArrayList<String>();
		ArrayList<String> titulos = new ArrayList<String>();
		String idiomasDisco = "";
		String titulo = "";
		String idc =  "";
		String descripcion = "";
		String duracion = "";
		String genero = "";
		Cancion tema;
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document fileXML;
			Document doc;
			for(String key : filesXML.keySet()){
				doc = filesXML.get(key);
				if(!usedFiles.contains(key)){
					usedFiles.add(key);
					NodeList discosXML = doc.getElementsByTagName("Disco");
					escribir("contamos con "+discosXML.getLength()+" discos");
					for(int i = 0; i < discosXML.getLength(); i++){
						idiomasDisco = "";
						if(discosXML.item(i).getAttributes().getNamedItem("langs") != null){
							idiomasDisco = discosXML.item(i).getAttributes().getNamedItem("langs").getTextContent();
						}else{
							idiomasDisco = discosXML.item(i).getParentNode().getAttributes().getNamedItem("lang").getTextContent();
						}
						if(idiomasDisco.contains(lang)){
							NodeList childNodes = discosXML.item(i).getChildNodes();
							for (int j = 0; j < childNodes.getLength(); j++) {
								if(childNodes.item(j).getNodeName() == "Cancion"){
									descripcion = "";
									Node cancion = childNodes.item(j);
									NodeList childCancion = cancion.getChildNodes();
									idc = cancion.getAttributes().getNamedItem("idc").getTextContent();
									for(int i_cancion = 0; i_cancion < childCancion.getLength(); i_cancion++){
										switch(childCancion.item(i_cancion).getNodeName()){
											case "Titulo":
												titulo = childCancion.item(i_cancion).getTextContent();
												escribir(titulo);
												break;
											case "#text":
												if(!childCancion.item(i_cancion).getTextContent().trim().isEmpty())
													descripcion = childCancion.item(i_cancion).getTextContent();																					
												break;
											case "Genero":
												genero = childCancion.item(i_cancion).getTextContent();
												break;
										}
									}
									tema = new Cancion(titulo, idc, descripcion, genero);
									if(!titulos.contains(titulo)){
										titulos.add(titulo);
										cancionList.add(tema);
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Cancion.ordenarPorGenero(cancionList);
	}

	ArrayList<String> getC2Interpretes(String lang, String gen)throws IOException{
		escribir("ORDENANDO interpretes");
		ArrayList<String> interpreteList= new ArrayList<String>();
		ArrayList<String> usedFiles = new ArrayList<String>();
		String interprete = "";
		String idiomasDisco = "";
		Boolean valid = false;
		Cancion tema;
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document fileXML;
			Document doc;
			for(String key : filesXML.keySet()){
				doc = filesXML.get(key);
				if(!usedFiles.contains(key)){
					usedFiles.add(key);
					NodeList discosXML = doc.getElementsByTagName("Disco");
					for(int i = 0; i < discosXML.getLength(); i++){
						valid = false;
						if(discosXML.item(i).getAttributes().getNamedItem("langs") != null){
							idiomasDisco = discosXML.item(i).getAttributes().getNamedItem("langs").getTextContent();
						}else{
							idiomasDisco = discosXML.item(i).getParentNode().getAttributes().getNamedItem("lang").getTextContent();
						}
						if(idiomasDisco.contains(lang)){
							NodeList childNodes = discosXML.item(i).getChildNodes();
							for (int j = 0; j < childNodes.getLength(); j++) {
								if(childNodes.item(j).getNodeName().equals("Interprete")){
									interprete = childNodes.item(j).getTextContent();
								}
								if(childNodes.item(j).getNodeName() == "Cancion"){
									Node cancion = childNodes.item(j);
									NodeList childCancion = cancion.getChildNodes();
									for(int i_cancion = 0; i_cancion < childCancion.getLength(); i_cancion++){
										if(childCancion.item(i_cancion).getNodeName().equals("Genero")){
											if(childCancion.item(i_cancion).getTextContent().equals(gen)){
												if(!interpreteList.contains(interprete)) interpreteList.add(interprete);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		Collections.sort(interpreteList, Collections.reverseOrder());
		return interpreteList;
	}

	ArrayList<Disco> getC2Resultado(String lang, String gen, String inter)throws IOException{
		ArrayList<Disco> discoList= new ArrayList<Disco>();
		ArrayList<String> usedFiles = new ArrayList<String>();
		String interprete = "";
		String idiomasDisco = "";
		String idd = "";
		String titulo = "";
		Boolean valid = false;
		Boolean artista = false;

		Disco disco;
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document fileXML;
			Document doc;
			for(String key : filesXML.keySet()){
				doc = filesXML.get(key);
				if(!usedFiles.contains(key)){
					usedFiles.add(key);
					NodeList discosXML = doc.getElementsByTagName("Disco");
					for(int i = 0; i < discosXML.getLength(); i++){
						artista = false;
						if(discosXML.item(i).getAttributes().getNamedItem("langs") != null){
							idiomasDisco = discosXML.item(i).getAttributes().getNamedItem("langs").getTextContent();
						}else{
							idiomasDisco = discosXML.item(i).getParentNode().getAttributes().getNamedItem("lang").getTextContent();
						}
						idd = discosXML.item(i).getAttributes().getNamedItem("idd").getTextContent();
						if(idiomasDisco.contains(lang)){
							NodeList childNodes = discosXML.item(i).getChildNodes();
							for (int j = 0; j < childNodes.getLength(); j++) {
								valid = false;
								if(childNodes.item(j).getNodeName().equals("Titulo")){
									titulo = childNodes.item(j).getTextContent();
								}
								if(childNodes.item(j).getTextContent().equals(inter) || artista){
									artista = true; // Encotramos un disco hecho por el artista que buscamos
									if(childNodes.item(j).getNodeName().equals("Cancion")){
										Node cancion = childNodes.item(j);
										NodeList childCancion = cancion.getChildNodes();
										for(int i_cancion = 0; i_cancion < childCancion.getLength(); i_cancion++){	
											if(childCancion.item(i_cancion).getNodeName().equals("Genero")){
												if(childCancion.item(i_cancion).getTextContent().equals(gen)){
													disco = new Disco(titulo, idd, inter, idiomasDisco);
													if(!discoList.contains(disco)) discoList.add(disco);
													break;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		Disco.ordenarPorTitulo(discoList);
		return discoList;
	}

		public void validateXML()throws IOException{	
		ArrayList<String> usedXML = new ArrayList<String>();	
		ArrayList<String> namesXML = new ArrayList<String>();
		namesXML.add(dirXML);
		ArrayList<String> aux = new ArrayList<String>();
		File file;
		Errores fallos;
		String nameXML = new String();
		ArrayList<Document> validXMLs = new ArrayList<Document>();
		escribir("validando XML. Numero en lista = "+namesXML.size());
		Enumeration<String> enm = Collections.enumeration(namesXML);
		Document doc;
		while(enm.hasMoreElements()){
			nameXML = enm.nextElement();
			escribir(nameXML);
			if (!usedXML.contains(nameXML)){
				usedXML.add(nameXML);
				try{
					SchemaFactory sFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
					Schema schema = sFactory.newSchema(dirSchema);
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					dbFactory.setSchema(schema);
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					fallos = new Errores();
					dBuilder.setErrorHandler(fallos);
					if (!nameXML.startsWith("http://")){
						nameXML = dir+nameXML;
					}
					doc = dBuilder.parse(nameXML);
					if(fallos.getErrors().size() != 0 || fallos.getWarnings().size() != 0 || fallos.getFatalErrors().size() != 0){
						if(fallos.getErrors().size() != 0){
							aux = fallos.getErrors();
							errores.put(nameXML, aux);
							for(int x= 0; x < fallos.getErrors().size(); x++){
								escribir(errores.get(nameXML).get(x));
							}
						}
						if(fallos.getWarnings().size() != 0){
							aux = fallos.getWarnings();
							warnings.put(nameXML, aux);
						}
						if(fallos.getFatalErrors().size() != 0){
							aux = fallos.getFatalErrors();
							erroresFatales.put(nameXML, aux);
						}
					}
					else{
						NodeList fileNames = doc.getElementsByTagName("IML");
						for(int i = 0; i < fileNames.getLength(); i++){
							if(!namesXML.contains(fileNames.item(i).getTextContent()) && doc.getElementsByTagName("IML").item(i).getTextContent().contains("xml")){
								namesXML.add(doc.getElementsByTagName("IML").item(i).getTextContent());
							}
							enm = Collections.enumeration(namesXML);
							doc.getDocumentElement().normalize();
							filesXML.put(nameXML,doc);
						}
					}
				}catch(SAXParseException e1){
					ArrayList<String> excepcion = new ArrayList<String>();
					excepcion.add(e1.getMessage());
					escribir(e1.getMessage());
					erroresFatales.put(nameXML, excepcion);
				}catch(IOException e){
					e.printStackTrace();
				}catch(SAXException e2){
					e2.printStackTrace();
				}catch(ParserConfigurationException e3){
					e3.printStackTrace();
				}
			}
		}
		return;
	}

	public static void escribir(String linea) throws IOException {
		/*
		FileWriter txt = new FileWriter(logFile,true);
		txt.write(linea);
		txt.write(" \r\n");
		txt.close();
		return;
		*/
	}
}
