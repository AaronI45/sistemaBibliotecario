/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo.pojo;

/**
 *
 * @author super
 */
public class Biblioteca {
    int idBiblioteca;
    int idFacultad;
    String nombreFacultad;
    String nombre;
    

    public Biblioteca() {
    }

    public Biblioteca(int idBiblioteca, int idFacultad ,String nombreFacultad, String nombre) {
        this.idBiblioteca = idBiblioteca;
        this.idFacultad = idFacultad;
        this.nombreFacultad = nombreFacultad;
        this.nombre = nombre;
    }

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public int getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(int idFacultad) {
        this.idFacultad = idFacultad;
    }
    
    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public void setNombreFacultad(String nombreFacultad) {
        this.nombreFacultad = nombreFacultad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "- " + nombre;
    }
    
    
}
