package mx.sgi.presentacion.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.itson.sgi.dto.PagoCuotaDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.vistas.TicketRegistrarDTO;
import mx.sgi.presentacion.caches.TicketRegistrarCache;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioPagos;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TicketController implements Initializable {

    @FXML
    private Label lblFolio;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblCliente;

    @FXML
    private Label lblMetodo;

    @FXML
    private Label lblMontoVencidos;

    @FXML
    private Label  lblMontoColegiatura;

    @FXML
    private Label lblMontoInscripcion;

    @FXML
    private Label lblMontoILibros;

    @FXML
    private Label lblMontoIEventos;

    @FXML
    private Label lblMontoIAcademias;

    @FXML
    private Label lblMontoUniforme;

    @FXML
    private Label lblTipoDescuento;

    @FXML
    private Label lblDescuento;

    @FXML
    private Label lblSubTotal;

    @FXML
    private Label lblTotal;

    private ServicioPagos servicioPagos;

    private Mediador mediador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.servicioPagos = new ServicioPagos();
        this.mediador = Mediador.getInstance();

        cargarInfoPago();
    }


    /**
     * aqui se carga cada campo del pago en el ticket
     */
    private void cargarInfoPago(){
        //obtenemos el pago registrado en el cache
        TicketRegistrarDTO ticketCache = TicketRegistrarCache.getInstance();

        //recogemos todos los datos del ticket
        String total = ticketCache.getMontoTotal().toString();
        String folio = ticketCache.getFolio().toString();
        String fecha = ticketCache.getFecha().toString();
        String cliente = ticketCache.getAlumno().getNombres();
        String metodoPago = ticketCache.getMetodoPago();
        String montoVencidos = ticketCache.getMontoVencidos().toString();
        String montoColegiatura = ticketCache.getMontoColegiatura().toString();
        String montoInscripcion = ticketCache.getMontoInscripcion().toString();
        String montoLibros = ticketCache.getMontoLibros().toString();
        String montoEventos = ticketCache.getMontoEventos().toString();
        String montoAcademias = ticketCache.getMontoAcademias().toString();
        String montoUniforme = ticketCache.getMontoUniforme().toString();
        String tipoDescuento = ticketCache.getTipoDescuento();
        String descuento = ticketCache.getDescuento().toString();
        String subTotal = ticketCache.getSubTotal().toString();

        lblTotal.setText(total);
        lblFolio.setText(ticketCache.getFolio().toString());
        lblFecha.setText(ticketCache.getFecha().toString());
        lblCliente.setText(cliente);
        lblMetodo.setText(metodoPago);
        lblMontoVencidos.setText(montoVencidos);
        lblMontoColegiatura.setText(montoColegiatura);
        lblMontoInscripcion.setText(montoInscripcion);
        lblMontoILibros.setText(montoLibros);
        lblMontoIEventos.setText(montoEventos);
        lblMontoIAcademias.setText(montoAcademias);
        lblMontoUniforme.setText(montoUniforme);
        lblTipoDescuento.setText(tipoDescuento);
        lblDescuento.setText(descuento);
        lblSubTotal.setText(subTotal);

    }

    /**
     *
     */
    @FXML
    public void confirmarPago(){
        try {
            //intancia del ticket
            TicketRegistrarDTO ticket = TicketRegistrarCache.getInstance();

            //nueva instancia para pago
            PagoDTO pago = new PagoDTO();

            pago.setFolio(ticket.getFolio());
            pago.setFecha(ticket.getFecha());
            pago.setHora(ticket.getHora());
            pago.setAlumno(ticket.getAlumno());
            pago.setMetodoPago(ticket.getMetodoPago());
            pago.setCuotasPagadas(crearCuotas());
            pago.setTipoDescuento(ticket.getTipoDescuento());
            pago.setDescuento(ticket.getDescuento());
            pago.setSubTotal(ticket.getSubTotal());
            pago.setMontoTotal(ticket.getMontoTotal());
            pago.setUsuario(ticket.getUsuario());

            //registramos el pago
            servicioPagos.registrarPago(pago);

            //refrescamos los pagos del alumno que pago
            mediador.refrescarPantallaPagos();

            //cerramos la pantalla
            cancelar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    private List<PagoCuotaDTO> crearCuotas(){

        BigDecimal comparador = new BigDecimal(0.00);

        TicketRegistrarDTO ticket =  TicketRegistrarCache.getInstance();

        List<PagoCuotaDTO> cuotas = new ArrayList<>();

        if (ticket.getMontoVencidos().compareTo(comparador) != 0) {
            PagoCuotaDTO cuota = new PagoCuotaDTO();

            cuota.setFecha(ticket.getFecha());
            cuota.setHora(ticket.getHora());
            cuota.setMonto(ticket.getMontoVencidos());
            cuota.getConcepto();

            cuotas.add(cuota);
        }
        if (ticket.getMontoColegiatura().compareTo(comparador) != 0) {
            PagoCuotaDTO cuota = new PagoCuotaDTO();

            cuota.setFecha(ticket.getFecha());
            cuota.setHora(ticket.getHora());
            cuota.setMonto(ticket.getMontoColegiatura());
            cuota.getConcepto();

            cuotas.add(cuota);
        }
        if (ticket.getMontoInscripcion().compareTo(comparador) != 0) {
            PagoCuotaDTO cuota = new PagoCuotaDTO();

            cuota.setFecha(ticket.getFecha());
            cuota.setHora(ticket.getHora());
            cuota.setMonto(ticket.getMontoInscripcion());
            cuota.getConcepto();

            cuotas.add(cuota);
        }
        if (ticket.getMontoLibros().compareTo(comparador) != 0) {
            PagoCuotaDTO cuota = new PagoCuotaDTO();

            cuota.setFecha(ticket.getFecha());
            cuota.setHora(ticket.getHora());
            cuota.setMonto(ticket.getMontoLibros());
            cuota.getConcepto();

            cuotas.add(cuota);
        }
        if (ticket.getMontoEventos().compareTo(comparador) != 0) {
            PagoCuotaDTO cuota = new PagoCuotaDTO();

            cuota.setFecha(ticket.getFecha());
            cuota.setHora(ticket.getHora());
            cuota.setMonto(ticket.getMontoEventos());
            cuota.getConcepto();

            cuotas.add(cuota);
        }
        if (ticket.getMontoAcademias().compareTo(comparador) != 0) {
            PagoCuotaDTO cuota = new PagoCuotaDTO();

            cuota.setFecha(ticket.getFecha());
            cuota.setHora(ticket.getHora());
            cuota.setMonto(ticket.getMontoAcademias());
            cuota.getConcepto();

            cuotas.add(cuota);
        }
        if (ticket.getMontoUniforme().compareTo(comparador) != 0) {
            PagoCuotaDTO cuota = new PagoCuotaDTO();

            cuota.setFecha(ticket.getFecha());
            cuota.setHora(ticket.getHora());
            cuota.setMonto(ticket.getMontoAcademias());
            cuota.getConcepto();

            cuotas.add(cuota);
        }

        return cuotas;
    }


    /**
     *
     */
    @FXML
    private void cancelar(){
        //limpiamos el cache del ticket guardado
        TicketRegistrarCache.limpiarCache();

        //cerramos la ventana
        Stage stage = (Stage) lblTotal.getScene().getWindow(); // Obtener el Stage (ventana) actual
        stage.close(); // Cerrar la ventana de inicio de sesiónS
    }

}
