package Logica.Entidad;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import Logica.Excepciones.InvalidSentence;
// import Logica.Excepciones.NoClosedLoop;
import Logica.Excepciones.NoSuchSentence;
import Logica.Excepciones.NoWhileLoopOnFile;

public class WhileChecker {
    List<String> fileLines;
    // Lista de en qué parte del archivo se encuentran los bucles
    public List<WhileLoop> loopsOnFile;

    public WhileChecker(String filePath) throws IOException {
        this.loopsOnFile = new ArrayList<>();
        var file = new File(filePath);
        this.fileLines = Files.readAllLines(file.toPath());
    }

    public void check() throws Exception {
        this.getWhileOnFile();
        this.checkSentences();
        // this.whileExist();
    }

    public void getWhileOnFile() {
        int index, sentEndIndex = -1, lineNum = 1;
        boolean sentenceEnd = false, bracketFound = false;
        String sentence = "";
        for (String line : fileLines) {
            //

            // Buscar la palabra while en la linea actual
            index = line.indexOf("while(", 0);

            // Validar si el ciclo anterior se

            // En caso de no encontrarlo, pasar a la siguiente linea
            if (index == -1) {
                lineNum++;
                continue;
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
            boolean multiLine = this.findMultiLine(sentEndIndex+1, lineCharArr);

            // Determinar metadatos de el bucle
            var newLoop = new WhileLoop(
                    lineNum,
                    multiLine,
                    false,
                    sentence
            );

            this.loopsOnFile.add(newLoop);

            lineNum++;
        }
    }

    /**
     * Itera una linea en busca de una llave de apertura
     * sin que exista algun otro tipo de caracter diferente
     * a espacios o saltos de linea
     * @return si se encontró o no lo una llave de apertura
     */
    private boolean findMultiLine(int indexInicio, char[] lineCharArr){
            for(int i = indexInicio; i < lineCharArr.length; i++){
                //System.out.println(lineCharArr[i]);

                if(lineCharArr[i] == '{') return true;

                if(!(lineCharArr[i] == '{' || lineCharArr[i] == ' ' || lineCharArr[i] == '\n'))
                    return false;
                
            }

            return true;
    }

    public void checkSentences() throws Exception{
        for(WhileLoop loop : loopsOnFile){
            if(
                loop.sentence.contains("&&") ||
                loop.sentence.contains("||") ||
                loop.sentence.contains("<") ||
                loop.sentence.contains(">")

            ){

            }
            else{
                if(loop.sentence.split(" ").length != 1){
                    throw new InvalidSentence(loop.startLine);
                }
            }
        }
    }
}