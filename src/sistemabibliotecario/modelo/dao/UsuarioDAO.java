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
import sistemabibliotecario.modelo.pojo.Prestamo;
import sistemabibliotecario.modelo.pojo.ResultadoOperacion;
import sistemabibliotecario.modelo.pojo.Usuario;

/**
 *
 * @author super
 */
public class UsuarioDAO {
    public static Usuario obtenerUsuarioPorMatricula(String matricula) throws SQLException{
        Usuario usuarioEncontrado = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null){
            try {
                String consulta = "SELECT idUsuario, nombres, apellidoPaterno, apellidoMaterno, facultad.idFacultad as 'idFacultad', "
                        + "carrera_idCarrera, usuario.estado_academico_idEstado_academico, prestamosVigentes FROM usuario "
                        + "INNER JOIN carrera ON usuario.carrera_idCarrera = carrera.idCarrera "
                        + "INNER JOIN facultad ON carrera.facultad_idFacultad = facultad.idFacultad "
                        + "WHERE matricula = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setString(1, matricula);
                ResultSet resultadoConsulta = prepararConsulta.executeQuery();
                if(resultadoConsulta.next()){
                    usuarioEncontrado = new Usuario();
                    usuarioEncontrado.setIdUsuario(resultadoConsulta.getInt("idUsuario"));
                    usuarioEncontrado.setNombre(resultadoConsulta.getString("nombres"));
                    usuarioEncontrado.setApellidoPaterno(resultadoConsulta.getString("apellidoPaterno"));
                    usuarioEncontrado.setApellidoMaterno(resultadoConsulta.getString("apellidoMaterno"));
                    usuarioEncontrado.setFacultad(resultadoConsulta.getInt("idFacultad"));
                    usuarioEncontrado.setPrestamosVigentes(resultadoConsulta.getInt("prestamosVigentes"));
                    usuarioEncontrado.setEstadoAcademico(resultadoConsulta.getInt("estado_academico_idEstado_academico"));
                }else{
                    Utilidades.mostrarAlertaSimple("No hay coincidencias de usuario", 
                            "No se ha encontrado el usuario con la matricula o número de personal", Alert.AlertType.WARNING);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuarioEncontrado;
    }
    
    public static ResultadoOperacion actualizarCantidadPrestamos(Usuario usuarioActualizar, int cantidadPrestamo){
        ResultadoOperacion resultado = new ResultadoOperacion();
        resultado.setError(true);
        resultado.setNumeroFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "UPDATE usuario SET prestamosVigentes = ? WHERE idUsuario =? ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                int prestamosActuales = usuarioActualizar.getPrestamosVigentes() + Prestamo.CANTIDAD_A_PRESTAR;
                prepararSentencia.setInt(1, prestamosActuales);
                prepararSentencia.setInt(2, usuarioActualizar.getIdUsuario());
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas >0){
                    resultado.setError(false);
                    resultado.setMensaje("Numero de préstamos actualizados");
                    resultado.setNumeroFilasAfectadas(numeroFilas);
                }else{
                    resultado.setMensaje("Error en la actualizacion de cantidad de préstamos");
                }
            } catch (SQLException e) {
            
            }
        }else{
            resultado.setMensaje("No hay conexión con la base de datos");
        }
        return resultado;
    }
}
