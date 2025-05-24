package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.FiltroPagoDTO;
import mx.itson.sgi.dto.UsuarioDTO;
import mx.sgi.presentacion.caches.FiltrosCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import mx.sgi.presentacion.servicios.ServicioUsuarios;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReportFiltersController implements Initializable {

    @FXML
    MFXComboBox<AlumnoConsultaDTO> cbStudent;

    @FXML
    MFXTextField txfMinimum;

    @FXML
    MFXTextField txfMaximum;

    @FXML
    MFXComboBox<UsuarioDTO> cbCashier;


    ServicioUsuarios usersService;

    ServicioAlumnos studentsService;

    FiltroPagoDTO filters;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInstances();
        setUpStudentsComboBox();
        setUpUsersComboBox();
        setPreChosenOptions();
    }



    private void loadInstances(){
        usersService = ServicioUsuarios.getInstance();
        studentsService = ServicioAlumnos.getInstance();
        filters = FiltrosCache.getInstance();
    }

    /**
     *
     */
    private  void setUpStudentsComboBox() {
        cbStudent.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                updateStudentOptions();
                cbStudent.show();
            }
        });
    }

    /**
     *
     */
    private void updateStudentOptions() {
        try {
            String name = cbStudent.getText();
            List<AlumnoConsultaDTO> students = studentsService.buscarAlumnos(name);

            ObservableList<AlumnoConsultaDTO> newStudents = FXCollections.observableArrayList(students);
            cbStudent.setItems(newStudents);
        }
        catch (ConexionServidorException e){
            notify("Error", e.getMessage());
        }
    }

    /**
     *
     */
    private  void setUpUsersComboBox() {
        cbCashier.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                updateUserOptions();
                cbCashier.show();
            }
        });
    }

    /**
     *
     */
    private void updateUserOptions() {
        try {
            String name = cbCashier.getText();
            List<UsuarioDTO> users = usersService.getUserByName(name);

            ObservableList<UsuarioDTO> newUsers = FXCollections.observableArrayList(users);
            cbCashier.setItems(newUsers);
        }
        catch (ConexionServidorException e){
            notify("Error", e.getMessage());
        }
    }

    /**
     *
     */
    private void setPreChosenOptions() {
        if (filters.getMontoMinimo() != null){ txfMinimum.setText(String.valueOf(filters.getMontoMaximo()));}
        if (filters.getMontoMaximo() != null){ txfMaximum.setText(String.valueOf(filters.getMontoMinimo()));}

        if (filters.getAlumno() != null){
            AlumnoConsultaDTO student = filters.getAlumno();
            ObservableList<AlumnoConsultaDTO> newStudents = FXCollections.observableArrayList(student);
            cbStudent.setItems(newStudents);

            // Asegura que se ejecute después del renderizado
            Platform.runLater(() -> cbStudent.setValue(student));
        }
        if (filters.getUsuario() != null) {
            UsuarioDTO user = filters.getUsuario();
            ObservableList<UsuarioDTO> newUsers = FXCollections.observableArrayList(user);
            cbCashier.setItems(newUsers);

            // Asegura que se ejecute después del renderizado
            Platform.runLater(() -> cbCashier.setValue(user));
        }
    }

    private void validateEntryFields() throws Exception{

        if (!txfMinimum.getText().isEmpty() &&  !txfMaximum.getText().isEmpty()){
            double minimum = Double.parseDouble(txfMinimum.getText());
            double maximum = Double.parseDouble(txfMaximum.getText());

            if (minimum > maximum){
                throw new Exception("El valor minimo no puede superar al maximo");
            }
            if (minimum < 0){
                throw new Exception("El valor minimo no puede ser negativo");
            }
            if (maximum < 0){
                throw new Exception("El valor maximo no puede ser negativo");
            }
        }
    }

    private void validateEntyFieldsFormat() throws Exception{
        try{
            if (!txfMinimum.getText().isEmpty()){
                Double.parseDouble(txfMinimum.getText());
            }
        }
        catch (NumberFormatException ne){
            throw new Exception("Ingrese un numero valido en el campo de minimo");
        }

        try{
            if (!txfMaximum.getText().isEmpty()){
                Double.parseDouble(txfMaximum.getText());
            }
        }
        catch (NumberFormatException ne){
            throw new Exception("Ingrese un numero valido en el campo de maximo");
        }

    }

    /**
     *
     */
    @FXML
    private void closeScreen(){
        try {
            validateEntryFields();
            validateEntyFieldsFormat();

            FiltroPagoDTO filters = FiltrosCache.getInstance();

            filters.setAlumno(cbStudent.getValue());
            filters.setMontoMinimo(Double.parseDouble(txfMinimum.getText().isEmpty() ? "0.00" : txfMinimum.getText()));
            filters.setMontoMaximo(Double.parseDouble(txfMaximum.getText().isEmpty() ? "0.00" : txfMaximum.getText()));
            filters.setUsuario(cbCashier.getValue());

            Stage stage = (Stage) txfMinimum.getScene().getWindow();
            stage.close();
        }
        catch (Exception ex){
            notify("Error", ex.getMessage());
        }
    }

    /**
     *
     * @param title
     * @param message
     */
    private void notify(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

}
