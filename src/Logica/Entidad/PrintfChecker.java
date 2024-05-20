package Logica.Entidad;

import java.util.ArrayList;
import java.util.List;

import Logica.Excepciones.PrintfSyntaxError;

public class PrintfChecker {
    String sentence;
    String quotesSentece;
    String argsSentece;
    int argsExpected;

    public PrintfChecker() {
        argsExpected = 0;
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
        char[] quotesSentCharArr = quotesSentece.toCharArray();

        for (int i = 0; i < quotesSentece.length(); i++) {
            if (argFound) {
                argFound = false;

                if (formatIsValid(quotesSentCharArr[i]))
                    argsExpected++;
            }

            if (quotesSentCharArr[i] == '%')
                argFound = true;
        }
    }

    private boolean formatIsValid(char format) {
        char[] formats = { 'd', 'i', 'o', 'u', 'x', 'X', 'f', 'e', 'g', 'c', 's', 'p', 'n' };

        for (char c : formats)
            if (c == format)
                return true;

        return false;
    }

    
}