public class Usuario {
    //Atributos
    private int cod;
    private String dni, nombre, apellido, telefono, correo, usuario, contrasena;
    private Tipo_Usuario tipo_usuario;
    private int dias_penalizacion;
    //Constructor
    public Usuario(int cod, String d, String n, String a, String t, String c, String u, String p, Tipo_Usuario tu, int dp) {
        this.cod = cod;
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
    
    public Usuario(int cod, String d, String n, String a, String t, String c, String u, String p){
        this.cod = cod;
        this.dni = d;
        this.nombre = n;
        this.apellido = a;
        this.telefono = t;
        this.correo = c;
        this.usuario = u;
        this.contrasena = p;
        this.tipo_usuario = new Tipo_Usuario(1, usuario);
        this.dias_penalizacion = 0;
    }
    //Getters y Setters
    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
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
}
