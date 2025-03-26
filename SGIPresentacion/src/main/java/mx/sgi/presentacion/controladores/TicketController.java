package mx.sgi.presentacion.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.itson.sgi.dto.CuotasDTO;
import mx.itson.sgi.dto.DetallePagoDTO;
import mx.itson.sgi.dto.MetodosPagoDTO;
import mx.itson.sgi.dto.PagoCuotaDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.vistas.TicketRegistrarDTO;
import mx.sgi.presentacion.caches.TicketRegistrarCache;
import mx.sgi.presentacion.mediador.Mediador;
import mx.sgi.presentacion.servicios.ServicioPagos;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class TicketController implements Initializable {

    @FXML
    Label lblTotal;

    @FXML
    Label lblFolio;

    @FXML
    Label lblFecha;

    @FXML
    Label lblCliente;

    @FXML
    Label lblMetodo;

    @FXML
    Label lblMontoVencidos;

    @FXML
    Label  lblMontoColegiatura;

    @FXML
    Label lblMontoInscripcion;

    @FXML
    Label lblMontoILibros;

    @FXML
    Label lblMontoIEventos;

    @FXML
    Label lblMontoIAcademias;

    @FXML
    Label lblMontoUniforme;

    @FXML
    Label lblDescuento;

    ServicioPagos servicioPagos;

    Mediador mediador;

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

        String cliente = ticketCache.getAlumno().getNombre();

        List<DetallePagoDTO> detalles = ticketCache.getDetalles();
        Map<String, Double> mapaDetalles = new HashMap<>();

        for (DetallePagoDTO detalle : detalles) {
            mapaDetalles.put(detalle.getConceptoCuota(), detalle.getMontoPagado());
        }

        MetodosPagoDTO metodoPago = ticketCache.getMetodoPago();
        // String montoVencidos = ticketCache.getMontoVencidos().toString();
        // String montoColegiatura = ticketCache.getMontoColegiatura().toString();
        // String montoInscripcion = ticketCache.getMontoInscripcion().toString();
        // String montoLibros = ticketCache.getMontoLibros().toString();
        // String montoEventos = ticketCache.getMontoEventos().toString();
        // String montoAcademias = ticketCache.getMontoAcademias().toString();
        // String montoUniforme = ticketCache.getMontoUniforme().toString();
        //String descuento = ticketCache.getDescuento().toString();

        lblTotal.setText(total);
        lblFolio.setText(ticketCache.getFolio());
        lblFecha.setText(ticketCache.getFecha().toString());
        lblCliente.setText(cliente);
        lblMetodo.setText(metodoPago.toString().toLowerCase());
        //lblMontoVencidos.setText(mapaDetalles.get("Vencidos").toString());
        lblMontoColegiatura.setText(mapaDetalles.get("Colegiatura")!= null ? mapaDetalles.get("Colegiatura").toString() : "0.00");
        lblMontoInscripcion.setText(mapaDetalles.get("Inscripcion")!= null ? mapaDetalles.get("Inscripcion").toString() : "0.00");
        lblMontoILibros.setText(mapaDetalles.get("Libros")!= null ? mapaDetalles.get("Libros").toString() : "0.00");
        lblMontoIEventos.setText(mapaDetalles.get("Eventos")!= null ? mapaDetalles.get("Eventos").toString() : "0.00");
        lblMontoIAcademias.setText(mapaDetalles.get("Academias")!= null ? mapaDetalles.get("Academias").toString() : "0.00");
        lblMontoUniforme.setText(mapaDetalles.get("Uniforme")!= null ? mapaDetalles.get("Uniforme").toString() : "0.00");
        //lblDescuento.setText(descuento);

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

            pago.setMontoTotal(ticket.getMontoTotal());
            pago.setFolio(ticket.getFolio());
            // pago.setFecha(LocalDateTime.of(ticket.getFecha(), ticket.getHora()));
            pago.setAlumno(ticket.getAlumno());
            pago.setMetodoPago(ticket.getMetodoPago());
            pago.setCuotasPagadas(ticket.getDetalles());
            //pago.setCuotasPagadas(crearCuotas());
            pago.setIdCicloEscolar(ticket.getCiclo().getId());
            pago.setIdUsuario(ticket.getIdUsuario());
            pago.setDescuento(ticket.getTipoDescuento());

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


    /*private List<PagoCuotaDTO> crearCuotas(){

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
    }*/


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
