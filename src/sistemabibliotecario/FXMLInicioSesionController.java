/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package sistemabibliotecario;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistemabibliotecario.Util.Utilidades;
import sistemabibliotecario.encargado.FXMLInicioEncargadoController;
import sistemabibliotecario.modelo.dao.StaffDAO;
import sistemabibliotecario.modelo.pojo.Staff;
import sistemabibliotecario.secretario.FXMLInicioSecretarioController;

/**
 *
 * @author super
 */
public class FXMLInicioSesionController implements Initializable {
    
    @FXML
    private Label lbAlertaUsuario;
    @FXML
    private Label lbAlertaPassword;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        String usuario = tfUsuario.getText();
        String password = tfPassword.getText();
        boolean inicioValido = true;
        
        if (usuario.isEmpty()){
           inicioValido = false;
           lbAlertaUsuario.setText("Campo requerido");
        }else{
            lbAlertaUsuario.setText("");
        }
        
        if(password.isEmpty()){
            inicioValido = false;
            lbAlertaPassword.setText("Campo requerido");
        }else{
            lbAlertaPassword.setText("");
        }
        
        if (inicioValido){
            checarCredenciales(usuario, password);            
        }
    }
    
    private void checarCredenciales(String usuario, String password){
        Staff usuarioSesion = null;
        try {
            usuarioSesion = StaffDAO.verificarUsuario(usuario, password);
            int tipoUsuario = usuarioSesion.getTipoUsuario();
            switch(tipoUsuario){
                case Staff.ENCARGADO:
                    irPantallaEncargado(usuarioSesion);
                    break;
                case Staff.SECRETARIO:
                    irPantallaSecretario(usuarioSesion);
                    break;
                default:
                    Utilidades.mostrarAlertaSimple("Credenciales incorrectas", 
                            "El nombre de usuario y/o contraseña es incorrecto", 
                            Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error de conexión", 
                    "No se ha podido establecer conexión con la base de datos", 
                    Alert.AlertType.WARNING);
            e.printStackTrace();
        }
    }
    
    private void irPantallaEncargado(Staff encargado){
        try{
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("encargado/FXMLInicioEncargado.fxml"));
            Parent vista = accesoControlador.load();
            FXMLInicioEncargadoController inicioEncargado = accesoControlador.getController();
            Scene escenaEncargado = new Scene(vista);
            Stage escenario = (Stage) tfUsuario.getScene().getWindow();
            escenario.setScene(escenaEncargado);
            inicioEncargado.inicializarValores(encargado);
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No se puede mostrar la pantalla inicial de encargado", 
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void irPantallaSecretario(Staff secretario){
        try{
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("secretario/FXMLInicioSecretario.fxml"));
            Parent vista = accesoControlador.load();
            FXMLInicioSecretarioController inicioEncargado = accesoControlador.getController();
            Scene escenaSecretario = new Scene(vista);
            Stage escenario = (Stage) tfUsuario.getScene().getWindow();
            escenario.setScene(escenaSecretario);
            inicioEncargado.inicializarValores(secretario);
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No se puede mostrar la pantalla inicial de secretario", 
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
