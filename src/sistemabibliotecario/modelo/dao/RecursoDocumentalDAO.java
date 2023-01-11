package sistemabibliotecario.modelo.dao;

import sistemabibliotecario.modelo.ConexionBD;
import sistemabibliotecario.modelo.pojo.RecursoDocumental;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import sistemabibliotecario.Util.Utilidades;
import sistemabibliotecario.modelo.pojo.ResultadoOperacion;

public class RecursoDocumentalDAO {
    public static ArrayList<RecursoDocumental> obtenerRecursoPorBiblioteca (int idBiblioteca) throws SQLException{
        ArrayList<RecursoDocumental> recursoBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT recurso_documental_idRecursoDocumental, biblioteca_idBiblioteca, biblioteca.nombreBiblioteca, "
                        +"estado_idEstado, estado.estado, copias, recurso_documental.tema, recurso_documental.coleccion, "
                        +"recurso_documental.tipo_Recurso_idTipoRecurso as 'idTipoRecurso',  tipo_recurso.tipoRecurso ,recurso_documental.titulo, "
                        +"recurso_documental.clasificacion, recurso_documental.codigoBarras, recurso_documental.nombreAutor, recurso_documental.clasificacion "
                        +"FROM biblioteca_recurso "
                        +"INNER JOIN biblioteca ON biblioteca_recurso.biblioteca_idBiblioteca = biblioteca.idBiblioteca "
                        +"INNER JOIN estado ON estado_idEstado = estado.idEstado "
                        +"LEFT JOIN recurso_documental ON biblioteca_recurso.recurso_documental_idRecursoDocumental = recurso_documental.idRecursoDocumental "
                        +"LEFT JOIN tipo_recurso ON recurso_documental.tipo_Recurso_idTipoRecurso = tipo_recurso.idTipoRecurso "
                        +"WHERE biblioteca_recurso.biblioteca_idBiblioteca = ?";
                PreparedStatement consultaObtenerDatos = conexionBD.prepareStatement(consulta);
                
                consultaObtenerDatos.setInt(1, idBiblioteca);
                ResultSet resultadoConsulta = consultaObtenerDatos.executeQuery();
                
                recursoBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    RecursoDocumental recursoTemp = new RecursoDocumental();
                    recursoTemp.setIdRecurso(resultadoConsulta.getInt("recurso_documental_idRecursoDocumental"));
                    recursoTemp.setCodigoBarras(resultadoConsulta.getInt("codigoBarras"));
                    recursoTemp.setTipoRecurso(resultadoConsulta.getString("tipoRecurso"));
                    recursoTemp.setTitulo(resultadoConsulta.getString("titulo"));
                    recursoTemp.setAutor(resultadoConsulta.getString("nombreAutor"));
                    recursoTemp.setTema(resultadoConsulta.getString("tema"));
                    recursoTemp.setClasificacion(resultadoConsulta.getString("clasificacion"));
                    recursoTemp.setColeccion(resultadoConsulta.getString("coleccion"));
                    recursoTemp.setCopias(resultadoConsulta.getInt("copias"));
                    recursoTemp.setEstado(resultadoConsulta.getString("estado"));
                    recursoTemp.setIdEstado(resultadoConsulta.getInt("estado_idEstado"));
                    recursoBD.add(recursoTemp);
                }
            } catch (SQLException s) {
                Utilidades.mostrarAlertaSimple("Error de conexión", "No hay conexión con la base de datos", 
                    Alert.AlertType.ERROR);
                s.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de conexión", "No hay conexión con la base de datos", 
                    Alert.AlertType.ERROR);
        }
        return recursoBD;
    }
    
    public static ResultadoOperacion editarCopias(RecursoDocumental recursoAEditar,int idBiblioteca, int cantidad, int tipoEdicion) throws SQLException{
        ResultadoOperacion resultadoEdicion = new ResultadoOperacion();
        
        resultadoEdicion.setError(true);
        resultadoEdicion.setNumeroFilasAfectadas(-1);
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "UPDATE biblioteca_recurso SET copias = ? WHERE biblioteca_idBiblioteca = ? "
                        + "AND recurso_documental_idRecursoDocumental = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                int numeroCopiasNuevo = 0;
                switch(tipoEdicion){
                    case RecursoDocumental.SUMA_COPIAS:
                        numeroCopiasNuevo = recursoAEditar.getCopias() + cantidad;
                        break;
                    case RecursoDocumental.RESTA_COPIAS:
                        numeroCopiasNuevo = recursoAEditar.getCopias() - cantidad;
                        break;
                }
                prepararSentencia.setInt(1, numeroCopiasNuevo);
                prepararSentencia.setInt(2, idBiblioteca);
                prepararSentencia.setInt(3, recursoAEditar.getIdRecurso());
                
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas >0){
                    resultadoEdicion.setMensaje("El número de copias se ha actualizado");
                    resultadoEdicion.setError(false);
                    resultadoEdicion.setNumeroFilasAfectadas(numeroFilas);
                    if(numeroCopiasNuevo == 0){
                        cambiarEstadoRecurso(recursoAEditar, idBiblioteca, RecursoDocumental.NO_DISPONIBLE);
                    }
                }else{
                    resultadoEdicion.setMensaje("No se pudo editar el número de copias");
                }
            } catch (SQLException e) {
                resultadoEdicion.setMensaje(e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            resultadoEdicion.setMensaje("No hay conexión a la base de datos");
        }
        return resultadoEdicion;
    }

    public static void cambiarEstadoRecurso(RecursoDocumental recursoAEditar, int biblioteca, int estado) throws SQLException{
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE biblioteca_recurso SET estado_idEstado = ? "
                        + "WHERE recurso_documental_idRecursoDocumental = ? AND biblioteca_idBiblioteca = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, estado);
                prepararSentencia.setInt(2, recursoAEditar.getIdRecurso());
                prepararSentencia.setInt(3, biblioteca);
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas < 0){
                    Utilidades.mostrarAlertaSimple("Recurso Documental no disponible", 
                            "El recurso documental ya no cuenta con copias disponibles", Alert.AlertType.ERROR);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
    }
    
    public static ResultadoOperacion registrarRecursoDaniadoPorIdRecurso(int idRecursoDocuemental, int idBiblioteca) throws SQLException{
        ResultadoOperacion resultado = new ResultadoOperacion();
        resultado.setError(true);
        resultado.setNumeroFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "INSERT INTO recurso_daniado (biblioteca_recurso_recurso_documental_idRecursoDocumental "
                        + ",biblioteca_recurso_biblioteca_idBiblioteca) "
                        + "VALUES (?,?) ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idRecursoDocuemental);
                prepararSentencia.setInt(2, idBiblioteca);
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas >0){
                    resultado.setMensaje("El recurso documental ha sido registrado como dañado");
                }else{
                    resultado.setMensaje("El recurso documental no se ha podido registrar como dañado");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                conexionBD.close();
            }
        }
        return resultado;
    }
    
    public static ArrayList<RecursoDocumental> obtenerRecurso(String recurso, String termino, String texto) throws SQLException{
        ArrayList<RecursoDocumental> recursoBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT idRecursoDocumental, tipo_recurso.tipoRecurso, titulo, nombreAutor, tema, clasificacion, coleccion "
                                + "FROM recurso_documental INNER JOIN tipo_recurso ON tipo_recurso.idTipoRecurso = recurso_documental.tipo_Recurso_idTipoRecurso "
                                + "LEFT JOIN biblioteca_recurso ON biblioteca_recurso.recurso_documental_idRecursoDocumental = recurso_documental.idRecursoDocumental "
                                + "WHERE tipoRecurso = ? AND biblioteca_recurso.biblioteca_idBiblioteca = 1 AND " + termino + " LIKE ?";
                PreparedStatement consultaObtenerDatos = conexionBD.prepareStatement(consulta);
                
                consultaObtenerDatos.setString(1, recurso);
                consultaObtenerDatos.setString(2, "%" + texto + "%");
                
                ResultSet resultadoConsulta = consultaObtenerDatos.executeQuery();
                recursoBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    RecursoDocumental recursoTemp = new RecursoDocumental();
                    recursoTemp.setTipoRecurso(resultadoConsulta.getString("tipoRecurso"));
                    recursoTemp.setTitulo(resultadoConsulta.getString("titulo"));
                    recursoTemp.setAutor(resultadoConsulta.getString("nombreAutor"));
                    recursoTemp.setTema(resultadoConsulta.getString("tema"));
                    recursoTemp.setClasificacion(resultadoConsulta.getString("clasificacion"));
                    recursoTemp.setColeccion(resultadoConsulta.getString("coleccion"));
                    recursoBD.add(recursoTemp);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return recursoBD;
    }
    
    public static ArrayList<RecursoDocumental> tipoRecurso() throws SQLException{
        ArrayList<RecursoDocumental> tipoRecursoBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT idTipoRecurso, tipoRecurso FROM tipo_recurso";
                PreparedStatement consultaObtenerDatos = conexionBD.prepareStatement(consulta);
                
                ResultSet resultadoConsulta = consultaObtenerDatos.executeQuery();
                
                tipoRecursoBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    RecursoDocumental recursoTemp = new RecursoDocumental();
                    recursoTemp.setTipoRecurso(resultadoConsulta.getString("tipoRecurso"));
                    tipoRecursoBD.add(recursoTemp);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return tipoRecursoBD;
    }
}
