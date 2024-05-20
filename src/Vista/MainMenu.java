package Vista;

import java.util.Scanner;

public class MainMenu extends Menu {
    PrintfCheckerView printfView;
    WhileCheckerView whileView;

    public MainMenu(){
        this.input = new Scanner(System.in);
        this.printfView = new PrintfCheckerView();
        this.whileView = new WhileCheckerView();
    }

    public void menu(){
        int opc;
        do{
            System.out.println("Bienvenido al sistema de validaciones");
            System.out.println("1) Validar sentencias printf");
            System.out.println("2) Validar sentencias while");
            System.out.println("3) Salir");
            opc = this.optSelector();

            switch (opc) {
                case 1:
                    printfView.menu();
                    break;
                case 2:
                    whileView.menu();
                    break;
                case 3: 
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }while(opc!=3);
    }
}
