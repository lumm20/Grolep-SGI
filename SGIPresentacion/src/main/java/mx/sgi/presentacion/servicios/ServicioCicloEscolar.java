package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.sgi.presentacion.interfaces.IServicioCicloEscolar;

import java.net.http.HttpClient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicioCicloEscolar implements IServicioCicloEscolar {

    // varibale para las peticiones
    private HttpClient client;

    /**
     * Contructor que inicializa las variables de la clase
     */
    public ServicioCicloEscolar() {
        // Inicializar HttpClient
        this.client = HttpClient.newHttpClient();
    }


    @Override
    public List<CicloEscolarDTO> obtenerCiclosEscolares() throws Exception {
        try {
            // Obtener la fecha actual
            LocalDate fechaActual = LocalDate.now();

            // Suponiendo que el ciclo escolar tiene una duración de un año
            // Ciclo escolar actual
            LocalDate inicioCicloActual = LocalDate.of(fechaActual.getYear(), 9, 1); // Inicio del ciclo escolar actual (1 de septiembre)
            LocalDate finCicloActual = inicioCicloActual.plusMonths(9); // Fin del ciclo escolar actual (agregamos 9 meses)

            // Ciclo escolar siguiente
            LocalDate inicioCicloSiguiente = inicioCicloActual.plusYears(1); // El siguiente ciclo empieza en septiembre del año siguiente
            LocalDate finCicloSiguiente = inicioCicloSiguiente.plusMonths(9); // Fin del ciclo siguiente (agregamos 9 meses)

            // Crear y devolver la lista con los ciclos escolares
            List<CicloEscolarDTO> ciclosEscolares = new ArrayList<>();
            ciclosEscolares.add(new CicloEscolarDTO(inicioCicloActual, finCicloActual));
            ciclosEscolares.add(new CicloEscolarDTO(inicioCicloSiguiente, finCicloSiguiente));

            return ciclosEscolares;
        } catch (Exception ex) {
            throw new Exception("Error al obtener los ciclos escolares", ex);
        }
    }

}
