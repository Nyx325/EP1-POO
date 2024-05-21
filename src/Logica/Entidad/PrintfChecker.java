package Logica.Entidad;

import java.util.ArrayList;
import java.util.List;

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
        List<String> args = new ArrayList<>();
        boolean closedParenthesis = false;
        String arg = "";
        char[] sentCharArr = sentence.toCharArray();
        // Caso printf sin argumentos
        if (argsExpected == 0 && sentCharArr[startArgsIndex] == ')')
            return;

        for (int i = startArgsIndex + 1; i < sentence.length(); i++) {
            if (sentCharArr[i] == ')') {
                closedParenthesis = true;
                break;
            }

            if (sentCharArr[i] == ',' && arg != "") {
                arg = arg.trim();
                System.out.println(arg);
                if (arg.contains(" ") || arg.contains("\n"))
                    throw new PrintfSyntaxError("Argumento \"" + arg + "\" no válido");
                args.add(arg);
                arg = "";
                continue;
            }

            arg = arg + sentCharArr[i];
        }

        System.out.println("Args: "+args.size()+" Expected: "+this.argsExpected);

        if (!closedParenthesis)
            throw new PrintfSyntaxError("Parentesis de cierre faltante");



        if (argsExpected != args.size())
            throw new PrintfSyntaxError("El numero de argumentos no corresponde\nEsperados: " + argsExpected
                    + "\nEncontrados: " + args.size());
    }
}