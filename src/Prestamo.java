import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Prestamo {
	//Atributos
    private String dni;
    private int id_ejemplar;
    private LocalDate fecha_prestamo, fecha_devolucion;

    // Constructor
    public Prestamo(String d, int ej) {
        this.dni = d;
        this.id_ejemplar = ej;
        this.fecha_prestamo = LocalDate.now();
        this.fecha_devolucion = LocalDate.now().plusDays(15);
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getId_ejemplar() {
        return id_ejemplar;
    }

    public void setId_ejemplar(int id_ejemplar) {
        this.id_ejemplar = id_ejemplar;
    }

    public LocalDate getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public LocalDate getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(LocalDate fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    // Funciones de Prestamo
    public static int insertPrestamo(Connection conn, Prestamo prestamo) throws SQLException {
        String dni = prestamo.getDni();
        int id_ejemplar = prestamo.getId_ejemplar();
        LocalDate fecha_prestamo = prestamo.getFecha_prestamo();
        LocalDate fecha_devolucion = prestamo.getFecha_devolucion();

        String str = "INSERT INTO TPRESTAMOS (PREUSUDNI, PREEJEID, PREFECHAPRESTAMO, PREFECHADEVOLUCION)";
        str += " VALUES ('" + dni + "', '" + id_ejemplar + "', '" + fecha_prestamo + "', '" + fecha_devolucion + "')";

        Statement stmt = conn.createStatement();
        int numeroCambios = stmt.executeUpdate(str);

        return numeroCambios;
    }

    public static void selectPrestamo(Connection conn) throws SQLException {
        String sql = "SELECT * FROM TPRESTAMOS";
        ResultSet rs = null;
        Statement st = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int swId = rs.getInt("PREID");
                String swDni = rs.getString("PREUSUDNI");
                int swIdEje = rs.getInt("PREEJEID");
                LocalDate swFechaPres = rs.getObject("PREFECHAPRESTAMO", LocalDate.class);
                LocalDate swFechaDev = rs.getObject("PREFECHADEVOLUCION",LocalDate.class);
                String swDev = (rs.getInt("PREDEVUELTO") == 0 ? "No" : "Si");
                LocalDate swFechaEnt = rs.getObject("PREFECHADEVOLUCIONREAL",LocalDate.class);


                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Prestamo: " + swId + " , DNI: " + swDni + ", ID Ejemplar: " + swIdEje + ", Fecha Prestamo: " + swFechaPres + ", Fecha Devolución: " + swFechaDev + ", Devuelto: " + swDev + ", Fecha Entrega: " + swFechaEnt);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean deletePrestamo(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM TPRESTAMOS WHERE PREID = '" + id + "'";

        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql) > 0;
    }

    public static Prestamo crearPrestamo(Connection conn) throws SQLException, InterruptedException {
        System.out.println("Introduce el DNI: ");
        String dni = Io.leerTexto();
        System.out.println("Introduce el ID del ejemplar: ");
        int id_ejemplar = Io.leerNumero();

        Prestamo nuevoPrestamo = new Prestamo(dni, id_ejemplar);
        int cambios = insertPrestamo(conn, nuevoPrestamo);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        return nuevoPrestamo;
    }

    public static Prestamo buscarPrestamo(Connection conn, int id_prestamo) throws SQLException {
        String sql = "SELECT * FROM TPRESTAMOS WHERE PREID = '" + id_prestamo + "'";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                int swId = rs.getInt("PREID");
                String swDni = rs.getString("PREUSUDNI");
                int swIdEje = rs.getInt("PREEJEID");
                LocalDate swFechaPres = rs.getObject("PREFECHAPRESTAMO", LocalDate.class);
                LocalDate swFechaDev = rs.getObject("PREFECHADEVOLUCION",LocalDate.class);
                String swDev = (rs.getInt("PREDEVUELTO") == 0 ? "No" : "Si");
                LocalDate swFechaEnt = rs.getObject("PREFECHADEVOLUCIONREAL",LocalDate.class);

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Prestamo: " + swId + " , DNI: " + swDni + ", ID Ejemplar: " + swIdEje + ", Fecha Prestamo: " + swFechaPres + ", Fecha Devolución: " + swFechaDev + ", Devuelto: " + swDev + ", Fecha Entrega: " + swFechaEnt);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null; // No devuelve el libro
    }
    public static void menuPrestamo(Connection conn) {
        int opcion = 0;

        do {
            System.out.println("Menu de prestamo:");
            System.out.println("1. Ver lista de prestamos");
            System.out.println("2. Añadir un prestamo");
            System.out.println("3. Eliminar un prestamo");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = Io.leerNumero();

            switch (opcion) {
                case 1:
                    try {
                        selectPrestamo(conn);
                    } catch (SQLException e) {
                        System.out.println("Error al consultar la lista de préstamos: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        crearPrestamo(conn);
                    } catch (SQLException | InterruptedException e) {
                        System.out.println("Error al crear el préstamo: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Introduce el ID del prestamo a eliminar: ");
                    int id_prestamo = Io.leerNumero();
                    try {
                        boolean eliminado = deletePrestamo(conn, id_prestamo);
                        if (eliminado) {
                            System.out.println("Prestamo eliminado correctamente.");
                        } else {
                            System.out.println("No se pudo eliminar el prestamo.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar el préstamo: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del menú de prestamo...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
    }
}