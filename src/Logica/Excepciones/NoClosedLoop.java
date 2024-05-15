package Logica.Excepciones;

public class NoClosedLoop extends Exception {
    public NoClosedLoop(int lineIndex) {
        super("No se cerró el bucle en la linea " + lineIndex);
    }
}
