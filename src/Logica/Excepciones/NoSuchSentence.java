package Logica.Excepciones;

public class NoSuchSentence extends Exception {
    public NoSuchSentence(int lineIndex) {
        super("Necesita especificarse una sentencia dentro del while en la linea " + (lineIndex + 1));
    }
}
