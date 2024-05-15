package Logica.Entidad;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import Logica.Excepciones.NoClosedLoop;
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
        this.whileExist();
    }

    /**
     * Método que busca, delimita y valida que el bucle while esté
     * cerrado correctamente
     * 
     * @throws NoWhileLoopOnFile significa que no se ha encontrado
     *                           ningun bucle while dentro del archivo
     */
    public void whileExist() throws Exception {
        int lineNum = 0;
        for (String line : fileLines) {
            if (line.contains("while(")) {
                var newLopp = new WhileLoop(lineNum);

                if (getSentence(line) == "") {
                    throw new NoSuchSentence(lineNum);
                }

                this.loopsOnFile.add(newLopp);
            }
            lineNum++;
        }

        if (loopsOnFile.isEmpty())
            throw new NoWhileLoopOnFile();
    }

    public String getSentence(String line) {
        boolean flag = false;
        String sentencia = "";
        for (char c : line.toCharArray()) {
            if (c == ')')
                flag = false;
            if (flag)
                sentencia = sentencia + c;
            if (c == '(')
                flag = true;
        }

        System.out.println(sentencia);

        return sentencia;
    }
}
