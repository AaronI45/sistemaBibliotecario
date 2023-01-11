/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemabibliotecario.encargado.prestamointerbibliotecario;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistemabibliotecario.Util.Utilidades;
import sistemabibliotecario.modelo.dao.RecursoDocumentalDAO;
import sistemabibliotecario.modelo.pojo.RecursoDocumental;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLPrestamoABibliotecaController implements Initializable {

    private int idBiblioteca = -1;
    private ObservableList<RecursoDocumental> listaRecursos;
    @FXML
    private TableView<RecursoDocumental> tvRecursos;
    @FXML
    private TableColumn colCodigoBarras;
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colAutor;
    @FXML
    private TableColumn colClasificacion;
    @FXML
    private TextField tfTituloRecurso;
    @FXML
    private TextField tfMatricula;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    
    
    public void inicializarIdBiblioteca (int idBiblioteca){
        this.idBiblioteca = idBiblioteca;
    }
    
    @FXML
    private void clicAgregar(ActionEvent event) {
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        try {
        FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("FXMLSeleccionPrestamoInterbibliotecario.fxml"));
        Parent vista = accesoControlador.load();
        FXMLSeleccionPrestamoInterbibliotecarioController seleccionPrestamo = accesoControlador.getController();
        seleccionPrestamo.inicializarIdBiblioteca(idBiblioteca);
        Scene escenaSeleccionPrestamo = new Scene(vista);
        Stage stagePrincipal = (Stage) tfMatricula.getScene().getWindow();
        stagePrincipal.setScene(escenaSeleccionPrestamo);
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error", "No se puede mostrar la pantalla de inicio de sesión", 
                    Alert.AlertType.ERROR);;
        }
    }
    
    private void configurarTabla(){
        colCodigoBarras.setCellValueFactory(new PropertyValueFactory("codigoBarras"));
        colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        colClasificacion.setCellValueFactory(new PropertyValueFactory("clasificacion"));
    }
    
    public void cargarDatosTabla(int idBiblioteca){
        try {
            listaRecursos = FXCollections.observableArrayList();
            ArrayList<RecursoDocumental> recursoBD = RecursoDocumentalDAO.obtenerRecursoPorBiblioteca(idBiblioteca);
            listaRecursos.addAll(recursoBD);
            tvRecursos.setItems(listaRecursos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cerrarVentana(){
        Stage escenaActual = (Stage) tfMatricula.getScene().getWindow();
        escenaActual.close();
    }    
    
    private void inicializarBusquedaRecurso(){
        if(listaRecursos.size() > 0){
            FilteredList<RecursoDocumental> filtroDato = new FilteredList<>(listaRecursos, p -> true);
            
            tfTituloRecurso.textProperty().addListener(new ChangeListener<String>(){
                
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                    filtroDato.setPredicate(busqueda ->{
                        if (newValue == null || newValue.isEmpty()){
                            return true;
                        }
                    
                        String lowerCaseFilter = newValue.toLowerCase();
                        
                        if(busqueda.getTitulo().toLowerCase().contains(lowerCaseFilter)){
                            return true;
                        }
                        return false;
                    });  

                }
            });
            
            SortedList<RecursoDocumental> sortedData = new SortedList<>(filtroDato);
            sortedData.comparatorProperty().bind(tvRecursos.comparatorProperty());
            tvRecursos.setItems(sortedData);
        }
    }

}
