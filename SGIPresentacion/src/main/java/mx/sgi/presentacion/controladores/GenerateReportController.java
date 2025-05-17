package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import java.net.URL;
import java.util.ResourceBundle;

public class GenerateReportController  implements Initializable {

    @FXML
    private MFXDatePicker dpFrom;

    @FXML
    private MFXDatePicker dpTo;

    @FXML
    private MFXButton btnSearch;

    @FXML
    private MFXButton btnAddFilters;

    @FXML
    private MFXButton btnCleanUpFilters;

    @FXML
    private MFXButton btnGeneratePDF;

    @FXML
    private MFXLegacyTableView<String> tblPayments;


    //Global instance methods
    private static GenerateReportController generateReportController;

    public static GenerateReportController getInstance(){
        return generateReportController;
    }

    public static void setInstance(GenerateReportController generateReportController){
        GenerateReportController.generateReportController = generateReportController;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBtnSearchStyles();
        setBtnAddFiltersStyles();
        setBtnCleanUpFiltersStyles();
        setBtnGeneratePDFFiltersStyles();
    }


    private void setBtnSearchStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_MAGNIFY);
        icon.setIconSize(25);
        btnSearch.setGraphic(icon);
        btnSearch.setText("");

        Tooltip tooltip = new Tooltip("Agregar filtros");
        Tooltip.install(btnSearch, tooltip);
    }

    private void setBtnAddFiltersStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_FILTER_OUTLINE);
        icon.setIconSize(25);
        btnAddFilters.setGraphic(icon);
        btnAddFilters.setText("");

        Tooltip tooltip = new Tooltip("Agregar filtros");
        Tooltip.install(btnAddFilters, tooltip);
    }

    private void setBtnCleanUpFiltersStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_FILTER_REMOVE_OUTLINE);
        icon.setIconSize(25);
        btnCleanUpFilters.setGraphic(icon);
        btnCleanUpFilters.setText("");

        Tooltip tooltip = new Tooltip("Quitar todos los filtros");
        Tooltip.install(btnCleanUpFilters, tooltip);
    }

    private void setBtnGeneratePDFFiltersStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_FILE_PDF);
        icon.setIconSize(25);

        btnGeneratePDF.setGraphic(icon);
        btnGeneratePDF.setText("  Generar");
        btnGeneratePDF.setContentDisplay(ContentDisplay.LEFT);

        Tooltip tooltip = new Tooltip("Generar PDF");
        Tooltip.install(btnGeneratePDF, tooltip);
    }

}
