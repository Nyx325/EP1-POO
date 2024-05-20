package Logica.Entidad;

import java.util.ArrayList;
import java.util.List;

import Logica.Excepciones.PrintfSyntaxError;


public class PrintfChecker {
    String sentence;
    String quotesSentece;
    String argsSentece;
    List<String> argsExpected;

    public PrintfChecker(){
        argsExpected = new ArrayList<>();
        quotesSentece = "";
        argsSentece = "";
    }

    public void check(String sentence) throws Exception {
        this.sentence = sentence;
        checkSentence();
    }

    public void checkSentence() throws Exception{
        if(!sentence.contains("printf(")) throw new PrintfSyntaxError("No se encuentra ninguna sentencia prinf");

        char[] sentCharArr = sentence.toCharArray();
        int printfStart = sentence.indexOf("printf(");
        boolean quotesClosed = false;

        if(sentCharArr[printfStart+7] != '"') throw new PrintfSyntaxError("El primer argumento debe estar entre comillas");

        for(int i = printfStart+8; i < sentence.length(); i++){
            if(sentCharArr[i] == '"' && sentCharArr[i-1] != '\\'){
                quotesClosed = true;
                break;
            } 
            quotesSentece = quotesSentece + sentCharArr[i];
            System.out.println(quotesSentece);
        }

        if(!quotesClosed) throw new PrintfSyntaxError("No se han cerrado las comillas del primer argumento");

    }

    public void getExtraArgs(){
        
    }
}
