package mx.sgi.presentacion.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.CuotasDTO;
import mx.sgi.presentacion.caches.AlumnoCache;
import mx.sgi.presentacion.caches.CicloEscolarCache;
import mx.sgi.presentacion.servicios.ServicioCuotas;
import org.controlsfx.control.Notifications;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class ColegiaturasAtrasadasController implements Initializable {

    @FXML
    private TableView<ColegiaturaAtrasadaDTO> tabla;
    @FXML
    private TableColumn<ColegiaturaAtrasadaDTO, LocalDate> colMes;
    @FXML
    private TableColumn<ColegiaturaAtrasadaDTO, BigDecimal> colMontoPagado;
    @FXML
    private TableColumn<ColegiaturaAtrasadaDTO, BigDecimal> colAdeudoAcumulado;

    @FXML
    private Label lblTotal;

    private ServicioCuotas servicioCuotas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarInstancias();
        cargarAdeudosAtrasados();
        propiedadesTabla();
        cargarDetallesCelda();
    }

    /**
     * Metodo encargado de inicializar las instancias de la clase
     */
    private void iniciarInstancias(){
        this.servicioCuotas = new ServicioCuotas();
    }

    /**
     * Metodo que carga los adeudos atrasados a la tabla
     */
    private void cargarAdeudosAtrasados(){
        try {
            //obtenemos la matricula del alumno
            String matricula = AlumnoCache.getInstance().getMatricula();
            //obtenemos el ciclo escolar seleccionado
            CicloEscolarDTO cicloEscolar = CicloEscolarCache.getInstance();
            //obtenemos los adeudos desde la base de datos
            List<ColegiaturaAtrasadaDTO> lista = servicioCuotas.obtenerColegiaturasAtrasadas(matricula, cicloEscolar);

            //si la lista no esta vacia llenamos la tabla
            if (lista != null){

                // Creamos un ObservableList para que los datos sean observables y JavaFX los pueda mostrar en la tabla
                ObservableList<ColegiaturaAtrasadaDTO> observableList = FXCollections.observableArrayList(lista);

                // Definimos los nombres de cada columna
                colMes.setCellValueFactory(new PropertyValueFactory<>("mes"));
                colMontoPagado.setCellValueFactory(new PropertyValueFactory<>("montoPagado"));
                colAdeudoAcumulado.setCellValueFactory(new PropertyValueFactory<>("adeudoAcumulado"));

                // Asignamos la lista de datos al TableView
                tabla.setItems(observableList);

                // Opcional: Mostrar el total de los adeudos acumulados
                BigDecimal total = lista.stream()
                        .map(ColegiaturaAtrasadaDTO::getAdeudoAcumulado)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                lblTotal.setText(total.toString());
            }
        } catch (Exception e) {
            notificarError(e.getMessage());
        }
    }

    /**
     * le añade propiedades visuales extra a la tabla
     */
    private void propiedadesTabla(){
        //desabilitamos la edicion de celdas
        tabla.setEditable(false);

        //desabilitamos el reordenamiento de celdas
        colMes.setReorderable(false);
        colMontoPagado.setReorderable(false);
        colAdeudoAcumulado.setReorderable(false);

        //ajustamos la altura de las filas
        tabla.setFixedCellSize(40);

        //le damos estilos a los encabezados
        colMes.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colMontoPagado.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");
        colAdeudoAcumulado.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-alignment: center;");

    }

    void cargarDetallesCelda(){

        colMes.setCellFactory(column -> {
            return new TableCell<ColegiaturaAtrasadaDTO, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle(""); // Restablecer el estilo si la celda está vacía
                    } else {
                        // Ajustar el tamaño de la fuente y la justificación
                        setText(item.getMonth().toString()); // Mostrar el nombre del mes
                        setStyle("-fx-font-size: 14px; -fx-alignment: center;"); // Tamaño de fuente y alineación
                    }
                }
            };
        });

        colMontoPagado.setCellFactory(column -> {
            return new TableCell<ColegiaturaAtrasadaDTO, BigDecimal>() {
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle(""); // Restablecer el estilo si la celda está vacía
                    } else {
                        // Ajustar el tamaño de la fuente y la justificación
                        setText(item.toString()); // Mostrar el monto
                        setStyle("-fx-font-size: 14px; -fx-alignment: center;"); // Tamaño de fuente y alineación
                    }
                }
            };
        });

        colAdeudoAcumulado.setCellFactory(column -> {
            return new TableCell<ColegiaturaAtrasadaDTO, BigDecimal>() {
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle(""); // Restablecer el estilo si la celda está vacía
                    } else {
                        // Ajustar el tamaño de la fuente y la justificación
                        setText(item.toString()); // Mostrar el adeudo acumulado
                        setStyle("-fx-font-size: 14px; -fx-alignment: center; -fx-text-fill: #f23535;");
                    }
                }
            };
        });

    }

    /**
     * Metodo que lanza mensaje flotante en caso de error
     * @param mensaje mensaje del error
     */
    private void notificarError(String mensaje){
        Notifications.create()
                .title("Error al obtener los pagos atrasados")
                .text(mensaje)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }
}

