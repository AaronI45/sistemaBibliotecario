/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemabibliotecario.encargado.registrarrecursodaniado;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistemabibliotecario.modelo.dao.RecursoDocumentalDAO;
import sistemabibliotecario.modelo.pojo.RecursoDocumental;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLRegistrarRecursoDaniadoController implements Initializable {

    int idBiblioteca = -1;
    ObservableList<RecursoDocumental> listaRecursos;
    @FXML
    private TableColumn<?, ?> colCodigoBarras;
    @FXML
    private TableColumn<?, ?> colTitulo;
    @FXML
    private TableColumn<?, ?> colAutor;
    @FXML
    private TableColumn<?, ?> colTipoRecurso;
    @FXML
    private TableColumn<?, ?> colEstado;
    @FXML
    private TextField tfTitiulo;
    @FXML
    private TableView<RecursoDocumental> tvRecursos;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    
    
    @FXML
    private void clicRegistrar(ActionEvent event) {
        
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        cerrarVentana();
    }
    
    public void inicializarIdBiblioteca(int idBiblioteca){
        this.idBiblioteca = idBiblioteca;
    }
    
        private void configurarTabla(){
        colCodigoBarras.setCellValueFactory(new PropertyValueFactory("codigoBarras"));
        colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        colTipoRecurso.setCellValueFactory(new PropertyValueFactory("tipoRecurso"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    public void cargarDatosTabla(){
        try {
            listaRecursos = FXCollections.observableArrayList();
            ArrayList<RecursoDocumental> recursosBD = RecursoDocumentalDAO.obtenerRecursoPorBiblioteca(idBiblioteca);
            listaRecursos.addAll(recursosBD);
            tvRecursos.setItems(listaRecursos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private RecursoDocumental verificarSeleccionRecurso(){
        int filaSeleccionada = tvRecursos.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0)? listaRecursos.get(filaSeleccionada) : null;
    }
    
    public void inicializarBusquedaRecurso(){
        if(listaRecursos.size() > 0){
            FilteredList<RecursoDocumental> filtroDato = new FilteredList<>(listaRecursos, p -> true);
            
            tfTitiulo.textProperty().addListener(new ChangeListener<String>(){
                
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

    private void cerrarVentana(){
        Stage escenarioPrincipal = (Stage)tfTitiulo.getScene().getWindow();
        escenarioPrincipal.close();
    }
}
