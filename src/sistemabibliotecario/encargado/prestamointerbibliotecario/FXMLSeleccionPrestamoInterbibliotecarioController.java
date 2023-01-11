/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemabibliotecario.encargado.prestamointerbibliotecario;

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
import javafx.stage.Stage;
import sistemabibliotecario.Util.Utilidades;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLSeleccionPrestamoInterbibliotecarioController implements Initializable {

    @FXML
    private Button botonVolver;

    private int idBiblioteca = -1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicPrestamoOtraBiblioteca(ActionEvent event) {
       try{
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("FXMLPrestamoABiblioteca.fxml"));
            Parent vista = accesoControlador.load();
            FXMLPrestamoABibliotecaController prestamoABiblioteca = accesoControlador.getController();
            prestamoABiblioteca.inicializarIdBiblioteca(idBiblioteca);
            prestamoABiblioteca.cargarDatosTabla(idBiblioteca);
            Scene escenaPrestamoAOtraBiblioteca = new Scene(vista);
            Stage escenario = (Stage) botonVolver.getScene().getWindow();
            escenario.setScene(escenaPrestamoAOtraBiblioteca);
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No se puede mostrar la pantalla inicial de encargado", 
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }       
    }

    @FXML
    private void clicPedirOtraBiblioteca(ActionEvent event) {
        try{
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("FXMLPedirPrestamo.fxml"));
            Parent vista = accesoControlador.load();
            FXMLPedirPrestamoController pedirOtraBiblioteca = accesoControlador.getController();
            pedirOtraBiblioteca.inicializarIdBiblioteca(idBiblioteca);
            pedirOtraBiblioteca.cargarListaDeBibliotecas();
            Scene escenaPedirPrestamoInterbibliotecario = new Scene(vista);
            Stage escenario = (Stage) botonVolver.getScene().getWindow();
            escenario.setScene(escenaPedirPrestamoInterbibliotecario);
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple("Error", "No se puede mostrar la pantalla inicial de encargado", 
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenario = (Stage) botonVolver.getScene().getWindow();
        escenario.close();
    }
    
    public void inicializarIdBiblioteca(int idBiblioteca){
        this.idBiblioteca = idBiblioteca;
    }
}
