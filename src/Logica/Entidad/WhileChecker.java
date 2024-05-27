package Logica.Entidad;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import Logica.Excepciones.InvalidSentence;
import Logica.Excepciones.MissingCloseBracket;
import Logica.Excepciones.NoWhileLoopOnFile;
import java.util.LinkedList;

public class WhileChecker {
    List<String> fileLines;
    // Lista de en qué parte del archivo se encuentran los bucles
    List<WhileLoop> loopsOnFile;

    public WhileChecker(String filePath) throws IOException {
        this.loopsOnFile = new LinkedList<>();
        var file = new File(filePath);
        this.fileLines = Files.readAllLines(file.toPath());
    }

    public void check() throws Exception {
        this.getWhileOnFile();
        this.checkSentences();
        this.checkClosedBrackets();
    }

    private void getWhileOnFile() throws Exception{
        int index, sentEndIndex = -1, lineNum = 1;
        boolean sentenceEnd = false, searchingBracket = false;
        String sentence = "";
        for (String line : fileLines) {

            // Buscar la palabra while en la linea actual
            index = line.indexOf("while(", 0);

            // Si no se ha encontrado un nuevo bucle y aún se busca una
            // llave de apertura del ultimo bucle, revisar linea actual y 
            // revisar de ser necesario

            // En caso de no encontrarlo, pasar a la siguiente linea
            if (index == -1) {
                searchingBracket = false;
                lineNum++;
                continue;
            }

            if(index == -1 && searchingBracket && this.findMultiLine(0, line.toCharArray())){
                loopsOnFile.get(loopsOnFile.size()-1).multiLine = true;
            }

            // Buscar lo que corresponde a la sentencia
            sentenceEnd = false;
            sentence = "";
            char[] lineCharArr = line.toCharArray();
            for (int i = index + 6; i < lineCharArr.length; i++) {
                // Encontrar si 

                // Delimitar dónde termina la sentencia
                if (lineCharArr[i] == ')') {
                    sentEndIndex = i;
                    sentenceEnd = true;
                }

                // Almacenar sentencia
                if (!sentenceEnd)
                    sentence = sentence + lineCharArr[i];
            }

            // Buscar si la primer llave se encuentra en esta misma
            // linea
            searchingBracket = !this.findMultiLine(sentEndIndex+1, lineCharArr);
            // Marcar 

            // Determinar metadatos de el bucle
            var newLoop = new WhileLoop(
                    lineNum,
                    !searchingBracket,
                    false,
                    sentence
            );

            this.loopsOnFile.add(newLoop);

            lineNum++;
        }

        if(loopsOnFile.isEmpty()) throw new NoWhileLoopOnFile();
    }

    /**
     * Itera una linea en busca de una llave de apertura
     * sin que exista algun otro tipo de caracter diferente
     * a espacios o saltos de linea
     * @return si se encontró o no lo una llave de apertura
     */
    private boolean findMultiLine(int indexInicio, char[] lineCharArr){
            for(int i = indexInicio; i < lineCharArr.length; i++){
                if(lineCharArr[i] == '{') return true;

                if(!(lineCharArr[i] == '{' || lineCharArr[i] == ' ' || lineCharArr[i] == '\n'))
                    return false;
                
            }

            return true;
    }

    private void checkSentences() throws Exception{
        for(WhileLoop loop : loopsOnFile){
            if(!(loop.sentence.contains("&&") ||
                 loop.sentence.contains("||") || 
                 loop.sentence.contains("==") || 
                 loop.sentence.contains("<") || 
                 loop.sentence.contains(">"))
            ){
                if(loop.sentence.split(" ").length != 1){
                    throw new InvalidSentence("Sentencia \"" + loop.sentence + "\" no válida\nen la linea "+loop.startLine+"\n");
                }
            }

            if(loop.sentence == "") throw new InvalidSentence("Sentencia faltante en la linea " + loop.startLine+"\n");
        }
    }

    private void checkClosedBrackets() throws Exception{
        List<Integer> lastLineUsed = new ArrayList<>();
        boolean closeLineUsed = false;

        for(int i = this.loopsOnFile.size() - 1; i >= 0; i--){
            WhileLoop loop = loopsOnFile.get(i);
            if(loop.multiLine == false) continue;

            for(int j = loop.startLine; j < this.fileLines.size(); j++){
                closeLineUsed = false;

                for(Integer index : lastLineUsed){
                    if(index == j){
                        closeLineUsed = true;
                        break;
                    }
                }

                if(closeLineUsed) break;

                for(char c : fileLines.get(j).toCharArray()){
                    if(c == '}'){
                        loop.closed = true;
                        loop.endLine = j;
                        lastLineUsed.add(j);
                        break;
                    }
                }
                if(loop.closed) break;
            }
        }
        
        for(WhileLoop loop : loopsOnFile){
            if(loop.multiLine == true && loop.closed == false) throw new MissingCloseBracket(loop.startLine);
        }
    }
}
