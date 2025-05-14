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
}