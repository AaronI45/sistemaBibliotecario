/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo.pojo;

/**
 *
 * @author super
 */
public class Prestamo {
    protected String fechaInicioPrestamo;
    protected String fechaFinalizacionPrestamo;
    protected String fechaEntrega;

    public static final int CANTIDAD_A_PRESTAR = 1;
    
    public Prestamo() {
    }
    
    public Prestamo(String fechaInicioPrestamo, String fechaFinalizacionPrestamo, String fechaEntrega) {
        this.fechaInicioPrestamo = fechaInicioPrestamo;
        this.fechaFinalizacionPrestamo = fechaFinalizacionPrestamo;
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaInicioPrestamo() {
        return fechaInicioPrestamo;
    }

    public void setFechaInicioPrestamo(String fechaInicioPrestamo) {
        this.fechaInicioPrestamo = fechaInicioPrestamo;
    }

    public String getFechaFinalizacionPrestamo() {
        return fechaFinalizacionPrestamo;
    }

    public void setFechaFinalizacionPrestamo(String fechaFinalizacionPrestamo) {
        this.fechaFinalizacionPrestamo = fechaFinalizacionPrestamo;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
}
