package sistemabibliotecario.modelo.pojo;


public class ResultadoOperacion {
    
    private boolean error;
    private String mensaje;
    private int numeroFilasAfectadas;

    public ResultadoOperacion() {
    }

    public ResultadoOperacion(boolean error, String mensaje, int numeroFilasAfectadas) {
        this.error = error;
        this.mensaje = mensaje;
        this.numeroFilasAfectadas = numeroFilasAfectadas;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getNumeroFilasAfectadas() {
        return numeroFilasAfectadas;
    }

    public void setNumeroFilasAfectadas(int numeroFilasAfectadas) {
        this.numeroFilasAfectadas = numeroFilasAfectadas;
    }
    
    
}
