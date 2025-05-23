import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Autor {
    //Atributos
    private String nombre, apellido1, apellido2;
    private String nacionalidad;
    private LocalDate fechaNacimiento;

    //Constructor
    public Autor(String n, String a1, String a2, String nacion, LocalDate fechaNac) {
        this.nombre = n;
        this.apellido1 = a1;
        this.apellido2 = a2;
        this.nacionalidad = nacion;
        this.fechaNacimiento = fechaNac;
    }

    public Autor() {}

    //Getters y Setters originales
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    
    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    //Funciones de Autor
    public static int insertAutor(Connection conn, Autor autor){
        String nombre = autor.getNombre();
        String apellido1 = autor.getApellido1();
        String apellido2 = autor.getApellido2();
        String nacionalidad = autor.getNacionalidad();
        LocalDate fechaNacimiento = autor.getFechaNacimiento();

        String str = "INSERT INTO TAUTORES (AUTNOM, AUTAPEL1, AUTAPEL2, AUTNACIONALIDAD, AUTFECHANACIMIENTO) ";
        str += "VALUES ('" + nombre + "', '" + apellido1 + "', '" + apellido2 + "', ";
        
        // Si la nacionalidad es null, ponemos NULL en el SQL
        if (nacionalidad == "") {
            str += "NULL, ";
        } else {
            str += "'" + nacionalidad + "', ";
        }
        
        // Si la fecha es null, ponemos NULL en el SQL
        if (fechaNacimiento == null) {
            str += "NULL)";
        } else {
            str += "'" + fechaNacimiento + "')";
        }
        
        int numeroCambios = 0;

        try {
            Statement stmt = conn.createStatement();
            numeroCambios = stmt.executeUpdate(str);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return numeroCambios;
    }

    public static void selectAutor(Connection conn) {
        String sql = "SELECT * FROM TAUTORES";
        ResultSet rs = null;
        Statement st = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_autor = rs.getInt("AUTID");
                String nombre = rs.getString("AUTNOM");
                String apellido1 = rs.getString("AUTAPEL1");
                String apellido2 = rs.getString("AUTAPEL2");
                String nacionalidad = rs.getString("AUTNACIONALIDAD");
                
                // Formateo a LocalDate si se encuentra fecha
                LocalDate fechaNacimiento = null;
                java.sql.Date sqlDate = rs.getDate("AUTFECHANACIMIENTO");
                if (sqlDate != null) {
                    fechaNacimiento = sqlDate.toLocalDate();
                }

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Autor: " + id_autor + ", Nombre: " + nombre + ", Apellido 1: " + apellido1 + 
                                    ", Apellido 2: " + apellido2 + ", Nacionalidad: " + nacionalidad + 
                                    ", Fecha Nacimiento: " + fechaNacimiento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteAutor(Connection conn, int id){
        Statement st = null;
        int borrados = 0;
        String sql = "DELETE FROM TAUTORES WHERE AUTID = " + id;

        try {
            st = conn.createStatement();
            borrados = st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (borrados > 0);
    }

    public static Autor crearAutor(Connection conn) {
        System.out.println("Introduce el nombre: ");
        String nombre = Io.leerTexto();
        System.out.println("Introduce el apellido 1: ");
        String apellido1 = Io.leerTexto();
        System.out.println("Introduce el apellido 2: ");
        String apellido2 = Io.leerTexto();
        System.out.println("Introduce la nacionalidad: ");
        String nacionalidad = Io.leerTexto();
        System.out.println("Introduce la fecha de nacimiento (formato DD-MM-YYYY): ");
        String fechaTexto = Io.leerTexto();
        LocalDate fechaNacimiento = null;
        
        // Solo se intenta convertir la fecha si no está vacía
        if (fechaTexto != null && !fechaTexto.trim().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                fechaNacimiento = LocalDate.parse(fechaTexto, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Se establecerá como NULL.");
            }
        }

        Autor nuevoAutor = new Autor(nombre, apellido1, apellido2, nacionalidad, fechaNacimiento);
        int cambios = insertAutor(conn, nuevoAutor);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        return nuevoAutor;
    }

    public static void menuAutor(Connection conn){
        int opcion = 0;

        do {
            Io.limpiarPantalla();
            System.out.println("Menu de autor:");
            System.out.println("1. Ver lista de autores");
            System.out.println("2. Añadir un autor");
            System.out.println("3. Eliminar un autor");
            System.out.println("4. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcion = Io.leerNumero();
            Io.limpiarPantalla();

            switch (opcion) {
                case 1:
                    selectAutor(conn);
                    break;
                case 2:
                    crearAutor(conn);
                    break;
                case 3:
                    System.out.println("Introduce el ID del autor a eliminar: ");
                    int id = Io.leerNumero();
                    boolean eliminado = deleteAutor(conn, id);
                    if (eliminado) {
                        System.out.println("Autor eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el autor.");
                    }
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
    }
}