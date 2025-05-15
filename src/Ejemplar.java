import java.sql.*;

public class Ejemplar {
    // Atributos
    private String estado_fisico;
    private int id_libro;
    private String estado;

    //Constructor
    public Ejemplar(String estadoFisico, int libro, String estado) {
        this.estado_fisico = estadoFisico;
        this.id_libro = libro;
        this.estado = estado;
    }
    
    //Getters y Setters
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
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Menu de ejemplar
    public static void menuEjemplar(Connection conn) {
        int opcion = 0;

        do {
            System.out.println("Menu de ejemplar:");
            System.out.println("1. Ver lista de ejemplares");
            System.out.println("2. Añadir un ejemplar");
            System.out.println("3. Eliminar un ejemplar");
            System.out.println("4. Cambiar estado de un ejemplar");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = Io.leerNumero();

            switch (opcion) {
                case 1:
                    try {
                        selectEjemplar(conn);
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
                    int id = Io.leerNumero();
                        boolean eliminado = deleteEjemplar(conn, id);
                        if (eliminado) {
                            System.out.println("Ejemplar eliminado correctamente.");
                        } else {
                            System.out.println("No se pudo eliminar el ejemplar.");
                        }
                    break;
                case 4:
                    try {
                        updateEstadoEje(conn);
                    } catch (SQLException e) {
                        System.out.println("Error al cambiar estado del ejemplar: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Saliendo del menú de ejemplar...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 5);
    }

    // Funciones de Ejemplar
    public static int insertEjemplar(Connection conn, Ejemplar eje) throws SQLException {
        String estado_fisico = eje.getEstadoFisico();
        int id_libro = eje.getIdLibro();
        String estado = eje.getEstado();

        String str = "INSERT INTO TEJEMPLARES (EJEFISICO, LIBID, EJEESTADO)";
        str += "VALUES ('" + estado_fisico + "', '" + id_libro + "', '" + estado + "')";

        Statement stmt = conn.createStatement();
        int numeroCambios = stmt.executeUpdate(str);

        return numeroCambios;
    }

    public static void selectEjemplar(Connection conn) throws SQLException {
        String sql = "SELECT * FROM TEJEMPLARES";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_ejemplar = rs.getInt("EJEID");
                String estado_fisico = rs.getString("EJEFISICO");
                int id_libro = rs.getInt("LIBID");
                String estado = rs.getString("EJEESTADO");

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Ejemplar: " + id_ejemplar + ", Estado Fisico: " + estado_fisico + 
                                ", ID Libro: " + id_libro + ", Estado: " + estado);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean deleteEjemplar(Connection conn, int id) {
        Statement st = null;
        ResultSet rs = null;
        int borrados = 0;

        String sel = "SELECT * FROM TEJEMPLARES WHERE EJEID = " + id;
        String del = "DELETE FROM TEJEMPLARES WHERE EJEID = " + id;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sel);
            
            if (rs.next()) {
                // Mostrar información del usuario que se va a eliminar
                System.out.println("\n===== INFORMACIÓN DEL EJEMPLAR A ELIMINAR =====");
                System.out.println("ID: " + rs.getInt("EJEID"));
                System.out.println("Estado Fisico: " + rs.getString("EJEFISICO"));
                System.out.println("Disponibilidad: " + rs.getString("EJEESTADO"));
                System.out.println("ID Libro: " + rs.getString("LIBID"));
                System.out.println("==============================================\n");
                
                if ((rs.getString("EJEESTADO") != "disponible") || (rs.getString("EJEESTADO") != "reparacion")) {
                    System.out.println("El libro no esta en la biblioteca. No se puede eliminar.");
                } else  {
                    System.out.println("¿Está seguro que desea eliminar este ejemplar? (Si/No)");
                    boolean confirmar = Io.leerSiNo();
                
                    if (confirmar) {
                        // Cerrar el ResultSet antes de realizar delete
                        rs.close();
                        rs = null;

                        // Crear una nueva declaración para el delete
                        Statement stDelete = conn.createStatement();
                        borrados = stDelete.executeUpdate(del);
                        stDelete.close();

                        if (borrados > 0) {
                            System.out.println("Usuario eliminado correctamente.");
                        }
                    } else {
                        System.out.println("Operación de eliminación cancelada.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (borrados > 0);
    }

    public static Ejemplar crearEjemplar(Connection conn) throws SQLException, InterruptedException {
        System.out.println("Introduce el estado fisico (Nuevo, Exelente, Bueno, Desgastado, Dañado): ");
        String fisico = Io.leerTexto().toLowerCase();
        
        if (fisico.equals("danado")) {
            fisico = "dañado";
        }
        while (!fisico.equals("nuevo") && !fisico.equals("exelente") && 
            !fisico.equals("bueno") && !fisico.equals("desgastado") &&
            !fisico.equals("dañado")) {
            System.out.println("Estado no válido. Por favor escriba una de las opciones ");
            fisico = Io.leerTexto().toLowerCase();
            if (fisico.equals("danado")) {
                fisico = "dañado";
            }
        }

        System.out.println("Introduce el ID del libro: ");
        int id_libro = Io.leerNumero();
        
        System.out.println("Introduce el estado del ejemplar (disponible, prestado, retrasado, reparacion): ");
        String estado = Io.leerTexto().toLowerCase();
        
        // Si no se introduce un estado válido, asignamos "disponible" por defecto
        while (!estado.equals("disponible") && !estado.equals("prestado") && 
            !estado.equals("retrasado") && !estado.equals("reparacion")) {
            System.out.println("Estado no válido. Por favor escriba una de las opciones ");
            estado = Io.leerTexto().toLowerCase();
        }

        Ejemplar nuevoEjemplar = new Ejemplar(fisico, id_libro, estado);
        int cambios = insertEjemplar(conn, nuevoEjemplar);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        return nuevoEjemplar;
    }
    
    // Nueva función para cambiar el estado de un ejemplar
    public static void updateEstadoEje(Connection conn) throws SQLException {
        System.out.println("Introduce el ID del ejemplar: ");
        int id_ejemplar = Io.leerNumero();
        
        System.out.println("Introduce el nuevo estado (disponible, prestado, retrasado, reparacion): ");
        String nuevoEstado = Io.leerTexto();
        
        // Comprobar si es un estado válido
        if (nuevoEstado == null || nuevoEstado.isEmpty() || 
            (!nuevoEstado.equals("disponible") && !nuevoEstado.equals("prestado") && 
            !nuevoEstado.equals("retrasado") && !nuevoEstado.equals("reparacion"))) {
            System.out.println("Estado no válido. No se realizará ningún cambio.");
            return;
        }
        
        String sql = "UPDATE TEJEMPLARES SET EJEESTADO = '" + nuevoEstado + "' WHERE EJECOD = " + id_ejemplar;
        
        Statement stmt = conn.createStatement();
        int filas = stmt.executeUpdate(sql);
        
        if (filas > 0) {
            System.out.println("Estado actualizado correctamente");
        } else {
            System.out.println("No se encontró el ejemplar con ID: " + id_ejemplar);
        }
    }

    public static void buscarEjemplar(Connection conn, int id_ejemplar) throws SQLException {
        String sql = "SELECT * FROM TEJEMPLARES WHERE EJECOD = '" + id_ejemplar + "'";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                int ejeid = rs.getInt("EJEID");
                String ejefisico = rs.getString("EJEFISICO");
                int libid = rs.getInt("LIBID");
                String ejeestado = rs.getString("EJEESTADO");
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Ejemplar: " + ejeid + ", Estado Fisico: " + ejefisico + 
                                ", ID Libro: " + libid + ", Estado: " + ejeestado);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}