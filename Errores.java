package p2;
import java.io.IOException;
import java.util.ArrayList;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import javax.servlet.http.HttpServletResponse;
import org.xml.sax.ErrorHandler;
import java.io.PrintWriter;

public class Errores extends DefaultHandler{
	private ArrayList<String> errors =  new ArrayList<String>();
	private ArrayList<String> fatalErrors =  new ArrayList<String>();
	private ArrayList<String> warnings =  new ArrayList<String>();

	@Override
	public void warning(SAXParseException ex){
        warnings.add(ex.getMessage());
    }

 
    @Override
    public void error(SAXParseException ex){
        errors.add(ex.getMessage());
    }

 
	@Override
    public void fatalError(SAXParseException ex){
        fatalErrors.add(ex.getMessage());
    }
    





	public ArrayList<String> getErrors(){
		return errors;
	}

	public void setErrors(ArrayList<String> errors){
		this.errors = errors;

	}
	public ArrayList<String> getFatalErrors(){
		return fatalErrors;
	}

	public void setFatalErrors(ArrayList<String> fatalErrors){
		this.fatalErrors = fatalErrors;

	}

	public ArrayList<String> getWarnings(){
		return warnings;
	}

	public void setWarnings(ArrayList<String> warnings){
		this.warnings = warnings;

	}

	public static void errorWindow(HttpServletResponse response, String parameter, String auto)throws IOException{
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		if(auto != null && auto.equals("si")){
			response.setContentType("text/xml; charset=UTF-8");
			out.println("<?xml version='1.0' encoding='utf-8'?>");
			switch(parameter){
				case "plang":
				case "pgen": 
				case "pint":
					out.println("<wrongRequest>no param:"+parameter+"</wrongRequest>");
					break;
				case "np":				
					out.println("<wrongRequest>no passwd</wrongRequest>");
					break;
				case "wp":
					out.println("<wrongRequest>bad passwd</wrongRequest>");
					break;
			}
		}else{
			response.setContentType("text/html; charset=UTF-8");
			out.println("<!Doctype html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servicio de consultas de información musical</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<form method='GET' action=''>");
			switch(parameter){
				case "panio":
				case "pidd": 
				case "pidc":
					out.println("<h1> No param: "+parameter+"</h1>");
					break;
				case "np":				
					out.println("<h1> no passwd </h1>");
					break;
				case "wp":
					out.println("<h1> bad passwd </h1>");
					break;
			}
			out.println("</body>");
			out.println("</html>");
		}
		return;
	}
}