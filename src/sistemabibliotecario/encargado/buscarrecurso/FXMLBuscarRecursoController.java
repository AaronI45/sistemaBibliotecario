package sistemabibliotecario.encargado.buscarrecurso;

import sistemabibliotecario.modelo.dao.BusquedaDAO;
import sistemabibliotecario.modelo.dao.RecursoDocumentalDAO;
import sistemabibliotecario.modelo.pojo.Busqueda;
import sistemabibliotecario.modelo.pojo.RecursoDocumental;
import sistemabibliotecario.Util.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistemabibliotecario.modelo.pojo.RecursoDocumental;

/**
 * FXML Controller class
 *
 * @author Crisólogo
 */
public class FXMLBuscarRecursoController implements Initializable {

    @FXML
    private TextField tfTerminoBuscar;
    @FXML
    private ComboBox<RecursoDocumental> cbTipoRecurso;
    @FXML
    private ComboBox<Busqueda> cbTipoBusqueda;
    
    private ObservableList<RecursoDocumental> listaTipoRecurso;
    
    private ObservableList<Busqueda> listaTipoBusqueda;
    
    @FXML
    private TableView<RecursoDocumental> tvResutadoBusqueda;
    
    private ObservableList<RecursoDocumental> listaRecursos;
    
    @FXML
    private TableColumn tcTitulo;
    @FXML
    private TableColumn tcAutor;
    @FXML
    private TableColumn tcTema;
    @FXML
    private TableColumn tcColeccion;
    @FXML
    private TableColumn tcClasificacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvResutadoBusqueda.setVisible(false);
        cargarTipoRecurso();
        cargarTipoBusqueda();
    }    

    @FXML
    private void clicBtnBuscar(ActionEvent event) {
        if(validarLlenado()){
            tvResutadoBusqueda.setVisible(true);
            configurarTabla();
            cargarDatosTabla();
        }else{
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor, completar la información solicitada.", Alert.AlertType.WARNING);
        }
    }
    
    private void cargarTipoRecurso(){
        listaTipoRecurso = FXCollections.observableArrayList();
        try {
            ArrayList<RecursoDocumental> tipoRecursoBD = RecursoDocumentalDAO.tipoRecurso();
            listaTipoRecurso.addAll(tipoRecursoBD);
            cbTipoRecurso.setItems(listaTipoRecurso);
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }
    
    private void cargarTipoBusqueda(){
        listaTipoBusqueda = FXCollections.observableArrayList();
        try {
            ArrayList<Busqueda> tipoBusquedaBD = BusquedaDAO.tipoBusqueda();
            listaTipoBusqueda.addAll(tipoBusquedaBD);
            cbTipoBusqueda.setItems(listaTipoBusqueda);
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }
    
    private boolean validarLlenado(){
        if(cbTipoRecurso.getSelectionModel().isEmpty() || cbTipoBusqueda.getSelectionModel().isEmpty() || tfTerminoBuscar.getText().isEmpty()){
            return false;
        }
        
        return true;
    }
    
    private void configurarTabla(){
        tcTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        tcAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        tcTema.setCellValueFactory(new PropertyValueFactory("tema"));
        tcClasificacion.setCellValueFactory(new PropertyValueFactory("clasificacion"));
        tcColeccion.setCellValueFactory(new PropertyValueFactory("coleccion"));
    }
    
    private void cargarDatosTabla(){
        try {
            listaRecursos = FXCollections.observableArrayList();
            String recurso = cbTipoRecurso.getSelectionModel().getSelectedItem().toString();
            String termino = cbTipoBusqueda.getSelectionModel().getSelectedItem().toString();
            if(termino.equals("Autor")){
                termino = "nombreAutor";
            }
            String texto = tfTerminoBuscar.getText();
            ArrayList<RecursoDocumental> recursosBD = RecursoDocumentalDAO.obtenerRecurso(recurso, termino, texto);
            if(!recursosBD.isEmpty()){
                listaRecursos.addAll(recursosBD);
                tvResutadoBusqueda.setItems(listaRecursos);
            }else{
                Utilidades.mostrarAlertaSimple("No se encuentran recursos", "No se encuentran recursos en la base de datos con esas especificaciones.", Alert.AlertType.WARNING);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clicBtnVolver(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioPrincipal = (Stage) tfTerminoBuscar.getScene().getWindow();
        escenarioPrincipal.close();
    }
}
