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
import sistemabibliotecario.modelo.ConexionBD;
import sistemabibliotecario.modelo.pojo.Facultad;

/**
 *
 * @author super
 */
public class FacultadDAO {
        public static ArrayList<Facultad> obtenerFacultades() throws SQLException{
        ArrayList<Facultad> facultades = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT idFacultad, nombre FROM facultad";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultadoConsulta = prepararConsulta.executeQuery();
                facultades = new ArrayList<>();
                while(resultadoConsulta.next()){
                    Facultad fac = new Facultad();
                    fac.setIdFacultad(resultadoConsulta.getInt("idFacultad"));
                    fac.setNombre(resultadoConsulta.getString("nombre"));
                    facultades.add(fac);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return facultades;
    }
}
