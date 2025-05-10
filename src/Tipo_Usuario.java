public class Tipo_Usuario {
    //Atributos
	private int id_tipo_usuario;
    private String nombre;
    //Constructor
    public Tipo_Usuario(int id, String nom) {
        this.id_tipo_usuario = id;
        this.nombre = nom;
    }
    //Getters y Setters
    public int getId_tipo_usuario() {
        return id_tipo_usuario;
    }
    public void setId_tipo_usuario(int id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
