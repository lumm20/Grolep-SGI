package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import mx.itson.sgi.dto.AlumnoRegistroDTO;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageStudentController implements Initializable {

    @FXML
    MFXTextField txfStudentSearch;

    @FXML
    MFXButton btnSearch;

    @FXML
    MFXButton btnRegisterStudent;

    @FXML
    MFXLegacyTableView<AlumnoRegistroDTO> tblStudents;

    //instance of the service.
    ServicioAlumnos servicioAlumnos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        starClassInstances();
        setBtnSearchStyles();
        setRegisterStudentButtonStyles();
        loadTable();
    }

    /**
     * Declares all the class required instances.
     */
    private void starClassInstances(){
        servicioAlumnos = ServicioAlumnos.getInstance();
    }

    /**
     * Set the styles for the search button.
     */
    private void setBtnSearchStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_MAGNIFY);
        icon.setIconSize(25);

        btnSearch.setGraphic(icon);
        btnSearch.setText("");

        Tooltip tooltip = new Tooltip("Buscar");
        Tooltip.install(btnSearch, tooltip);
    }

    /**
     * Set the styles for the register button.
     */
    private void setRegisterStudentButtonStyles(){
        FontIcon icon = new FontIcon(MaterialDesign.MDI_ACCOUNT_PLUS);
        icon.setIconSize(25);

        btnRegisterStudent.setGraphic(icon);
        btnRegisterStudent.setText("  Registrar Alumno");
        btnRegisterStudent.setContentDisplay(ContentDisplay.LEFT);

        Tooltip tooltip = new Tooltip("Registrar nuevo alumno");
        Tooltip.install(btnRegisterStudent, tooltip);
    }

    /**
     * Set the styles for the register button.
     */
    private void setTblStudentsStyles(){
        
    }

    /**
     * Sets the gotten data from the service in the table and ads the edit
     * button for each row.
     */
    public void loadTable() {
        // Limpiamos columnas previas
        tblStudents.getColumns().clear();

        // Columna: Matrícula
        TableColumn<AlumnoRegistroDTO, String> colMatricula = new TableColumn<>("Matrícula");
        colMatricula.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMatricula()));

        // Columna: Nombre
        TableColumn<AlumnoRegistroDTO, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

        // Columna: Promedio
        TableColumn<AlumnoRegistroDTO, String> colPromedio = new TableColumn<>("Promedio");
        colPromedio.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPromedio())));

        // Columna: Botón Editar
        TableColumn<AlumnoRegistroDTO, Void> colEditar = new TableColumn<>("Editar");
        colEditar.setCellFactory(param -> new TableCell<>() {
            private final MFXButton btn = new MFXButton();

            {
                FontIcon icon = new FontIcon(MaterialDesign.MDI_PENCIL);
                icon.setIconSize(18);
                btn.setGraphic(icon);
                btn.setStyle("-fx-background-color: transparent;");
                btn.setOnAction(event -> {
                    AlumnoRegistroDTO alumno = getTableView().getItems().get(getIndex());
                    System.out.println("Editando a: " + alumno.getNombre());
                    // Lógica de edición aquí
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        // Agregar columnas a la tabla
        tblStudents.getColumns().addAll(colMatricula, colNombre, colPromedio, colEditar);

        // Llenar datos
        try {
            List<AlumnoRegistroDTO> alumnos = servicioAlumnos.searchCompleteStudent("");
            tblStudents.setItems(FXCollections.observableArrayList(alumnos));
        } catch (Exception e) {
            System.err.println("Error al cargar alumnos: " + e.getMessage());
        }
    }



}
