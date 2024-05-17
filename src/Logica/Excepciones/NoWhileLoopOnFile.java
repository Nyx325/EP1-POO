package Logica.Excepciones;

public class NoWhileLoopOnFile extends Exception {
    public NoWhileLoopOnFile() {
        super("No existe ningún bucle while en el archivo\n");
    }
}
