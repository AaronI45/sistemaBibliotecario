/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo.pojo;

import java.util.Calendar;

/**
 *
 * @author super
 */
public class PrestamoInterbibliotecario extends Prestamo{
    public String bibliotecaOrigen;
    public String bibliotecaDestino;
    public int idOrigen;
    public int idDestino;

    public static final int DIAS_PRESTAMO_INTERBIBLIOTECARIO = 21;
    private static final int COMPENSACION_MESES = 1;//para aniadir el mes de compensacion en la libreria de java Calendar
            
    public PrestamoInterbibliotecario() {
    }

    public PrestamoInterbibliotecario(String bibliotecaOrigen, String bibliotecaDestino, int idOrigen, int idDestino, String fechaInicioPrestamo, String fechaFinalizacionPrestamo, String fechaEntrega) {
        super(fechaInicioPrestamo, fechaFinalizacionPrestamo, fechaEntrega);
        this.bibliotecaOrigen = bibliotecaOrigen;
        this.bibliotecaDestino = bibliotecaDestino;
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
    }

    public String getBibliotecaOrigen() {
        return bibliotecaOrigen;
    }

    public void setBibliotecaOrigen(String bibliotecaOrigen) {
        this.bibliotecaOrigen = bibliotecaOrigen;
    }

    public String getBibliotecaDestino() {
        return bibliotecaDestino;
    }

    public void setBibliotecaDestino(String bibliotecaDestino) {
        this.bibliotecaDestino = bibliotecaDestino;
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(int idOrigen) {
        this.idOrigen = idOrigen;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
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
    
    public static boolean validarPedidoDePrestamo(int bibliotecaUsuario, int destino){
        boolean esValido = false;
        if (bibliotecaUsuario == destino){
            esValido = true;
        }
        return esValido;
    }
    
    public static String generarFechaInicioSQL(Calendar fechaInicial){
        String fechaInicio = "";
        
        fechaInicio = fechaInicio + Integer.toString(fechaInicial.get(Calendar.YEAR));
        fechaInicio = fechaInicio + "-";
        fechaInicio = fechaInicio + Integer.toString(fechaInicial.get(Calendar.MONTH) + COMPENSACION_MESES);
        fechaInicio = fechaInicio + "-";
        fechaInicio = fechaInicio + Integer.toString(fechaInicial.get(Calendar.DAY_OF_MONTH));
        
        return fechaInicio;
    }
    
    public static String generarFechaFinalizacionSQL(Calendar fechaInicio){
        String fechaFin = "";
        
        fechaInicio.add(Calendar.DAY_OF_MONTH, DIAS_PRESTAMO_INTERBIBLIOTECARIO);
        
        fechaFin = fechaFin + Integer.toString(fechaInicio.get(Calendar.YEAR));
        fechaFin = fechaFin + "-";
        fechaFin = fechaFin + Integer.toString(fechaInicio.get(Calendar.MONTH) + COMPENSACION_MESES);
        fechaFin = fechaFin + "-";
        fechaFin = fechaFin + Integer.toString(fechaInicio.get(Calendar.DAY_OF_MONTH));
        
        return fechaFin;
    }
}
