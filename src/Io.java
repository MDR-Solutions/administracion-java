import java.sql.*;
import java.util.Scanner;

public class Io {
    //Funciones generales:
    //Funcion PADL
    public static String PADL(String texto,int longitud){
        if (texto.length() > longitud){
            return texto.substring(0, longitud);
        }else {
            while (texto.length() < longitud){
                texto = " " + texto;
            }
            return texto;
        }
    }
    //Funcion leer numero
    public static int leerNumero(){
        Scanner input = new Scanner(System.in);
        int numero = 0;
        boolean valido = false;
        try {
            while (!valido) {
                try {
                    numero = input.nextInt();
                    valido = true;
                } catch (Exception e) {
                    System.out.println("Entrada no válida. Inténtalo de nuevo.");
                    input.next();
                }
            }
        } finally {
            input.close();
        }
        return numero;
    }
    //Funcion para cerrar la conexion con la bbdd
    public static void cerrarConexion(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//Funciones de Usuario(Cod, dni, nombre, apellido, telefono, correo, usuario, contrasena, tipo_usuario, dias_penalizacion):
    //Funcion para Insertar un usuario a la BBDD
    public static int ejecutarInsert(Connection conn, Usuario obj){
        int cod = obj.getCod();
        String dni = obj.getDni();
        String nombre = obj.getNombre();
        String apellido = obj.getApellido();
        String telefono = obj.getTelefono();
        String correo = obj.getCorreo();
        String usuario = obj.getUsuario();
        String contrasena = obj.getContrasena();
        int id_tipo_usuario = obj.getTipo_usuario().getId_tipo_usuario();
        int dias_penalizacion = obj.getDias_penalizacion();

        String str = "INSERT INTO TUSUARIOS (USUCOD, USUDNI, USUNOM, USUAPEL, USUTELEFONO, USUCORREO, USUARIO, USUCONTRASENA, USUTIPO, USUDIASPENALIZACION) ";
        str += "VALUES ('" + cod + "', '" + dni + "', '" + nombre + "', '" + apellido + "', '" + telefono + "', '" + correo + "', '" + usuario + "', '" + contrasena + "', " + id_tipo_usuario + ", " + dias_penalizacion + ")";
        int numeroCambios = 0;

        try {
            Statement stmt = conn.createStatement();
            numeroCambios = stmt.executeUpdate(str);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return numeroCambios;
    }
    //Funcion para registrar usuarios normales
    public static void registrarUsuarioNormal(Connection conn) {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce el nombre: ");
        String nombre = input.next();
        System.out.println("Introduce el apellido: ");
        String apellido = input.next();
        System.out.println("Introduce el dni: ");
        String dni = input.next();
        System.out.println("Introduce el telefono: ");
        String telefono = input.next();
        System.out.println("Introduce el correo: ");
        String correo = input.next();
        System.out.println("Introduce el nombre de usuario: ");
        String usuario = input.next();
        System.out.println("Introduce la contraseña: ");
        String contrasena = input.next();

        Usuario nuevoUsuario = new Usuario((int) (Math.random() * 1000), dni, nombre, apellido, telefono, correo, usuario, contrasena);
        int cambios = Io.ejecutarInsert(conn, nuevoUsuario);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
    }
    //Funcion para seleccionar un usuario de la bbdd
    public static void ejecutarSelect(Connection conn){
        String sql = "SELECT * FROM TUSUARIOS";
        ResultSet rs = null;
        Statement st = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int cod = rs.getInt("USUCOD");
                String dni = rs.getString("USUDNI");
                String nombre = rs.getString("USUNOM");
                String apellido = rs.getString("USUAPEL");
                String telefono = rs.getString("USUTELEFONO");
                String correo = rs.getString("USUCORREO");
                String usuario = rs.getString("USUARIO");
                String contrasena = rs.getString("USUCONTRASENA");
                int id_tipo_usuario = rs.getInt("USUTIPO");
                int dias_penalizacion = rs.getInt("USUDIASPENALIZACION");

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("Codigo: " + cod + ", DNI: " + dni + ", Nombre: " + nombre + ", Apellido: " + apellido + 
                                   ", Telefono: " + telefono + ", Correo: " + correo + ", Usuario: " + usuario + 
                                   ", Contraseña: " + contrasena + ", ID Tipo Usuario: " + id_tipo_usuario + 
                                   ", Dias Penalizacion: " + dias_penalizacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Funcion para eliminar un usuario de la bbdd
    public static boolean ejecutarDelete(Connection conn, Usuario obj){
        PreparedStatement st = null;
        int borrados = 0;
        int codigo = obj.getCod();
        String sql = "DELETE FROM TUSUARIOS WHERE USUCOD = " + codigo;

        try {
            st = conn.prepareStatement(sql);
            borrados = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (borrados > 0);
    }

    //Funcion para crear un usuario
    public static Usuario crearUsuario(Connection conn){
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce el nombre: ");
        String nombre = input.next();
        System.out.println("Introduce el apellido: ");
        String apellido = input.next();
        System.out.println("Introduce el dni: ");
        String dni = input.next();
        System.out.println("Introduce el telefono: ");
        String telefono = input.next();
        System.out.println("Introduce el correo: ");
        String correo = input.next();
        System.out.println("Introduce el nombre de usuario: ");
        String usuario = input.next();
        System.out.println("Introduce la contraseña: ");
        String contrasena = input.next();
        System.out.println("Introduce el ID del tipo de usuario: ");
        int id_tipo_usuario = Io.leerNumero();
        System.out.println("Introduce los dias de penalizacion: ");
        int dias_penalizacion = Io.leerNumero();

        Tipo_Usuario tipoUsuario = new Tipo_Usuario(id_tipo_usuario, ""); // Se puede ajustar el nombre si es necesario
        Usuario nuevoUsuario = new Usuario((int) (Math.random() * 1000), dni, nombre, apellido, telefono, correo, usuario, contrasena, tipoUsuario, dias_penalizacion);
        int cambios = Io.ejecutarInsert(conn, nuevoUsuario);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
        return nuevoUsuario;
    }
//Funciones de Autor(id_autor,nombre,apellido1,apellido2):
    //Funcion para añadir un autor a la BBDD
    public static int ejecutarInsertAutor(Connection conn, Autor obj){
        int id_autor = obj.getId_autor();
        String nombre = obj.getNombre();
        String apellido1 = obj.getApellido1();
        String apellido2 = obj.getApellido2();

        String str = "INSERT INTO TAUTORES (AUTID, AUTNOM, AUTAPEL1, AUTAPEL2) ";
        str += "VALUES ('" + id_autor + "', '" + nombre + "', '" + apellido1 + "', '" + apellido2 + "')";
        int numeroCambios = 0;

        try {
            Statement stmt = conn.createStatement();
            numeroCambios = stmt.executeUpdate(str);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return numeroCambios;
    }

    //Funcion para seleccionar un autor de la bbdd
    public static void ejecutarSelectAutor(Connection conn){
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

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Autor: " + id_autor + ", Nombre: " + nombre + ", Apellido 1: " + apellido1 + ", Apellido 2: " + apellido2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Funcion para eliminar un autor de la bbdd
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

    //Funcion para crear un autor
    public static Autor crearAutor(Connection conn){
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce el nombre: ");
        String nombre = input.next();
        System.out.println("Introduce el apellido 1: ");
        String apellido1 = input.next();
        System.out.println("Introduce el apellido 2: ");
        String apellido2 = input.next();

        Autor nuevoAutor = new Autor((int) (Math.random() * 1000), nombre, apellido1, apellido2);
        int cambios = Io.ejecutarInsertAutor(conn, nuevoAutor);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
        return nuevoAutor;
    }
//Funciones de Libro(id_libro,titulo,copias,isbn):
    //Funcion para añadir un libro a la BBDD
    public static int ejecutarInsertLibro(Connection conn, Libro obj){
        int id_libro = obj.getId_libro();
        String titulo = obj.getTitulo();
        int copias = obj.getCopias();
        String isbn = obj.getIsbn();

        String str = "INSERT INTO TLIBROS (LIBID, LIBTITULO, LIBCOPIAS, LIBISBN) ";
        str += "VALUES ('" + id_libro + "', '" + titulo + "', '" + copias + "', '" + isbn + "')";
        int numeroCambios = 0;

        try {
            Statement stmt = conn.createStatement();
            numeroCambios = stmt.executeUpdate(str);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return numeroCambios;
    }

    //Funcion para seleccionar un libro de la bbdd
    public static void ejecutarSelectLibro(Connection conn){
        String sql = "SELECT * FROM TLIBROS";
        ResultSet rs = null;
        Statement st = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int id_libro = rs.getInt("LIBID");
                String titulo = rs.getString("LIBTITULO");
                int copias = rs.getInt("LIBCOPIAS");
                String isbn = rs.getString("LIBISBN");

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Libro: " + id_libro + ", Titulo: " + titulo + ", Copias: " + copias + ", ISBN: " + isbn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Funcion para eliminar un libro de la bbdd
    public static boolean ejecutarDeleteLibro(Connection conn, Libro obj){
        PreparedStatement st = null;
        int borrados = 0;
        int id_libro = obj.getId_libro();
        String sql = "DELETE FROM TLIBROS WHERE LIBID = " + id_libro;

        try {
            st = conn.prepareStatement(sql);
            borrados = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (borrados > 0);
    }
    //Funcion para crear un libro
    public static Libro crearLibro(Connection conn) {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce el ID del libro: ");
        int id_libro = Io.leerNumero();
        System.out.println("Introduce el titulo: ");
        String titulo = input.next();
        System.out.println("Introduce el ISBN: ");
        String isbn = input.next();
        System.out.println("Introduce el numero de copias: ");
        int copias = Io.leerNumero();

        Libro nuevoLibro = new Libro(id_libro, titulo, copias, isbn);
        int cambios = Io.ejecutarInsertLibro(conn, nuevoLibro);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
        return nuevoLibro;
    }
    //Funcion para buscar libro por ISBN
    public static Libro BuscarLibro(Connection conn,String isbn){
        String sql = "Select * from tlbrios where LIBISBN = '" + isbn + "'";
        ResultSet rs = null;
        Statement st = null;
        Libro libro = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            if(rs.next()){
                String swisbn = rs.getString("LIBISBN");
                String swtitulo = rs.getString("LIBTITULO");
                int swcopias = rs.getInt("LIBCOPIAS");
                int swcod = rs.getInt("LIBID");
                libro = new Libro(swcod, swtitulo, swcopias, swisbn);
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("Titulo: " + swtitulo +"," + " ISBN: " + swisbn + "," + " Copias: " + swcopias + "," + " ID: " + swcod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return libro;
    }
//Funciones para ejemplar(id_ejemplar,estado fisico,id_libro):
    //Funcion para añadir un ejemplar a la BBDD
    public static int ejecutarInsertEjemplar(Connection conn, Ejemplar obj){
        int id_ejemplar = obj.getIdEjemplar();
        String estado_fisico = obj.getEstadoFisico();
        int id_libro = obj.getIdLibro();

        String str = "INSERT INTO TEJEMPLARES (EJECOD, EJEFISICO, LIBID) ";
        str += "VALUES ('" + id_ejemplar + "', '" + estado_fisico + "', '" + id_libro + "')";
        int numeroCambios = 0;

        try {
            Statement stmt = conn.createStatement();
            numeroCambios = stmt.executeUpdate(str);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return numeroCambios;
    }
    //Funcion para seleccionar un ejemplar de la bbdd
    public static void ejecutarSelectEjemplar(Connection conn){
        String sql = "SELECT * FROM TEJEMPLARES";
        ResultSet rs = null;
        Statement st = null;

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
            e.printStackTrace();
        }
    }
    //Funcion para eliminar un ejemplar de la bbdd
    public static boolean ejecutarDeleteEjemplar(Connection conn, Ejemplar obj){
        PreparedStatement st = null;
        int borrados = 0;
        int id_ejemplar = obj.getIdEjemplar();
        String sql = "DELETE FROM TEJEMPLARES WHERE EJECOD = " + id_ejemplar;

        try {
            st = conn.prepareStatement(sql);
            borrados = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (borrados > 0);
    }
    //Funcion para crear un ejemplar
    public static Ejemplar crearEjemplar(Connection conn) {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce el ID del ejemplar: ");
        int id_ejemplar = Io.leerNumero();
        System.out.println("Introduce el estado fisico: ");
        String estado_fisico = input.next();
        System.out.println("Introduce el ID del libro: ");
        int id_libro = Io.leerNumero();

        Ejemplar nuevoEjemplar = new Ejemplar(id_ejemplar, estado_fisico, id_libro);
        int cambios = Io.ejecutarInsertEjemplar(conn, nuevoEjemplar);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
        return nuevoEjemplar;
    }
//Funciones para prestamos(id_prestamo,dni,id_ejemplar,fecha_prestamo,fecha_devolucion):
    //Funcion para añadir un prestamo a la BBDD
    public static int ejecutarInsertPrestamo(Connection conn, Prestamo obj){
        int id_prestamo = obj.getId_prestamo();
        String dni = obj.getDni();
        int id_ejemplar = obj.getId_ejemplar();
        Date fecha_prestamo = obj.getFecha_prestamo();
        Date fecha_devolucion = obj.getFecha_devolucion();

        String str = "INSERT INTO TPRESTAMOS (PREID, PREUSUDNI, PREEJECOD, PREFECHAPRESTAMO, PREFECHADEVOLUCION) ";
        str += "VALUES ('" + id_prestamo + "', '" + dni + "', '" + id_ejemplar + "', '" + fecha_prestamo + "', '" + fecha_devolucion + "')";
        int numeroCambios = 0;

        try {
            Statement stmt = conn.createStatement();
            numeroCambios = stmt.executeUpdate(str);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return numeroCambios;
    }

    //Funcion para seleccionar un prestamo de la bbdd
    public static void ejecutarSelectPrestamo(Connection conn){
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
                String fecha_prestamo = rs.getString("PREFECHAPRESTAMO");
                String fecha_devolucion = rs.getString("PREFECHADEVOLUCION");

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID Prestamo: " + id_prestamo + ", DNI: " + dni + ", ID Ejemplar: " + id_ejemplar + ", Fecha Prestamo: " + fecha_prestamo + ", Fecha Devolucion: " + fecha_devolucion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Funcion para eliminar un prestamo de la bbdd
    public static boolean ejecutarDeletePrestamo(Connection conn, Prestamo obj){
        PreparedStatement st = null;
        int borrados = 0;
        int id_prestamo = obj.getId_prestamo();
        String sql = "DELETE FROM TPRESTAMOS WHERE PREID = " + id_prestamo;

        try {
            st = conn.prepareStatement(sql);
            borrados = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (borrados > 0);
    }
    //Funcion para crear un prestamo
    public static Prestamo crearPrestamo(Connection conn) {
        Scanner input = new Scanner(System.in);
        System.out.println("Introduce el ID del prestamo: ");
        int id_prestamo = Io.leerNumero();
        System.out.println("Introduce el DNI: ");
        String dni = input.next();
        System.out.println("Introduce el ID del ejemplar: ");
        int id_ejemplar = Io.leerNumero();
        System.out.println("Introduce la fecha de prestamo (yyyy-MM-dd): ");
        String fecha_prestamo = input.next();

        String fecha_devolucion = "";
        try {
            java.time.LocalDate fechaPrestamo = java.time.LocalDate.parse(fecha_prestamo);
            java.time.LocalDate fechaDevolucion = fechaPrestamo.plusDays(15);
            fecha_devolucion = fechaDevolucion.toString();
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Asegúrate de usar el formato yyyy-MM-dd.");
            input.close();
            return null;
        }

        Prestamo nuevoPrestamo = new Prestamo(id_prestamo, dni, id_ejemplar, fecha_prestamo, fecha_devolucion);
        int cambios = Io.ejecutarInsertPrestamo(conn, nuevoPrestamo);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
        return nuevoPrestamo;
    }
//Funciones de Menus:
    //Menu de usuario:
    public static void menuUsuario(Connection conn) {
        Scanner input = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("Menu de usuario:");
            System.out.println("1. Ver lista de usuarios");
            System.out.println("2. Añadir un usuario");
            System.out.println("3. Eliminar un usuario");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = Io.leerNumero();

            switch (opcion) {
                case 1:
                    ejecutarSelect(conn);
                    break;
                case 2:
                    crearUsuario(conn);
                    break;
                case 3:
                    System.out.println("Introduce el código del usuario a eliminar: ");
                    int cod = Io.leerNumero();
                    Usuario usuarioAEliminar = new Usuario(cod, null, null, null, null, null, null, null, null, 0);
                    boolean eliminado = ejecutarDelete(conn, usuarioAEliminar);
                    if (eliminado) {
                        System.out.println("Usuario eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el usuario.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del menú de usuario...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
        input.close();
    }
    //Menu de autor:
    public static void menuAutor(Connection conn) {
        Scanner input = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("Menu de autor:");
            System.out.println("1. Ver lista de autores");
            System.out.println("2. Añadir un autor");
            System.out.println("3. Eliminar un autor");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = Io.leerNumero();

            switch (opcion) {
                case 1:
                    ejecutarSelectAutor(conn);
                    break;
                case 2:
                    crearAutor(conn);
                    break;
                case 3:
                    System.out.println("Introduce el ID del autor a eliminar: ");
                    int id_autor = Io.leerNumero();
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
        input.close();
    }
    //Menu de libro:
    public static void menuLibro(Connection conn) {
        Scanner input = new Scanner(System.in);
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

            switch (opcion) {
                case 1:
                    ejecutarSelectLibro(conn);
                    break;
                case 2:
                    crearLibro(conn);
                    break;
                case 3:
                    System.out.println("Introduce el ID del libro a eliminar: ");
                    int id_libro = Io.leerNumero();
                    Libro libroAEliminar = new Libro(id_libro, null, 0, null);
                    boolean eliminado = ejecutarDeleteLibro(conn, libroAEliminar);
                    if (eliminado) {
                        System.out.println("Libro eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el libro.");
                    }
                    break;
                case 4:
                    System.out.println("Introduce el ISBN del libro a buscar: ");
                    String isbn = input.next();
                    BuscarLibro(conn, isbn);
                    break;
                case 5:
                    System.out.println("Saliendo del menú de libro...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 5);
        input.close();
    }
    //Menu de ejemplar:
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
                    ejecutarSelectEjemplar(conn);
                    break;
                case 2:
                    crearEjemplar(conn);
                    break;
                case 3:
                    System.out.println("Introduce el ID del ejemplar a eliminar: ");
                    int id_ejemplar = Io.leerNumero();
                    Ejemplar ejemplarAEliminar = new Ejemplar(id_ejemplar, null, 0);
                    boolean eliminado = ejecutarDeleteEjemplar(conn, ejemplarAEliminar);
                    if (eliminado) {
                        System.out.println("Ejemplar eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el ejemplar.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del menú de ejemplar...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
        input.close();
    }
    //Menu de prestamo:
    public static void menuPrestamo(Connection conn) {
        Scanner input = new Scanner(System.in);
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
                    ejecutarSelectPrestamo(conn);
                    break;
                case 2:
                    crearPrestamo(conn);
                    break;
                case 3:
                    System.out.println("Introduce el ID del prestamo a eliminar: ");
                    int id_prestamo = Io.leerNumero();
                    Prestamo prestamoAEliminar = new Prestamo(id_prestamo, null, 0, null, null);
                    boolean eliminado = ejecutarDeletePrestamo(conn, prestamoAEliminar);
                    if (eliminado) {
                        System.out.println("Prestamo eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el prestamo.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del menú de prestamo...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 4);
        input.close();
    }
}