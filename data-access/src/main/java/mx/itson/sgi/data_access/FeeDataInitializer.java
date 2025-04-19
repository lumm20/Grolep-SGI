package mx.itson.sgi.data_access;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.CuotaMensual;
import mx.itson.sgi.data_access.repositories.CuotaMensualRepository;
import mx.itson.sgi.data_access.repositories.CuotaRepository;

@Service
public class FeeDataInitializer {

    @Autowired
	private CuotaRepository cuotaRepository;
	@Autowired
	private CuotaMensualRepository cuotaMensualRepository;
    /**
     * Metodo principal para cargar la info de las cuotas
     * @param ciclo2324 instancia del ciclo escolar del 2023-2024
     * @param ciclo2425 instancia del ciclo escolar del 2024-2025
     * @param alumno1 instancia del alumno 1
     * @param alumno2 instancia del alumno 2
     * @param alumno3 instancia del alumno 3
     */
    protected void cargarCuotas(CicloEscolar ciclo2324,CicloEscolar ciclo2425, Alumno alumno1, Alumno alumno2, Alumno alumno3) {
        Map<Long, Cuota> cuotasMap = new HashMap<>();
        
        // Cuotas del ciclo 23-24
        // ALUMNO 1
        cuotasMap.put(1L, crearCuota(2300.00, ciclo2324, alumno1, Concepto.INSCRIPCION));
        crearCuotasMensualesCiclo2324(alumno1, ciclo2324, 1600.00);//2L
        cuotasMap.put(13L, crearCuota(2000.00, ciclo2324, alumno1, Concepto.UNIFORMES));
        cuotasMap.put(14L, crearCuota(350.00, ciclo2324, alumno1, Concepto.LIBROS));
        
        // ALUMNO 2
        cuotasMap.put(5L, crearCuota(350.00, ciclo2324, alumno2, Concepto.LIBROS));
        cuotasMap.put(6L, crearCuota(2000.00, ciclo2324, alumno2, Concepto.UNIFORMES));
        crearCuotasMensualesCiclo2324(alumno2, ciclo2324, 1200.00);//7L
        
        // ALUMNO 3
        cuotasMap.put(8L, crearCuota(350.00, ciclo2324, alumno3, Concepto.LIBROS));
        crearCuotasMensualesCiclo2324(alumno3, ciclo2324, 1300.00);//9L
        cuotasMap.put(10L, crearCuota(800.00, ciclo2324, alumno3, Concepto.EVENTOS));
        cuotasMap.put(11L, crearCuota(2300.00, ciclo2324, alumno3, Concepto.INSCRIPCION));

        // Cuotas del ciclo 24-25
        // ALUMNO 1
        cuotasMap.put(12L, crearCuota(2500.00, ciclo2425, alumno1, Concepto.INSCRIPCION));
        crearCuotasMensualesCiclo2425(alumno1, ciclo2425, 1800.00);//13L
        cuotasMap.put(14L, crearCuota(2000.00, ciclo2425, alumno1, Concepto.UNIFORMES));
        cuotasMap.put(15L, crearCuota(400.00, ciclo2425, alumno1, Concepto.LIBROS));
        
        // ALUMNO 2
        cuotasMap.put(16L, crearCuota(400.00, ciclo2425, alumno2, Concepto.LIBROS));
        cuotasMap.put(17L, crearCuota(2500.00, ciclo2425, alumno2, Concepto.INSCRIPCION));
        cuotasMap.put(18L, crearCuota(2000.00, ciclo2425, alumno2, Concepto.UNIFORMES));
        crearCuotasMensualesCiclo2425(alumno2, ciclo2425, 1350.00);//19L
        
        // ALUMNO 3
        cuotasMap.put(20L, crearCuota(400.00, ciclo2425, alumno3, Concepto.LIBROS));
        crearCuotasMensualesCiclo2425(alumno3, ciclo2425, 1500.00);//21L
        cuotasMap.put(22L, crearCuota(2500.00, ciclo2425, alumno3, Concepto.INSCRIPCION));
        
        System.out.println("Cuotas creadas: " + cuotasMap);
    }

    /**
     * Metodo para crear y guardar los objetos de cuota 
     * @param montoBase Monto base de la cuota
     * @param ciclo Instancia del ciclo escolar de la cuota
     * @param alumno instancia del alumno de la cuota
     * @param concepto instancia del concepto de la cuota
     * @return el objeto Cuota guardado
     */
    @Transactional
    private Cuota crearCuota(Double montoBase, CicloEscolar ciclo, Alumno alumno, Concepto concepto) {
        Cuota cuota = new Cuota();
        cuota.setMontoBase(montoBase);
        cuota.setCiclo(ciclo);
        cuota.setAlumno(alumno);
        cuota.setConcepto(concepto);
        return cuotaRepository.save(cuota);
    }

    /**
     * Metodo para crear los objetos de CuotaMensual de colegiatura para un alumno en el ciclo 23-24
     * @param alumno instancia del alumno de las cuotas
     * @param ciclo instancia del ciclo escolar 23-24
     * @param montoBase monto base para la cuota de colegiatura en el ciclo 23-24
     */
    @Transactional
    private void crearCuotasMensualesCiclo2324(Alumno alumno, CicloEscolar ciclo, Double montoBase) {
        // Crear 11 cuotas mensuales de agosto 2023 a junio 2024
        LocalDate fechaInicio = ciclo.getFechaInicio();
        
        // Double adeudoAcumulado =0.0;
        for (int i = 0; i < 11; i++) {
            LocalDate mesCuota = fechaInicio.plusMonths(i);
            
            CuotaMensual cuotaMensual = new CuotaMensual();
            cuotaMensual.setMontoBase(montoBase);
            cuotaMensual.setCiclo(ciclo);
            cuotaMensual.setAlumno(alumno);
            cuotaMensual.setConcepto(Concepto.COLEGIATURA);
            cuotaMensual.setMes(mesCuota);
            cuotaMensual.setMontoPagado(0.0); // Se actualizará con los pagos
            cuotaMensual.setAdeudoGeneradoDelMes(montoBase); // Inicialmente todo es adeudo
            cuotaMensual.setCuotaDelMes(montoBase);
            
            // if(i>0){
            //     adeudoAcumulado+=montoBase;
            // }
            cuotaMensual.setAdeudoAcumulado(0.0);
            cuotaMensualRepository.save(cuotaMensual);
        }
    }

    /**
     * Metodo para crear los objetos de CuotaMensual de colegiatura para un alumno en el ciclo 24-25
     * @param alumno instancia del alumno de las cuotas
     * @param ciclo instancia del ciclo escolar 24-25
     * @param montoBase monto base para la cuota de colegiatura en el ciclo 24-25
     */
    @Transactional
    private void crearCuotasMensualesCiclo2425(Alumno alumno, CicloEscolar ciclo, Double montoBase) {
        // crear las cuotas mensuales de agosto 2024 hasta la fecha actual
        LocalDate fechaInicio = ciclo.getFechaInicio();
        LocalDate hoy = LocalDate.now();
        // calcula los meses de diferencia entre la fecha de inicio del ciclo y hoy
        long meses = ChronoUnit.MONTHS.between(fechaInicio.withDayOfMonth(1), hoy.withDayOfMonth(1)) + 1;
        // Double adeudoAcumulado = 0.0;
        for (int i = 0; i < meses; i++) {
            LocalDate mesCuota = fechaInicio.plusMonths(i);
            
            CuotaMensual cuotaMensual = new CuotaMensual();
            cuotaMensual.setMontoBase(montoBase);
            cuotaMensual.setCiclo(ciclo);
            cuotaMensual.setAlumno(alumno);
            cuotaMensual.setConcepto(Concepto.COLEGIATURA);
            cuotaMensual.setMes(mesCuota);
            cuotaMensual.setMontoPagado(0.0); //se actualizara con los pagos
            cuotaMensual.setAdeudoGeneradoDelMes(montoBase); //inicialmente todo es adeudo
            cuotaMensual.setCuotaDelMes(montoBase); 
            // if(i>0){
            //     adeudoAcumulado+=montoBase;
            // }
            cuotaMensual.setAdeudoAcumulado(0.0);
            
            cuotaMensualRepository.save(cuotaMensual);
        }
    }

    /**
     * Busca las cuotas mensuales de colegiatura de un alumno dentro de un ciclo escolar
     * @param alumno Alumno del cual se buscaran las cuotas; debe tener la matricula inicializada
     * @param ciclo Ciclo escolar en el cual se buscaran las cuotas; debe tener el id inicializado
     * @return la lista de cuotas mensuales del alumno
     */
    @Transactional(readOnly = true)
    protected List<CuotaMensual> buscarCuotasMensuales(Alumno alumno, CicloEscolar ciclo){
        Optional<List<CuotaMensual>> cuotas = cuotaMensualRepository.findByAlumnoAndCiclo(alumno, ciclo);
        if(cuotas.isPresent()){
            List<CuotaMensual> cuotasMensuales = cuotas.get();
            if(!cuotasMensuales.isEmpty()){
                return cuotasMensuales;
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    protected List<Cuota> buscarCuotas(Alumno alumno, CicloEscolar ciclo){
        List<Cuota> cuotas = cuotaRepository.findByAlumnoAndCiclo(alumno, ciclo);
        if(cuotas != null && !cuotas.isEmpty()){
            return cuotas;
        }
        return null;
    }

    @Transactional(readOnly = true)
    protected Cuota buscarCuota(Concepto concepto, List<Cuota> cuotas){
        for (Cuota cuota : cuotas) {
            if(cuota.getConcepto().equals(concepto)){
                return cuota;
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    protected CuotaMensual buscarCuotaMensual(List<CuotaMensual> cuotas, Month mes){
        for(CuotaMensual cuota : cuotas){
            if(cuota.getMes().getMonth().equals(mes)){
                return cuota;
            }
        }
        return null;
    }

    @Transactional
    protected CuotaMensual actualizarCuotaMensual( Double montoPagado, Double adeudoDelMes,Double adeudoAcumulado, CuotaMensual cuotaM){
        cuotaM.setMontoPagado(cuotaM.getMontoPagado()+montoPagado);
        Double acumuladoActual = adeudoAcumulado+adeudoDelMes;
        if(montoPagado>0.0){
            Double pagado = cuotaM.getMontoPagado();
            if(pagado>=acumuladoActual+cuotaM.getCuotaDelMes()){
                cuotaM.setAdeudoGeneradoDelMes(cuotaM.getAdeudoGeneradoDelMes()-(pagado-acumuladoActual));
            }
        }
        System.out.println(cuotaM);
        cuotaM.setAdeudoAcumulado(acumuladoActual);
        CuotaMensual c = cuotaMensualRepository.save(cuotaM);
        return c;
    }
}
