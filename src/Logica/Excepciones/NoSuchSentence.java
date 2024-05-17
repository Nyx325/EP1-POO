package Logica.Excepciones;

public class NoSuchSentence extends Exception {
    public NoSuchSentence(int lineIndex) {
        super("Necesita especificarse una sentencia dentro\ndel while en la linea " + (lineIndex + 1)+"\n");
    }
}
