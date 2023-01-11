/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo.pojo;

/**
 *
 * @author super
 */
public class Staff {
    private int idStaff;
    private int idBiblioteca;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int tipoUsuario;
    private byte[] foto;

    public final static int ENCARGADO = 1;
    public final static int SECRETARIO = 2;
    
    public Staff() {
    }

    public Staff(int idStaff, int idBiblioteca, String nombre, String apellidoPaterno, String apellidoMaterno, int tipoUsuario, byte[] foto) {
        this.idStaff = idStaff;
        this.idBiblioteca = idBiblioteca;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.tipoUsuario = tipoUsuario;
        this.foto = foto;
    }

    public int getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(int idStaff) {
        this.idStaff = idStaff;
    }

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
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

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return apellidoPaterno + " " + apellidoMaterno + " " + nombre ;
    }

    
}
