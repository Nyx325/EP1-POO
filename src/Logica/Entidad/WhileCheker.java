package Logica.Entidad;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import Logica.Excepciones.NoWhileLoopOnFile;

public class WhileCheker {
    List<String> fileLines;
    List<WhileLoop> loopsOnFile;

    public WhileCheker(String filePath) throws IOException{
        this.loopsOnFile = new ArrayList<>();
        var file = new File(filePath);
        this.fileLines = Files.readAllLines(file.toPath());
    }

    public void check(){
        
    }

    /**
     * Método que busca, delimita y valida que el bucle while esté
     * cerrado correctamente
     * @throws NoWhileLoopOnFile significa que no se ha encontrado
     * ningun bucle while dentro del archivo
     */
    public void whileExist() throws Exception{
        int lineNum = 0;
        for(String line : fileLines){
            if(line.contains("while(")){
                var newLopp = new WhileLoop(lineNum);
                if(line.contains("{"))
                    newLopp.multiLine = true;
                
                this.loopsOnFile.add(newLopp);
            }
            lineNum++;
        }

        if (loopsOnFile.isEmpty())
            throw new NoWhileLoopOnFile();
    }
}
