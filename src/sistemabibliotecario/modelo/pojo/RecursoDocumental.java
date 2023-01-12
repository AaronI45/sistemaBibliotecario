package sistemabibliotecario.modelo.pojo;

public class RecursoDocumental {
    private int idRecurso;
    private int codigoBarras;
    private String tipoRecurso;
    private String titulo;
    private String autor;
    private String fechaPublicacion;
    private String descripcion;
    private String clasificacion;
    private String coleccion;
    private String tema;
    private int copias;
    private String estado;
    private int idEstado;
    
    public static final int DISPONIBLE = 1;
    public static final int NO_DISPONIBLE = 2;
    public static final int SUMA_COPIAS = 1;
    public static final int RESTA_COPIAS = 2;
    public static final int CANTIDAD_DONADO = 1;
    public static final int CANTIDAD_DANIADO = 1;

    public RecursoDocumental() {
    }

    public RecursoDocumental(int idRecurso, int codigoBarras, String tipoRecurso, String titulo, String autor, String fechaPublicacion, String descripcion, String clasificacion, String coleccion, String tema, int copias, String estado, int idEstado) {
        this.idRecurso = idRecurso;
        this.codigoBarras = codigoBarras;
        this.tipoRecurso = tipoRecurso;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.descripcion = descripcion;
        this.clasificacion = clasificacion;
        this.coleccion = coleccion;
        this.tema = tema;
        this.copias = copias;
        this.estado = estado;
        this.idEstado = idEstado;
    }

    public int getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(int codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String recurso) {
        this.tipoRecurso = recurso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getColeccion() {
        return coleccion;
    }

    public void setColeccion(String coleccion) {
        this.coleccion = coleccion;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCopias() {
        return copias;
    }

    public void setCopias(int copias) {
        this.copias = copias;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    
    @Override
    public String toString() {
        return tipoRecurso;
    }

}
