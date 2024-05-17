package Logica.Entidad;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

// import Logica.Excepciones.NoClosedLoop;
import Logica.Excepciones.NoSuchSentence;
import Logica.Excepciones.NoWhileLoopOnFile;

public class WhileChecker {
    List<String> fileLines;
    // Lista de en qué parte del archivo se encuentran los bucles
    List<WhileLoop> loopsOnFile;

    public WhileChecker(String filePath) throws IOException {
        this.loopsOnFile = new ArrayList<>();
        var file = new File(filePath);
        this.fileLines = Files.readAllLines(file.toPath());
    }

    public void check() throws Exception {
        this.getWhileOnFile();
        // this.whileExist();
    }

    public void getWhileOnFile() {
        int index, sentEndIndex, lineNum = 0;
        boolean sentenceEnd = false, noCharSep = false;
        String sentence = "";
        // No tengo idea de qué estoy haciendo
        for (String line : fileLines) {
            // Buscar la palabra while en la linea actual
            index = line.indexOf("while(", 0);

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

            // Determinar metadatos de el bucle
            var newLoop = new WhileLoop(
                    lineNum,
                    false,
                    false,
                    sentence
            );

            this.loopsOnFile.add(newLoop);

            lineNum++;
        }
    }
}