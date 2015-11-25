package Classes;

// @author Otaku
public class Proyecto {

    private Integer id;
    private String titulo;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    private int maxEmple;

    public Proyecto() {
    }

    public Proyecto(String titulo, String fechaInicio, String fechaFin, String descripcion, int maxEmple) {
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.maxEmple = maxEmple;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMaxEmple() {
        return this.maxEmple;
    }

    public void setMaxEmple(int maxEmple) {
        this.maxEmple = maxEmple;
    }

}
