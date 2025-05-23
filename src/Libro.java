import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Libro {
    //Atributos
    private long isbn;
    private int copias;
    private String titulo;
    private int id_libro;

    // Constructor
    public Libro(long i, int c, String t) {
        this.isbn = i;
        this.copias = c;
        this.titulo = t;
        this.id_libro = 0; // Id no se gestiona en la BBDD
    }
    
    // Constructor con ID
    public Libro(int id, long i, int c, String t) {
        this.id_libro = id;
        this.isbn = i;
        this.copias = c;
        this.titulo = t;
    }

    // Getters y Setters
    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getCopias() {
        return copias;
    }

    public void setCopias(int copias) {
        this.copias = copias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    // Menu de libro
    public static void menuLibro(Connection conn) {
        int opcion = 0;

        do {
            System.out.println("Menu de libro:");
            System.out.println("1. Ver lista de libros");
            System.out.println("2. Añadir un libro");
            System.out.println("3. Eliminar un libro");
            System.out.println("4. Buscar libro por ISBN");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = Io.leerNumero();
            Io.limpiarPantalla();

            switch (opcion) {
                case 1:
                    try {
                        selectLibro(conn);
                    } catch (SQLException e) {
                        System.out.println("Error al mostrar libros: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        crearLibro(conn);
                    } catch (SQLException | InterruptedException e) {
                        System.out.println("Error al crear libro: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Introduce el ID del libro a eliminar: ");
                    int id = Io.leerNumero();
                    try {
                        boolean eliminado = deleteLibro(conn, id);
                        if (eliminado) {
                            System.out.println("Libro eliminado correctamente.");
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar libro: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Introduce el ISBN del libro a buscar: ");
                    long isbn = Io.leerLong();
                    try {
                        buscarLibro(conn, isbn);
                    } catch (SQLException e) {
                        System.out.println("Error al buscar libro: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Saliendo del menú de libro...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 5);
    }

    // Funciones de Libro
    public static int insertLibro(Connection conn, Libro libro) throws SQLException {
        long isbn = libro.getIsbn();
        int copias = libro.getCopias();
        String titulo = libro.getTitulo();

        String str = "INSERT INTO TLIBROS (LIBISBN, LIBTITULO, LIBCOPIAS) VALUES ('" + isbn + "', '" + titulo + "', '" + copias + "')";

        Statement stmt = conn.createStatement();
        int numeroCambios = stmt.executeUpdate(str);

        return numeroCambios;
    }

    public static void selectLibro(Connection conn) throws SQLException {
        String sql = "SELECT * FROM TLIBROS";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("LIBID");
                long isbn = rs.getLong("LIBISBN");
                String titulo = rs.getString("LIBTITULO");
                int copias = rs.getInt("LIBCOPIAS");

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Libro: " + id + ", ISBN: " + isbn + ", Titulo: " + titulo + ", Copias: " + copias);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean deleteLibro(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM TLIBROS WHERE LIBID = " + id;

        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql) > 0;
    }

    public static Libro crearLibro(Connection conn) throws SQLException, InterruptedException {
        System.out.println("Introduce el ISBN: ");
        long isbn = Io.leerLong();
        System.out.println("Introduce el titulo: ");
        String titulo = Io.leerTexto();
        System.out.println("Introduce el numero de copias: ");
        int copias = Io.leerNumero();

        Libro nuevoLibro = new Libro(isbn, copias, titulo);
        int cambios = insertLibro(conn, nuevoLibro);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        return nuevoLibro;
    }

    public static Libro buscarLibro(Connection conn, long isbn) throws SQLException {
        String sql = "SELECT * FROM TLIBROS WHERE LIBISBN = '" + isbn + "'";
        Statement st = null;
        ResultSet rs = null;
        Libro libro = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
				String swtitulo = rs.getString("LIBTITULO");
                long swisbn = rs.getLong("LIBISBN");
                int swcop = rs.getInt("LIBCOPIAS");
                int swid = rs.getInt("LIBID");

                libro = new Libro(swisbn, swcop, swtitulo);
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("Titulo: " + swtitulo + ", ISBN: " + isbn + ", Copias: "  + swcop + " ID: " + swid);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return libro;
    }
}