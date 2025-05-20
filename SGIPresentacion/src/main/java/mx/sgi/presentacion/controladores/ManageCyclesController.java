package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageCyclesController implements Initializable {

    @FXML
    private MFXDatePicker dpBegin;

    @FXML
    private MFXDatePicker dpEnd;

    @FXML
    private MFXButton btnSearch;

    @FXML
    private MFXButton btnResetFilters;

    @FXML
    private MFXButton btnRegisterCycle;



    private static ManageCyclesController manageCyclesController;

    public static ManageCyclesController getInstance(){
        return manageCyclesController;
    }

    public static void setInstance(ManageCyclesController manageCyclesController){
        ManageCyclesController.manageCyclesController = manageCyclesController;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBtnSearchStyles();
        setBtnResetFiltersStyles();
        setBtnRegisterCycleStyles();
    }

    private void setBtnSearchStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_MAGNIFY);
        icon.setIconSize(25);
        btnSearch.setGraphic(icon);
        btnSearch.setText("");

        Tooltip tooltip = new Tooltip("Buscar ciclo");
        Tooltip.install(btnSearch, tooltip);
    }

    private void setBtnResetFiltersStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_FILTER_REMOVE_OUTLINE);
        icon.setIconSize(25);
        btnResetFilters.setGraphic(icon);
        btnResetFilters.setText("");

        Tooltip tooltip = new Tooltip("Quitar todos los parametros de busqueda");
        Tooltip.install(btnResetFilters, tooltip);
    }

    private void setBtnRegisterCycleStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_ACCOUNT_PLUS);
        icon.setIconSize(25);

        btnRegisterCycle.setGraphic(icon);
        btnRegisterCycle.setText("  Registrar ciclo");
        btnRegisterCycle.setContentDisplay(ContentDisplay.LEFT);

        Tooltip tooltip = new Tooltip("Registrar nuevo ciclo");
        Tooltip.install(btnRegisterCycle, tooltip);
    }

}
