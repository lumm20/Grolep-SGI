package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXPagination;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageStudentController implements Initializable {

    @FXML
    MFXFilterComboBox<String> cmbxStudentSearch;

    @FXML
    MFXButton btnSearch;

    @FXML
    MFXButton btnRegisterStudent;

    @FXML
    MFXTableView<String> tblStudents;

    @FXML
    MFXPagination pagination;


    public static ManageStudentController manageStudentController;

    public static ManageStudentController getInstance(){
        return manageStudentController;
    }

    public static void setInstance(ManageStudentController manageStudentController){
        ManageStudentController.manageStudentController = manageStudentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBtnSearchStyles();
        setRegisterStudentButtonStyles();
    }


    private void setBtnSearchStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_MAGNIFY);
        icon.setIconSize(25);

        btnSearch.setGraphic(icon);
        btnSearch.setText("");

        Tooltip tooltip = new Tooltip("Buscar");
        Tooltip.install(btnSearch, tooltip);
    }

    private void setRegisterStudentButtonStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_ACCOUNT_PLUS);
        icon.setIconSize(25);

        btnRegisterStudent.setGraphic(icon);
        btnRegisterStudent.setText("  Registrar Alumno");
        btnRegisterStudent.setContentDisplay(ContentDisplay.LEFT);

        Tooltip tooltip = new Tooltip("Registrar nuevo alumno");
        Tooltip.install(btnRegisterStudent, tooltip);
    }
}
