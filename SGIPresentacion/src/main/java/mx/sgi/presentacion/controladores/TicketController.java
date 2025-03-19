package mx.sgi.presentacion.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketController implements Initializable {

    @FXML
    Label lblTotal;

    @FXML
    Label lblFolio;

    @FXML
    Label lblFecha;

    @FXML
    Label lblCliente;

    @FXML
    Label lblMetodo;

    @FXML
    Label lblMontoVencidos;

    @FXML
    Label  lblAdeudoColegiatura;

    @FXML
    Label lblMontoInscripcion;

    @FXML
    Label lblMontoILibros;

    @FXML
    Label lblMontoIEventos;

    @FXML
    Label lblMontoIAcademias;

    @FXML
    Label lblMontoUniforme;

    @FXML
    Label lblDescuento;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void mostrarDetalles(){
        cargarInfoPago();
    }

    /**
     * aqui se carga cada campo del pago
     */
    private void cargarInfoPago(){

    }

    /**
     *
     */
    @FXML
    public void confirmarPago(){

    }

    /**
     *
     */
    @FXML
    private void cancelar(){
        Stage stage = (Stage) lblTotal.getScene().getWindow(); // Obtener el Stage (ventana) actual
        stage.close(); // Cerrar la ventana de inicio de sesiónS
    }

}
