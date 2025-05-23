import java.util.Scanner;

public class Io {
    private static final Scanner scanner = new Scanner(System.in);

    public static String PADL(String texto, int longitud) {
        if (texto.length() > longitud) {
            return texto.substring(0, longitud);
        } else {
            while (texto.length() < longitud) {
                texto = " " + texto;
            }
            return texto;
        }
    }
    
    public static Scanner getScanner() {
        return scanner;
    }

    public static void cerrarScanner() {
        if (scanner != null){
            scanner.close();
        }
    }

    public static boolean leerSiNo() {
        String respuesta;
        do {
            respuesta = scanner.nextLine().toLowerCase();
        } while (!respuesta.equals("s") && !respuesta.equals("n") && !respuesta.equals("si") && !respuesta.equals("no"));
        
        if (respuesta.equals("s") || respuesta.equals("si")) {
            return true;
        } else {
            return false;
        }
    }

    public static int leerNumero() {
        while (true) {
            try {
                int numero = scanner.nextInt();
                scanner.nextLine();
                return numero;
            } catch (Exception e) {
                System.out.println("Entrada no válida. Inténtalo de nuevo.");
                scanner.nextLine();
            }
        }
    }

    public static long leerLong() {
        while (true) {
            try {
                long numero = scanner.nextLong();
                scanner.nextLine();
                return numero;
            } catch (Exception e) {
                System.out.println("Entrada no válida. Inténtalo de nuevo.");
                scanner.nextLine();
            }
        }
    }

    public static String leerTexto() {
        while (true) {
            try {
                String texto = scanner.nextLine();
                return texto;
            } catch (Exception e) {
                System.out.println("Entrada no válida. Inténtalo de nuevo.");
                scanner.nextLine();
            }
        }
    }

    // Limpiar pantalla para win o linux. 
    public static void limpiarPantalla() {
        try {
            String os = System.getProperty("os.name");
            
            if (os.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                pb.inheritIO().start().waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                pb.inheritIO().start().waitFor();
            }
        } catch (Exception error) {
            // si no funciona imprimir muchas lineas vacias
            int i = 0;
            while (i < 25) {
                System.out.println("");
                i++;
            }
        }
    }

    public static void esperarEnter() {
        System.out.print("Presiona Enter para continuar...");
        try {
            System.in.read();
            scanner.nextLine();
            limpiarPantalla();
        } catch (Exception error) {
        }
    }
}