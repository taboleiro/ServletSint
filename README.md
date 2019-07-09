# ServletSint
práctica 2 de sint. Validada pero que, a pesar de marcar los ficheros que están mal formados, los usa



********************************************************************************************
***********************   COMO EJECUTAR Y VALIDAR EL PROYECTO ******************************
********************************************************************************************
Una vez abierto el túnel, NO tenéis que lanzar vuestro TOMCAT. Sólo debéis invocar en el Firefox de vuestro ordenador:

http://localhost:7000/sintprof/P2IMCheck

Y la solicitud al puerto 7000 irá directamente a la máquina externo1 en lugar de a la vuestra.
javac -classpath lib/servlet-api.jar -d webapps/p2/WEB-INF/classes/ webapps/p2/*.java
