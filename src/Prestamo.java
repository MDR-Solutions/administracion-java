import java.sql.Date;

public class Prestamo {
		//Atributos
	 	private int id_prestamo;
	    private String dni;
	    private int id_ejemplar;
	    private Date fecha_prestamo, fecha_devolucion;
	    //Constructor
	    public Prestamo(int id, String d, int ej, String fp, String fd) {
	        this.id_prestamo = id;
	        this.dni = d;
	        this.id_ejemplar = ej;
			this.fecha_prestamo = Date.valueOf(fp);
			this.fecha_devolucion = Date.valueOf(fd);
		}
		//Getters y Setters
		public int getId_prestamo() {
			return id_prestamo;
		}
		public void setId_prestamo(int id_prestamo) {
			this.id_prestamo = id_prestamo;
		}
		public String getDni() {
			return dni;
		}
		public void setDni(String dni) {
			this.dni = dni;
		}
		public int getId_ejemplar() {
			return id_ejemplar;
		}
		public void setId_ejemplar(int id_ejemplar) {
			this.id_ejemplar = id_ejemplar;
		}
		public Date getFecha_prestamo() {
			return fecha_prestamo;
		}
		public void setFecha_prestamo(Date fecha_prestamo) {
			this.fecha_prestamo = fecha_prestamo;
		}
		public Date getFecha_devolucion() {
			return fecha_devolucion;
		}
		public void setFecha_devolucion(Date fecha_devolucion) {
			this.fecha_devolucion = fecha_devolucion;
	    }
}
