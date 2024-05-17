package Vista;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
import Logica.Entidad.WhileChecker;
import Logica.Entidad.WhileLoop;

public class WhileCheckerView extends Menu {
    WhileChecker checker;

    public WhileCheckerView() {
        this.input = new Scanner(System.in);
    }

    public void menu() {
        int opt = 1;

        do {

            System.out.println("Bienvenido a la validacion de bucles while");
            System.out.println("1) Validar estructuras while de un archivo");
            System.out.println("2) Salir");

            opt = super.optSelector();
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
        } while (opt != 2);
    }

    public void checkFile() {
        int opt;
        do {
            System.out.println("Ingrese una opción");
            System.out.println("1) Capturar una ruta al archivo");
            System.out.println("2) Cancelar");
            opt = this.optSelector();

            switch (opt) {
                case 1:

                    System.out.printf("Ingrese la ruta absoluta del archivo: ");
                    String path = input.nextLine();
                    try {
                        this.checker = new WhileChecker(path);
                    } catch (NoSuchFileException e) {
                        System.out.println("ERROR: El archivo ingresado no existe\n");
                        continue;
                    } catch (IOException e) {
                        System.out.println("Ocurrió un error al abrir el archivo\n");
                        System.out.println("\n");
                        // Mostrar estos mensajes de en qué linea y en qué archivo ocurrió el error
                        e.getStackTrace();
                        System.out.println("\n");
                        continue;
                    }

                    try {
                        this.checker.check();
                        //this.print();
                        System.out.println("No se encontraron errores\n");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Cancelando operación...\n");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opt != 2);
    }

    public void print(){
        for(WhileLoop loop : checker.loopsOnFile){
            System.out.println(loop);
        }
    }
}
