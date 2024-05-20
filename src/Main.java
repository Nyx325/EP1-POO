import java.util.Scanner;

import Logica.Entidad.PrintfChecker;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Ingresa la cadena de printf: ");
        var sc = new Scanner(System.in);
        var printfChecker = new PrintfChecker();

        try {
            printfChecker.check(sc.nextLine());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        /*
        var wcView = new WhileCheckerView();
        wcView.menu();
         */
    }
}
