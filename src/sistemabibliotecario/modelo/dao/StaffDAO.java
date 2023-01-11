/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import sistemabibliotecario.Util.Utilidades;
import sistemabibliotecario.modelo.ConexionBD;
import sistemabibliotecario.modelo.pojo.Staff;

/**
 *
 * @author super
 */
public class StaffDAO {
        public static Staff verificarUsuario(String usuario, String password) throws SQLException{
        Staff usuarioSesion = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT idStaff, nombres, apellidoPaterno, biblioteca_idBiblioteca, "
                        + "apellidoMaterno, tipo_Staff_idTipoStaff, foto FROM staff WHERE username = ? and "
                        + "password = ?";
                PreparedStatement consultaLogin = conexionBD.prepareStatement(consulta);
                consultaLogin.setString(1, usuario);
                consultaLogin.setString(2, password);
                ResultSet resultadoConsulta = consultaLogin.executeQuery();
                usuarioSesion = new Staff();
                if(resultadoConsulta.next()){
                    usuarioSesion.setIdStaff(resultadoConsulta.getInt("idStaff"));
                    usuarioSesion.setIdBiblioteca(resultadoConsulta.getInt("biblioteca_idBiblioteca"));
                    usuarioSesion.setNombre(resultadoConsulta.getString("nombres"));
                    usuarioSesion.setApellidoPaterno(resultadoConsulta.getString("apellidoPaterno"));
                    usuarioSesion.setApellidoMaterno(resultadoConsulta.getString("apellidoMaterno"));
                    usuarioSesion.setTipoUsuario(resultadoConsulta.getInt("tipo_Staff_idTipoStaff"));
                    usuarioSesion.setFoto(resultadoConsulta.getBytes("foto"));
                }else{
                    usuarioSesion.setIdStaff(-1);
                }
            } catch (SQLException e) {
                Utilidades.mostrarAlertaSimple("Error de conexión", 
                        "No hay conexión con la base de datos", Alert.AlertType.WARNING);
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return usuarioSesion;
    }
    
        
}
