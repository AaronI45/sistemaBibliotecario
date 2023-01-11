/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistemabibliotecario.Util.Utilidades;
import sistemabibliotecario.modelo.ConexionBD;
import sistemabibliotecario.modelo.pojo.Biblioteca;

/**
 *
 * @author super
 */
public class BibliotecaDAO {
    public static ArrayList<Biblioteca>recuperarBibliotecas() throws SQLException{
        ArrayList<Biblioteca> bibliotecas= null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null){
            try{
                String consulta = "SELECT biblioteca.idBiblioteca, biblioteca.nombreBiblioteca, "
                        + "biblioteca.facultad_idFacultad, facultad.nombre as 'nombreFacultad' "
                        + "FROM biblioteca INNER JOIN facultad ON biblioteca.facultad_idFacultad = facultad.idFacultad";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = prepararConsulta.executeQuery();
                bibliotecas = new ArrayList<>();
                while (resultadoConsulta.next()){
                    Biblioteca bibliotecaAux = new Biblioteca();
                    bibliotecaAux.setIdBiblioteca(resultadoConsulta.getInt("idBiblioteca"));
                    bibliotecaAux.setIdFacultad(resultadoConsulta.getInt("facultad_idFacultad"));
                    bibliotecaAux.setNombre(resultadoConsulta.getString("nombreBiblioteca"));
                    bibliotecaAux.setNombreFacultad(resultadoConsulta.getString("nombreFacultad"));
                    bibliotecas.add(bibliotecaAux);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                conexionBD.close();
            }
        }
        return bibliotecas;
    }
}
