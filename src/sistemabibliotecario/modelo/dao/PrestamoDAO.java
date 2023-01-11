/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import sistemabibliotecario.modelo.ConexionBD;
import java.sql.Types;
import sistemabibliotecario.modelo.pojo.PrestamoInterbibliotecario;
import sistemabibliotecario.modelo.pojo.RecursoDocumental;
import sistemabibliotecario.modelo.pojo.ResultadoOperacion;
import sistemabibliotecario.modelo.pojo.Usuario;

/**
 *
 * @author super
 */
public class PrestamoDAO {
    public static ResultadoOperacion realizarPrestamoInterbibliotecario(Usuario usuarioParaPrestamo, RecursoDocumental recursoSeleccionado, int origen) throws SQLException{
        ResultadoOperacion resultado = new ResultadoOperacion();
        
        resultado.setError(true);
        resultado.setNumeroFilasAfectadas(-1);
        
            Connection conexionBD = ConexionBD.abrirConexionBD();
            if (conexionBD != null){
                try {
                    String sentencia = "INSERT INTO prestamo (biblioteca_recurso_recurso_documental_idRecursoDocumental, biblioteca_recurso_biblioteca_idBiblioteca, "
                            + "usuario_idUsuario, fechaInicioPrestamo,fechaFinalizacionPrestamo, fechaEntrega) "
                            + "VALUES (?,?,?,?,?,?)";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setInt(1, recursoSeleccionado.getIdRecurso());
                    prepararSentencia.setInt(2, origen);
                    prepararSentencia.setInt(3, usuarioParaPrestamo.getIdUsuario());
                    Calendar fechaActual = Calendar.getInstance();
                    prepararSentencia.setString(4, PrestamoInterbibliotecario.generarFechaInicioSQL(fechaActual));
                    prepararSentencia.setString(5, PrestamoInterbibliotecario.generarFechaFinalizacionSQL(fechaActual));
                    prepararSentencia.setNull(6, Types.DATE);
                    
                    int numeroFilasAfectadas = prepararSentencia.executeUpdate();
                    if(numeroFilasAfectadas > 0){
                        resultado.setError(false);
                        resultado.setMensaje("El recurso se ha prestado con éxito");
                        resultado.setNumeroFilasAfectadas(numeroFilasAfectadas);
                    }else{
                        resultado.setMensaje("No se pudo registrar el préstamo");
                    }
                }catch(SQLException e){
                    resultado.setMensaje(e.getMessage());
                }
                finally{
                    conexionBD.close();
                }
            }else{
                resultado.setMensaje("No hay conexión a la base de datos");
            }
        return resultado;
    }
}
