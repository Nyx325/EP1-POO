package Vista;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.InputMismatchException;
import java.util.Scanner;
import Logica.Entidad.WhileChecker;

public class WhileCheckerView {
    Scanner input;
    WhileChecker checker;

    public WhileCheckerView(){
        input = new Scanner(System.in);
    }

    public void menu(){
        int opt = 1;

        do{

            System.out.println("Bienvenido a la validacion de bucles while");
            System.out.println("1) Validar estructuras while de un archivo");
            System.out.println("2) Salir");
            System.out.printf("Elija una opción: ");
            
            try{
                opt = input.nextInt();
                input.nextLine();
            }catch(InputMismatchException e){
                System.out.println("\nERROR: El valor ingresado debe ser un número\n");
                input.nextLine();
                continue;
            }

            switch (opt) {
                case 1:
                    this.checkFile();
                    break;
                case 2:
                    System.out.println("\nVolviendo al menú anterior...\n");
                    break;
                default:
                    System.out.println("\nOpción no válida\n");
                    break;
            }
        }while(opt != 2);
    }

    public void checkFile(){
        boolean flag = false;
        do{
            System.out.println("Ingrese la dirección del archivo a revisar");
            String path = input.nextLine();
            try {
                this.checker = new WhileChecker(path);
            } catch (NoSuchFileException e) {
                System.out.println("El archivo ingresado no existe");
                flag = true;
            } catch (IOException e){
                System.out.println("Ocurrió un error al abrir el archivo");
                flag = true;
            }
        }while(flag);
        
    }


}
