package sistemabibliotecario.modelo.pojo;

public class Titulo {
    private int idRecursoDocumental;
    private int idTipoRecurso;
    private String tipoRecurso;
    private String titulo;

    public Titulo() {
    }

    public Titulo(int idRecursoDocumental, int idTipoRecurso, String tipoRecurso, String titulo) {
        this.idRecursoDocumental = idRecursoDocumental;
        this.idTipoRecurso = idTipoRecurso;
        this.tipoRecurso = tipoRecurso;
        this.titulo = titulo;
    }

    public int getIdRecursoDocumental() {
        return idRecursoDocumental;
    }

    public void setIdRecursoDocumental(int idRecursoDocumental) {
        this.idRecursoDocumental = idRecursoDocumental;
    }

    public int getIdTipoRecurso() {
        return idTipoRecurso;
    }

    public void setIdTipoRecurso(int idTipoRecurso) {
        this.idTipoRecurso = idTipoRecurso;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }
    
}
