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
import java.net.URL;
import java.util.List;
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
    private MFXLegacyTableView<PagoReporteDTO> tblPayments;


    private ServicioPagos servicioPagos;


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

    private void notify(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

    @FXML
    private void searchPayments(){
        FiltroPagoDTO filters = FiltrosCache.getInstance();

        filters.setFechaDesde(dpFrom.getValue());
        filters.setFechaHasta(dpTo.getValue());

        System.out.println(filters);

        loadTable();
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

    private void generateReport(){

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
            tblPayments.setItems(FXCollections.observableArrayList(payments));
        } catch (ConexionServidorException e) {
            notify("Ups!!", e.getMessage());
        }
    }


}
