package Logica.Excepciones;

public class InvalidSentence extends Exception{
    public InvalidSentence(long lineNum){
        super("Sentencia no válida en el bucle de la linea "+lineNum);
    }
}
