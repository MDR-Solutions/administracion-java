import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Variable para almacenar la única instancia
    private static ConexionDB instancia;
    
    // La conexión a la base de datos
    private Connection conexion;
    
    // Datos para conectar a la BD
    private String url = "jdbc:mariadb://192.168.4.1:3306/dbreto";
    private String usuario = "admin";
    private String contrasena = "admin";
    
    private ConexionDB() {
        try {
            // Intentamos conectar a la base de datos
            this.conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Conexión exitosa a la base de datos.");
            
            // Registramos un hook para cerrar la conexión al salir
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    cerrarConexion();
                }
            });
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static ConexionDB getInstancia() {
        // Si no existe la instancia, la creamos
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        // Devolvemos la instancia
        return instancia;
    }
    public static Connection getConexion() {
        return getInstancia().conexion;
    }
    
    public void cerrarConexion() {
        try {
            if (this.conexion != null && !this.conexion.isClosed()) {
                this.conexion.close();
                System.out.println("Conexión a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}