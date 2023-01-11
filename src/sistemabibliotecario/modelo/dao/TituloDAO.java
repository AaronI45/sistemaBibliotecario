package sistemabibliotecario.modelo.dao;

import sistemabibliotecario.modelo.ConexionBD;
import sistemabibliotecario.modelo.pojo.Titulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TituloDAO {
    public static ArrayList<Titulo> obtenerTitulo() throws SQLException{
        ArrayList<Titulo> tituloBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT idRecursoDocumental, tipoRecurso, titulo FROM tipo_recurso "
                                + "INNER JOIN recurso_documental ON idTipoRecurso = tipo_Recurso_idTipoRecurso";
                PreparedStatement consultaObtenerDatos = conexionBD.prepareStatement(consulta);
                
                ResultSet resultadoConsulta = consultaObtenerDatos.executeQuery();
                
                tituloBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Titulo tituloTemp = new Titulo();
                    tituloTemp.setIdRecursoDocumental(resultadoConsulta.getInt("idRecursoDocumental"));
                    tituloTemp.setTipoRecurso(resultadoConsulta.getString("tipoRecurso"));
                    tituloTemp.setTitulo(resultadoConsulta.getString("titulo"));
                    tituloBD.add(tituloTemp);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return tituloBD;
    }
}
