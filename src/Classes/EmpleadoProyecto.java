package Classes;

public class EmpleadoProyecto {

    private Integer id;
    private Empleado empleado;
    private Proyecto proyecto;

    public EmpleadoProyecto() {
    }

    public EmpleadoProyecto(Empleado empleado, Proyecto proyecto, String rol) {
        this.empleado = empleado;
        this.proyecto = proyecto;

    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return this.empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Proyecto getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

}
