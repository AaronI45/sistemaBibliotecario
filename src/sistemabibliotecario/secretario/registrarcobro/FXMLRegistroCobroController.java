/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemabibliotecario.secretario.registrarcobro;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistemabibliotecario.Util.Utilidades;
import sistemabibliotecario.modelo.dao.DeudaDAO;
import sistemabibliotecario.modelo.dao.UsuarioDAO;
import sistemabibliotecario.modelo.pojo.Deuda;
import sistemabibliotecario.modelo.pojo.ResultadoOperacion;
import sistemabibliotecario.modelo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLRegistroCobroController implements Initializable {

    private int idBiblioteca;
    private Usuario usuarioACobrar;
    private ObservableList<Deuda> listaDeudas;
    @FXML
    private TableView<Deuda> tvAdeudos;
    @FXML
    private TableColumn<?, ?> colFechaAdeudo;
    @FXML
    private TableColumn<?, ?> colMonto;
    @FXML
    private TableColumn<?, ?> colEstudiante;
    @FXML
    private TextField tfMatricula;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        
    }    
    
    public void inicializarIdBiblioteca(int idBiblioteca){
        this.idBiblioteca = idBiblioteca;
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void clicBuscar(ActionEvent event) {
        String matricula = tfMatricula.getText();
        usuarioACobrar = null;
        if(matricula.isEmpty()){
            Utilidades.mostrarAlertaSimple("Error de búsqueda", "Por favor introduzca la matrícula del alumno a buscar", 
                    Alert.AlertType.ERROR);
        }else{
            try {
                usuarioACobrar = UsuarioDAO.obtenerUsuarioPorMatricula(matricula);
                if (usuarioACobrar != null){
                    cargarDatosTabla();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clicCobrarAdeudo(ActionEvent event) {
        Deuda seleccionDeuda = verificarSeleccionDeuda();
        if (seleccionDeuda != null){
            try {
                ResultadoOperacion resultadoPago = pagarDeuda(seleccionDeuda.getIdDeuda());
                if(!resultadoPago.isError()){
                    Utilidades.mostrarAlertaSimple("Éxito en el pago de deuda", resultadoPago.getMensaje(), 
                            Alert.AlertType.INFORMATION);
                    cerrarVentana();
                }else{
                    Utilidades.mostrarAlertaSimple("Error en el pago de deuda", resultadoPago.getMensaje(), 
                            Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error de selección", "Por favor seleccione un adeudo a cobrar de la tabla", 
                    Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla(){
        colFechaAdeudo.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colMonto.setCellValueFactory(new PropertyValueFactory("monto"));
        colEstudiante.setCellValueFactory(new PropertyValueFactory("nombreEstudiante"));
    }
    
    private void cargarDatosTabla(){
        try {
            listaDeudas = FXCollections.observableArrayList();
            ArrayList<Deuda> deudasBD = DeudaDAO.deudasCargadasAUsuario(usuarioACobrar.getIdUsuario(), Deuda.PENDIENTE);
            if(deudasBD.isEmpty()){
                Utilidades.mostrarAlertaSimple("No hay registro de deudas", "El usuario introducido no cuenta con ninguna deuda pendiente", 
                        Alert.AlertType.INFORMATION);
            }else{
                listaDeudas.addAll(deudasBD);
                tvAdeudos.setItems(listaDeudas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
                
    }
    
    private Deuda verificarSeleccionDeuda(){
        int filaSeleccionada = tvAdeudos.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0)? listaDeudas.get(filaSeleccionada) : null;
    }
    
    private void cerrarVentana(){
        Stage escenario = (Stage) tfMatricula.getScene().getWindow();
        escenario.close();
    }
    
    private ResultadoOperacion pagarDeuda(int idDeuda) throws SQLException{
        ResultadoOperacion resultado = new ResultadoOperacion();
        resultado = DeudaDAO.cambiarEstadoDeuda(idDeuda, Deuda.PAGADO);
        if (!resultado.isError()){
            resultado = DeudaDAO.registrarFechaPago(idDeuda);
        }
        return resultado;
    }
}
