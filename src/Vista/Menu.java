package Vista;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class Menu {
    Scanner input;
    /**
     * Método que captura un valor numérico ingresado por
     * el usuario, validando el caso donde el usuario ingrese
     * una cadena por error
     * @return número ingresado por el usuario
     */
    public int optSelector(){
        int opt;
        while(true){
            try{
                System.out.printf("Elija una opción: ");
                opt = input.nextInt();
                input.nextLine();
                return opt;
            }catch(InputMismatchException e){
                System.out.println("\nERROR: El valor ingresado debe ser un número\n");
                input.nextLine();
            }
        }
    }
}
