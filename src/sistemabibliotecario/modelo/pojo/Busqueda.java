package sistemabibliotecario.modelo.pojo;

public class Busqueda {
    private int idBusqueda;
    private String termino;

    public Busqueda() {
    }

    public Busqueda(int idBusqueda, String termino) {
        this.idBusqueda = idBusqueda;
        this.termino = termino;
    }

    public int getIdBusqueda() {
        return idBusqueda;
    }

    public void setIdBusqueda(int idBusqueda) {
        this.idBusqueda = idBusqueda;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    

    @Override
    public String toString() {
        return termino;
    }
    
}
