package main;

import java.util.LinkedList;
import java.util.Scanner;

import javax.sound.sampled.Line;

public class restaurante {
 
    static char[] idProductos = {'A','B','C','D','E'};
    static String[] productos = {"Arroz","Pollo","Leche","Panes","Huevo"};
    static double[] precios = {3.1, 10.2, 4.0, 5.6, 6.2};

    public static void main(String[] args) {

        // Initializing the Inventory Stock (All start with 10000 units)
        LinkedList<Integer> inventario = new LinkedList<>();
        inventario.add(10000); // Arroz
        inventario.add(10000); // Pollo
        inventario.add(10000); // Leche
        inventario.add(10000); // Panes
        inventario.add(10000); // Huevo

        boolean keepProgram = true;
        while (keepProgram) {
            clearScreen();
            char choicePrincipal = showScreenPrincipal();
            System.out.println(choicePrincipal);
            if (choicePrincipal=='A') {
    
                LinkedList<String> items = new LinkedList<>();
                LinkedList<Integer> cantidades = new LinkedList<>();
    
    
                boolean makePedido = true;
                while (makePedido){
                    char choicePedido = showScreenPedido();
                    int idxProduct = getIdxProduct(choicePedido);
                    String product = productos[idxProduct];
            
                    int cant = showScreenCantidad();
                    System.out.println(cant);
            
                    System.out.println("\nEligio "+cant+" unidades de "+product+"\n");
    
                    items.add(product);
                    cantidades.add(cant);
                    inventario.set(idxProduct, inventario.get(idxProduct) - cant);
    
                    System.out.print("Desea realizar otro pedido [Y/N]?:\t");
                    Scanner sc = new Scanner(System.in);
                    char choiceOther = sc.next().charAt(0);
                    if (choiceOther=='N') makePedido=false;
    
                }
    
    
                double precioFinal = getPrecioFinal(items, cantidades);        
            }

            else if (choicePrincipal=='B') {
                // Imprime inventario disponible
                clearScreen();
                String mensajeInv = "\n"
                + "===========================================================\n"
                + "================= Plataforma de Inventario ================\n"
                + "===========================================================\n"
                + "\n"
                + "Su stock de productos es el siguiente:\n\n";
                System.out.print(mensajeInv);
                    
                for (int i=0; i < productos.length;i++){
                    System.out.println(" - "+productos[i]+"\t"+inventario.get(i)+" und. disponibles");
                }
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
        + "===========================================================\n"
        + "=========== Bienvenido al modulo de inventario! ===========\n"
        + "===========================================================\n"
        + "\n"
        + "\tOpciones disponibles:\n"
        + "\tA) Registrar un pedido\t[A]\n"
        + "\tB) Consultar inventario\t[B]\n"
        + "\tC) Cerrar el modulo \t[C]\n"
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
        String mensaje = "\n"
        + "===========================================================\n"
        + "================== Plataforma de Pedidos ==================\n"
        + "===========================================================\n"
        + "\n"
        + "\tOpciones disponibles:\n"
        + "\tA) Arroz  (1 kg)\t[A]\n"
        + "\tB) Pollo  (1 kg)\t[B]\n"
        + "\tC) Leche  (1 Lt)\t[C]\n"
        + "\tD) Pan (1 bolsa)\t[D]\n"
        + "\tE) Huevos (1 kg)\t[E]\n"
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
        + "===========================================================\n"
        + "================== Plataforma de Pedidos ==================\n"
        + "===========================================================\n"
        + "\n"
        + "\n"
        + "\nCuantas unidades deseas comprar?:\t";
        System.out.print(mensaje);
        Scanner sc = new Scanner(System.in);
        int cant = sc.nextInt();
//        sc.close();

        return cant;

    }


    private static double getPrecioFinal(LinkedList<String> items,
                                        LinkedList<Integer> cantidades){
                                            
        double precioFinal = 0;
        double precioUnit = 0;
        double precioParcial = 0;
        String item = "";
        int cant = 0; 
        double IGV = 0.18;

        clearScreen();
        String mensaje = "\n"
        + "===========================================================\n"
        + "================== Plataforma de Pedidos ==================\n"
        + "===========================================================\n"
        + "\n"
        + "\n=================== Resumen del pedido ====================\n"
        + "\nProducto\tCantidad\tPU\t\tSubtotal\n";
        System.out.print(mensaje);        
        for (int i=0; i < items.size(); i++){

            item = items.get(i);
            cant = cantidades.get(i);
            precioUnit = getPrecioParcial(item);
            precioParcial = Math.round(cant * precioUnit * 100) /100.0; 
            precioFinal += precioParcial;
            System.out.println("- "+item+"\t\t"+cant+"\t\t"+precioUnit+"\t\t"+precioParcial);
        }
        precioFinal = Math.round(precioFinal * 100) /100; 
        double precioIGV = Math.round(precioFinal*(IGV)*100)/100.0;
        double precioFinalIGV = Math.round((precioFinal+precioIGV) * 100)/100.0;
        System.out.println("\n\t\t\t\tTOTAL:\t\t"+precioFinal);
        System.out.println("\t\t\t\tIGV (18%)\t"+precioIGV);
        System.out.println("\t\t\t\tTOTAL (+IGV):\t"+(precioFinalIGV));
        

        return precioFinal;                                           
    }

    private static double getPrecioParcial(String product){
        double precioParcial = 0;
        for (int i=0; i<productos.length;i++){
            if (product == productos[i]) precioParcial = precios[i];
        }
        return precioParcial;
    }

}