import java.sql.*;

public class Autor {
    //Atributos
    private int id_autor;
    private String nombre, apellido1, apellido2;

    //Constructor
    public Autor(int id, String n, String a1, String a2) {
        this.id_autor = id;
        this.nombre = n;
        this.apellido1 = a1;
        this.apellido2 = a2;
    }

    public Autor() {}

    //Getters y Setters
    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

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

    //Funciones de Autor
    public static int ejecutarInsertAutor(Connection conn, Autor obj){
        int id_autor = obj.getId_autor();
        String nombre = obj.getNombre();
        String apellido1 = obj.getApellido1();
        String apellido2 = obj.getApellido2();

        String str = "INSERT INTO TAUTORES (AUTID, AUTNOM, AUTAPEL1, AUTAPEL2) ";
        str += "VALUES ('" + id_autor + "', '" + nombre + "', '" + apellido1 + "', '" + apellido2 + "')";
        int numeroCambios = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(str);
            numeroCambios = stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return numeroCambios;
    }

    public static void ejecutarSelectAutor(Connection conn){
        String sql = "SELECT * FROM TAUTORES";
        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                int id_autor = rs.getInt("AUTID");
                String nombre = rs.getString("AUTNOM");
                String apellido1 = rs.getString("AUTAPEL1");
                String apellido2 = rs.getString("AUTAPEL2");

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Autor: " + id_autor + ", Nombre: " + nombre + ", Apellido 1: " + apellido1 + ", Apellido 2: " + apellido2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean ejecutarDeleteAutor(Connection conn, Autor obj){
        PreparedStatement st = null;
        int borrados = 0;
        int id_autor = obj.getId_autor();
        String sql = "DELETE FROM TAUTORES WHERE AUTID = " + id_autor;

        try {
            st = conn.prepareStatement(sql);
            borrados = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (borrados > 0);
    }

    public static Autor crearAutor(Connection conn){
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce el nombre: ");
        String nombre = input.next();
        System.out.println("Introduce el apellido 1: ");
        String apellido1 = input.next();
        System.out.println("Introduce el apellido 2: ");
        String apellido2 = input.next();

        Autor nuevoAutor = new Autor((int) (Math.random() * 1000), nombre, apellido1, apellido2);
        int cambios = ejecutarInsertAutor(conn, nuevoAutor);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
        return nuevoAutor;
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tautores", "root", "");
        Scanner input = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("Menu de autor:");
            System.out.println("1. Ver lista de autores");
            System.out.println("2. Añadir un autor");
            System.out.println("3. Eliminar un autor");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = input.nextInt();

            switch (opcion) {
                case 1:
                    ejecutarSelectAutor(conn);
                    break;
                case 2:
                    crearAutor(conn);
                    break;
                case 3:
                    System.out.println("Introduce el ID del autor a eliminar: ");
                    int id_autor = input.nextInt();
                    Autor autorAEliminar = new Autor(id_autor, null, null, null);
                    boolean eliminado = ejecutarDeleteAutor(conn, autorAEliminar);
                    if (eliminado) {
                        System.out.println("Autor eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el autor.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del menú de autor...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
        conn.close();
    }
}
