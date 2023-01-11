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
public class Deuda {
    private int idDeuda;
    private int monto;
    private String nombreEstudiante;
    private String fechaInicio;
    private String fechaPago;

    public static final int PENDIENTE = 1;
    public static final int COMPENSACION_MESES = 1;
    public static final int PAGADO = 2;
    
    public Deuda() {
    }
    
    public Deuda(int idDeuda, int monto, String nombreEstudiante, String fechaInicio, String fechaPago) {
        this.idDeuda = idDeuda;
        this.monto = monto;
        this.nombreEstudiante = nombreEstudiante;
        this.fechaInicio = fechaInicio;
        this.fechaPago = fechaPago;
    }

    public int getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(int idDeuda) {
        this.idDeuda = idDeuda;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
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
}
