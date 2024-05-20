package Vista;

import java.util.Scanner;

import Logica.Entidad.PrintfChecker;

public class PrintfCheckerView extends Menu {
    public PrintfChecker checker;

    public PrintfCheckerView() {
        this.checker = new PrintfChecker();
        this.input = new Scanner(System.in);
    }

    public void menu() {
        int opt = 1;

        do {
            System.out.println("Bienvenido a la validacion de sentnecias printf");
            System.out.println("1) Validar sentencia");
            System.out.println("2) Salir");

            opt = super.optSelector();
            switch (opt) {
                case 1:
                    System.out.printf("Ingrese la sentencia printf: ");
                    String sentence = input.nextLine();
                    try {
                        checker.check(sentence);
                        System.out.println("No se encontraron errores\n");
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "\n");
                    }
                    break;
                case 2:
                    System.out.println("Volviendo al menú anterior\n");
                    break;
                default:
                    break;
            }
        } while (opt != 2);
    }
}
