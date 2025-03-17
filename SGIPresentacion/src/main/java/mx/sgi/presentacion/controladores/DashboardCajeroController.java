package mx.sgi.presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardCajeroController implements Initializable {

    @FXML
    JFXButton btnPagos;

    @FXML
    JFXButton btnRegistros;

    @FXML
    JFXButton btnUniformes;

    @FXML
    JFXButton btnCuenta;

    @FXML
    VBox boxCajero;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FontAwesomeIconView iconPagos = new FontAwesomeIconView(FontAwesomeIcon.CREDIT_CARD);
        //iconPagos.setStyle("-fx-fill: white; -fx-font-size: 18px;"); // Color blanco y tamaño

        //btnPagos.setGraphic(iconPagos);

        //FontAwesomeIconView iconRegistros = new FontAwesomeIconView(FontAwesomeIcon.USER_PLUS);
        //iconPagos.setStyle("-fx-fill: white; -fx-font-size: 18px;"); // Color blanco y tamaño

        //btnRegistros.setGraphic(iconRegistros);

        //FontAwesomeIconView iconUniformes = new FontAwesomeIconView(FontAwesomeIcon.USER);
        //iconPagos.setStyle("-fx-fill: white; -fx-font-size: 18px;"); // Color blanco y tamaño

        //btnUniformes.setGraphic(iconUniformes);

        //FontAwesomeIconView iconCuenta = new FontAwesomeIconView(FontAwesomeIcon.CREDIT_CARD);
        //iconPagos.setStyle("-fx-fill: white; -fx-font-size: 18px;"); // Color blanco y tamaño

        //btnCuenta.setGraphic(iconCuenta);
    }

    @FXML
    public void abirVentanaPagos(){

    }

    @FXML
    public void abirVentanaRegistros(){

    }

    @FXML
    public void abirVentanaUniformes(){

    }

    @FXML
    public void abirVentanaSalirCuenta(){

    }

    public VBox getDashboard(){
        return boxCajero;
    }
}
