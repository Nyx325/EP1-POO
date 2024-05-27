package Logica.Entidad;

import Logica.Excepciones.PrintfSyntaxError;

public class PrintfChecker {
    String sentence;
    String quotesSentece;
    int argsExpected;

    public PrintfChecker() {
        argsExpected = 0;
        quotesSentece = "";
    }

    public void check(String sentence) throws Exception {
        argsExpected = 0;
        quotesSentece = "";
        this.sentence = sentence;
        int startArgs = checkSentence();
        getExtraArgs();
        validArgs(startArgs);
    }

    private int checkSentence() throws Exception {
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

        return i + 1;
    }

    private void getExtraArgs() throws Exception {
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

    private void validArgs(int startArgsIndex) throws Exception {
        int args = 0;
        boolean parenthesisClosed = false, semicolon = false;
        char[] sentence = this.sentence.toCharArray();
        for (int i = startArgsIndex; i < this.sentence.length(); i++) {
            if (sentence[i] == ',')
                args++;
                
            if (sentence[i] == ')') {
                parenthesisClosed = true;
                if(sentence[i+1] == ';')
                    semicolon = true;
                break;
            }
        }

        if (!parenthesisClosed)
            throw new PrintfSyntaxError("No se ha colocado paréntesis de cierre");
        if (!semicolon)
            throw new PrintfSyntaxError("No se ha colocado punto y coma");
        if (args != argsExpected)
            throw new PrintfSyntaxError(
                    "No coinciden los argumentos\nEsperados: " + argsExpected + "\nEncontrados: " + args);
    }
}