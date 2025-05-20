package mx.sgi.presentacion.controladores;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import mx.itson.sgi.dto.FiltroPagoDTO;
import mx.itson.sgi.dto.PagoReporteDTO;
import mx.sgi.presentacion.caches.FiltrosCache;
import mx.sgi.presentacion.excepciones.ConexionServidorException;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioPagos;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GenerateReportController  implements Initializable {

    @FXML
    private MFXDatePicker dpBegin;

    @FXML
    private MFXDatePicker dpEnd;

    @FXML
    private MFXButton btnSearch;

    @FXML
    private MFXButton btnAddFilters;

    @FXML
    private MFXButton btnCleanUpFilters;

    @FXML
    private MFXButton btnGeneratePDF;

    @FXML
    private MFXLegacyTableView<PagoReporteDTO> tblPayments;


    private ServicioPagos servicioPagos;

    List<PagoReporteDTO> listedpayments = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        starInstances();
        setBtnSearchStyles();
        setBtnAddFiltersStyles();
        setBtnCleanUpFiltersStyles();
        setBtnGeneratePDFFiltersStyles();
        setTblStudentsStyles();
        cellsStyles();
    }

    private void starInstances(){
        servicioPagos = ServicioPagos.getInstance();
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
        icon.setIconColor(Color.WHITE);
        icon.setIconSize(25);

        btnGeneratePDF.setGraphic(icon);
        btnGeneratePDF.setText("  Generar");
        btnGeneratePDF.setContentDisplay(ContentDisplay.LEFT);

        Tooltip tooltip = new Tooltip("Generar PDF");
        Tooltip.install(btnGeneratePDF, tooltip);
    }



    private void validateDates() throws Exception{

        LocalDate begin = Optional.ofNullable(dpBegin.getValue())
                .orElseThrow(() -> new Exception("Debe seleccionar una fecha de inicio"));

        LocalDate end = Optional.ofNullable(dpEnd.getValue())
                .orElseThrow(() -> new Exception("Debe seleccionar una fecha de fin"));

        if (end.isBefore(begin)){
                throw new Exception("La fecha de inicio no puede ser mayor a la final");
        }

    }

    private void validateEmptyPaymentsList() throws Exception {
        if (listedpayments.isEmpty()){
            throw new Exception("Tiene que haber mas de un pago existente para generar el reporte");
        }
    }

    @FXML
    private void searchPayments(){
        try {
            validateDates();

            FiltroPagoDTO filters = FiltrosCache.getInstance();

            filters.setFechaDesde(dpBegin.getValue());
            filters.setFechaHasta(dpEnd.getValue());

            System.out.println(filters);

            loadTable();
        }
        catch (Exception ex){
            notify("Error", ex.getMessage());
        }
    }

    @FXML
    private void cleanFilters(){
        FiltroPagoDTO filters = FiltrosCache.getInstance();
        filters.setFechaDesde(null);
        filters.setFechaHasta(null);
        filters.setMontoMinimo(null);
        filters.setMontoMaximo(null);
        filters.setUsuario(null);
        filters.setAlumno(null);
        filters.setMetodoPago(null);

        System.out.println(filters);
    }

    @FXML
    private void openReportFiltersScreen(){
        Mediador mediador = Mediador.getInstance();
        mediador.openAddFiltersScreen();
    }

    /**
     *
     * @return
     */
    private File chooseFileToSaveReport(){
        // Crear y configurar el FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar reporte");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf")
        );

        // Establecer nombre por defecto del archivo
        fileChooser.setInitialFileName("reporte-pagos_" + LocalDate.now().toString() + "_" +
                LocalTime.now().toString() + ".pdf");

        // Mostrar diálogo de guardado
        File file = fileChooser.showSaveDialog(btnSearch.getScene().getWindow());
        return file;
    }

    /**
     *
     */
    @FXML
    private void generateReport() {
        try {

            validateEmptyPaymentsList();
            File file = chooseFileToSaveReport();

            if (file != null) {
                // Aquí iría la lógica para generar el reporte y guardarlo en el archivo seleccionado
                System.out.println("Guardar en: " + file.getAbsolutePath());

                // Ejemplo:
                // reportService.generarReportePagos(listaPagos, file);

                notify("Éxito", "Reporte guardado en:\n" + file.getAbsolutePath());
            }

        } catch (Exception ex) {
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


    //Table methods and declarations

    //Column for badge number
    TableColumn<PagoReporteDTO, String> colFolio = new TableColumn<>("Folio");

    //Column for name
    TableColumn<PagoReporteDTO, String> colFecha = new TableColumn<>("Fecha");

    //Column for cellphone
    TableColumn<PagoReporteDTO, String> colSubtotal = new TableColumn<>("Subtotal");

    //Column for mail
    TableColumn<PagoReporteDTO, String> colDescuento = new TableColumn<>("Descuento");

    //Column for status
    TableColumn<PagoReporteDTO, String> colTipo = new TableColumn<>("Tipo");

    //Column for nothing
    TableColumn<PagoReporteDTO, String> colTotal = new TableColumn<>("Total");

    //Column for nothing
    TableColumn<PagoReporteDTO, String> colAlumno = new TableColumn<>("Alumno");

    /**
     * Set the styles for the register button.
     */
    private void setTblStudentsStyles(){
        tblPayments.setEditable(false);
        tblPayments.setFixedCellSize(60);

        //desabilitamos el reordenamiento
        colFolio.setReorderable(false);
        colFecha.setReorderable(false);
        colSubtotal.setReorderable(false);
        colDescuento.setReorderable(false);
        colTipo.setReorderable(false);
        colTotal.setReorderable(false);
        colAlumno.setReorderable(false);

        //le damos estilos a los encabezados
        colFolio.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colFecha.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colSubtotal.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colDescuento.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colTipo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colTotal.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colAlumno.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");

        tblPayments.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colFolio.prefWidthProperty().bind(tblPayments.widthProperty().multiply(0.1));
        colFecha.prefWidthProperty().bind(tblPayments.widthProperty().multiply(0.3));
        colSubtotal.prefWidthProperty().bind(tblPayments.widthProperty().multiply(0.2));
        colDescuento.prefWidthProperty().bind(tblPayments.widthProperty().multiply(0.2));
        colTipo.prefWidthProperty().bind(tblPayments.widthProperty().multiply(0.2));
        colTotal.prefWidthProperty().bind(tblPayments.widthProperty().multiply(0.2));
        colAlumno.prefWidthProperty().bind(tblPayments.widthProperty().multiply(0.2));
    }

    /**
     * Set the styles for each cell.
     */
    private void cellsStyles(){
        colFolio.setCellFactory(column -> {
            TableCell<PagoReporteDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colFecha.setCellFactory(column -> {
            TableCell<PagoReporteDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colSubtotal.setCellFactory(column -> {
            TableCell<PagoReporteDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colDescuento.setCellFactory(column -> {
            TableCell<PagoReporteDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colTipo.setCellFactory(column -> {
            TableCell<PagoReporteDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colTotal.setCellFactory(column -> {
            TableCell<PagoReporteDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

        colAlumno.setCellFactory(column -> {
            TableCell<PagoReporteDTO, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    setStyle("-fx-alignment: center; -fx-font-size: 14px;");
                }
            };
            return cell;
        });

    }

    /**
     * Sets the gotten data from the service in the table and ads the edit
     * button for each row.
     */
    public void loadTable() {

        // Limpiamos columnas previas
        tblPayments.getColumns().clear();


        colFolio.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFolio()));

        colFecha.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha().toLocalDate().toString()));

        colSubtotal.setCellValueFactory(data -> new SimpleStringProperty( String.valueOf(data.getValue().getMontoTotal() + data.getValue().getMontoDescuento())));

        colDescuento.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMontoDescuento())));

        colTipo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoDescuento()));

        colTotal.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMontoTotal())));

        colAlumno.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAlumno().getNombre())));



        // Agregar columnas a la tabla
        tblPayments.getColumns().addAll(colFolio, colFecha, colSubtotal, colDescuento, colTipo, colTotal, colAlumno);

        // Llenar datos
        try {
            FiltroPagoDTO filtros = FiltrosCache.getInstance();
            List<PagoReporteDTO> payments = servicioPagos.getPaymentsForReport(filtros);
            this.listedpayments = payments;
            tblPayments.setItems(FXCollections.observableArrayList(payments));
        } catch (ConexionServidorException e) {
            notify("Ups!!", e.getMessage());
        }
    }


}
