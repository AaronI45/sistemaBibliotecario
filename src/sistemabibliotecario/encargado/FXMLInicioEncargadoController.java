/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemabibliotecario.encargado;

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
import sistemabibliotecario.encargado.prestamointerbibliotecario.FXMLSeleccionPrestamoInterbibliotecarioController;
import sistemabibliotecario.encargado.registrarrecursodaniado.FXMLRegistrarRecursoDaniadoController;
import sistemabibliotecario.encargado.registrarrecursodonado.FXMLRegistrarRecursoDonadoController;
import sistemabibliotecario.modelo.pojo.Staff;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLInicioEncargadoController implements Initializable {

    Staff encargado;
    @FXML
    private ImageView imvFotoUsuario;
    @FXML
    private Label lbNombreUsuario;
    @FXML
    private AnchorPane panePerfil;
    @FXML
    private AnchorPane paneUsuarios;
    @FXML
    private AnchorPane paneRecursos;
    @FXML
    private AnchorPane paneReportes;
    @FXML
    private Label lbNombre;
    @FXML
    private Button botonPerfil;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void inicializarValores(Staff encargado){
        this.encargado = encargado;
        cargarValores();
    }
    
    public void cargarValores(){
        lbNombreUsuario.setText(encargado.toString());
        lbNombre.setText(encargado.toString());
        botonPerfil.requestFocus();
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(encargado.getFoto());
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
    private void clicRecursos(ActionEvent event) {
        esconderPanes();
        paneRecursos.setVisible(true);
        paneRecursos.toFront();
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

    @FXML
    private void clicReportes(ActionEvent event) {
        esconderPanes();
        paneReportes.setVisible(true);
        paneReportes.toFront();
    }

    @FXML
    private void clicBuscarRecurso(ActionEvent event) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("buscarrecurso/FXMLBuscarRecurso.fxml"));
            Scene escenaBuscar = new Scene (vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setScene(escenaBuscar);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (Exception e) {
                Utilidades.mostrarAlertaSimple("Error", "Error al cargar la ventana de buscar recurso", 
                        Alert.AlertType.ERROR);
                e.printStackTrace();
        }        
    }

    @FXML
    private void clicGenerarPrestamoInterbibliotecario(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("prestamointerbibliotecario/FXMLSeleccionPrestamoInterbibliotecario.fxml"));
            Parent vista = accesoControlador.load();
            FXMLSeleccionPrestamoInterbibliotecarioController seleccionPrestamo = accesoControlador.getController();
            seleccionPrestamo.inicializarIdBiblioteca(encargado.getIdBiblioteca());
            Scene escenaPrestamo = new Scene (vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setScene(escenaPrestamo);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (Exception e) {
                Utilidades.mostrarAlertaSimple("Error", "Error al cargar la ventana prestamo interbibliotecario", 
                        Alert.AlertType.ERROR);
                e.printStackTrace();
        }
    }

    @FXML
    private void clicRegistrarRecursoDonado(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("registrarrecursodonado/FXMLRegistrarRecursoDonado.fxml"));
            Parent vista = accesoControlador.load();
            FXMLRegistrarRecursoDonadoController registrarDonacion = accesoControlador.getController();
            registrarDonacion.inicializarIdBiblioteca(encargado.getIdBiblioteca());
            registrarDonacion.cargarDatosTabla();
            registrarDonacion.inicializarBusquedaRecurso();
            Scene escenaRegistrarRecursoDonado = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setScene(escenaRegistrarRecursoDonado);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (Exception e) {
                Utilidades.mostrarAlertaSimple("Error", "Error al cargar la ventana registrar recurso donado", 
                        Alert.AlertType.ERROR);
        }        
    }

    @FXML
    private void clicRegistrarRecursoDaniado(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("registrarrecursodaniado/FXMLRegistrarRecursoDaniado.fxml"));
            Parent vista = accesoControlador.load();
            FXMLRegistrarRecursoDaniadoController registrarDanio = accesoControlador.getController();
            registrarDanio.inicializarIdBiblioteca(encargado.getIdBiblioteca());
            registrarDanio.cargarDatosTabla();
            registrarDanio.inicializarBusquedaRecurso();
            Scene escenaRegistrarRecursoDaniado = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setScene(escenaRegistrarRecursoDaniado);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        } catch (Exception e) {
        
        }
        
    }
    
    private void esconderPanes(){
        panePerfil.setVisible(false);
        paneRecursos.setVisible(false);
        paneReportes.setVisible(false);
        paneUsuarios.setVisible(false);
    }
    
}
