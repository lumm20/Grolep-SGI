package mx.sgi.presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.itson.sgi.dto.*;
import mx.sgi.presentacion.caches.AlumnoCache;
import mx.sgi.presentacion.caches.PagoCache;
import mx.sgi.presentacion.caches.TicketRegistrarCache;
import mx.sgi.presentacion.caches.UsuarioCache;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import mx.sgi.presentacion.interfaces.IServicioCicloEscolar;
import mx.sgi.presentacion.interfaces.IServicioCuotas;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import mx.sgi.presentacion.servicios.ServicioCicloEscolar;
import mx.sgi.presentacion.servicios.ServicioCuotas;
import org.springframework.beans.factory.annotation.InjectionMetadata;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PantallaPrincipalController implements Initializable {

    @FXML
    JFXButton btnPagos;

    @FXML
    JFXButton btnRegistros;

    @FXML
    JFXButton btnUniformes;

    @FXML
    JFXButton btnCuenta;

    @FXML
    VBox boxCajero;

    //de aqui en adelante se declaran los componentes que representan datos

    @FXML
    Label lblAdeudoVencido;

    @FXML
    Label  lblAdeudoColegiatura;

    @FXML
    Label lblCuotaInscripcion;

    @FXML
    Label lblCuotaLibros;

    @FXML
    Label lblCuotaEventos;

    @FXML
    Label lblCuotaAcademias;

    @FXML
    Label lblUniforme;

    @FXML
    Label lblTotal;


    //declaracion de los checkBoxes

    @FXML
    CheckBox cbxConsiderarColegiatura;

    @FXML
    CheckBox cbxConsiderarCuotas;


    //declaracion de los botones

    @FXML
    JFXButton btnDetalles;

    @FXML
    JFXButton btnRegistrar;


    //Declaracion de los ComboBox

    @FXML
    ComboBox<AlumnoConsultaDTO> cmbxAlumnos;

    @FXML
    ComboBox<CicloEscolarDTO> cmbxCicloEscolar;

    @FXML
    ComboBox<String> cmbxMetodoPago;


    //de aqui en adelante se declaran los componentes de entradas

    @FXML
    TextField txfAlumnos;

    @FXML
    TextField txfMontoVencido;

    @FXML
    TextField txfMontoColegiatura;

    @FXML
    TextField txfMontoInscripcion;

    @FXML
    TextField txfMontoLibros;

    @FXML
    TextField txfMontoEventos;

    @FXML
    TextField txfMontoAcademias;

    @FXML
    TextField txfMontoUniforme;


    @FXML
    private BorderPane borderPane;

    private Mediador mediador;

    private IServicioAlumnos servicioAlumnos;

    private IServicioCuotas  servicioCuotas;

    private IServicioCicloEscolar servicioCicloEscolar;
    /**
     * Instancia estatica del controlador
     */
    private static PantallaPrincipalController instancia;

    /**
     * Metodo para obtener la instancia unica de la clase
     * @return instancia unica de la clase
     */
    public static synchronized PantallaPrincipalController getInstance() {
        return instancia;
    }

    /**
     * Metodo para establecer la instancia unica de la clase
     * @param instancia instancia a establecer
     */
    public static synchronized void setInstancia(PantallaPrincipalController  instancia){
        PantallaPrincipalController.instancia = instancia;
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.servicioAlumnos = new ServicioAlumnos();

        this.servicioCuotas =  new ServicioCuotas();

        this.mediador = Mediador.getInstance();

        this.servicioCicloEscolar = new ServicioCicloEscolar();

        //establecemos los ciclos escolares en el comboBox
        establecerCiclos();

        //cargamos los metodos de pago:
        establecerMetodosDePago();

        //establecemos la configuracion para los campos de entrada
        establecerListenersCamposDeEntrada();

        //establecemos la configuracion para el buscador de alumnos
        configurarFiltroAlumnos();


    }

    /**
     *
     *
    public void setDashboard(String direccion) {
        try {
            // Cargar el archivo FXML del Dashboard
            FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource(direccion));

            // Obtener el controlador del dashboard
            DashboardCajeroController dashboardControler = dashboardLoader.getController();

            System.out.println("si llegue hasta aca");

            // Obtener el VBox (o cualquier contenedor) del dashboard
            VBox dashboard = dashboardControler.getDashboard();

            if (dashboard != null) {
                System.out.println("si tenemos dashboard");
            }

            // Asignar el VBox del dashboard a la región izquierda del BorderPane

        } catch (Exception e) {
            System.err.println("Error al cargar el dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
    */

    //de aqui en adelante comienzan los listeners para validaciones

    /**
     * establece la configuracion de cada listener para los campos de entrada.
     */
    private void establecerListenersCamposDeEntrada(){
        // Método para el campo "Monto Vencido"
        txfMontoVencido.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoVencido, lblAdeudoVencido);
        });

        // Método para el campo "Monto Colegiatura"
        txfMontoColegiatura.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoColegiatura, lblAdeudoColegiatura);
        });

        // Método para el campo "Monto Inscripción"
        txfMontoInscripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoInscripcion, lblCuotaInscripcion);
        });

        // Método para el campo "Monto Libros"
        txfMontoLibros.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoLibros, lblCuotaLibros);
        });

        // Método para el campo "Monto Eventos"
        txfMontoEventos.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoEventos, lblCuotaEventos);
        });

        // Método para el campo "Monto Academias"
        txfMontoAcademias.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoAcademias, lblCuotaAcademias);
        });

        //// Método para el campo "Monto Uniforme"
        txfMontoUniforme.textProperty().addListener((observable, oldValue, newValue) -> {
            verificarFormato(txfMontoUniforme, lblUniforme);
        });

    }

    //de aqui en adelante comienzan los listeners para los combo box

    /**
     * establece la configuracion para la busqueda de alumnos.
     */
    private void configurarFiltroAlumnos() {

        // Escuchar cambios en el texto del TextField
        txfAlumnos.textProperty().addListener((observable, oldValue, newValue) -> {
            // Llamar al servicio para obtener los alumnos que coinciden con el texto
            actualizarOpcionesAlumnos(newValue);
        });

        cmbxAlumnos.setOnAction(event -> {
            AlumnoConsultaDTO alumnoSeleccionado = cmbxAlumnos.getValue();
            if (alumnoSeleccionado != null && !cmbxAlumnos.getItems().isEmpty()) {
                Platform.runLater(() -> {
                    String texto = alumnoSeleccionado != null ? alumnoSeleccionado.toString() : "";
                    txfAlumnos.setText(texto);
                });
                AlumnoCache.limpiarCache(); //limpiamos en caso de que hubiera otro alumno ocupando la instancia
                AlumnoCache.setInstance(alumnoSeleccionado); //guardamos al alumno seleccionado en el cache
                consultarCuotas(); //consultamos sus cuotas
            }
        });

    }

    /**
     * Método para actualizar la lista basada en el texto ingresado
     */
    private void actualizarOpcionesAlumnos(String filtro) {
        try {

            if (!filtro.isEmpty()){

                List<AlumnoConsultaDTO> alumnosFiltrados = servicioAlumnos.consultarAlumnos(filtro);

                // Convertimos la lista en un ObservableList
                ObservableList<AlumnoConsultaDTO> listaObservable = FXCollections.observableArrayList(alumnosFiltrados);

                cmbxAlumnos.visibleRowCountProperty().setValue(alumnosFiltrados.size());

                // Actualizamos los ítems del ComboBox
                cmbxAlumnos.setItems(listaObservable);

                // Mostramos el dropdown con las opciones actualizadas
                cmbxAlumnos.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ahora de aquí en adelante van los metodos para el procesamiento del pago

    /**
     * Carga los metodos de pago existentes a su respectivo combo box
     */
    private void establecerMetodosDePago() {
        // Definir la lista de métodos de pago
        List<String> listaMetodosPago = Arrays.asList("Efectivo", "Tarjeta", "Transferencia");  // Ajusta según los métodos de pago

        // Convertir la lista a un ObservableList
        ObservableList<String> observableList = FXCollections.observableArrayList(listaMetodosPago);

        // Establecer los items del ComboBox
        cmbxMetodoPago.setItems(observableList);
    }

    /**
     * Establece los ciclos escolares dentro de su respectivo comboBox
     */
    private void establecerCiclos(){
        try {

            List<CicloEscolarDTO> listaCiclos = servicioCicloEscolar.obtenerCiclosEscolares();

            ObservableList<CicloEscolarDTO> observableList = FXCollections.observableArrayList(listaCiclos);

            cmbxCicloEscolar.setItems(observableList);

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    /**
     * aqui se consultaran las cuotas correspondientes al alumno que haya sido seleccionado
     */
    private void consultarCuotas() {
        try{

            List<CuotaDTO> cuotas;
            List<ColegiaturaAtrasadaDTO>  colegiaturaAtrasadas;

            if (cmbxCicloEscolar.getValue() == null &&  cmbxAlumnos.getValue() != null) {

                System.out.println("Entre a la primera opcion");
                cmbxCicloEscolar.getSelectionModel().select(0);
                CicloEscolarDTO cicloEscolar = cmbxCicloEscolar.getValue();
                String matricula = cmbxAlumnos.getValue().getMatricula();

                establecerCuotas(matricula, cicloEscolar);
            }
            else if (cmbxCicloEscolar.getValue() != null && cmbxAlumnos.getValue() != null) {

                System.out.println("Entre a la segunda opcion");
                CicloEscolarDTO cicloEscolar = cmbxCicloEscolar.getValue();
                String matricula = cmbxAlumnos.getValue().getMatricula();

                establecerCuotas(matricula, cicloEscolar);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void establecerCuotas(String matricula,CicloEscolarDTO cicloEscolar) {
        try {
            CuotaDTO cuotas = servicioCuotas.obtenerCuotasAlumno(matricula, cicloEscolar);

            lblAdeudoVencido.setText(cuotas.getAdeudoVencido());
            lblAdeudoColegiatura.setText(cuotas.getAdeudoColegiatura());
            lblCuotaInscripcion.setText(cuotas.getAdeudoInscripcion());
            lblCuotaLibros.setText(cuotas.getAdeudoLibros());
            lblCuotaEventos.setText(cuotas.getAdeudoEventos());
            lblCuotaAcademias.setText(cuotas.getAdeudoAcademias());
            lblUniforme.setText(cuotas.getAdeudoUniformes());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Recolecta la informacion para el pago y la pasa al ticket al igual que abre
     * la pantalla del ticket
     */
    @FXML
    public void registrarPago(){
        try {

        if (AlumnoCache.getInstance().getMatricula() == null){
            System.out.println("asegurese de seleccionar un alumno");
            return;
        }

        if (cmbxMetodoPago.getValue() == null){
            System.out.println("no lo selecciono el wey");
            return;
        }

            //recolectamos todos los campos del pago y los guardamos en la cache

            TicketRegistrarDTO ticket = TicketRegistrarCache.getInstance();

            BigDecimal montoTotal = toBigDecimal(lblTotal.getText());
            String folio = "AX123123DS942";
            LocalDate fecha = LocalDate.now();
            LocalTime hora = LocalTime.now();
            String metodoPago = cmbxMetodoPago.getValue();
            BigDecimal montoVencidos = toBigDecimal(txfMontoVencido.getText());
            BigDecimal montoColegiatura = toBigDecimal(txfMontoColegiatura.getText());
            BigDecimal montoInscripcion = toBigDecimal(txfMontoInscripcion.getText());
            BigDecimal montoLibros = toBigDecimal(txfMontoLibros.getText());
            BigDecimal montoEventos = toBigDecimal(txfMontoEventos.getText());
            BigDecimal montoAcademias = toBigDecimal(txfMontoAcademias.getText());
            BigDecimal montoUniforme = toBigDecimal(txfMontoUniforme.getText());
            String descuento = "Descuento por pago temprano";
            AlumnoConsultaDTO alumno = AlumnoCache.getInstance();
            UsuarioDTO usuario = UsuarioCache.getInstance();

            ticket.setMontoTotal(montoTotal);
            ticket.setFolio(folio);
            ticket.setFecha(fecha);
            ticket.setHora(hora);
            ticket.setMetodoPago(metodoPago);
            ticket.setMontoVencidos(montoVencidos);
            ticket.setMontoColegiatura(montoColegiatura);
            ticket.setMontoInscripcion(montoInscripcion);
            ticket.setMontoLibros(montoLibros);
            ticket.setMontoEventos(montoEventos);
            ticket.setMontoAcademias(montoAcademias);
            ticket.setMontoUniforme(montoUniforme);
            ticket.setDescuento(descuento);
            ticket.setAlumno(alumno);
            ticket.setUsuario(usuario);

            System.out.println(ticket.toString());

            mediador.abrirPantallaTicket();

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * @param valor
     * @return
     * @throws Exception
     */
    private BigDecimal toBigDecimal(String valor) throws Exception {
        try{
            String campo = valor;

            campo = campo.equalsIgnoreCase("") ? "0" : valor;

            return new BigDecimal(campo).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        catch (Exception ex){
            throw new Exception("Porfavor ingrese datos validos en todos los campos");
        }
    }

    /**
     *
     */
    private void validarDescuento(){

    }

    /**
     *
     */
    private void generarFolio(){

    }




    /**
     *
     */
    @FXML
    private void considerarColegiatura() {
        boolean considerar = cbxConsiderarColegiatura.isSelected();

        txfMontoVencido.setText(considerar ? lblAdeudoVencido.getText() : "");
        txfMontoColegiatura.setText(considerar ? lblAdeudoColegiatura.getText() : "");

        //actualizamos el total
        actualizarTotal();

    }

    /**
     * actualizamos el total
     */
    @FXML
    private void considerarTodos() {
        boolean considerar = cbxConsiderarCuotas.isSelected();

        Map<TextField, Label> campos = Map.of(
                txfMontoInscripcion, lblCuotaInscripcion,
                txfMontoLibros, lblCuotaLibros,
                txfMontoEventos, lblCuotaEventos,
                txfMontoAcademias, lblCuotaAcademias,
                txfMontoUniforme, lblUniforme
        );

        campos.forEach((campo, etiqueta) -> campo.setText(considerar ? etiqueta.getText() : ""));

        // Actualizamos el total
        actualizarTotal();

    }

    /**
     * Metodo que actualiza el campo para el total
     */
    private void actualizarTotal() {
        BigDecimal total = BigDecimal.ZERO;

        // Sumar los montos de cada campo
        total = total.add(ParseBigDecimal(txfMontoVencido));
        total = total.add(ParseBigDecimal(txfMontoColegiatura));
        total = total.add(ParseBigDecimal(txfMontoInscripcion));
        total = total.add(ParseBigDecimal(txfMontoLibros));
        total = total.add(ParseBigDecimal(txfMontoEventos));
        total = total.add(ParseBigDecimal(txfMontoAcademias));
        total = total.add(ParseBigDecimal(txfMontoUniforme));

        // Actualizar el total en la interfaz
        lblTotal.setText(total.toString());
    }

    /**
     * Método para parsear el texto a BigDecimal, devolviendo BigDecimal.ZERO si es vacío o no válido
     */
    private BigDecimal ParseBigDecimal(TextField textField) {
        try {
            return new BigDecimal(textField.getText().isEmpty() ? "0,00" : textField.getText());
        } catch (NumberFormatException e) {
            return new BigDecimal(0.00);
        }
    }

    /**
     *
     * @param textField
     * @param label
     */
    private void verificarFormato(TextField textField, Label label) {
        try {

            // Verificamos si el texto no está vacío y es un número válido
            if (textField.getText().isEmpty()) {
                // Si el campo está vacío, puedes manejarlo de la forma que prefieras, por ejemplo:
                textField.setStyle("-fx-border-color: #000000;");
                textField.setDisable(false);
                return;  // Salir del método, ya que no queremos procesar el campo vacío
            }

            BigDecimal valorIngresado = new BigDecimal(textField.getText()); // valor del campo a evaluar
            BigDecimal valorReal = new BigDecimal(label.getText()); // valor del campo indicador del monto
            BigDecimal comparador = new BigDecimal(0); // valor a comparar

            // Comparar si el valor ingresado es menor que 0
            int comparacionMenor = valorIngresado.compareTo(comparador);
            int comparacionMayor = valorIngresado.compareTo(valorReal);

            // Si el número ingresado es negativo, aplicar estilo y deshabilitar el botón
            if (comparacionMenor < 0) {
                aplicarEstiloDeError(textField);
            }
            // Si el valor ingresado es mayor que el valor real, también aplica estilo de error
            else if (comparacionMayor > 0) {
                aplicarEstiloDeError(textField);
            }
            // Si el valor ingresado está dentro del rango esperado, aplicar estilo por defecto
            else {
                aplicarEstiloPorDefecto(textField);
                actualizarTotal();
            }
        } catch (NumberFormatException ex) {
            // Si el formato no es un número válido, aplicar el estilo de error
            aplicarEstiloDeError(textField);
        }
    }

    /**
     *  Método auxiliar para aplicar estilo de error al botón
     */
    private void aplicarEstiloDeError(TextField textField) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    /**
     * Método auxiliar para aplicar estilo por defecto al botón
     *
     */
   private void aplicarEstiloPorDefecto(TextField textField) {
        textField.setStyle("-fx-border-color: #000000;");
    }


}