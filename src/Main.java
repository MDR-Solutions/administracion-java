import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Obtenemos la conexión a la BBDD
        Connection conn = ConexionDB.getConexion();

        // Verificamos que tenemos conexión
        if (conn != null) {
            boolean activo = true;
            while (activo) {
                System.out.println("Menú:");
                System.out.println("1. Login");
                System.out.println("2. Registrarse");
                System.out.println("3. Salir");
                System.out.print("Elige una opción: ");
                int opcion = Io.leerNumero();

                if (opcion == 1) {
                    // Login
                    System.out.print("Introduce tu DNI: ");
                    String dni = Io.leerTexto();
                    System.out.print("Introduce tu contraseña: ");
                    String contrasena = Io.leerTexto();

                    String query = "SELECT * FROM TUSUARIOS WHERE USUDNI = ? AND USUCONTRASENA = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, dni);
                        stmt.setString(2, contrasena);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            System.out.println("Login exitoso. Bienvenido, " + rs.getString("USUNOM") + "!");

                            boolean sesion = true;
                            while (sesion) {
                                System.out.println("Menú de usuario:");
                                System.out.println("1. Libros");
                                System.out.println("2. Usuario");
                                System.out.println("3. Préstamos");
                                System.out.println("4. Ejemplares");
                                System.out.println("5. Autores");
                                System.out.println("0. Cerrar sesión");
                                System.out.print("Elige una opción: ");
                                int subOpcion = Io.leerNumero();

                                if (subOpcion == 0) {
                                    System.out.println("Cerrando sesión...");
                                    sesion = false;
                                    break;
                                } else if (subOpcion == 1) {
                                    System.out.println("Accediendo a Libros...");
                                    Libro.menuLibro(conn);
                                } else if (subOpcion == 2) {
                                    System.out.println("Accediendo a Usuarios...");
                                    Usuario.menuUsuario(conn);
                                } else if (subOpcion == 3) {
                                    System.out.println("Accediendo a Prestamos...");
                                    Prestamo.menuPrestamo(conn);
                                } else if (subOpcion == 4) {
                                    System.out.println("Accediendo a Ejemplares...");
                                    Ejemplar.menuEjemplar(conn);
                                } else if (subOpcion == 5) {
                                    System.out.println("Accediendo a Autores...");
                                    Autor.menuAutor(conn);
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
                    Usuario.registrarUsuarioNormal(conn);
                } else if (opcion == 3) {
                    System.out.println("Saliendo del programa...");
                    activo = false;
                    break;
                } else {
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
            
            // Cerramos el Scanner global antes de terminar la aplicación
            Io.cerrarScanner();
        } else {
            System.out.println("No se pudo establecer conexión a la base de datos.");
        }
    }
}