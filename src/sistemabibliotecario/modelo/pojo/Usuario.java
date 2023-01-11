/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo.pojo;

/**
 *
 * @author super
 */
public class Usuario {
    public int idUsuario;
    public String matricula;
    public String nombre;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public String carrera;
    public int prestamosVigentes;
    public int estadoAcademico;
    public int facultad;
    
    public final static int MAXIMO_PRESTAMOS = 4;
    public final static int RENOVADO = 1;
    public final static int NO_RENOVADO = 2;
    
    public Usuario() {
    }

    public Usuario(int idUsuario, String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String carrera, int prestamosVigentes, int estadoAcademico, int facultad) {
        this.idUsuario = idUsuario;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.carrera = carrera;
        this.prestamosVigentes = prestamosVigentes;
        this.estadoAcademico = estadoAcademico;
        this.facultad = facultad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getPrestamosVigentes() {
        return prestamosVigentes;
    }

    public void setPrestamosVigentes(int prestamosVigentes) {
        this.prestamosVigentes = prestamosVigentes;
    }

    public int getEstadoAcademico() {
        return estadoAcademico;
    }

    public void setEstadoAcademico(int estadoAcademico) {
        this.estadoAcademico = estadoAcademico;
    }

    public int getFacultad() {
        return facultad;
    }

    public void setFacultad(int facultad) {
        this.facultad = facultad;
    }
    
    
}
