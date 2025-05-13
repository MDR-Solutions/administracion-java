public class Prestamo {
	//Atributos
    private int id_prestamo;
    private String dni;
    private int id_ejemplar;
    private java.time.LocalDate fecha_prestamo, fecha_devolucion;

    // Constructor
    public Prestamo(int id, String d, int ej, String fp, String fd) {
        this.id_prestamo = id;
        this.dni = d;
        this.id_ejemplar = ej;
        this.fecha_prestamo = java.time.LocalDate.parse(fp);
        this.fecha_devolucion = java.time.LocalDate.parse(fd).plusDays(15);
    }

    // Getters y Setters
    public int getId_prestamo() {
        return id_prestamo;
    }

    public void setId_prestamo(int id) {
        this.id_prestamo = id;
    }

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

    public java.time.LocalDate getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(java.time.LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public java.time.LocalDate getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(java.time.LocalDate fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    // Funciones de Prestamo
    public static int ejecutarInsertPrestamo(Connection conn, Prestamo obj) throws SQLException {
        int id_prestamo = obj.getId_prestamo();
        String dni = obj.getDni();
        int id_ejemplar = obj.getId_ejemplar();
        java.time.LocalDate fecha_prestamo = obj.getFecha_prestamo();
        java.time.LocalDate fecha_devolucion = obj.getFecha_devolucion();

        String str = "INSERT INTO TPRESTAMOS (PREID, PREUSUDNI, PREEJECOD, PREFECHAPRESTAMO, PREFECHADEVOLUCION) VALUES ('" + id_prestamo + "', '" + dni + "', '" + id_ejemplar + "', '" + fecha_prestamo + "', '" + fecha_devolucion + "')";

        Statement stmt = conn.createStatement();
        int numeroCambios = stmt.executeUpdate(str);

        return numeroCambios;
    }

    public static void ejecutarSelectPrestamo(Connection conn) throws SQLException {
        String sql = "SELECT * FROM TPRESTAMOS";
        ResultSet rs = null;
        Statement st = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_prestamo = rs.getInt("PREID");
                String dni = rs.getString("PREUSUDNI");
                int id_ejemplar = rs.getInt("PREEJECOD");
                java.time.LocalDate fecha_prestamo = rs.getObject("PREFECHAPRESTAMO", java.time.LocalDate.class);
                java.time.LocalDate fecha_devolucion = rs.getObject("PREFECHADEVOLUCION", java.time.LocalDate.class);

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Prestamo: " + id_prestamo + ", DNI: " + dni + ", ID Ejemplar: " + id_ejemplar + ", Fecha Prestamo: " + fecha_prestamo + ", Fecha Devolucion: " + fecha_devolucion);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean ejecutarDeletePrestamo(Connection conn, Prestamo obj) throws SQLException {
        int id_prestamo = obj.getId_prestamo();
        String sql = "DELETE FROM TPRESTAMOS WHERE PREID = '" + id_prestamo + "'";

        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql) > 0;
    }

    public static Prestamo crearPrestamo(Connection conn) throws SQLException, InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce el ID del prestamo: ");
        int id_prestamo = Io.leerNumero();
        System.out.println("Introduce el DNI: ");
        String dni = input.next();
        System.out.println("Introduce el ID del ejemplar: ");
        int id_ejemplar = Io.leerNumero();

        java.time.LocalDate fechaPrestamo = java.time.LocalDate.parse(Io.leerFecha());
        java.time.LocalDate fechaDevolucion = fechaPrestamo.plusDays(15);

        Prestamo nuevoPrestamo = new Prestamo(id_prestamo, dni, id_ejemplar, fechaPrestamo.toString(), fechaDevolucion.toString());
        int cambios = ejecutarInsertPrestamo(conn, nuevoPrestamo);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
        return nuevoPrestamo;
    }

    public static Prestamo BuscarPrestamo(Connection conn, int id_prestamo) throws SQLException {
        String sql = "SELECT * FROM TPRESTAMOS WHERE PREID = '" + id_prestamo + "'";
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                int swid_prestamo = rs.getInt("PREID");
                String swdni = rs.getString("PREUSUDNI");

                Prestamo libro = new Prestamo(swid_prestamo, swdni, 0, "", "");
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Libro: 0, DNI: " + swdni + ", ID Ejemplar: " + swid_prestamo);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null; // No devuelve el libro
    }
}