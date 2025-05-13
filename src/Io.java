import java.sql.*;
import java.util.Scanner;

public class Io {
    //Funciones generales:
    //Funcion PADL
    public static String PADL(String texto,int longitud){
        if (texto.length() > longitud){
            return texto.substring(0, longitud);
        }else {
            while (texto.length() < longitud){
                texto = " " + texto;
            }
            return texto;
        }
    }
    //Funcion leer numero
    public static int leerNumero(){
        Scanner input = new Scanner(System.in);
        int numero = 0;
        boolean valido = false;
        try {
            while (!valido) {
                try {
                    numero = input.nextInt();
                    valido = true;
                } catch (Exception e) {
                    System.out.println("Entrada no válida. Inténtalo de nuevo.");
                    input.next();
                }
            }
        } finally {
            input.close();
        }
        return numero;
    }
    //Funcion para cerrar la conexion con la bbdd
    public static void cerrarConexion(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}