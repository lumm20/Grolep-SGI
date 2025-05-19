package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import mx.itson.sgi.dto.AlumnoRegistroDTO;
import mx.itson.sgi.dto.BecaDTO;
import mx.itson.sgi.dto.enums.Estatus;
import mx.itson.sgi.dto.enums.Genero;
import mx.itson.sgi.dto.enums.Nivel;
import mx.sgi.presentacion.caches.AlumnoEditarCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import org.controlsfx.control.Notifications;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {
    @FXML
    private MFXTextField txfBadge;

    @FXML
    private MFXTextField txfAverage;

    @FXML
    private MFXTextField txfGrade;

    @FXML
    private MFXTextField txfGroup;

    @FXML
    private MFXTextField txfName;

    @FXML
    private MFXTextField txfPhone;

    @FXML
    private MFXTextField txfMail;

    @FXML
    private MFXTextField txfTutor;


    @FXML
    private MFXComboBox<Estatus> cbStatus;

    @FXML
    private MFXComboBox<Nivel> cbLevel;

    @FXML
    private MFXComboBox<Genero> cbGender;

    @FXML
    private MFXComboBox<BecaDTO> cbScholarship;


    @FXML
    private MFXDatePicker dpBirthDate;


    private ServicioAlumnos servicioAlumnos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInstances();
        loadStatusComboBox();
        loadLevelComboBox();
        loadGenderComboBox();
        loadSchoolarshipComboBox();
        setUpBatchField();
        loadStudentFields();
    }

    private void loadInstances(){
        servicioAlumnos = ServicioAlumnos.getInstance();
    }

    private void loadStatusComboBox(){
        cbStatus.setItems(FXCollections.observableArrayList(Estatus.values()));
    }

    private void loadLevelComboBox(){
        cbLevel.setItems(FXCollections.observableArrayList(Nivel.values()));
    }

    private void loadGenderComboBox(){
        cbGender.setItems(FXCollections.observableArrayList(Genero.values()));
    }

    private void loadSchoolarshipComboBox() {
        BecaDTO beca1 = new BecaDTO("DEPORTIVA", new BigDecimal("20.0"));
        BecaDTO beca2 = new BecaDTO("CIVICA", new BigDecimal("30.0"));
        BecaDTO beca3 = new BecaDTO("SEC", new BigDecimal("20.0"));
        BecaDTO beca4 = new BecaDTO("NINGUNA", new BigDecimal("0.0"));

        ObservableList<BecaDTO> scholarships = FXCollections.observableArrayList(beca1, beca2, beca3, beca4);
        cbScholarship.setItems(scholarships);

        // (Opcional) Mostrar solo el nombre en el combo box
        cbScholarship.setConverter(new StringConverter<>() {
            @Override
            public String toString(BecaDTO beca) {
                return beca != null ? beca.getTipo() : "";
            }

            @Override
            public BecaDTO fromString(String string) {
                return scholarships.stream()
                        .filter(b -> b.getTipo().equalsIgnoreCase(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }


    public void loadStudentFields() {

        AlumnoRegistroDTO student = AlumnoEditarCache.getInstance();

        // TextFields
        txfBadge.setText(student.getMatricula());
        txfName.setText(student.getNombre());
        txfAverage.setText(String.valueOf(student.getPromedio()));
        txfGrade.setText(String.valueOf(student.getGrado()));
        txfGroup.setText(student.getGrupo());
        txfPhone.setText(student.getTelefono());
        txfMail.setText(student.getCorreo());
        txfTutor.setText(student.getTutor());

        // DatePicker
        dpBirthDate.setValue(LocalDate.parse(student.getFechaNacimiento())); // Asegúrate del formato

        Platform.runLater(() -> {
            cbStatus.setValue(student.getEstatus());
            cbLevel.setValue(student.getNivel());
            cbGender.setValue(student.getGenero());
            cbScholarship.setValue(student.getBeca());
        });
    }


    private void setUpBatchField(){
        txfBadge.setDisable(true);
    }


    @FXML
    private void editStudent() {
        try {
            // Construimos el DTO con los datos de la interfaz
            AlumnoRegistroDTO alumno = AlumnoRegistroDTO.builder()
                    .matricula(txfBadge.getText())
                    .nombre(txfName.getText())
                    .promedio(Double.parseDouble(txfAverage.getText()))
                    .grado(Integer.parseInt(txfGrade.getText()))
                    .grupo(txfGroup.getText())
                    .estatus(cbStatus.getValue())
                    .nivel(cbLevel.getValue())
                    .fechaNacimiento(dpBirthDate.getValue().toString()) // Asegúrate del formato
                    .telefono(txfPhone.getText())
                    .genero(cbGender.getValue())
                    .correo(txfMail.getText())
                    .beca(cbScholarship.getValue())
                    .tutor(txfTutor.getText())
                    .build();

            // Aquí puedes llamar a tu servicio para guardar el alumno
            servicioAlumnos.editStudent(alumno);

            MainFrameController mainFrame = MainFrameController.getInstance();
            ManageStudentController controller = (ManageStudentController) mainFrame.getCenter();
            controller.loadTable();

            closeScreen();

            notify("Exito","El Alumno a sido editado con exito");

        } catch (ConexionServidorException e) {
            notify("Error","Error al registrar al alumno");
        }
    }


    private void notify(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }


    private void closeScreen(){
        Stage stage = (Stage) txfBadge.getScene().getWindow(); // Obtener el Stage (ventana) actual
        stage.close(); // Cerrar la ventana de inicio de sesión
    }

}
