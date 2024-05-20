package Logica.Entidad;

import java.util.ArrayList;
import java.util.List;

import Logica.Excepciones.PrintfSyntaxError;

public class PrintfChecker {
    String sentence;
    String quotesSentece;
    String argsSentece;
    List<String> argsExpected;

    public PrintfChecker() {
        argsExpected = new ArrayList<>();
        quotesSentece = "";
        argsSentece = "";
    }

    public void check(String sentence) throws Exception {
        this.sentence = sentence;
        int startArgs = checkSentence();
        getExtraArgs(startArgs);
    }

    public int checkSentence() throws Exception {
        int i;
        if (!sentence.contains("printf("))
            throw new PrintfSyntaxError("No se encuentra ninguna sentencia prinf");

        char[] sentCharArr = sentence.toCharArray();
        int printfStart = sentence.indexOf("printf(");
        boolean quotesClosed = false;

        if (sentCharArr[printfStart + 7] != '"')
            throw new PrintfSyntaxError("El primer argumento debe estar entre comillas");

        for (i = printfStart + 8; i < sentence.length(); i++) {
            if (sentCharArr[i] == '"' && sentCharArr[i - 1] != '\\') {
                quotesClosed = true;
                break;
            }
            quotesSentece = quotesSentece + sentCharArr[i];
        }

        if (!quotesClosed)
            throw new PrintfSyntaxError("No se han cerrado las comillas del primer argumento");

        return i + 2;
    }

    public void getExtraArgs(int startArgsIndex) throws Exception {
        boolean argFound = false;
        String arg = "";
        char[] quotesSentCharArr = quotesSentece.toCharArray();

        for (int i = 0; i < quotesSentece.length(); i++) {
            System.out.println(quotesSentCharArr[i]);

            if (argFound == true && quotesSentCharArr[i] == ' ' || quotesSentCharArr[i] == '%') {
                argsExpected.add(arg);
                arg = "";
                argFound = false;
            }

            System.out.println("argFound: " + argFound + " quotesSenCA: " + quotesSentCharArr[i]);
            System.out.println("Sentencia: " + (!argFound && quotesSentCharArr[i] == '%'));
            if (argFound == false && quotesSentCharArr[i] == '%')
                argFound = true;

            if (argFound)
                arg = arg + quotesSentCharArr[i];

        }

        System.out.println("Args encotrados");
        for(String args:argsExpected){
            System.out.println(args);
        }
    }
}