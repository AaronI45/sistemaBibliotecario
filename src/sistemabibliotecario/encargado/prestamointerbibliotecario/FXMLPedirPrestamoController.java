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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistemabibliotecario.Util.Utilidades;
import sistemabibliotecario.modelo.dao.BibliotecaDAO;
import sistemabibliotecario.modelo.dao.PrestamoDAO;
import sistemabibliotecario.modelo.dao.RecursoDocumentalDAO;
import sistemabibliotecario.modelo.dao.UsuarioDAO;
import sistemabibliotecario.modelo.pojo.Biblioteca;
import sistemabibliotecario.modelo.pojo.Prestamo;
import sistemabibliotecario.modelo.pojo.PrestamoInterbibliotecario;
import sistemabibliotecario.modelo.pojo.RecursoDocumental;
import sistemabibliotecario.modelo.pojo.ResultadoOperacion;
import sistemabibliotecario.modelo.pojo.Usuario;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLPedirPrestamoController implements Initializable {
    private int idBiblioteca = -1;
    @FXML
    private ComboBox<Biblioteca> cbListaBibliotecas;
    @FXML
    private TableView<RecursoDocumental> tvRecursos;

    private ObservableList<Biblioteca> listaBibliotecas;
    private ObservableList<RecursoDocumental> listaRecursos;
    @FXML
    private TableColumn colCodigoBarras;
    @FXML
    private TableColumn colTitulo;
    @FXML
    private TableColumn colAutor;
    @FXML
    private TableColumn colClasificacion;
    @FXML
    private TableColumn colTipoRecurso;
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
        cbListaBibliotecas.valueProperty().addListener(new ChangeListener<Biblioteca>(){
            
            @Override
            public void changed(ObservableValue<? extends Biblioteca> observable, 
                    Biblioteca oldValue, Biblioteca newValue) {
                if(newValue != null){
                    cargarDatosTabla(newValue.getIdBiblioteca());
                    inicializarBusquedaRecurso();
                }
            }
        });
    }    
    
    @FXML
    private void clicVolver(ActionEvent event) {
        try {
            FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("FXMLSeleccionPrestamoInterbibliotecario.fxml"));
            Parent vista = accesoControlador.load();
            FXMLSeleccionPrestamoInterbibliotecarioController seleccionPrestamo = accesoControlador.getController();
            seleccionPrestamo.inicializarIdBiblioteca(idBiblioteca);
            Scene escenaSeleccionPrestamo = new Scene(vista);
            Stage stagePrincipal = (Stage) cbListaBibliotecas.getScene().getWindow();
            stagePrincipal.setScene(escenaSeleccionPrestamo);
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", "No se puede mostrar la pantalla de inicio de sesión", 
                        Alert.AlertType.ERROR);;
            }
    }

    @FXML
    private void clicAgregar(ActionEvent event) {
        RecursoDocumental recursoSeleccionado = verificarSeleccionRecurso();
        if(recursoSeleccionado != null){
            if(validarDisponibilidad(recursoSeleccionado)){
                Usuario usuarioARealizarPrestamo = validarUsuario();
                if(usuarioARealizarPrestamo != null){
                    if (validarNumPrestamosUsuario(usuarioARealizarPrestamo) && validarEstadoUsuario(usuarioARealizarPrestamo)){
                        if (PrestamoInterbibliotecario.validarPedidoDePrestamo(usuarioARealizarPrestamo.getFacultad(), idBiblioteca)){
                            try {
                                ResultadoOperacion resultadoPrestamo = realizarPrestamoInterbibliotecario(usuarioARealizarPrestamo, recursoSeleccionado);
                                if(!resultadoPrestamo.isError()){
                                    Utilidades.mostrarAlertaSimple("Éxito en el registro de préstamo interbibliotecario", 
                                            resultadoPrestamo.getMensaje(), Alert.AlertType.INFORMATION);
                                    cerrarVentana();
                                }else{
                                    Utilidades.mostrarAlertaSimple("Error en el registro de préstamo interbibliotecario", 
                                            resultadoPrestamo.getMensaje(), Alert.AlertType.ERROR);
                                }
                            } catch (SQLException e) {
                                Utilidades.mostrarAlertaSimple("Error de conexión", "No hay conexión a la base de datos", 
                                        Alert.AlertType.ERROR);
                            }
                        }else{
                            Utilidades.mostrarAlertaSimple("Error en el registro de préstamos interbibliotecario", 
                                    "El usuario no se encuentra registrado en la facultad donde se está realizando el préstamo", Alert.AlertType.ERROR);
                        }
                    }
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple("Falta selección de recurso", 
                    "Por favor seleccione un recurso documental a prestar", 
                    Alert.AlertType.ERROR);
        }
    }
    
    public void cargarListaDeBibliotecas(){
        listaBibliotecas = FXCollections.observableArrayList();
        try{
            ArrayList<Biblioteca> bibliotecasBD = BibliotecaDAO.recuperarBibliotecas();
            for (int i=0; i<bibliotecasBD.size();i++){
                if(bibliotecasBD.get(i).getIdBiblioteca() == idBiblioteca){
                    bibliotecasBD.remove(i);
                    break;
                }
            }
            listaBibliotecas.addAll(bibliotecasBD);
            cbListaBibliotecas.setItems(listaBibliotecas);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private void configurarTabla(){
        colCodigoBarras.setCellValueFactory(new PropertyValueFactory("codigoBarras"));
        colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory("autor"));
        colClasificacion.setCellValueFactory(new PropertyValueFactory("clasificacion"));
        colTipoRecurso.setCellValueFactory(new PropertyValueFactory("tipoRecurso"));
    }
    
    private void cargarDatosTabla(int idBiblioteca){
        try {
            listaRecursos = FXCollections.observableArrayList();
            ArrayList<RecursoDocumental> recursoBD = RecursoDocumentalDAO.obtenerRecursoPorBiblioteca(idBiblioteca);
            listaRecursos.addAll(recursoBD);
            tvRecursos.setItems(listaRecursos);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    private void cerrarVentana(){
        Stage escenaActual = (Stage) cbListaBibliotecas.getScene().getWindow();
        escenaActual.close();
    }
    
    private Usuario validarUsuario(){
        String matricula = tfMatricula.getText();
        Usuario usuarioEncontrado = null;
        if (matricula.isEmpty()){
            Utilidades.mostrarAlertaSimple("No se ha introducido el usuario", 
                    "El usuario no ha sido introducido en el campo correspondiente", 
                    Alert.AlertType.ERROR);
        }else{
            try {
                usuarioEncontrado = UsuarioDAO.obtenerUsuarioPorMatricula(matricula);
            } catch (SQLException e) {
                Utilidades.mostrarAlertaSimple("Error de conexión", 
                        "No hay conexión con la base de datos", Alert.AlertType.ERROR);
                cerrarVentana();
            }
        }
        return usuarioEncontrado;
    }
    
    private boolean validarNumPrestamosUsuario(Usuario usuario){
        boolean valido = false;
        if(usuario.getPrestamosVigentes() < Usuario.MAXIMO_PRESTAMOS){
            valido = true;
        }else{
            Utilidades.mostrarAlertaSimple("Exceso de préstamos para el usuario", 
                    "El usuario cuenta con cuatro o más préstamos vigentes", 
                    Alert.AlertType.ERROR);
        }
        return valido;
    }
    
    private boolean validarDisponibilidad (RecursoDocumental recursoSeleccionado){
        boolean disponible = false;
        if(recursoSeleccionado.getIdEstado() == RecursoDocumental.DISPONIBLE){
            disponible = true;
        }else{
            Utilidades.mostrarAlertaSimple("Recurso no disponible", 
                    "El recurso seleccionado no está disponible por el momento", 
                    Alert.AlertType.ERROR);
        }
        return disponible;
    }
    
    private boolean validarEstadoUsuario (Usuario usuario){
        boolean disponible = false;
        if(usuario.getEstadoAcademico() == Usuario.RENOVADO){
            disponible =true;
        }else{
            Utilidades.mostrarAlertaSimple("Usuario no renovado", 
                    "El usuario introducido no se encuentra inscrito al periodo actual", 
                    Alert.AlertType.ERROR);
        }
        return disponible;
    }
    
    private RecursoDocumental verificarSeleccionRecurso(){
        int filaSeleccionada = tvRecursos.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0)? listaRecursos.get(filaSeleccionada) : null;
    }

    private ResultadoOperacion realizarPrestamoInterbibliotecario(Usuario usuarioARealizarPrestamo, RecursoDocumental recursoSeleccionado) throws SQLException{
        int bibliotecaOrigen = cbListaBibliotecas.getSelectionModel().getSelectedItem().getIdBiblioteca();
        ResultadoOperacion resultadoPrestamo = UsuarioDAO.actualizarCantidadPrestamos(usuarioARealizarPrestamo,
                Prestamo.CANTIDAD_A_PRESTAR);
        if (!resultadoPrestamo.isError()){
            resultadoPrestamo = RecursoDocumentalDAO.editarCopias(recursoSeleccionado, bibliotecaOrigen, Prestamo.CANTIDAD_A_PRESTAR, 
                    RecursoDocumental.RESTA_COPIAS);
            if(!resultadoPrestamo.isError()){
                resultadoPrestamo = PrestamoDAO.realizarPrestamoInterbibliotecario(usuarioARealizarPrestamo, recursoSeleccionado, bibliotecaOrigen);
            }
        }
        return resultadoPrestamo;
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
    
    public void inicializarIdBiblioteca(int idBiblioteca){
        this.idBiblioteca = idBiblioteca;
    }
    
}
