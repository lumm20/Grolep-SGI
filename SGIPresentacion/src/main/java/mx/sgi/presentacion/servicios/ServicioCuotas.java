package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.CuotaDTO;
import mx.sgi.presentacion.interfaces.IServicioCuotas;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public List<CuotaDTO> obtenerCuotasAlumno(String matricula, CicloEscolarDTO cicloEscolar) throws Exception {
        // Simulación de obtención de datos (en un caso real, aquí iría la lógica de la consulta)

        List<CuotaDTO> cuotas = new ArrayList<>();

        // Ejemplo de cuotas para el alumno con la matrícula indicada
            cuotas.add(new CuotaDTO(new BigDecimal("100O.00"), "COLEGIATURA"));
            cuotas.add(new CuotaDTO(new BigDecimal("500.00"), "INSCRIPCION"));
            cuotas.add(new CuotaDTO(new BigDecimal("700.00"), "LIBROS"));
            cuotas.add(new CuotaDTO(new BigDecimal("1200.00"), "EVENTOS"));
            cuotas.add(new CuotaDTO(new BigDecimal("400.00"), "ACADEMIAS"));
            cuotas.add(new CuotaDTO(new BigDecimal("800.00"), "UNIFORME"));


        return cuotas;
    }

    /**
     * Metodo que obtiene las colegiaturas atrasadas de un alumno, solo se pueden
     * obtener tres colegiaturas atrasadas.
     *
     * @param matricula matricula del alumno
     * @param cicloEscolar ciclo escolar en el que se encuentran las colegiaturas
     */
    @Override
    public List<ColegiaturaAtrasadaDTO> obtenerColegiaturasAtrasadas(String matricula, CicloEscolarDTO cicloEscolar) throws Exception {
        // Simulación de la lógica para obtener las colegiaturas atrasadas (por ejemplo, con una API o base de datos)
        List<ColegiaturaAtrasadaDTO> colegiaturasAtrasadas = new ArrayList<>();

        // Aquí agregarías la lógica real para consultar las colegiaturas atrasadas
        // Usamos un ejemplo con datos simulados
        // Agregamos tres colegiaturas atrasadas simuladas
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(new BigDecimal("1500.00"), LocalDate.of(2024, 1, 15)));
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(new BigDecimal("1800.00"), LocalDate.of(2024, 2, 15)));
        colegiaturasAtrasadas.add(new ColegiaturaAtrasadaDTO(new BigDecimal("1200.00"), LocalDate.of(2024, 3, 15)));

        return colegiaturasAtrasadas;
    }


}
