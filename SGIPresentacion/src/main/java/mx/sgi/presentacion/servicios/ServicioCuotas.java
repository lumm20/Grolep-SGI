package mx.sgi.presentacion.servicios;

import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.CuotasDTO;
import mx.itson.sgi.dto.DescuentoDTO;
import mx.sgi.presentacion.interfaces.IServicioCuotas;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
    public CuotasDTO obtenerCuotasAlumno(String matricula, CicloEscolarDTO cicloEscolar) throws Exception {
        // Simulación de obtención de datos
        System.out.println("Valores que llegaron: " + matricula + " " + cicloEscolar);

        List<CuotasDTO> listaCuotas = new ArrayList<>();

        // Creación de varias cuotas simuladas con diferentes valores
        listaCuotas.add(new CuotasDTO("500.00", "1000.00", "500.00", "700.00", "1200.00", "400.00", "800.00", "CIVICA", new DescuentoDTO("Primer Periodo", new BigDecimal("300.00"))));
        listaCuotas.add(new CuotasDTO("200.00", "800.00", "400.00", "600.00", "1000.00", "350.00", "750.00", "DEPORTIVA", new DescuentoDTO("Segundo Periodo", new BigDecimal("250.00"))));
        listaCuotas.add(new CuotasDTO("0.00", "900.00", "300.00", "500.00", "1100.00", "450.00", "700.00", null, new DescuentoDTO("Primer Periodo", new BigDecimal("400.00"))));
        listaCuotas.add(new CuotasDTO("100.00", "950.00", "350.00", "550.00", "1150.00", "375.00", "725.00", "SEC", new DescuentoDTO("Segundo Periodo", new BigDecimal("350.00"))));
        listaCuotas.add(new CuotasDTO("600.00", "1100.00", "600.00", "750.00", "1300.00", "500.00", "850.00", null, new DescuentoDTO("Primer Periodo", new BigDecimal("320.00"))));
        listaCuotas.add(new CuotasDTO("450.00", "1025.00", "525.00", "675.00", "1250.00", "425.00", "775.00", "SEC", new DescuentoDTO("Segundo Periodo", new BigDecimal("280.00"))));
        listaCuotas.add(new CuotasDTO("300.00", "975.00", "475.00", "625.00", "1205.00", "390.00", "740.00", null, new DescuentoDTO("Primer Periodo", new BigDecimal("330.00"))));

        // Selección aleatoria de una cuota
        Random random = new Random();
        return listaCuotas.get(random.nextInt(listaCuotas.size()));
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
