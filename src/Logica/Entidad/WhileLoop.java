package Logica.Entidad;

/**
 * Considereando que un archivo pueda tener más de
 * una estructura repetitiva en todo el archivo e incluso
 * anidando varios uno dentro de otro. Debo almacenar
 * informacion de estos como, en qué linea empiezan, en
 * cuál terminan, e incluso si este es un bucle de una
 * única linea. Por lo cual mediante este objeto guardo
 * estos metadatos de los bucles, para poder almacenarlos
 * en una lista
 */
public class WhileLoop {
    public long startLine;
    public long endLine;
    public boolean multiLine;
    public boolean closed;
    public String sentence;

    public WhileLoop(){
        this.startLine = -1;
        this.endLine = -1;
        this.multiLine = false;
        this.closed = false;
    }

    public WhileLoop(long startLine) {
        this.startLine = startLine;
        this.endLine = -1;
        this.multiLine = false;
        this.closed = false;
    }

    public WhileLoop(long startLine, long endLine, boolean multiLine, boolean closed, String sentence) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.multiLine = multiLine;
        this.closed = closed;
        this.sentence = sentence;
    }

    public WhileLoop(long startLine, boolean multiLine, boolean closed, String sentence) {
        this.startLine = startLine;
        this.multiLine = multiLine;
        this.closed = closed;
        this.sentence = sentence;
    }

}
