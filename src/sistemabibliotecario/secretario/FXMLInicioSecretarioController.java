/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemabibliotecario.secretario;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemabibliotecario.Util.Utilidades;
import sistemabibliotecario.modelo.pojo.Staff;
import sistemabibliotecario.secretario.registrarcobro.FXMLRegistroCobroController;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLInicioSecretarioController implements Initializable {

    Staff secretario;
    @FXML
    private ImageView imvFotoUsuario;
    @FXML
    private Label lbNombreUsuario;
    @FXML
    private AnchorPane panePerfil;
    @FXML
    private AnchorPane paneUsuarios;
    @FXML
    private Label lbNombre;
    @FXML
    private Button botonPerfil;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void inicializarValores(Staff secretario){
        this.secretario = secretario;
        cargarValores();
    }
    
    public void cargarValores(){
        lbNombreUsuario.setText(secretario.toString());
        lbNombre.setText(secretario.toString());
        botonPerfil.requestFocus();
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(secretario.getFoto());
            Image imagenUsuario = new Image(input);
            imvFotoUsuario.setImage(imagenUsuario);
        } catch (Exception e) {
        
        }
    }

    @FXML
    private void clicCerrarSesion(ActionEvent event) {
        boolean validarCierreSesion = Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", 
                "¿Está seguro de cerrar la sesión?");
        if (validarCierreSesion){
            try {
            Parent vista = FXMLLoader.load(getClass().getResource("/sistemabibliotecario/FXMLInicioSesion.fxml"));
            Scene escenaInicioSesion = new Scene(vista);
            Stage stagePrincipal = (Stage) imvFotoUsuario.getScene().getWindow();
            stagePrincipal.setScene(escenaInicioSesion);
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", "No se puede mostrar la pantalla de inicio de sesión", 
                        Alert.AlertType.ERROR);;
            }
            
        }
        
    }

    @FXML
    private void clicPerfil(ActionEvent event) {
        esconderPanes();
        panePerfil.setVisible(true);
        panePerfil.toFront();
    }

    @FXML
    private void clicUsuarios(ActionEvent event) {
        esconderPanes();
        paneUsuarios.setVisible(true);
        paneUsuarios.toFront();
    }

    private void esconderPanes(){
        panePerfil.setVisible(false);
        paneUsuarios.setVisible(false);
    }

    @FXML
    private void clicRegistrarCobroAdeudo(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("registrarcobro/FXMLRegistroCobro.fxml"));
            Parent vista = accesoControlador.load();
            FXMLRegistroCobroController registroCobro = accesoControlador.getController();
            registroCobro.inicializarIdBiblioteca(secretario.getIdBiblioteca());
            Scene escenaCobro = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setScene(escenaCobro);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
