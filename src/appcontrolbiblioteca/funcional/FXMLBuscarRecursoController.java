/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package appcontrolbiblioteca.funcional;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLBuscarRecursoController implements Initializable {

    @FXML
    private ComboBox<?> cbTipoRecurso;
    @FXML
    private ComboBox<?> cbTipoBusqueda;
    @FXML
    private TextField tfTerminoBuscar;
    @FXML
    private TableView<?> tvResutadoBusqueda;
    @FXML
    private TableColumn<?, ?> tcTitulo;
    @FXML
    private TableColumn<?, ?> tcAutor;
    @FXML
    private TableColumn<?, ?> tcTema;
    @FXML
    private TableColumn<?, ?> tcClasificacion;
    @FXML
    private TableColumn<?, ?> tcColeccion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void clicBtnVolver(ActionEvent event) {
    }
    
}
