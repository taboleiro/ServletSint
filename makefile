CONTEXT = ../..
JFLAGS = -g
JC = javac
LIBDIR = $(CONTEXT)/apache-tomcat-9.0.16/lib/servlet-api.jar
CLSDIR = $(CONTEXT)/public_html/webapps/WEB-INF/classes
.SUFFIXES: .java .class
.java.class:
	$(JC) -classpath $(LIBDIR) -d $(CLSDIR) *.java

CLASSES = \
	Cancion.java \
	Disco.java \
	Errores.java \
	Sint137P2.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
