package SistemaVentas;

import java.util.LinkedList;
import java.util.Scanner;

import javax.sound.sampled.Line;

public class restaurante {
 
    static char[] idProductos = {'A','B','C','D','E'};
    static String[] productos = {"Arroz con Pato","Aji de Gallina","Pachamanca    ","Cuy chactado  ","Lomo Saltado  "};
    static double[] precios = {31, 45, 32, 25, 25};

    public static void main(String[] args) {

        // Initializing the Inventory Stock (All start with 100 units available)
        LinkedList<Integer> platos = new LinkedList<>();
        platos.add(100); // Arroz
        platos.add(100); // Pollo
        platos.add(100); // Leche
        platos.add(100); // Panes
        platos.add(100); // Huevo

        boolean keepProgram = true;
        while (keepProgram) {
            clearScreen();
            char choicePrincipal = showScreenPrincipal();
            System.out.println(choicePrincipal);
            if (choicePrincipal=='A') {

                // Imprime platos disponible
                clearScreen();
                String mensajeInv = "\n"
                + "===============================================================\n"
                + "===================== Plataforma de Carta =====================\n"
                + "===============================================================\n"
                + "\n"
                + "Tenemos a la carta los siguientes platos:\n"
                + "\n"                
                + "Platos\t\t\tUnd. Disponibles\n"
                ;
                System.out.print(mensajeInv);
                    
                for (int i=0; i < productos.length;i++){
                    System.out.println(" - "+productos[i]+"\t "+platos.get(i)+" und.");
                }
    

            }

            else if (choicePrincipal=='B') {
                LinkedList<String> items = new LinkedList<>();
                LinkedList<Integer> cantidades = new LinkedList<>();
    
    
                boolean makePedido = true;
                while (makePedido){
                    char choicePedido = showScreenPedido();
                    int idxProduct = getIdxProduct(choicePedido);
                    String product = productos[idxProduct];
            
                    int cant = showScreenCantidad();
                    System.out.println(cant);
            
                    System.out.println("\nEligio "+cant+" platos de "+product+"\n");
    
                    items.add(product);
                    cantidades.add(cant);
                    platos.set(idxProduct, platos.get(idxProduct) - cant);
    
                    System.out.print("Desea realizar otro pedido [Y/N]?:\t");
                    Scanner sc = new Scanner(System.in);
                    char choiceOther = sc.next().charAt(0);
                    if (choiceOther=='N') makePedido=false;
    
                }
    
                printBoleta(items, cantidades);        
            }
            else if (choicePrincipal=='C'){
                keepProgram=false;
            }
    
            if (keepProgram){
                System.out.print("\nDesea regresar al menu principal [Y/N]?:\t");
                Scanner sc = new Scanner(System.in);
                char choiceReturn = sc.next().charAt(0);
                if (choiceReturn=='N') keepProgram=false;    
            }
        }

        
    }



    private static int getIdxProduct(char choice){
        int idxProduct = 0;
        for (int i=0; i<idProductos.length; i++){
            if (choice == idProductos[i]) idxProduct = i;
        }
        return idxProduct;
    } 
    private static String getProduct(char choice){
        String product = "";
        for (int i=0; i<idProductos.length; i++){
            if (choice == idProductos[i]) product = productos[i];
        }
        return product;
    } 

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    private static char showScreenPrincipal(){

        String mensaje = "\n"
        + "===============================================================\n"
        + "============= Bienvenido al Sistema de Restaurant =============\n"
        + "===============================================================\n"
        + "\n"
        + "\tOpciones disponibles:\n"
        + "\tA) Consultar carta disponible\t[A]\n"
        + "\tB) Registrar un pedido\t\t[B]\n"
        + "\tC) Cerrar el modulo \t\t[C]\n"
        + "\nQue accion desea realizar?:\t";
        System.out.print(mensaje);
        Scanner sc = new Scanner(System.in);
        char choice = sc.next().charAt(0);
//        sc.close();

        String product = getProduct(choice);

        return choice;

    }

    private static char showScreenPedido(){
        clearScreen();
//        static String[] productos = {"Arroz con Pato","Pachamanca","Trio Marino","Chicharron de chancho","Arroz chaufa salvaje"};

        String mensaje = "\n"
        + "===============================================================\n"
        + "==================== Plataforma de Pedidos ====================\n"
        + "===============================================================\n"
        + "\n"
        + "\tOpciones disponibles:\n"
        + "\tA) Arroz con Pato (1 plato)\t[A]\n"
        + "\tB) Aji de Gallina (1 plato)\t[B]\n"
        + "\tC) Pachamanca     (1 plato)\t[C]\n"
        + "\tD) Cuy chactado   (1 plato)\t[D]\n"
        + "\tE) Lomo Saltado   (1 plato)\t[E]\n"
        + "\nPor favor, escoja un producto:\t";
        System.out.print(mensaje);
        Scanner sc = new Scanner(System.in);
        char choice = sc.next().charAt(0);
//        sc.close();

        return choice;
    }

    private static int showScreenCantidad(){
        clearScreen();
        String mensaje = "\n"
        + "===============================================================\n"
        + "==================== Plataforma de Pedidos ====================\n"
        + "===============================================================\n"
        + "\n"
        + "\n"
        + "\nCuantos platos deseas comprar?:\t";
        System.out.print(mensaje);
        Scanner sc = new Scanner(System.in);
        int cant = sc.nextInt();
//        sc.close();

        return cant;

    }


    private static void printBoleta(LinkedList<String> items,
                                        LinkedList<Integer> cantidades){
                                            
        double precioFinal = 0;
        double precioUnit = 0;
        double precioParcial = 0;
        String item = "";
        int cant = 0; 
        double IGV = 0.18;

        clearScreen();
        String mensaje = "\n"
        + "===============================================================\n"
        + "==================== Plataforma de Pedidos ====================\n"
        + "===============================================================\n"
        + "\n"
        + "\n===================== Resumen del pedido ======================\n"
        + "\nProducto\t\tCantidad\tPU\t\tSubtotal\n";
        System.out.print(mensaje);        
        for (int i=0; i < items.size(); i++){

            item = items.get(i);
            cant = cantidades.get(i);
            precioUnit = getPrecioParcial(item);
            precioParcial = Math.round(cant * precioUnit * 100) /100.0; 
            precioFinal += precioParcial;
            System.out.println("- "+item+"\t"+cant+"\t\t"+precioUnit+"\t\t"+precioParcial);
        }
        precioFinal = Math.round(precioFinal * 100) /100; 
        double precioIGV = Math.round(precioFinal*(IGV)*100)/100.0;
        double precioFinalIGV = Math.round((precioFinal+precioIGV) * 100)/100.0;
        System.out.println("\n\t\t\t\t\tTOTAL:\t\t"+precioFinal);
        System.out.println("\t\t\t\t\tIGV (18%):\t"+precioIGV);
        System.out.println("\t\t\t\t\tTOTAL (+IGV):\t"+(precioFinalIGV));
        

//        return precioFinal;                                           
    }

    private static double getPrecioParcial(String product){
        double precioParcial = 0;
        for (int i=0; i<productos.length;i++){
            if (product == productos[i]) precioParcial = precios[i];
        }
        return precioParcial;
    }

}