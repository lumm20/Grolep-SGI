package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.util.Duration;
import mx.itson.sgi.dto.CicloConDetallesDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.DetalleCicloDTO;
import mx.sgi.presentacion.caches.CycleWithDetailsCache;
import mx.sgi.presentacion.servicios.ServicioCicloEscolar;
import org.controlsfx.control.Notifications;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditCycleController implements Initializable {

    @FXML
    private MFXDatePicker dpStart;

    @FXML
    private MFXDatePicker dpEnd;

    @FXML
    private MFXTextField txfTuition;

    @FXML
    private MFXTextField txfRegistration;

    @FXML
    private MFXTextField txfBooks;

    @FXML
    private MFXTextField txfAcademies;

    @FXML
    private MFXTextField txfEvents;

    @FXML
    private MFXTextField txfUniform;


    ServicioCicloEscolar cyclesService;

    CicloConDetallesDTO cycleRetrived;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInstances();
        loadCycleFields();
    }

    private void loadInstances(){
        cyclesService = ServicioCicloEscolar.getInstance();
        cycleRetrived = CycleWithDetailsCache.getInstance();
    }

    private void loadCycleFields(){
        System.out.println(cycleRetrived.getCicloEscolar().getFin());

        Platform.runLater(()->{
            dpStart.setValue(LocalDate.parse(cycleRetrived.getCicloEscolar().getInicio()));
            dpEnd.setValue(LocalDate.parse(cycleRetrived.getCicloEscolar().getFin()));
        });

        txfTuition.setText(String.valueOf(cycleRetrived.getDetalleCiclo().getCuotaColegiatura()));
        txfRegistration.setText(String.valueOf(cycleRetrived.getDetalleCiclo().getCuotaInscripcion()));
        txfBooks.setText(String.valueOf(cycleRetrived.getDetalleCiclo().getCuotaLibros()));
        txfAcademies.setText(String.valueOf(cycleRetrived.getDetalleCiclo().getCuotaAcademias()));
        txfEvents.setText(String.valueOf(cycleRetrived.getDetalleCiclo().getCuotaEventos()));
        txfUniform.setText(String.valueOf(cycleRetrived.getDetalleCiclo().getCuotaUniforme()));

    }

    private void validateCycleFields() throws IllegalArgumentException {
        // Verifica fechas
        if (dpStart.getValue() == null) {
            throw new IllegalArgumentException("Debe seleccionar una fecha de inicio.");
        }

        if (dpEnd.getValue() == null) {
            throw new IllegalArgumentException("Debe seleccionar una fecha de fin.");
        }

        // Verifica campos numéricos
        validateNumericField(txfTuition, "cuota de colegiatura");
        validateNumericField(txfRegistration, "cuota de inscripción");
        validateNumericField(txfBooks, "cuota de libros");
        validateNumericField(txfAcademies, "cuota de academias");
        validateNumericField(txfEvents, "cuota de eventos");
        validateNumericField(txfUniform, "cuota de uniforme");
    }

    private void validateNumericField(MFXTextField field, String fieldName) {
        String text = field.getText();
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar la " + fieldName + ".");
        }
        try {
            Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La " + fieldName + " debe ser un número válido.");
        }
    }

    private void validateDateChoose() throws Exception{
//        if (dpStart.getValue().getYear() == dpEnd.getValue().getYear()){
//            throw new
//        }
        if (dpStart.getValue() != null || dpEnd.getValue() != null) {
            if (dpEnd.getValue().isBefore(dpStart.getValue())){
                throw new Exception("La fecha de fin no puede ser menor a la de inicio");
            }
        }
    }


    @FXML
    private void editCycle(){
        try {
            validateCycleFields();
            validateDateChoose();

            CicloEscolarDTO cycle = CicloEscolarDTO.builder()
                    .id(cycleRetrived.getCicloEscolar().getId())
                    .inicio(dpStart.getValue().toString())
                    .fin(dpEnd.getValue().toString())
                    .build();

            DetalleCicloDTO details = DetalleCicloDTO.builder()
                    .idCicloEscolar(cycleRetrived.getCicloEscolar().getId())
                    .cuotaInscripcion(Double.parseDouble(txfRegistration.getText()))
                    .cuotaColegiatura(Double.parseDouble(txfTuition.getText()))
                    .cuotaLibros(Double.parseDouble(txfBooks.getText()))
                    .cuotaEventos(Double.parseDouble(txfEvents.getText()))
                    .cuotaAcademias(Double.parseDouble(txfAcademies.getText()))
                    .cuotaUniforme(Double.parseDouble(txfUniform.getText()))
                    .build();

            CicloConDetallesDTO fullCycle = new CicloConDetallesDTO(cycle, details);
            System.out.println(fullCycle);
            cyclesService.editCycle(fullCycle);
        }
        catch (Exception ex) {
            notify("Error", ex.getMessage());
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


}
