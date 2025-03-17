package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.CuotaDTO;
import mx.sgi.presentacion.interfaces.IServicioCuotas;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.List;


public class ServicioCuotas implements IServicioCuotas {

    // varibale para las peticiones
    private HttpClient client;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioCuotas() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Metodo que obtiene los adeudos por cuota de cada alumno
     * @param matricula matricula del alumno
     */
    @Override
    public List<CuotaDTO> obtenerCuotasAlumno(String matricula)  throws Exception{

    }

    /**
     * Metodo que obtiene las colegiaturas atrasadas de un alumno
     * @param matricula matricula matricula del alumno
     */
    @Override
    public List<ColegiaturaAtrasadaDTO> obtenerColegiaturasAtrasadas(String matricula)  throws Exception{

    }

}
