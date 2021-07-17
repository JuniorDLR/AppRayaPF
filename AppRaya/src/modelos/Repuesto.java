
package modelos;


public class Repuesto {
    
    private int id;
    private String nombreRepuesto;
    private String descripcion;
    private float precio;
    private int existencia;
    private int estado;

    public Repuesto() {
    }

    public Repuesto(int id, String nombreRepuesto, String descripcion, float precio, int existencia, int estado) {
        this.id = id;
        this.nombreRepuesto = nombreRepuesto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencia = existencia;
        this.estado = estado;
    }

    public boolean verificarCodigoR(int codigo)
    {
        return this.id == codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreRepuesto() {
        return nombreRepuesto;
    }

    public void setNombreRepuesto(String nombreRepuesto) {
        this.nombreRepuesto = nombreRepuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Repuesto{" + "id=" + id + ", nombreRepuesto=" + nombreRepuesto + ", descripcion=" + descripcion + ", precio=" + precio + ", estado=" + estado + ", existencia=" + existencia + '}';
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

   
 }
