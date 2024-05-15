package Logica.Entidad;

public class WhileLoop {
    public long startLine;
    public long endLine;
    public boolean multiLine;
    public boolean closed;

    public WhileLoop(){}
    public WhileLoop(long startLine){
        this.startLine = startLine;
        this.endLine = -1;
        this.multiLine = false;
        this.closed = false;
    }
}
