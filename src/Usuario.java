public class Usuario {
    //Atributos
    private static int id;
    private String dni, nombre, apellido, telefono, correo, usuario, contrasena;
    private Tipo_Usuario tipo_usuario;
    private int dias_penalizacion;
    // Constructor
    public Usuario(int id, String d, String n, String a, String t, String c, String u, String p, Tipo_Usuario tu, int dp) {
        this.id = id;
        this.dni = d;
        this.nombre = n;
        this.apellido = a;
        this.telefono = t;
        this.correo = c;
        this.usuario = u;
        this.contrasena = p;
        this.tipo_usuario = tu;
        this.dias_penalizacion = dp;
    }

    public Usuario() {}
    
    public Usuario(int id, String d, String n, String a, String t, String c, String u) {
        this.id = id;
        this.dni = d;
        this.nombre = n;
        this.apellido = a;
        this.telefono = t;
        this.correo = c;
        this.usuario = u;
        this.contrasena = "";
        this.tipo_usuario = new Tipo_Usuario(1, usuario);
        this.dias_penalizacion = 0;
    }
    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        Usuario.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Tipo_Usuario getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(Tipo_Usuario tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public int getDias_penalizacion() {
        return dias_penalizacion;
    }

    public void setDias_penalizacion(int dias_penalizacion) {
        this.dias_penalizacion = dias_penalizacion;
    }

    // Methods
    public static int ejecutarInsert(Connection conn, Usuario obj) {
        String dni = obj.getDni();
        String nombre = obj.getNombre();
        String apellido = obj.getApellido();
        String telefono = obj.getTelefono();
        String correo = obj.getCorreo();
        String usuario = obj.getUsuario();
        int id_tipo_usuario = obj.getTipo_usuario().getId_tipo_usuario();
        int dias_penalizacion = obj.getDias_penalizacion();

        String str = "INSERT INTO TUSUARIOS (USID, USUDNI, USUNOM, USUAPEL, USUTELEFONO, USUCORREO, USUARIO, USUCONTRASENA, USUTIPO, USUDIASPENALIZACION) ";
        str += "VALUES ('" + id + "', '" + dni + "', '" + nombre + "', '" + apellido + "', '" + telefono + "', '" + correo + "', '" + usuario + "', '" + contrasena + "', " + id_tipo_usuario + ", " + dias_penalizacion + ")";

        int numeroCambios = 0;

        try {
            Statement stmt = conn.createStatement();
            numeroCambios = stmt.executeUpdate(str);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return numeroCambios;
    }

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

        Usuario nuevoUsuario = new Usuario(0, dni, nombre, apellido, telefono, correo, usuario, contrasena);
        int cambios = ejecutarInsert(null, nuevoUsuario);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
    }

    public static void ejecutarSelect(Connection conn) {
        String sql = "SELECT * FROM TUSUARIOS";
        ResultSet rs = null;
        Statement st = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("USID");
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
                System.out.println("ID: " + id + ", DNI: " + dni + ", Nombre: " + nombre + ", Apellido: " + apellido + 
                                    ", Telefono: " + telefono + ", Correo: " + correo + ", Usuario: " + usuario + 
                                    ", Contraseña: " + contrasena + ", ID Tipo Usuario: " + id_tipo_usuario + 
                                    ", Dias Penalizacion: " + dias_penalizacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean ejecutarDelete(Connection conn, Usuario obj) {
        PreparedStatement st = null;
        int borrados = 0;
        int id = obj.getId();

        String sql = "DELETE FROM TUSUARIOS WHERE USID = '" + id + "'";

        try {
            st = conn.prepareStatement(sql);
            borrados = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (borrados > 0);
    }

    public static Usuario crearUsuario(Connection conn) {
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

        int id_tipo_usuario = 0;
        int dias_penalizacion = 0;

        Tipo_Usuario tipoUsuario = new Tipo_Usuario(0, usuario);
        Usuario nuevoUsuario = new Usuario(id = 0, dni, nombre, apellido, telefono, correo, usuario, contrasena);
        int cambios = ejecutarInsert(null, nuevoUsuario);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        input.close();
        return null;
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlite:TUSUARIOS.db");
        Scanner input = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("Menu de usuario:");
            System.out.println("1. Ver lista de usuarios");
            System.out.println("2. Añadir un usuario");
            System.out.println("3. Eliminar un usuario");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = input.nextInt();
            input.nextLine(); // Consume el salto de línea

            switch (opcion) {
                case 1:
                    Usuario.ejecutarSelect(null);
                    break;
                case 2:
                    crearUsuario(conn);
                    break;
                case 3:
                    System.out.println("Introduce el código del usuario a eliminar: ");
                    int cod = input.nextInt();
                    input.nextLine(); // Consume el salto de línea
                    Usuario usuarioAEliminar = new Usuario(cod, null, null, null, null, null, null, null, null, 0);
                    boolean eliminado = Usuario.ejecutarDelete(null, usuarioAEliminar);
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

}
