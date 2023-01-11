/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import sistemabibliotecario.modelo.ConexionBD;
import sistemabibliotecario.modelo.pojo.Deuda;
import sistemabibliotecario.modelo.pojo.ResultadoOperacion;

/**
 *
 * @author super
 */
public class DeudaDAO {
    public static ArrayList<Deuda> deudasCargadasAUsuario(int idUsuario, int estadoDeuda) throws SQLException{
        ArrayList<Deuda> deudasBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT deuda.idDeuda, deuda.monto, deuda.fechaInicioDeuda, usuario.nombres, usuario.apellidoPaterno, usuario.apellidoMaterno " 
                            +"FROM deuda " 
                            +"INNER JOIN prestamo ON deuda.prestamo_idPrestamo = prestamo.idPrestamo " 
                            +"LEFT JOIN usuario ON prestamo.usuario_idUsuario = usuario.idUsuario " 
                            +"WHERE estado_deuda_idEstado_deuda = ? AND usuario.idUsuario = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setInt(1, estadoDeuda);
                prepararConsulta.setInt(2, idUsuario);
                ResultSet resultadoConsulta = prepararConsulta.executeQuery();
                deudasBD = new ArrayList<>();
                while(resultadoConsulta.next()){
                    Deuda temp = new Deuda();
                    String nombreCompleto = resultadoConsulta.getString("nombres");
                    nombreCompleto = nombreCompleto + " " + resultadoConsulta.getString("apellidoPaterno");
                    nombreCompleto = nombreCompleto + " " + resultadoConsulta.getString("apellidoMaterno");
                    temp.setIdDeuda(resultadoConsulta.getInt("idDeuda"));
                    temp.setNombreEstudiante(nombreCompleto);
                    temp.setMonto(resultadoConsulta.getInt("monto"));
                    Date fechaInicio = resultadoConsulta.getDate("fechaInicioDeuda");
                    temp.setFechaInicio(fechaInicio.toString());
                    deudasBD.add(temp);
                }
            } catch (SQLException e) {
            }finally{
                conexionBD.close();
            }
        }
        return deudasBD;
    }
    
    public static ResultadoOperacion cambiarEstadoDeuda(int idDeuda, int nuevoEstado) throws SQLException{
        ResultadoOperacion resultado = new ResultadoOperacion();
        resultado.setError(true);
        resultado.setNumeroFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "UPDATE deuda SET estado_deuda_idEstado_deuda = ? WHERE idDeuda = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, nuevoEstado);
                prepararSentencia.setInt(2, idDeuda);
                int filasAfectadas = prepararSentencia.executeUpdate();
                if(filasAfectadas > 0){
                    resultado.setError(false);
                    resultado.setMensaje("Éxito en el cambio de estado de deuda");
                }else{
                    resultado.setMensaje("Error en el cambio de estado de deuda");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
            
        }
        
        return resultado;
    }
    
    public static ResultadoOperacion registrarFechaPago(int idDeuda) throws SQLException{
        ResultadoOperacion resultado = new ResultadoOperacion();
        resultado.setError(true);
        resultado.setNumeroFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null){
            String sentencia = "UPDATE deuda SET fechaPago = ? WHERE idDeuda = ?";
            Calendar fechaActual = Calendar.getInstance();
            String fechaSQL = Deuda.generarFechaInicioSQL(fechaActual);
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, fechaSQL);
            prepararSentencia.setInt(2, idDeuda);
            int numeroFilas = prepararSentencia.executeUpdate();
            if(numeroFilas > 0 ){
                resultado.setError(false);
                resultado.setMensaje("El registro del pago de deuda ha sido registrado correctamente");
            }else{
                resultado.setMensaje("El registro del pago de deuda no se pudo registrar");
            }
        }
        return resultado;
    }
}
