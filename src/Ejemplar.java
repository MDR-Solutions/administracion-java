import java.sql.*;

public class Ejemplar {
    // Atributos
    private int id_ejemplar;
    private String estado_fisico;
    private int id_libro;

    //Constructor
    public Ejemplar(int id, String estado, int libro) {
        this.id_ejemplar = id;
        this.estado_fisico = estado;
        this.id_libro = libro;
    }
    
    //Getters y Setters
    public int getIdEjemplar() {
        return id_ejemplar;
    }

    public void setIdEjemplar(int id) {
        this.id_ejemplar = id;
    }

    public String getEstadoFisico() {
        return estado_fisico;
    }

    public void setEstadoFisico(String estado) {
        this.estado_fisico = estado;
    }

    public int getIdLibro() {
        return id_libro;
    }

    public void setIdLibro(int libro) {
        this.id_libro = libro;
    }

    // Menu de ejemplar
    public static void menuEjemplar(Connection conn) {
        Scanner input = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("Menu de ejemplar:");
            System.out.println("1. Ver lista de ejemplares");
            System.out.println("2. Añadir un ejemplar");
            System.out.println("3. Eliminar un ejemplar");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = Io.leerNumero();

            switch (opcion) {
                case 1:
                    try {
                        ejecutarSelectEjemplar(conn);
                    } catch (SQLException e) {
                        System.out.println("Error al mostrar ejemplares: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        crearEjemplar(conn);
                    } catch (SQLException | InterruptedException e) {
                        System.out.println("Error al crear ejemplar: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Introduce el ID del ejemplar a eliminar: ");
                    int id_ejemplar = Io.leerNumero();
                    Ejemplar ejemplarAEliminar = new Ejemplar(id_ejemplar, null, 0);
                    try {
                        boolean eliminado = ejecutarDeleteEjemplar(conn, ejemplarAEliminar);
                        if (eliminado) {
                            System.out.println("Ejemplar eliminado correctamente.");
                        } else {
                            System.out.println("No se pudo eliminar el ejemplar.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar ejemplar: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del menú de ejemplar...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
    }

    // Funciones de Ejemplar
    public static int ejecutarInsertEjemplar(Connection conn, Ejemplar obj) throws SQLException {
        int id_ejemplar = obj.getIdEjemplar();
        String estado_fisico = obj.getEstadoFisico();
        int id_libro = obj.getIdLibro();

        String str = "INSERT INTO TEJEMPLARES (EJECOD, EJEFISICO, LIBID) VALUES ('" + id_ejemplar + "', '" + estado_fisico + "', '" + id_libro + "')";

        Statement stmt = conn.createStatement();
        int numeroCambios = stmt.executeUpdate(str);

        return numeroCambios;
    }

    public static void ejecutarSelectEjemplar(Connection conn) throws SQLException {
        String sql = "SELECT * FROM TEJEMPLARES";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_ejemplar = rs.getInt("EJECOD");
                String estado_fisico = rs.getString("EJEFISICO");
                int id_libro = rs.getInt("LIBID");

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Ejemplar: " + id_ejemplar + ", Estado Fisico: " + estado_fisico + ", ID Libro: " + id_libro);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean ejecutarDeleteEjemplar(Connection conn, Ejemplar obj) throws SQLException {
        int id_ejemplar = obj.getIdEjemplar();
        String sql = "DELETE FROM TEJEMPLARES WHERE EJECOD = '" + id_ejemplar + "'";

        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql) > 0;
    }

    public static Ejemplar crearEjemplar(Connection conn) throws SQLException, InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce el ID del ejemplar: ");
        int id_ejemplar = Io.leerNumero();
        System.out.println("Introduce el estado fisico: ");
        String estado_fisico = input.next();
        System.out.println("Introduce el ID del libro: ");
        int id_libro = Io.leerNumero();

        Ejemplar nuevoEjemplar = new Ejemplar(id_ejemplar, estado_fisico, id_libro);
        int cambios = ejecutarInsertEjemplar(conn, nuevoEjemplar);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        return nuevoEjemplar;
    }

    public static Ejemplar BuscarEjemplar(Connection conn, int id_ejemplar) throws SQLException {
        String sql = "SELECT * FROM TEJEMPLARES WHERE EJECOD = '" + id_ejemplar + "'";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                int swid_ejemplar = rs.getInt("EJECOD");
                String swestado_fisico = rs.getString("EJEFISICO");

                Ejemplar libro = new Ejemplar(swid_ejemplar, swestado_fisico, 0);
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Libro: 0, Estado Fisico: " + swestado_fisico + ", ID Ejemplar: " + swid_ejemplar);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null; // No devuelve el libro
    }
}