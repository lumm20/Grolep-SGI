package mx.sgi.presentacion.controladores;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import mx.itson.sgi.dto.*;
import mx.sgi.presentacion.caches.*;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import mx.itson.sgi.dto.vistas.TicketRegistrarDTO;
import mx.sgi.presentacion.interfaces.IServicioAlumnos;
import mx.sgi.presentacion.interfaces.IServicioCicloEscolar;
import mx.sgi.presentacion.interfaces.IServicioCuotas;
import mx.sgi.presentacion.interfaces.IServicioPagos;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioAlumnos;
import mx.sgi.presentacion.servicios.ServicioCicloEscolar;
import mx.sgi.presentacion.servicios.ServicioCuotas;
import mx.sgi.presentacion.servicios.ServicioPagos;

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
    Label lblAdeudoActual;
    @FXML
    Label lblAdeudoConDescuento;
    @FXML
    Label lblCicloEscolar;

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

    @FXML
    Label lblSubTotal;

    @FXML
    Label lblTipoDescuento;

    @FXML
    Label lblDescuentoDescuento;


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

    @FXML
    JFXButton btnLimpiarFiltroAlumnos;


    //Declaracion de los ComboBox

    @FXML
    ComboBox<AlumnoConsultaDTO> cmbxAlumnos;

    @FXML
    JFXComboBox<CicloEscolarDTO> cmbxCicloEscolar;

    @FXML
    JFXComboBox<String> cmbxMetodoPago;



    //de aqui en adelante se declaran los componentes de entradas

    @FXML
    TextField txfAlumnos;

    // @FXML
    // TextField txfMontoVencido;

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
    private IServicioPagos  servicioPagos;

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
        this.servicioPagos = ServicioPagos.getInstance();

        this.servicioCuotas =  ServicioCuotas.getInstance();

        this.mediador = Mediador.getInstance();

        this.servicioCicloEscolar = new ServicioCicloEscolar();
        // txfMontoVencido.setDisable(true);
        //establecemos los ciclos escolares en el comboBox
        establecerCiclos();

        //cargamos los metodos de pago:
        establecerMetodosDePago();

        //establecemos la configuracion para los campos de entrada
        establecerListenersCamposDeEntrada();

        //establecemos la configuracion para el buscador de alumnos
        configurarFiltroAlumnos();

        //establecemos el descuento
        establecerDescuento();
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

    @FXML
    private void limpiarFiltroAlumnos(){
        txfAlumnos.setText("");
        cmbxAlumnos.getSelectionModel().clearSelection();
        cmbxAlumnos.getItems().clear();
        cmbxAlumnos.hide();
        cleanup();
    }

    //de aqui en adelante comienzan los listeners para validaciones

    /**
     * establece la configuracion de cada listener para los campos de entrada.
     */
    private void establecerListenersCamposDeEntrada(){
        // Método para el campo "Monto Vencido"
        // txfMontoVencido.textProperty().addListener((observable, oldValue, newValue) -> {
        //     verificarFormato(txfMontoVencido, lblAdeudoVencido);
        // });

        // Método para el campo "Monto Colegiatura"
        txfMontoColegiatura.textProperty().addListener((observable, oldValue, newValue) -> {
                verificarFormato(txfMontoColegiatura, lblAdeudoVencido);
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


    private void configurarFiltroAlumnos() {
        // Crear el temporizador para detectar cuando se deja de escribir
        PauseTransition pause = new PauseTransition(Duration.millis(500)); // 500 ms de espera
        pause.setOnFinished(event -> {
            // Acción cuando el usuario ha dejado de escribir
            // Reactivar el ComboBox después de que se haya dejado de escribir
            cmbxAlumnos.setDisable(false);  // Reactivar ComboBox

            String nuevoValor = txfAlumnos.getText();
            actualizarOpcionesAlumnos(nuevoValor); // Actualizar las opciones de los alumnos

        });

        // Detectar cuando el usuario escribe
        txfAlumnos.setOnKeyTyped(event -> {
            // Desactivar el ComboBox mientras se escribe
            cmbxAlumnos.setDisable(true);  // Desactivar ComboBox

            // Cancelar cualquier temporizador anterior (si el usuario sigue escribiendo)
            pause.stop();
            // Reiniciar el temporizador
            pause.playFromStart();
        });

        // Configurar el ComboBox para manejar la selección
        cmbxAlumnos.setOnAction(event -> {
            AlumnoConsultaDTO alumnoSeleccionado = cmbxAlumnos.getValue();
            if (alumnoSeleccionado != null && !cmbxAlumnos.getItems().isEmpty()) {
                Platform.runLater(() -> {
                    String texto = alumnoSeleccionado != null ? alumnoSeleccionado.toString() : "";
                    txfAlumnos.setText(texto); // Actualizar el TextField con el alumno seleccionado
                });
                AlumnoCache.limpiarCache(); // Limpiar el cache en caso de que hubiera otro alumno ocupando la instancia
                AlumnoCache.setInstance(alumnoSeleccionado); // Guardar al alumno seleccionado en el cache
                consultarCuotas(); // Consultar las cuotas del alumno seleccionado
                //establecerDescuento();
            }
        });
    }

    /**
     * Método para actualizar la lista basada en el texto ingresado
     */
    private void actualizarOpcionesAlumnos(String filtro) {
        try {

            if (!filtro.isEmpty() && !filtro.isBlank() && filtro.matches("^[a-zA-Z\\s]+$")){
                filtro = filtro.trim();
                // List<AlumnoConsultaDTO> alumnosFiltrados = servicioAlumnos.consultarAlumnos(filtro);
                List<AlumnoConsultaDTO> alumnosFiltrados = servicioAlumnos.buscarAlumnos(filtro);

                System.out.println("Alumnos filtrados: " + alumnosFiltrados);
                if(alumnosFiltrados != null && alumnosFiltrados.size()>0){
                    ObservableList<AlumnoConsultaDTO> listaObservable = FXCollections.observableArrayList(alumnosFiltrados);
                // Actualizamos los ítems del ComboBox
                    cmbxAlumnos.getItems().setAll(listaObservable);
                    // Mostramos el dropdown con las opciones actualizadas
                    cmbxAlumnos.hide();
                    cmbxAlumnos.show();
                }
            }else{
                cmbxAlumnos.hide();
            }
        } catch (Exception ex) {
            notificarError(ex.getMessage());
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
            
            if(listaCiclos != null && !listaCiclos.isEmpty()){
                ObservableList<CicloEscolarDTO> observableList = FXCollections.observableArrayList(listaCiclos);
                cmbxCicloEscolar.setItems(observableList);
                
                CicloEscolarDTO cicloEscolar = servicioCicloEscolar.obtenerCicloEscolarActual();
                if(cicloEscolar != null){
                    CicloEscolarCache.setInstance(cicloEscolar);
                    // String anioInicio = cicloEscolar.getInicio().substring(0, 4);
                    // String anioFin = cicloEscolar.getFin().substring(0, 4);
                    // String cicloTxt = anioInicio + " - " + anioFin;
                    for (CicloEscolarDTO cicloEscolarDTO : observableList) {
                        if(cicloEscolarDTO.getId().equals(cicloEscolar.getId())){
                            cmbxCicloEscolar.getSelectionModel().select(cicloEscolarDTO);
                            break;
                        }
                    }
                }
            }

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    /**
     * aqui se consultaran las cuotas correspondientes al alumno que haya sido seleccionado
     */
    private void consultarCuotas() {

            // if (cmbxCicloEscolar.getValue() == null &&  cmbxAlumnos.getValue() != null) {

                // cmbxCicloEscolar.getSelectionModel().select(0);
                CicloEscolarDTO cicloEscolar = CicloEscolarCache.getInstance();

                String matricula = cmbxAlumnos.getValue().getMatricula();

                establecerCuotas(matricula, cicloEscolar);
                establecerDescuento();
            // }
            // else if (cmbxCicloEscolar.getValue() != null && cmbxAlumnos.getValue() != null) {

            //     System.out.println("Entre a la segunda opcion");
            //     CicloEscolarDTO cicloEscolar = cmbxCicloEscolar.getValue();
            //     String matricula = cmbxAlumnos.getValue().getMatricula();

            //     CicloEscolarCache.limpiarCache();
            //     CicloEscolarCache.setInstance(cicloEscolar);

            //     establecerCuotas(matricula, cicloEscolar);
            // }

    }

    /**
     * Establece las cuotas para el usuario elegido basandonos en el ciclo escolar
     * y la matricula del alumno.
     *
     * @param matricula Matricula del alumno
     * @param cicloEscolar Ciclo escolar a consultar.
     */
    public void establecerCuotas(String matricula,CicloEscolarDTO cicloEscolar) {
        try {

            String idCiclo = cicloEscolar.getId();
            CuotasDTO cuotas = servicioCuotas.obtenerCuotasAlumno(matricula, idCiclo);

            Double descuento = Double.parseDouble(lblDescuentoDescuento.getText());

            lblAdeudoVencido.setText(cuotas.getAdeudoVencido().toString());
            lblAdeudoActual.setText(cuotas.getAdeudoColegiatura().toString());
            lblAdeudoConDescuento.setText(String.valueOf(cuotas.getAdeudoColegiatura() - descuento));
            lblCuotaInscripcion.setText(cuotas.getAdeudoInscripcion().toString());
            lblCuotaLibros.setText(cuotas.getAdeudoLibros().toString());
            lblCuotaEventos.setText(cuotas.getAdeudoEventos().toString());
            lblCuotaAcademias.setText(cuotas.getAdeudoAcademias().toString());
            lblUniforme.setText(cuotas.getAdeudoUniformes().toString());

            disableTxfields(cuotas);
            btnDetalles.setDisable(cuotas.getAdeudoVencido()== null || cuotas.getAdeudoVencido()==0);

            //establecemos la demas informacion del alumno
            String tipoBeca = cuotas.getBeca() != null ? cuotas.getBeca() : "No Aplica";

            // lblTipoBeca.setText(tipoBeca);


        } catch (Exception ex) {
            notificarError(ex.getMessage());
        }

    }

    /**
     * Algun dia lo cambiamos por una implementacion real
     */
    private void establecerDescuento(){

        DescuentoDTO descuento =  new DescuentoDTO();
        AlumnoConsultaDTO alumno = AlumnoCache.getInstance();
        if(alumno.getMatricula() == null){
            DescuentoCache.limpiarCache();
            return;
        }
        String matricula = alumno.getMatricula();

        String tipoDesc = "No aplica";
        Double montoDesc = 0.00;
        //TODO: arreglar lo de obtener los pagos del mes. Debe adaptar la fecha al ciclo escolar elegido
        long cantidadPagosDelMes = servicioPagos.obtenerPagosDeColegiaturaDelMes(matricula, CicloEscolarCache.getInstance().getId());
        if(cantidadPagosDelMes == 0){
            int diaActual = LocalDate.now().getDayOfMonth();
    
    
            //descuento para la primer semana
            if (diaActual <= 7){
                tipoDesc = "Primer periodo";
                montoDesc = 400.00;
            }
            //descuento para la segunda semana
            else if (diaActual > 7 && diaActual <= 14) {
                tipoDesc = "Segundo periodo";
                montoDesc = 200.00;
            }
        }
        descuento.setTipo(tipoDesc);
        descuento.setDescuento(montoDesc);
        lblTipoDescuento.setText(tipoDesc);
        lblDescuentoDescuento.setText(String.valueOf(montoDesc));
        DescuentoCache.setInstance(descuento);

    }

    private void disableTxfields(CuotasDTO cuotas){
        // if(cuotas.getAdeudoVencido() == 0.0){
        //     txfMontoVencido.setDisable(true);
        //     txfMontoColegiatura.setDisable(true);
        // }
        txfMontoAcademias.setDisable(cuotas.getAdeudoAcademias()== null || cuotas.getAdeudoAcademias() == 0.0);
        txfMontoColegiatura.setDisable(cuotas.getAdeudoColegiatura()== null || cuotas.getAdeudoColegiatura() == 0.0);
        txfMontoEventos.setDisable(cuotas.getAdeudoEventos()== null || cuotas.getAdeudoEventos() == 0.0);
        txfMontoInscripcion.setDisable(cuotas.getAdeudoInscripcion()== null || cuotas.getAdeudoInscripcion() == 0.0);
        txfMontoUniforme.setDisable(cuotas.getAdeudoUniformes()== null || cuotas.getAdeudoUniformes() == 0.0);
        txfMontoLibros.setDisable(cuotas.getAdeudoLibros()== null || cuotas.getAdeudoLibros() == 0.0);
    }

    private void cleanup(){
        // txfMontoVencido.setDisable(false);
        txfMontoAcademias.setDisable(true);
        txfMontoColegiatura.setDisable(true);
        txfMontoEventos.setDisable(true);
        txfMontoInscripcion.setDisable(true);
        txfMontoUniforme.setDisable(true);
        txfMontoLibros.setDisable(true);

        // txfMontoVencido.setText("");
        txfMontoAcademias.setText("");
        txfMontoColegiatura.setText("");
        txfMontoEventos.setText("");
        txfMontoInscripcion.setText("");
        txfMontoUniforme.setText("");
        txfMontoLibros.setText("");

        lblAdeudoVencido.setText("0000.00");
        lblAdeudoActual.setText("0000.00");
        lblAdeudoConDescuento.setText("0000.00");
        lblCuotaInscripcion.setText("0000.00");
        lblCuotaLibros.setText("0000.00");
        lblCuotaEventos.setText("0000.00");
        lblCuotaAcademias.setText("0000.00");
        lblUniforme.setText("0000.00");
        lblTotal.setText("0000.00");
        cmbxAlumnos.getSelectionModel().clearSelection();
        cmbxMetodoPago.getSelectionModel().clearSelection();
        btnDetalles.setDisable(true);
    }

    /**
     * Consulta los cuotas para cuando se selecciona un ciclo escolar distinto;
     */
    @FXML
    private void ConsultarAdeudosConCicloEscolar(){
        CicloEscolarCache.limpiarCache();
        CicloEscolarCache.setInstance(cmbxCicloEscolar.getValue());
        if(cmbxAlumnos.getValue() != null){

            consultarCuotas();
        }
    }

    private void showError(String mensaje){
        Notifications.create()
                .title("Error en el registro de pago")
                .text(mensaje)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

    /**
     * Recolecta la informacion para el pago y la pasa al ticket al igual que abre
     * la pantalla del ticket
     */
    @FXML
    private void registrarPago() throws Exception{
        try {

            if (AlumnoCache.getInstance().getMatricula() == null) {
                showError("Debe haber un alumno seleccionado");
                return;
            }

            double total = Double.parseDouble(lblTotal.getText());
            
            if(total <= 0.0){
                showError("Debe ingresar, al menos, un monto a pagar");
                return;
            }
            if (cmbxMetodoPago.getValue() == null) {
                showError("Debe seleccionar un metodo de pago");
                return;
            }
            // recolectamos todos los campos del pago y los guardamos en la cache

            TicketRegistrarDTO ticket = new TicketRegistrarDTO();

            String folio = generarFolio();
            LocalDate fecha = LocalDate.now();
            LocalTime hora = LocalTime.now();
            MetodosPagoDTO metodoPago = MetodosPagoDTO.valueOf(cmbxMetodoPago.getValue());
            // Double montoVencidos = (txfMontoVencido.getText().isBlank() ? 0.0
            //         : Double.parseDouble(txfMontoVencido.getText()));
            Double montoColegiatura = (txfMontoColegiatura.getText().isBlank() ? 0.0
                    : Double.parseDouble(txfMontoColegiatura.getText()));
            Double montoInscripcion = (txfMontoInscripcion.getText().isBlank() ? 0.0
                    : Double.parseDouble(txfMontoInscripcion.getText()));
            Double montoLibros = (txfMontoLibros.getText().isBlank() ? 0.0
                    : Double.parseDouble(txfMontoLibros.getText()));
            Double montoEventos = (txfMontoEventos.getText().isBlank() ? 0.0
                    : Double.parseDouble(txfMontoEventos.getText()));
            Double montoAcademias = (txfMontoAcademias.getText().isBlank() ? 0.0
                    : Double.parseDouble(txfMontoAcademias.getText()));
            Double montoUniforme = (txfMontoUniforme.getText().isBlank() ? 0.0
                    : Double.parseDouble(txfMontoUniforme.getText()));
            
            AlumnoConsultaDTO alumno = AlumnoCache.getInstance();
            UsuarioDTO usuario = new UsuarioDTO(UsuarioCache.getSession().getIdUsuario());
            CicloEscolarDTO cicloEscolar = CicloEscolarCache.getInstance();
            Map<String, Double> cuotas = new HashMap<>();
            // cuotas.put("VENCIDOS", montoVencidos);
            cuotas.put("COLEGIATURA", montoColegiatura);
            cuotas.put("INSCRIPCION", montoInscripcion);
            cuotas.put("LIBROS", montoLibros);
            cuotas.put("EVENTOS", montoEventos);
            cuotas.put("ACADEMIAS", montoAcademias);
            cuotas.put("UNIFORME", montoUniforme);

            List<DetallePagoDTO> detalles = new ArrayList<>();
            
            String tipoDescuento = lblTipoDescuento.getText();
            Double descuento = Double.parseDouble(lblDescuentoDescuento.getText());
            
            Double montoConDescuento =0.0;
            for(Map.Entry<String,Double> cuota:cuotas.entrySet()){
                if(cuota.getValue()>0.0){
                    // if(cuota.getKey().equals("COLEGIATURA")){
                    //     montoConDescuento = total-descuento;
                    //     // Double montoAnterior = cuota.getValue();
                    //     // cuota.setValue(montoAnterior-descuento);
                    // }
                    detalles.add(new DetallePagoDTO(cuota.getKey(), cuota.getValue()));
                }
            }

            ticket.setMontoTotal(montoConDescuento>0.0? montoConDescuento:total);
            ticket.setFolio(folio);
            ticket.setFecha(fecha);
            ticket.setHora(hora);
            ticket.setMetodoPago(metodoPago);
            ticket.setDetalles(detalles);
            ticket.setAlumno(alumno);
            ticket.setIdUsuario(usuario.getId());
            ticket.setCiclo(cicloEscolar);
            ticket.setSubTotal(lblSubTotal.getText().toString());

            System.out.println(ticket.toString());
            ticket.setTipoDescuento(DescuentoCache.getInstance().getTipo());
            ticket.setMontoDescuento(DescuentoCache.getInstance().getDescuento());
            TicketRegistrarCache.setInstance(ticket);
            System.out.println(TicketRegistrarCache.getInstance());

            mediador.abrirPantallaTicket();

        }
        catch (Exception ex){
            notificarError(ex.getMessage());
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
     * Genera un folio aleatorio para el pago y garantizar su unicidad
     */
    private String generarFolio(){
        LocalDate fecha = LocalDate.now();

        // Día de la semana (primera letra en mayúscula)
        String diaSemana = fecha.getDayOfWeek().toString().substring(0, 1);

        // Día del mes (2 dígitos)
        String diaMes = String.format("%02d", fecha.getDayOfMonth());

        // Mes del año (2 dígitos)
        String mes = String.format("%02d", fecha.getMonthValue());

        // Año (últimos 2 dígitos)
        String anio = String.valueOf(fecha.getYear()).substring(2);

        // Parte aleatoria del UUID (4 caracteres)
        String parteUUID = UUID.randomUUID().toString().replace("-", "").substring(0, 4).toUpperCase();

        // Construcción del folio
        return diaSemana + diaMes + mes + anio + parteUUID;
    }



    /**
     * Campo para considerar todos los campos correspondiente a la colegiatura
     */
    @FXML
    private void considerarColegiatura() {
        boolean considerar = cbxConsiderarColegiatura.isSelected();

        // txfMontoVencido.setText(considerar ? lblAdeudoVencido.getText() : "");
        txfMontoColegiatura.setText(considerar ? lblAdeudoVencido.getText() : "");

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
    private void actualizarSubTotal() {
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);


        // Sumar los montos de cada campo
        //total = total.add(ParseBigDecimal(txfMontoVencido.getText()));
        total = total.add(ParseBigDecimal(txfMontoColegiatura.getText()));
        total = total.add(ParseBigDecimal(txfMontoInscripcion.getText()));
        total = total.add(ParseBigDecimal(txfMontoLibros.getText()));
        total = total.add(ParseBigDecimal(txfMontoEventos.getText()));
        total = total.add(ParseBigDecimal(txfMontoAcademias.getText()));
        total = total.add(ParseBigDecimal(txfMontoUniforme.getText()));

        // Actualizar el total en la interfaz
        lblSubTotal.setText(total.toString());
    }

    /**
     * actualiza el subtotal
     */
    private void actualizarTotal() {
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        
        // Sumar los montos de cada campo
        //total = total.add(ParseBigDecimal(txfMontoVencido.getText()));
        total = total.add(validarDescuento());
        total = total.add(ParseBigDecimal(txfMontoInscripcion.getText()));
        total = total.add(ParseBigDecimal(txfMontoLibros.getText()));
        total = total.add(ParseBigDecimal(txfMontoEventos.getText()));
        total = total.add(ParseBigDecimal(txfMontoAcademias.getText()));
        total = total.add(ParseBigDecimal(txfMontoUniforme.getText()));

        // Actualizar el total en la interfaz
        lblTotal.setText(total.toString());
    }

    private BigDecimal validarDescuento(){
        if(!txfMontoColegiatura.getText().isBlank()){
            if (!lblDescuentoDescuento.getText().equalsIgnoreCase("0.00")){

                TicketRegistrarDTO ticket = TicketRegistrarCache.getInstance();

                BigDecimal descuento = new BigDecimal(lblDescuentoDescuento.getText());
                BigDecimal adeudo = new BigDecimal(lblAdeudoActual.getText());
                BigDecimal ingresado = new  BigDecimal(txfMontoColegiatura.getText());

                if(ingresado.compareTo(adeudo) == 0){

                    //hacemos visible el descuento
                    //lblDescuentoDescuento.setText(DescuentoCache.getInstance().getDescuento().toString());

                    //marcamos como verde la orilla del campo
                    txfMontoColegiatura.setStyle("-fx-border-color: #66b328; -fx-border-width: 2px;");

                    //guardamos en el ticket el tipo de descuento
                    ticket.setTipoDescuento(DescuentoCache.getInstance().getTipo());

                    //guaramos en el ticket el descuento
                    ticket.setMontoDescuento(DescuentoCache.getInstance().getDescuento());
                    System.out.println(ticket);

                    TicketRegistrarCache.setInstance(ticket);
                    //retornamos el adeudo menos el descuento
                    return adeudo.subtract(descuento);
                }
                else {

                    //guardamos en el ticket el tipo de descuento
                    ticket.setTipoDescuento("No aplica");

                    //guaramos en el ticket el descuento
                    ticket.setMontoDescuento(0.00);

                    //hacemos invisible el descuento
                    // lblDescuentoDescuento.setText("0.00");
                    System.out.println(ticket);
                    TicketRegistrarCache.setInstance(ticket);
                    //retornamos el adeudo
                    return ParseBigDecimal(ingresado.toString());
                }
            }
        }
        return new BigDecimal("0.00");
    }

    /**
     * Método para parsear el texto a BigDecimal, devolviendo BigDecimal.ZERO si es vacío o no válido
     */
    private BigDecimal ParseBigDecimal(String text) {
        try {
            return new BigDecimal(text.isEmpty() ? "0,00" : text);
        } catch (NumberFormatException e) {
            return new BigDecimal(0.00);
        }
    }

    /**
     * Verifica el formato de un campo de entrada y se cambia de color
     * el borde de su componente en caso de no ser valido
     *
     * @param textField campo de texto a validar su contenido
     * @param label campo que indica la cantidad total
     */
    private void verificarFormato(TextField textField, Label label) {
        try {

            // Verificamos si el texto no está vacío y es un número válido
            if (textField.getText().isEmpty()) {
                // Si el campo está vacío, puedes manejarlo de la forma que prefieras, por ejemplo:
                textField.setStyle("-fx-border-color: #000000;");
                textField.setDisable(false);
                actualizarTotal();
                actualizarSubTotal();
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
                actualizarSubTotal();
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


    /**
     * Lanza notificacion flotante indicando algun error
     * @param mensaje mensaje a mostrar en la notificacion
     */
    private void notificarError(String mensaje){
        Notifications.create()
                .title("Ups!!")
                .text(mensaje)
                .graphic(null)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .show();
    }

    /**
     * Muestra la ventana de detalles
     */
    @FXML
    public void mostrarDetalles(){

        String idCiclo = CicloEscolarCache.getInstance().getId();
        String matricula = AlumnoCache.getInstance().getMatricula();
        System.out.println(idCiclo);
        if (matricula != null){

            List<DetalleAdeudoDTO> detalles = servicioCuotas.obtenerDetallesAdeudosColegiatura(matricula,idCiclo);
            if(detalles != null){
                DetallesAdeudoCache.getInstance().setDetalles(detalles);
                mediador.mostrarPantallaColegiaturasAtrasadas();
            }else{
                notificarError("Hubo un error en la busqueda de detalles");
            }
        }else {
            notificarError("Selecione un alumno primero");
        }
        
    }

}