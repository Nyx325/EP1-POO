package Logica.Excepciones;

public class MissingCloseBracket extends Exception{
    public MissingCloseBracket(int lineNum){
        super("No se ha cerrado el ciclo de la linea "+lineNum);
    }
}
