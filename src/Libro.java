public class Libro {
	//Atributos
	private int id_libro;
	private String titulo;
	private int copias;
    private String isbn;
	//Constructor
    public Libro(int id, String t, int c, String i) {
        this.id_libro = id;
        this.titulo = t;
        this.copias = c;
        this.isbn = i;
    }
	//Getters y Setters
	public int getId_libro() {
		return id_libro;
	}
	public void setId_libro(int id_libro) {
		this.id_libro = id_libro;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getCopias() {
		return copias;
	}
	public void setCopias(int copias) {
		this.copias = copias;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}
