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
}
