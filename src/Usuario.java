import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuario {
    //Atributos
    private String dni, nombre, apellido, correo, usuario, contrasena;
    private int tipo_usuario, telefono, dias_penalizacion;
    // Constructor

    public Usuario() {}
    
    public Usuario(String dni, String nombre, String apellido, int telefono, String correo, String usuario, String contrasena, int tipoUsu) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.tipo_usuario = tipoUsu;
    }

    //Getters y Setters
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
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

    public int getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(int tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public int getDias_penalizacion() {
        return dias_penalizacion;
    }

    public void setDias_penalizacion(int dias_penalizacion) {
        this.dias_penalizacion = dias_penalizacion;
    }

    // Methods
    public static int ejecutarInsert(Connection conn, Usuario usu) {
        String dni = usu.getDni();
        String nombre = usu.getNombre();
        String apellido = usu.getApellido();
        int telefono = usu.getTelefono();
        String correo = usu.getCorreo();
        String usuario = usu.getUsuario();
        String passwd = usu.getContrasena();
        int id_tipo_usuario = usu.getTipo_usuario();

        String str = "INSERT INTO TUSUARIOS (USUDNI, USUNOM, USUAPEL, USUTELEFONO, USUCORREO, USUARIO, USUCONTRASENA, USUTIPO, USUDIASPENALIZACION) ";
        str += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
        int numeroCambios = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement(str);
            stmt.setString(1, dni);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido);
            stmt.setInt(4, telefono);
            stmt.setString(5, correo);
            stmt.setString(6, usuario);
            stmt.setString(7, passwd);
            stmt.setInt(8, id_tipo_usuario);

            numeroCambios = stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return numeroCambios;
    }

    public static void registrarUsuarioNormal(Connection conn) {
        System.out.println("Introduce el nombre: ");
        String nombre = Io.leerTexto();
        System.out.println("Introduce el apellido: ");
        String apellido = Io.leerTexto();
        System.out.println("Introduce el dni: ");
        String dni = Io.leerTexto();
        System.out.println("Introduce el telefono: ");
        int telefono = Io.leerNumero();
        System.out.println("Introduce el correo: ");
        String correo = Io.leerTexto();
        System.out.println("Introduce el nombre de usuario: ");
        String usuario = Io.leerTexto();
        System.out.println("Introduce la contraseña: ");
        String contrasena = Io.leerTexto();

        Usuario nuevoUsuario = new Usuario(dni, nombre, apellido, telefono, correo, usuario, contrasena, 1);
        int cambios = ejecutarInsert(conn, nuevoUsuario);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
    }

    public static void ejecutarSelect(Connection conn) {
        String sql = "SELECT * FROM TUSUARIOS";
        ResultSet rs = null;
        Statement st = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("USUID");
                String dni = rs.getString("USUDNI");
                String nombre = rs.getString("USUNOM");
                String apellido = rs.getString("USUAPEL");
                String telefono = rs.getString("USUTELEFONO");
                String correo = rs.getString("USUCORREO");
                String usuario = rs.getString("USUARIO");
                String contrasena = rs.getString("USUCONTRASENA");
                int dias_penalizacion = rs.getInt("USUDIASPENALIZACION");
                String tipoUsu = (rs.getInt("USUTIPO") == 0 ? "Administrador":"Usuario Normal");

                System.out.println("------------------------------------------------------------------------------");
                System.out.println("ID: " + id + ", DNI: " + dni + ", Nombre: " + nombre + ", Apellido: " + apellido + 
                                ", Telefono: " + telefono + ", Correo: " + correo + ", Usuario: " + usuario + ", Contraseña: "
                                + contrasena + ", Tipo Usuario: " + tipoUsu + ", Dias Penalizacion: " + dias_penalizacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteUsuario(Connection conn, int id) {
        Statement st = null;
        ResultSet rs = null;
        int borrados = 0;

        String sel = "SELECT * FROM TUSUARIOS WHERE USUID = " + id;
        String del = "DELETE FROM TUSUARIOS WHERE USUID = " + id;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sel);
            Io.limpiarPantalla();
            
            if (rs.next()) {
                // Mostrar información del usuario que se va a eliminar
                System.out.println("\n===== INFORMACIÓN DEL USUARIO A ELIMINAR =====");
                System.out.println("ID: " + rs.getInt("USUID"));
                System.out.println("DNI: " + rs.getString("USUDNI"));
                System.out.println("Nombre: " + rs.getString("USUNOM"));
                System.out.println("Apellido: " + rs.getString("USUAPEL"));
                System.out.println("Teléfono: " + rs.getString("USUTELEFONO"));
                System.out.println("Correo: " + rs.getString("USUCORREO"));
                System.out.println("Usuario: " + rs.getString("USUARIO"));
                System.out.println("Tipo de Usuario: " + (rs.getInt("USUTIPO") == 0 ? "Administrador" : "Normal"));
                System.out.println("==============================================\n");
                
                // Pedir confirmación antes de eliminar
                System.out.println("¿Está seguro que desea eliminar este usuario? (Si/No)");
                boolean confirmar = Io.leerSiNo();
                
                if (confirmar) {
                    // Cerrar el ResultSet antes de realizar otra operación
                    rs.close();
                    rs = null;
                    
                    // Crear una nueva declaración para el delete
                    Statement stDelete = conn.createStatement();
                    borrados = stDelete.executeUpdate(del);
                    stDelete.close();
                } else {
                    System.out.println("Operación de eliminación cancelada.");
                }
            } else {
                System.out.println("No se encontró ningún usuario con el ID: " + id);
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

    public static Usuario crearUsuario(Connection conn) {
        System.out.println("Introduce el nombre: ");
        String nombre = Io.leerTexto();
        System.out.println("Introduce el apellido: ");
        String apellido = Io.leerTexto();
        System.out.println("Introduce el dni: ");
        String dni = Io.leerTexto();
        System.out.println("Introduce el telefono: ");
        int telefono = Io.leerNumero();
        System.out.println("Introduce el correo: ");
        String correo = Io.leerTexto();
        System.out.println("Introduce el nombre de usuario: ");
        String usuario = Io.leerTexto();
        System.out.println("Introduce la contraseña: ");
        String contrasena = Io.leerTexto();
        System.out.println("¿Es el nuevo usuario administrador? (Si/No)");
        int tipoUsu;
        boolean tipoUsuCheck = Io.leerSiNo();
        if (tipoUsuCheck == true) {
            System.out.println("¿Esta seguro que quiere crear un usuario administrador?");
            tipoUsuCheck = Io.leerSiNo();
            if (tipoUsuCheck == true) {
                tipoUsu = 0; 
            } else {
                tipoUsu = 1;
            }
        } else {
            tipoUsu = 1;
        }

        Usuario nuevoUsuario = new Usuario(dni, nombre, apellido, telefono, correo, usuario, contrasena, tipoUsu);
        int cambios = ejecutarInsert(conn, nuevoUsuario);

        if (cambios == 0) {
            System.out.println("No se ha podido añadir el registro");
        } else {
            System.out.println("Registro añadido correctamente");
        }
        return null;
    }

    public static void menuUsuario(Connection conn) {
        int opcion = 0;

        do {
            System.out.println("Menu de usuario:");
            System.out.println("1. Ver lista de usuarios");
            System.out.println("2. Añadir un usuario");
            System.out.println("3. Eliminar un usuario");
            System.out.println("4. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcion = Io.leerNumero();
            Io.limpiarPantalla();

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
                    if (cod == -1) {
                        menuUsuario(conn);
                    } else {
                        boolean eliminado = deleteUsuario(conn, cod);
                        if (eliminado) {
                            System.out.println("Usuario eliminado correctamente.");
                        } else {
                            System.out.println("No se pudo eliminar el usuario.");
                        }
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