package Classes;

public class Empleado {

    private Integer id;
    private String nombre;
    private String apellido;
    private String nif;
    private String fechaNacimiento;

    public Empleado() {
    }

    public Empleado(String nombre, String apellido, String nif, String fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nif = nif;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNif() {
        return this.nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}
