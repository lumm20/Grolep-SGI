package mx.itson.sgi.data_access;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.CuotaMensual;
import mx.itson.sgi.data_access.entities.DetallePago;
import mx.itson.sgi.data_access.entities.MetodoPago;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.AlumnoRepository;
import mx.itson.sgi.data_access.repositories.CicloRepository;
import mx.itson.sgi.data_access.repositories.CuotaMensualRepository;
import mx.itson.sgi.data_access.repositories.CuotaRepository;
import mx.itson.sgi.data_access.repositories.PagoRepository;
import mx.itson.sgi.data_access.repositories.UsuarioRepository;

public class DataInitializer {

    @Autowired
	private UsuarioRepository userRepository;
	@Autowired
	private CicloRepository cicloRepository;
	@Autowired
	private CuotaRepository cuotaRepository;
	@Autowired
	private CuotaMensualRepository cuotaMensualRepository;
	@Autowired
	private AlumnoRepository alumnoRepository;
	@Autowired
	private PagoRepository pagoRepository;

    private Alumno buscarAlumno(String matricula){
        Optional<Alumno> alumno = alumnoRepository.findById(matricula);
        if(alumno.isPresent()){
            return alumno.get();
        }
        return null;
    }
    private Usuario buscarUsuario(String correo){
        Optional<Usuario> usuario = userRepository.findByCorreo(correo);
        if(usuario.isPresent()){
            return usuario.get();
        }
        return null;
    }

    private Cuota buscarCuota(Long id){
        Optional<Cuota> cuota = cuotaRepository.findById(id);
        if(cuota.isPresent()){
            return cuota.get();
        }
        return null;
    }
    private Cuota buscarCuotaMensual(List<CuotaMensual> cuotas, Month mes){
        for(CuotaMensual cuota : cuotas){
            if(cuota.getMes().getMonth().equals(mes)){
                return cuota;
            }
        }
        return null;
    }

    protected void guardarPagos()throws Exception{
        Usuario cajero = buscarUsuario("lum@escuela.com");
        if (cajero == null) {
            System.out.println("Cajero no encontrado");
            throw new Exception("Cajero no encontrado");
        }

        try {
            for (Pago pago : crearPagosAlumno1(cajero)) {
                pagoRepository.save(pago);
            }
            for (Pago pago : crearPagosAlumno2(cajero)) {
                pagoRepository.save(pago);
            }
            for (Pago pago : crearPagosAlumno3(cajero)) {
                pagoRepository.save(pago);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception ("Error al guardar los pagos");
        }
        
    }

    private List<CuotaMensual> buscarCuotasMensual(Alumno alumno, CicloEscolar ciclo){
        Optional<List<CuotaMensual>> cuotas = cuotaMensualRepository.findByAlumnoAndCiclo(alumno, ciclo);
        if(cuotas.isPresent()){
            List<CuotaMensual> cuotasMensuales = cuotas.get();
            if(!cuotasMensuales.isEmpty()){
                return cuotasMensuales;
            }
        }
        return null;
    }

    private List<Pago> crearPagosAlumno1(Usuario cajero)throws Exception {
        Alumno alumno1 = buscarAlumno("A202200001");
        if (alumno1 == null) {
            System.out.println("Alumno no encontrado");
            throw new Exception("Alumno no encontrado");
        }
        List<CuotaMensual> cuotasMensuales = buscarCuotasMensual(alumno1, new CicloEscolar("23-24"));
        if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
            System.out.println("No se encontraron cuotas mensuales para el alumno");
            throw new Exception("No se encontraron cuotas mensuales para el alumno");
        }
        List<Pago> pagos = new ArrayList<>();
        //agregar pago de inscripcion
        pagos.add(new Pago("P202300004", LocalDateTime.of(2023, 8, 15, 9, 15, 30),
        2300.0, 0.0, "No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuota(1L), 2300.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de agosto
        pagos.add(new Pago("P202300006", LocalDateTime.of(2023, 8, 20, 10, 0, 0),
        1600.0, 0.0, "No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1600.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de uniformes
        pagos.add(new Pago("P202300003", LocalDateTime.of(2023, 8, 13, 10, 15, 0),
        2350.0, 0.0, "No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuota(3L), 2000.0),new DetallePago(new Cuota(4L), 350.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de septiembre
        pagos.add(new Pago("P202300008", LocalDateTime.of(2023, 9, 10, 9, 50, 0), 1600.00,
        0.0, "No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 2300.0)), MetodoPago.TRANSFERENCIA));
        
        //agregar pago de colegiatura de octubre y noviembre
        pagos.add(new Pago("P202300011", LocalDateTime.of(2023, 11, 15, 8, 30, 0), 3200.00, 
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1600.0),
                new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1600.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de diciembre
        pagos.add(new Pago("P202300013", LocalDateTime.of(2023, 12, 5, 12, 0, 0), 1600.00, 
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.DECEMBER), 1600.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de enero y febrero
        pagos.add(new Pago("P202400002", LocalDateTime.of(2024, 2, 10, 10, 10, 0), 3200.00, 
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.JANUARY), 1600.0),
        new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.FEBRUARY), 1600.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de marzo
        pagos.add(new Pago("P202400003", LocalDateTime.of(2024, 3, 8, 11, 20, 0), 1600.00, 
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MARCH), 1600.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de abril y mayo
        pagos.add(new Pago("P202400007", LocalDateTime.of(2024, 5, 13, 8, 45, 0), 3200.00,
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.APRIL), 1600.0),
        new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MAY), 1600.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de junio
        pagos.add(new Pago("P202400010", LocalDateTime.of(2024, 6, 5, 10, 30, 0), 1600.00,
        0.0,"No aplica", cajero, alumno1,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.JUNE), 1600.0)), MetodoPago.EFECTIVO));
        
        return pagos;
    }

    private List<Pago> crearPagosAlumno2(Usuario cajero)throws Exception {
        Alumno alumno = buscarAlumno("A202200002");
        if (alumno == null) {
            System.out.println("Alumno no encontrado");
            throw new Exception("Alumno no encontrado");
        }
        List<CuotaMensual> cuotasMensuales = buscarCuotasMensual(alumno, new CicloEscolar("23-24"));
        if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
            System.out.println("No se encontraron cuotas mensuales para el alumno");
            throw new Exception("No se encontraron cuotas mensuales para el alumno");
        }
        List<Pago> pagos = new ArrayList<>();
        //agregar pago de libros y uniformes
        pagos.add(new Pago("P202300002", LocalDateTime.of(2023, 8, 10, 11, 0, 0), 2350.00, 0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuota(6L), 2000.0),
        new DetallePago(buscarCuota(5L), 350.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de agosto
        pagos.add(new Pago("P202300007", LocalDateTime.of(2023, 8, 20, 11, 0, 0), 1200.00,
         0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1600.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de septiembre, octubre y noviembre
        pagos.add(new Pago("P202300010", LocalDateTime.of(2023, 11, 5, 9, 20, 0), 3600.00,
        0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1200.0),
        new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1200.0),
        new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1200.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de diciembre y enero
        pagos.add(new Pago("P202400001", LocalDateTime.of(2024, 1, 15, 10, 40, 0), 2400.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1200.0),
                new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1200.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de febrero y marzo
        pagos.add(new Pago("P202400004", LocalDateTime.of(2024, 3, 15, 9, 0, 0), 2400.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.FEBRUARY), 1200.0),
        new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MARCH), 1200.0)), MetodoPago.TRANSFERENCIA));
        
        //agregar pago de colegiatura de abril
        pagos.add(new Pago("P202400006", LocalDateTime.of(2024, 4, 15, 11, 0, 0), 1200.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.APRIL), 1200.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de mayo
        pagos.add(new Pago("P202400011", LocalDateTime.of(2024, 5, 20, 10, 30, 0), 1200.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MAY), 1200.0)), MetodoPago.TRANSFERENCIA));
        
        return pagos;
    }

    private List<Pago> crearPagosAlumno3(Usuario cajero) throws Exception {
        Alumno alumno = buscarAlumno("A202200003");
        if (alumno == null) {
            System.out.println("Alumno no encontrado");
            throw new Exception("Alumno no encontrado");
        }
        List<CuotaMensual> cuotasMensuales = buscarCuotasMensual(alumno, new CicloEscolar("23-24"));
        if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
            System.out.println("No se encontraron cuotas mensuales para el alumno");
            throw new Exception("No se encontraron cuotas mensuales para el alumno");
        }
        List<Pago> pagos = new ArrayList<>();
        //agregar pago de inscripcion, eventos y libros
        pagos.add(new Pago("P202300001", LocalDateTime.of(2023, 8, 5, 10, 0, 0), 3450.00,
        0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuota(6L), 2000.0),
        new DetallePago(buscarCuota(11L), 2300.0),
        new DetallePago(buscarCuota(10L), 800.0),
        new DetallePago(buscarCuota(8L), 350.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de agosto
        pagos.add(new Pago("P202300005", LocalDateTime.of(2023, 8, 18, 9, 30, 0), 1300.00,
         0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1300.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de septiembre
        pagos.add(new Pago("P202300009", LocalDateTime.of(2023, 9, 18, 9, 30, 0), 1300.00,
         0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1300.0)), MetodoPago.TRANSFERENCIA));
        
        //agregar pago de colegiatura de octubre, noviembre y diciembre
        pagos.add(new Pago("P202300012", LocalDateTime.of(2023, 12, 1, 11, 0, 0), 3900.00,
        0.0, "No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1300.0),
        new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1300.0),
        new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1300.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de enero, febrero, marzo y abril
        pagos.add(new Pago("P202400005", LocalDateTime.of(2024, 4, 10, 9, 15, 0), 5200.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1300.0),
                new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY), 1300.0),
                new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.MARCH), 1300.0),
                new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.APRIL), 1300.0)), MetodoPago.EFECTIVO));
        
        //agregar pago de colegiatura de mayo y junio
        pagos.add(new Pago("P202400009", LocalDateTime.of(2024, 6, 2, 10, 20, 0), 2600.00, 
        0.0,"No aplica", cajero, alumno,
        List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MAY), 1300.0),
        new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.JUNE), 1300.0)), MetodoPago.TRANSFERENCIA));
        
        return pagos;
    }
}
