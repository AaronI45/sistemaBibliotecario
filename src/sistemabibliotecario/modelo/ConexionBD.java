/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabibliotecario.modelo;

/**
 *
 * @author super
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import sistemabibliotecario.Util.Utilidades;

public class ConexionBD {
    private static String driver="com.mysql.jdbc.Driver";
    
    private static String bd = "bibliotecafei";
    
    private static String ip = "localhost";
    
    private static String puerto = "3306";
    
    private static String urlConexion = "jdbc:mysql://"+ip+":"+puerto
            +"/"+bd+"?allowPublicKeyRetrieval=true&useSSL=false";
    
    private static String usuario = "ControlEscolarAdm";
    private static String password = "Fun33M0nk335";
    
    public static Connection abrirConexionBD(){
        Connection conexionBD = null;
        try {
            Class.forName(driver);
            conexionBD = DriverManager.getConnection(urlConexion, usuario, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            Utilidades.mostrarAlertaSimple("Error de conexión", "No hay conexión a la base de datos",
                    Alert.AlertType.WARNING);
            e.printStackTrace();
        }
        return conexionBD;
    }
}