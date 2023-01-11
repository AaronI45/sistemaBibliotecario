package sistemabibliotecario.encargado.registrarrecursodonado;

import sistemabibliotecario.modelo.dao.RecursoDocumentalDAO;
import sistemabibliotecario.modelo.pojo.RecursoDocumental;
import sistemabibliotecario.Util.Utilidades;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistemabibliotecario.modelo.pojo.ResultadoOperacion;


public class FXMLRegistrarRecursoDonadoController implements Initializable {

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
    private TableColumn<?, ?> colClasificacion;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TableView<RecursoDocumental> tvRecursos;
    
   
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    

    @FXML
    private void clicGuardar(ActionEvent event) {
        RecursoDocumental recursoSeleccionado = verificarSeleccionRecurso();
        if (recursoSeleccionado != null){
            boolean confirmarDonacion = Utilidades.mostrarDialogoConfirmacion("Confirmar donación", "¿Está seguro de realizar la donación?");
            if(confirmarDonacion){
                try {
                    ResultadoOperacion resultadoDonacion = 
                    RecursoDocumentalDAO.editarCopias(recursoSeleccionado, idBiblioteca, 
                            RecursoDocumental.CANTIDAD_DONADO, RecursoDocumental.SUMA_COPIAS);
                    if(!resultadoDonacion.isError()){
                        Utilidades.mostrarAlertaSimple("Éxito en el registro de donación", 
                                resultadoDonacion.getMensaje(), Alert.AlertType.INFORMATION);
                        cerrarVentana();
                    }else{
                        Utilidades.mostrarAlertaSimple("Error en el registro de donación", 
                                resultadoDonacion.getMensaje(), Alert.AlertType.ERROR);
                    }
                } catch (SQLException e) {
                    Utilidades.mostrarAlertaSimple("Error de conexión", "No hay ocnexión con la base de datos", 
                            Alert.AlertType.ERROR);
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de selección", "Por favor seleccione un recurso documental para registrar la donación", 
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana(){
        Stage escenario = (Stage) tfTitulo.getScene().getWindow();
        escenario.close();
    }

    public void inicializarIdBiblioteca(int idBiblioteca){
        this.idBiblioteca = idBiblioteca;
    }
    
    private void configurarTabla(){
        colCodigoBarras.setCellValueFactory(new PropertyValueFactory("codigoBarras"));
        colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        colTipoRecurso.setCellValueFactory(new PropertyValueFactory("tipoRecurso"));
        colClasificacion.setCellValueFactory(new PropertyValueFactory("clasificacion"));
    }
    
    public void cargarDatosTabla(){
        try {
            listaRecursos = FXCollections.observableArrayList();
            ArrayList<RecursoDocumental> recursosBD = RecursoDocumentalDAO.obtenerRecursoPorBiblioteca(idBiblioteca);
            listaRecursos.addAll(recursosBD);
            tvRecursos.setItems(listaRecursos);
        } catch (SQLException e) {
        }
    }
    
    private RecursoDocumental verificarSeleccionRecurso(){
        int filaSeleccionada = tvRecursos.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0)? listaRecursos.get(filaSeleccionada) : null;
    }
    
    public void inicializarBusquedaRecurso(){
        if(listaRecursos.size() > 0){
            FilteredList<RecursoDocumental> filtroDato = new FilteredList<>(listaRecursos, p -> true);
            
            tfTitulo.textProperty().addListener(new ChangeListener<String>(){
                
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
