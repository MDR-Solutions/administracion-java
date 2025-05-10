public class Ejemplar {
    //Atributos
	private int id_ejemplar;
    private String estado_fisico;
    private int id_libro;
    //Constructor
    public Ejemplar(int id, String estado, int libro) {
        this.id_ejemplar = id;
        this.estado_fisico = estado;
        this.id_libro = libro;
    }
    //Getters y Setters
    public int getIdEjemplar() {
        return id_ejemplar;
    }
    public void setIdEjemplar(int id) {
        this.id_ejemplar = id;
    }
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
}
