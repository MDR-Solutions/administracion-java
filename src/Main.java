import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {
            // Conexión a la base de datos
            connection = DriverManager.getConnection("http://10.42.0.1:8080/your_database", "your_username", "your_password");
            System.out.println("Conexión exitosa a la base de datos.");

            boolean salir = true;
            while (salir) {
                System.out.println("Menú:");
                System.out.println("1. Login");
                System.out.println("2. Registrarse");
                System.out.println("3. Salir");
                System.out.print("Elige una opción: ");
                int opcion = Io.leerNumero();
                scanner.nextLine();

                if (opcion == 1) {
                    // Login
                    System.out.print("Introduce tu DNI: ");
                    String dni = scanner.nextLine();
                    System.out.print("Introduce tu contraseña: ");
                    String contrasena = scanner.nextLine();

                    String query = "SELECT * FROM TUSUARIOS WHERE USUDNI = ? AND USUCONTRASENA = ?";
                    try (PreparedStatement stmt = connection.prepareStatement(query)) {
                        stmt.setString(1, dni);
                        stmt.setString(2, contrasena);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            System.out.println("Login exitoso. Bienvenido, " + rs.getString("USUNOMBRE") + "!");

                            boolean cerrarSesion = true;
                            while (cerrarSesion) {
                                System.out.println("Menú de usuario:");
                                System.out.println("1. Libros");
                                System.out.println("2. Usuario");
                                System.out.println("3. Préstamos");
                                System.out.println("4. Ejemplares");
                                System.out.println("5. Autores");
                                System.out.println("0. Cerrar sesión");
                                System.out.print("Elige una opción: ");
                                int subOpcion = Io.leerNumero();
                                scanner.nextLine();

                                if (subOpcion == 0) {
                                    System.out.println("Cerrando sesión...");
                                    cerrarSesion = false;
                                    break;
                                } else if (subOpcion == 1) {
                                    System.out.println("Accediendo a Libros...");
                                    Io.menuLibro(connection);
                                } else if (subOpcion == 2) {
                                    System.out.println("Accediendo a Usuarios...");
                                    Io.menuUsuario(connection);
                                } else if (subOpcion == 3) {
                                    System.out.println("Accediendo a Prestamos...");
                                    Io.menuPrestamo(connection);
                                } else if (subOpcion == 4) {
                                    System.out.println("Accediendo a Ejemplares...");
                                    Io.menuEjemplar(connection);
                                } else if (subOpcion == 5) {
                                    System.out.println("Accediendo a Autores...");
                                    Io.menuAutor(connection);
                                }
                            }
                        } else {
                            System.out.println("DNI o contraseña incorrectos.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al realizar el login: " + e.getMessage());
                    }
                } else if (opcion == 2) {
                    // Registrarse
                    Io.registrarUsuarioNormal(connection);
                } else if (opcion == 3) {
                    System.out.println("Saliendo del programa...");
                    salir = false;
                    break;
                } else {
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (SQLException e) {
           System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }         finally {
            Io.cerrarConexion(connection);
            scanner.close();
        }
    }
}