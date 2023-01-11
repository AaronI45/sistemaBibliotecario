package sistemabibliotecario.modelo.dao;

import sistemabibliotecario.modelo.ConexionBD;
import sistemabibliotecario.modelo.pojo.Busqueda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusquedaDAO {
    public static ArrayList<Busqueda> tipoBusqueda() throws SQLException{
        ArrayList<Busqueda> tipoBusquedaBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT idBusqueda, termino FROM busqueda";
                PreparedStatement consultaObtenerDatos = conexionBD.prepareStatement(consulta);
                
                ResultSet resultadoConsulta = consultaObtenerDatos.executeQuery();
                
                tipoBusquedaBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Busqueda busquedaTemp = new Busqueda();
                    busquedaTemp.setTermino(resultadoConsulta.getString("termino"));
                    tipoBusquedaBD.add(busquedaTemp);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return tipoBusquedaBD;
    }
}
