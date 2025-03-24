package mx.sgi.presentacion.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
import java.util.List;
import java.util.ResourceBundle;

public class ColegiaturasAtrasadasController implements Initializable {

    @FXML
    Label lblMes1;

    @FXML
    Label  lblMes2;

    @FXML
    Label lblMes3;

    @FXML
    Label lblAdeudoAcumulado1;

    @FXML
    Label lblAdeudoAcumulado2;

    @FXML
    Label lblAdeudoAcumulado3;

    @FXML
    Label lbllMontoPagado1;

    @FXML
    Label lbllMontoPagado2;

    @FXML
    Label lbllMontoPagado3;

    @FXML
    Label lblTotal;

    ServicioCuotas  servicioCuotas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarInstancias();
        iniciarComponentesEnInvisible();
        cargarAdeudosAtrasados();
    }

    private void iniciarInstancias(){
        this.servicioCuotas = new ServicioCuotas();
    }

    private void iniciarComponentesEnInvisible(){
        lblMes1.setVisible(false);
        lblAdeudoAcumulado1.setVisible(false);
        lbllMontoPagado1.setVisible(false);

        lblMes2.setVisible(false);
        lblAdeudoAcumulado2.setVisible(false);
        lbllMontoPagado2.setVisible(false);

        lblMes3.setVisible(false);
        lblAdeudoAcumulado3.setVisible(false);
        lbllMontoPagado3.setVisible(false);
    }

    private void cargarAdeudosAtrasados(){
        try {
            //traemos al estudiante registrado del cache
            AlumnoConsultaDTO alumno = AlumnoCache.getInstance();

            String matricula = alumno.getMatricula();
            CicloEscolarDTO cicloEscolar = CicloEscolarCache.getInstance();

            List<ColegiaturaAtrasadaDTO> atrasados = servicioCuotas.
                    obtenerColegiaturasAtrasadas(matricula, cicloEscolar);

            BigDecimal total = BigDecimal.ZERO;

            //cargamos los datos en la pantalla
            if (atrasados.get(0) != null) {
                lblMes1.setVisible(true);
                lblAdeudoAcumulado1.setVisible(true);
                lbllMontoPagado1.setVisible(true);

                ColegiaturaAtrasadaDTO colegiatura = atrasados.get(0);

                lblMes1.setText(colegiatura.getMes().getMonth().toString());
                lblAdeudoAcumulado1.setText(colegiatura.getAdeudoAcumulado().toString());
                lbllMontoPagado1.setText(colegiatura.getMontoPagado().toString());

                BigDecimal nuevoTotal = colegiatura.getAdeudoAcumulado();
                total.add(nuevoTotal);
            }
            if (atrasados.get(1) != null) {
                lblMes2.setVisible(true);
                lblAdeudoAcumulado2.setVisible(true);
                lbllMontoPagado2.setVisible(true);

                ColegiaturaAtrasadaDTO colegiatura = atrasados.get(1);

                lblMes2.setText(colegiatura.getMes().getMonth().toString());
                lblAdeudoAcumulado2.setText(colegiatura.getAdeudoAcumulado().toString());
                lbllMontoPagado2.setText(colegiatura.getMontoPagado().toString());

                BigDecimal nuevoTotal = colegiatura.getAdeudoAcumulado();
                total.add(nuevoTotal);
            }
            if (atrasados.get(2) != null) {
                lblMes3.setVisible(true);
                lblAdeudoAcumulado3.setVisible(true);
                lbllMontoPagado3.setVisible(true);

                ColegiaturaAtrasadaDTO colegiatura = atrasados.get(2);

                lblMes3.setText(colegiatura.getMes().getMonth().toString());
                lblAdeudoAcumulado3.setText(colegiatura.getAdeudoAcumulado().toString());
                lbllMontoPagado3.setText(colegiatura.getMontoPagado().toString());

                BigDecimal nuevoTotal = colegiatura.getAdeudoAcumulado();
                total.add(nuevoTotal);
            }

        } catch (Exception e) {
            notificarError(e.getMessage());
        }
    }

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
