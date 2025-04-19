package mx.itson.sgi.data_access;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.Beca;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.CuotaMensual;
import mx.itson.sgi.data_access.entities.DetallePago;
import mx.itson.sgi.data_access.entities.MetodoPago;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.data_access.entities.Rol;
import mx.itson.sgi.data_access.entities.TipoBeca;
import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.AlumnoRepository;
import mx.itson.sgi.data_access.repositories.CicloRepository;
import mx.itson.sgi.data_access.repositories.CuotaMensualRepository;
import mx.itson.sgi.data_access.repositories.CuotaRepository;
import mx.itson.sgi.data_access.repositories.PagoRepository;
import mx.itson.sgi.data_access.repositories.UsuarioRepository;

@Component
public class DataInitializer {

    // @Autowired
	// private UsuarioRepository userRepository;
	// @Autowired
	// private CicloRepository cicloRepository;
	// @Autowired
	// private CuotaRepository cuotaRepository;
	// @Autowired
	// private CuotaMensualRepository cuotaMensualRepository;
	// @Autowired
	// private AlumnoRepository alumnoRepository;
	// @Autowired
	// private PagoRepository pagoRepository;

    @Autowired
    private UserDataInitializer userInit;
    @Autowired
    private CycleDataInitializer cycleInit;
    @Autowired
    private StudentDataInitializer studentInit;
    @Autowired
    private FeeDataInitializer feeInit;
    @Autowired
    private PaymentDataInitializer paymentInit;


    protected void initData()throws Exception{
        Usuario cajero = userInit.cargarUsuarios();//guardamos a los usuarios
		List<Alumno> alumnos = studentInit.cargarAlumnos();//guardamos a los alumnos
		Alumno alumno1 = alumnos.get(0);
		Alumno alumno2 = alumnos.get(1);
		Alumno alumno3 = alumnos.get(2);
		List<CicloEscolar> ciclos = cycleInit.cargarCiclosEscolares();//guardamos los ciclos escolares
		feeInit.cargarCuotas(ciclos.getFirst(), ciclos.getLast(), alumno1, alumno2, alumno3);//guardamos las cuotas
		paymentInit.cargarPagos2324(cajero, alumno1, alumno2, alumno3);//guardamos los pagos del 23-24
		paymentInit.cargarPagos2425(cajero, alumno1, alumno2, alumno3);//guardamos los pagos del 24-25
    }

    
    // -------------- METODOS PARA CARGAR LA INFO DE CICLOS ---------------
    // @Transactional
    // protected List<CicloEscolar> cargarCiclosEscolares() {
    //     List<CicloEscolar> ciclos = new ArrayList<>();
    //     CicloEscolar ciclo1 = new CicloEscolar();
    //     ciclo1.setId("23-24");
    //     ciclo1.setFechaInicio(LocalDate.of(2023, 8, 15));
    //     ciclo1.setFechaFin(LocalDate.of(2024, 7, 15));
    //     ciclos.add(cicloRepository.save(ciclo1));

    //     CicloEscolar ciclo2 = new CicloEscolar();
    //     ciclo2.setId("24-25");
    //     ciclo2.setFechaInicio(LocalDate.of(2024, 8, 15));
    //     ciclo2.setFechaFin(LocalDate.of(2025, 7, 15));
    //     ciclos.add(cicloRepository.save(ciclo2));
    //     return ciclos;
    // }

    // // -------------- METODOS PARA CARGAR LA INFO DE USUARIOS ---------------
    // @Transactional(readOnly = true)
    // private Usuario buscarUsuario(String correo){
    //     Optional<Usuario> usuario = userRepository.findByCorreo(correo);
    //     if(usuario.isPresent()){
    //         return usuario.get();
    //     }
    //     return null;
    // }

    
    // @Transactional
    // protected Usuario cargarUsuarios(){
    //     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    //     Usuario usuario1 = new Usuario();
    //     usuario1.setNombre("juan");
    //     String encodedPass1 = encoder.encode("admin123");
    //     usuario1.setContrasena(encodedPass1);
    //     usuario1.setRol(Rol.ADMIN);
    //     usuario1.setCorreo("admin@escuela.edu");
    //     userRepository.save(usuario1);

    //     Usuario usuario2 = new Usuario();
    //     usuario2.setNombre("luisa");
    //     String encodedPass2 = encoder.encode("lumm20");
    //     usuario2.setContrasena(encodedPass2);
    //     usuario2.setRol(Rol.CAJERO);
    //     usuario2.setCorreo("lum@escuela.com");
    //     usuario2 = userRepository.save(usuario2);
        
    //     Usuario usuario3 = new Usuario();
    //     usuario3.setNombre("maria");
    //     String encodedPass3 = encoder.encode("dir789");
    //     usuario3.setContrasena(encodedPass3);
    //     usuario3.setRol(Rol.ADMIN);
    //     usuario3.setCorreo("directora@escuela.edu");
    //     userRepository.save(usuario3);
        
    //     return usuario2;
    // }

    // // -------------- METODOS PARA CARGAR LA INFO DE ALUMNOS ---------------
    // @Transactional
    // protected List<Alumno> cargarAlumnos() {
    //     List<Alumno> alumnos = new ArrayList<>();
    //     Alumno alumno1 = new Alumno();
    //     alumno1.setMatricula("A20220001");
    //     alumno1.setNombre("Carlos Rodríguez Vega");
    //     alumno1.setBeca(new Beca(TipoBeca.NINGUNA,0.0));
    //     alumno1.setTelefonoPadre("5551234567");
    //     alumnos.add(alumnoRepository.save(alumno1));
        
    //     Alumno alumno2 = new Alumno();
    //     alumno2.setMatricula("A20220002");
    //     alumno2.setNombre("Ana Martínez Soto");
    //     alumno2.setBeca(new Beca(TipoBeca.SEC,30.0));
    //     alumno2.setTelefonoPadre("5559876543");
    //     alumnos.add(alumnoRepository.save(alumno2));
        
    //     Alumno alumno3 = new Alumno();
    //     alumno3.setMatricula("A20220003");
    //     alumno3.setNombre("Miguel López Jiménez");
    //     alumno3.setBeca(new Beca(TipoBeca.DEPORTIVA,40.0));
    //     alumno3.setTelefonoPadre("5552468013");
    //     alumnos.add(alumnoRepository.save(alumno3));

    //     return alumnos;
    // }
    
    // @Transactional(readOnly = true)
    // private Alumno buscarAlumno(String matricula){
    //     Optional<Alumno> alumno = alumnoRepository.findById(matricula);
    //     if(alumno.isPresent()){
    //         return alumno.get();
    //     }
    //     return null;
    // }
    
    // // ----------------------- METODOS PARA CARGAR INFORMACION DE LAS CUOTAS ---------------------
    // /**
    //  * Metodo principal para cargar la info de las cuotas
    //  * @param ciclo2324 instancia del ciclo escolar del 2023-2024
    //  * @param ciclo2425 instancia del ciclo escolar del 2024-2025
    //  * @param alumno1 instancia del alumno 1
    //  * @param alumno2 instancia del alumno 2
    //  * @param alumno3 instancia del alumno 3
    //  */
    // protected void cargarCuotas(CicloEscolar ciclo2324,CicloEscolar ciclo2425, Alumno alumno1, Alumno alumno2, Alumno alumno3) {
    //     Map<Long, Cuota> cuotasMap = new HashMap<>();
        
    //     // Cuotas del ciclo 23-24
    //     // ALUMNO 1
    //     cuotasMap.put(1L, crearCuota(2300.00, ciclo2324, alumno1, Concepto.INSCRIPCION));
    //     crearCuotasMensualesCiclo2324(alumno1, ciclo2324, 1600.00);//2L
    //     cuotasMap.put(13L, crearCuota(2000.00, ciclo2324, alumno1, Concepto.UNIFORMES));
    //     cuotasMap.put(14L, crearCuota(350.00, ciclo2324, alumno1, Concepto.LIBROS));
        
    //     // ALUMNO 2
    //     cuotasMap.put(5L, crearCuota(350.00, ciclo2324, alumno2, Concepto.LIBROS));
    //     cuotasMap.put(6L, crearCuota(2000.00, ciclo2324, alumno2, Concepto.UNIFORMES));
    //     crearCuotasMensualesCiclo2324(alumno2, ciclo2324, 1200.00);//7L
        
    //     // ALUMNO 3
    //     cuotasMap.put(8L, crearCuota(350.00, ciclo2324, alumno3, Concepto.LIBROS));
    //     crearCuotasMensualesCiclo2324(alumno3, ciclo2324, 1300.00);//9L
    //     cuotasMap.put(10L, crearCuota(800.00, ciclo2324, alumno3, Concepto.EVENTOS));
    //     cuotasMap.put(11L, crearCuota(2300.00, ciclo2324, alumno3, Concepto.INSCRIPCION));

    //     // Cuotas del ciclo 24-25
    //     // ALUMNO 1
    //     cuotasMap.put(12L, crearCuota(2500.00, ciclo2425, alumno1, Concepto.INSCRIPCION));
    //     crearCuotasMensualesCiclo2425(alumno1, ciclo2425, 1800.00);//13L
    //     cuotasMap.put(14L, crearCuota(2000.00, ciclo2425, alumno1, Concepto.UNIFORMES));
    //     cuotasMap.put(15L, crearCuota(400.00, ciclo2425, alumno1, Concepto.LIBROS));
        
    //     // ALUMNO 2
    //     cuotasMap.put(16L, crearCuota(400.00, ciclo2425, alumno2, Concepto.LIBROS));
    //     cuotasMap.put(17L, crearCuota(2500.00, ciclo2425, alumno2, Concepto.INSCRIPCION));
    //     cuotasMap.put(18L, crearCuota(2000.00, ciclo2425, alumno2, Concepto.UNIFORMES));
    //     crearCuotasMensualesCiclo2425(alumno2, ciclo2425, 1350.00);//19L
        
    //     // ALUMNO 3
    //     cuotasMap.put(20L, crearCuota(400.00, ciclo2425, alumno3, Concepto.LIBROS));
    //     crearCuotasMensualesCiclo2425(alumno3, ciclo2425, 1500.00);//21L
    //     cuotasMap.put(22L, crearCuota(2500.00, ciclo2425, alumno3, Concepto.INSCRIPCION));
        
    //     System.out.println("Cuotas creadas: " + cuotasMap);
    // }

    // /**
    //  * Metodo para crear y guardar los objetos de cuota 
    //  * @param montoBase Monto base de la cuota
    //  * @param ciclo Instancia del ciclo escolar de la cuota
    //  * @param alumno instancia del alumno de la cuota
    //  * @param concepto instancia del concepto de la cuota
    //  * @return el objeto Cuota guardado
    //  */
    // @Transactional
    // private Cuota crearCuota(Double montoBase, CicloEscolar ciclo, Alumno alumno, Concepto concepto) {
    //     Cuota cuota = new Cuota();
    //     cuota.setMontoBase(montoBase);
    //     cuota.setCiclo(ciclo);
    //     cuota.setAlumno(alumno);
    //     cuota.setConcepto(concepto);
    //     return cuotaRepository.save(cuota);
    // }

    // /**
    //  * Metodo para crear los objetos de CuotaMensual de colegiatura para un alumno en el ciclo 23-24
    //  * @param alumno instancia del alumno de las cuotas
    //  * @param ciclo instancia del ciclo escolar 23-24
    //  * @param montoBase monto base para la cuota de colegiatura en el ciclo 23-24
    //  */
    // @Transactional
    // private void crearCuotasMensualesCiclo2324(Alumno alumno, CicloEscolar ciclo, Double montoBase) {
    //     // Crear 11 cuotas mensuales de agosto 2023 a junio 2024
    //     LocalDate fechaInicio = ciclo.getFechaInicio();
        
    //     Double adeudoAcumulado =0.0;
    //     for (int i = 0; i < 11; i++) {
    //         LocalDate mesCuota = fechaInicio.plusMonths(i);
            
    //         CuotaMensual cuotaMensual = new CuotaMensual();
    //         cuotaMensual.setMontoBase(montoBase);
    //         cuotaMensual.setCiclo(ciclo);
    //         cuotaMensual.setAlumno(alumno);
    //         cuotaMensual.setConcepto(Concepto.COLEGIATURA);
    //         cuotaMensual.setMes(mesCuota);
    //         cuotaMensual.setMontoPagado(0.0); // Se actualizará con los pagos
    //         cuotaMensual.setAdeudoDelMes(montoBase); // Inicialmente todo es adeudo
            
    //         if(i>0){
    //             adeudoAcumulado+=montoBase;
    //         }
    //         cuotaMensual.setAdeudoAcumulado(adeudoAcumulado);
    //         cuotaMensualRepository.save(cuotaMensual);
    //     }
    // }

    // /**
    //  * Metodo para crear los objetos de CuotaMensual de colegiatura para un alumno en el ciclo 24-25
    //  * @param alumno instancia del alumno de las cuotas
    //  * @param ciclo instancia del ciclo escolar 24-25
    //  * @param montoBase monto base para la cuota de colegiatura en el ciclo 24-25
    //  */
    // @Transactional
    // private void crearCuotasMensualesCiclo2425(Alumno alumno, CicloEscolar ciclo, Double montoBase) {
    //     // crear las cuotas mensuales de agosto 2024 hasta la fecha actual
    //     LocalDate fechaInicio = ciclo.getFechaInicio();
    //     LocalDate hoy = LocalDate.now();
    //     // calcula los meses de diferencia entre la fecha de inicio del ciclo y hoy
    //     long meses = ChronoUnit.MONTHS.between(fechaInicio.withDayOfMonth(1), hoy.withDayOfMonth(1)) + 1;
    //     Double adeudoAcumulado = 0.0;
    //     for (int i = 0; i < meses; i++) {
    //         LocalDate mesCuota = fechaInicio.plusMonths(i);
            
    //         CuotaMensual cuotaMensual = new CuotaMensual();
    //         cuotaMensual.setMontoBase(montoBase);
    //         cuotaMensual.setCiclo(ciclo);
    //         cuotaMensual.setAlumno(alumno);
    //         cuotaMensual.setConcepto(Concepto.COLEGIATURA);
    //         cuotaMensual.setMes(mesCuota);
    //         cuotaMensual.setMontoPagado(0.0); //se actualizara con los pagos
    //         cuotaMensual.setAdeudoDelMes(montoBase); //inicialmente todo es adeudo
    //         if(i>0){
    //             adeudoAcumulado+=montoBase;
    //         }
    //         cuotaMensual.setAdeudoAcumulado(adeudoAcumulado);
            
    //         cuotaMensualRepository.save(cuotaMensual);
    //     }
    // }

    // /**
    //  * Busca las cuotas mensuales de colegiatura de un alumno dentro de un ciclo escolar
    //  * @param alumno Alumno del cual se buscaran las cuotas; debe tener la matricula inicializada
    //  * @param ciclo Ciclo escolar en el cual se buscaran las cuotas; debe tener el id inicializado
    //  * @return la lista de cuotas mensuales del alumno
    //  */
    // @Transactional(readOnly = true)
    // private List<CuotaMensual> buscarCuotasMensuales(Alumno alumno, CicloEscolar ciclo){
    //     Optional<List<CuotaMensual>> cuotas = cuotaMensualRepository.findByAlumnoAndCiclo(alumno, ciclo);
    //     if(cuotas.isPresent()){
    //         List<CuotaMensual> cuotasMensuales = cuotas.get();
    //         if(!cuotasMensuales.isEmpty()){
    //             return cuotasMensuales;
    //         }
    //     }
    //     return null;
    // }

    // @Transactional(readOnly = true)
    // private List<Cuota> buscarCuotas(Alumno alumno, CicloEscolar ciclo){
    //     List<Cuota> cuotas = cuotaRepository.findByAlumnoAndCiclo(alumno, ciclo);
    //     if(cuotas != null && !cuotas.isEmpty()){
    //         return cuotas;
    //     }
    //     return null;
    // }

    // @Transactional(readOnly = true)
    // private Cuota buscarCuota(Concepto concepto, List<Cuota> cuotas){
    //     for (Cuota cuota : cuotas) {
    //         if(cuota.getConcepto().equals(concepto)){
    //             return cuota;
    //         }
    //     }
    //     return null;
    // }

    // @Transactional(readOnly = true)
    // private CuotaMensual buscarCuotaMensual(List<CuotaMensual> cuotas, Month mes){
    //     for(CuotaMensual cuota : cuotas){
    //         if(cuota.getMes().getMonth().equals(mes)){
    //             return cuota;
    //         }
    //     }
    //     return null;
    // }
    // // ---------------- METODOS PARA CARGAR LA INFO DE LOS PAGOS ----------------

    // /**
    //  * Guarda todos los registros de los pagos por cada alumno en el ciclo 23-24
    //  * @throws Exception en caso de que no se encuentre el cajero, o haya
    //  * un error al guardar los pagos
    //  */
    // @Transactional
    // protected void cargarPagos2324(Usuario cajero,Alumno alumno1, Alumno alumno2, Alumno alumno3)throws Exception{
    //     try {
    //         for (Pago pago : crearPagos2324Alumno1(cajero,alumno1)) {
    //             pagoRepository.save(pago);
    //         }
    //         for (Pago pago : crearPagos2324Alumno2(cajero,alumno2)) {
    //             pagoRepository.save(pago);
    //         }
    //         for (Pago pago : crearPagos2324Alumno3(cajero,alumno3)) {
    //             pagoRepository.save(pago);
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         throw new Exception ("Error al guardar los pagos");
    //     }
        
    // }

    //  /**
    //  * Guarda todos los registros de los pagos por cada alumno en el ciclo 23-24
    //  * @throws Exception en caso de que no se encuentre el cajero, o haya
    //  * un error al guardar los pagos
    //  */
    // @Transactional
    // protected void cargarPagos2425(Usuario cajero, Alumno alumno1, Alumno alumno2, Alumno alumno3)throws Exception{

    //     try {
    //         for (Pago pago : crearPagos2425Alumno1(cajero, alumno1)) {
    //             pagoRepository.save(pago);
    //         }
    //         for (Pago pago : crearPagos2425Alumno2(cajero, alumno2)) {
    //             pagoRepository.save(pago);
    //         }
    //         for (Pago pago : crearPagos2425Alumno3(cajero, alumno3)) {
    //             pagoRepository.save(pago);
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         throw new Exception ("Error al guardar los pagos");
    //     }
        
    // }

    // private CuotaMensual actualizarCuotaMensual( Double montoPagado, Double adeudoDelMes,Double adeudoAcumulado, CuotaMensual cuotaM){
    //     cuotaM.setMontoPagado(cuotaM.getMontoPagado()+montoPagado);
    //     Double acumuladoActual = adeudoAcumulado+adeudoDelMes;
    //     if(montoPagado>0.0){
    //         Double pagado = cuotaM.getMontoPagado();
    //         if(pagado>acumuladoActual){
    //             cuotaM.setAdeudoDelMes(cuotaM.getAdeudoDelMes()-(pagado-acumuladoActual));
    //         }
    //     }
    //     cuotaM.setAdeudoAcumulado(acumuladoActual);
    //     cuotaMensualRepository.save(cuotaM);
    //     return cuotaM;
    // }
    // /**
    //  * Crea los objetos de tipo Pago en el ciclo 23-24 referente al alumno 1
    //  * @param cajero Usuario el cual se registrara en el pago
    //  * @return la lista de pagos generados
    //  * @throws Exception en caso de que no se encuentre el alumno o las cuotas mensuales
    //  */
    // private List<Pago> crearPagos2324Alumno1(Usuario cajero, Alumno alumno1)throws Exception {
    //     List<CuotaMensual> cuotasMensuales = buscarCuotasMensuales(alumno1, new CicloEscolar("23-24"));
    //     if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
    //         System.out.println("No se encontraron cuotas mensuales para el alumno");
    //         throw new Exception("No se encontraron cuotas mensuales para el alumno");
    //     }
    //     List<Cuota> cuotas = buscarCuotas(alumno1, new CicloEscolar("23-24"));
    //     if (cuotas == null || cuotas.isEmpty()) {
    //         System.out.println("No se encontraron cuotas para el alumno");
    //         throw new Exception("No se encontraron cuotas para el alumno");
    //     }
    //     List<Pago> pagos = new ArrayList<>();
    //     //agregar pago de inscripcion
    //     pagos.add(new Pago("P202300004", LocalDateTime.of(2023, 8, 15, 9, 15, 30),
    //     2300.0, 0.0, "No aplica", cajero, alumno1,
    //     List.of(new DetallePago(buscarCuota(Concepto.INSCRIPCION, cuotas), 2300.0, new Pago("P202300004"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de agosto
    //     CuotaMensual cuotaM = buscarCuotaMensual(cuotasMensuales, Month.AUGUST);
    //     Double adeudoAcumulado = cuotaM.getAdeudoAcumulado(); 
    //     Double adeudoDelMes= 0.0; 
    //     pagos.add(new Pago("P202300006", LocalDateTime.of(2023, 8, 20, 10, 0, 0),
    //     1600.0, 0.0, "No aplica", cajero, alumno1,
    //     List.of(new DetallePago(cuotaM, 1600.0,new Pago("P202300006"))), MetodoPago.EFECTIVO));
    //     cuotaM = actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();
        
    //     //agregar pago de uniformes y libros
    //     pagos.add(new Pago("P202300003", LocalDateTime.of(2023, 8, 13, 10, 15, 0),
    //     2350.0, 0.0, "No aplica", cajero, alumno1,
    //     List.of(new DetallePago(buscarCuota(Concepto.UNIFORMES, cuotas), 2000.0,new Pago("P202300003")),new DetallePago(buscarCuota(Concepto.LIBROS, cuotas), 350.0,new Pago("P202300003"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de septiembre
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.SEPTEMBER);
    //     pagos.add(new Pago("P202300008", LocalDateTime.of(2023, 9, 10, 9, 50, 0), 1600.00,
    //     0.0, "No aplica", cajero, alumno1,
    //     List.of(new DetallePago(cuotaM, 1600.0,new Pago("P202300008"))), MetodoPago.TRANSFERENCIA));
    //     cuotaM = actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado = cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();

    //     //agregar pago de colegiatura de octubre y noviembre
    //     pagos.add(new Pago("P202300011", LocalDateTime.of(2023, 11, 15, 8, 30, 0), 3200.00, 
    //     0.0,"No aplica", cajero, alumno1,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1600.0,new Pago("P202300011")) ,
    //     new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1600.0,new Pago("P202300011"))), MetodoPago.EFECTIVO));
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.OCTOBER);
    //     cuotaM = actualizarCuotaMensual(0.0, adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado= cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.NOVEMBER);
    //     cuotaM = actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado = cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();
        
    //     //agregar pago de colegiatura de diciembre
    //     pagos.add(new Pago("P202300013", LocalDateTime.of(2023, 12, 5, 12, 0, 0), 1600.00, 
    //     0.0,"No aplica", cajero, alumno1,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.DECEMBER), 1600.0,new Pago("P202300013"))), MetodoPago.EFECTIVO));
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.DECEMBER);
    //     cuotaM = actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado = cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();

    //     //agregar pago de colegiatura de enero y febrero
    //     pagos.add(new Pago("P202400002", LocalDateTime.of(2024, 2, 10, 10, 10, 0), 3200.00, 
    //     0.0,"No aplica", cajero, alumno1,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.JANUARY), 1600.0,new Pago("P202400002")),
    //     new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.FEBRUARY), 1600.0,new Pago("P202400002"))), MetodoPago.EFECTIVO));
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.JANUARY);
    //     cuotaM = actualizarCuotaMensual(0.0, adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado = cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.FEBRUARY);
    //     cuotaM = actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado = cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();

    //     //agregar pago de colegiatura de marzo
    //     pagos.add(new Pago("P202400003", LocalDateTime.of(2024, 3, 8, 11, 20, 0), 1600.00, 
    //     0.0,"No aplica", cajero, alumno1,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MARCH), 1600.0,new Pago("P202400003"))), MetodoPago.EFECTIVO));
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.MARCH);
    //     cuotaM = actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado = cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();

    //     //agregar pago de colegiatura de abril y mayo
    //     pagos.add(new Pago("P202400007", LocalDateTime.of(2024, 5, 13, 8, 45, 0), 3200.00,
    //     0.0,"No aplica", cajero, alumno1,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.APRIL), 1600.0,new Pago("P202400007")),
    //     new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MAY), 1600.0,new Pago("P202400007"))), MetodoPago.EFECTIVO));
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.APRIL);
    //     cuotaM = actualizarCuotaMensual(0.0, adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado = cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.MAY);
    //     cuotaM = actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado = cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();

    //     //agregar pago de colegiatura de junio
    //     pagos.add(new Pago("P202400010", LocalDateTime.of(2024, 6, 5, 10, 30, 0), 1600.00,
    //     0.0,"No aplica", cajero, alumno1,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.JUNE), 1600.0,new Pago("P202400010"))), MetodoPago.EFECTIVO));
    //     cuotaM = buscarCuotaMensual(cuotasMensuales, Month.JUNE);
    //     cuotaM = actualizarCuotaMensual(pagos.getLast().getMontoTotal(), adeudoDelMes, adeudoAcumulado, cuotaM);
    //     adeudoAcumulado = cuotaM.getAdeudoAcumulado();
    //     adeudoDelMes = cuotaM.getAdeudoDelMes();

    //     return pagos;
    // }

    // /**
    //  * Crea los objetos de tipo Pago en el ciclo 23-24 referente al alumno 2
    //  * @param cajero Usuario el cual se registrara en el pago
    //  * @return la lista de pagos generados
    //  * @throws Exception en caso de que no se encuentre el alumno o las cuotas mensuales
    //  */
    // private List<Pago> crearPagos2324Alumno2(Usuario cajero, Alumno alumno)throws Exception {
    //     List<CuotaMensual> cuotasMensuales = buscarCuotasMensuales(alumno, new CicloEscolar("23-24"));
    //     if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
    //         System.out.println("No se encontraron cuotas mensuales para el alumno");
    //         throw new Exception("No se encontraron cuotas mensuales para el alumno");
    //     }
    //     List<Cuota> cuotas = buscarCuotas(alumno, new CicloEscolar("23-24"));
    //     if (cuotas == null || cuotas.isEmpty()) {
    //         System.out.println("No se encontraron cuotas  para el alumno");
    //         throw new Exception("No se encontraron cuotas para el alumno");
    //     }
    //     List<Pago> pagos = new ArrayList<>();
    //     //agregar pago de libros y uniformes
    //     pagos.add(new Pago("P202300002", LocalDateTime.of(2023, 8, 10, 11, 0, 0), 2350.00, 0.0, "No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuota(Concepto.UNIFORMES, cuotas), 2000.0,new Pago("P202300002")),
    //     new DetallePago(buscarCuota(Concepto.LIBROS, cuotas), 350.0,new Pago("P202300002"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de agosto
    //     pagos.add(new Pago("P202300007", LocalDateTime.of(2023, 8, 20, 11, 0, 0), 1200.00,
    //      0.0, "No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1200.0,new Pago("P202300007"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de septiembre, octubre y noviembre
    //     pagos.add(new Pago("P202300010", LocalDateTime.of(2023, 11, 5, 9, 20, 0), 3600.00,
    //     0.0, "No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1200.0,new Pago("P202300010")),
    //     new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1200.0,new Pago("P202300010")),
    //     new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1200.0,new Pago("P202300010"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de diciembre y enero
    //     pagos.add(new Pago("P202400001", LocalDateTime.of(2024, 1, 15, 10, 40, 0), 2400.00, 
    //     0.0,"No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1200.0,new Pago("P202400001")),
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1200.0,new Pago("P202400001"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de febrero y marzo
    //     pagos.add(new Pago("P202400004", LocalDateTime.of(2024, 3, 15, 9, 0, 0), 2400.00, 
    //     0.0,"No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.FEBRUARY), 1200.0,new Pago("P202400004")),
    //     new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MARCH), 1200.0,new Pago("P202400004"))), MetodoPago.TRANSFERENCIA));
        
    //     //agregar pago de colegiatura de abril
    //     pagos.add(new Pago("P202400006", LocalDateTime.of(2024, 4, 15, 11, 0, 0), 1200.00, 
    //     0.0,"No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.APRIL), 1200.0,new Pago("P202400006"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de mayo
    //     pagos.add(new Pago("P202400008", LocalDateTime.of(2024, 5, 20, 10, 30, 0), 1200.00, 
    //     0.0,"No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MAY), 1200.0,new Pago("P202400008"))), MetodoPago.TRANSFERENCIA));
        
    //     return pagos;
    // }

    // /**
    //  * Crea los objetos de tipo Pago en el ciclo 23-24 referente al alumno 2
    //  * @param cajero Usuario el cual se registrara en el pago
    //  * @return la lista de pagos generados
    //  * @throws Exception en caso de que no se encuentre el alumno o las cuotas mensuales
    //  */
    // private List<Pago> crearPagos2324Alumno3(Usuario cajero, Alumno alumno) throws Exception {
    //     List<CuotaMensual> cuotasMensuales = buscarCuotasMensuales(alumno, new CicloEscolar("23-24"));
    //     if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
    //         System.out.println("No se encontraron cuotas mensuales para el alumno");
    //         throw new Exception("No se encontraron cuotas mensuales para el alumno");
    //     }
    //     List<Cuota> cuotas = buscarCuotas(alumno, new CicloEscolar("23-24"));
    //     if (cuotas == null || cuotas.isEmpty()) {
    //         System.out.println("No se encontraron cuotas para el alumno");
    //         throw new Exception("No se encontraron cuotas para el alumno");
    //     }
    //     List<Pago> pagos = new ArrayList<>();
    //     //agregar pago de inscripcion, eventos y libros
    //     pagos.add(new Pago("P202300001", LocalDateTime.of(2023, 8, 5, 10, 0, 0), 3450.00,
    //     0.0, "No aplica", cajero, alumno,
    //     List.of(
    //     new DetallePago(buscarCuota(Concepto.INSCRIPCION,cuotas), 2300.0,new Pago("P202300001")),
    //     new DetallePago(buscarCuota(Concepto.EVENTOS,cuotas), 800.0,new Pago("P202300001")),
    //     new DetallePago(buscarCuota(Concepto.LIBROS,cuotas), 350.0,new Pago("P202300001"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de agosto
    //     pagos.add(new Pago("P202300005", LocalDateTime.of(2023, 8, 18, 9, 30, 0), 1300.00,
    //      0.0, "No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1300.0,new Pago("P202300005"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de septiembre
    //     pagos.add(new Pago("P202300009", LocalDateTime.of(2023, 9, 18, 9, 30, 0), 1300.00,
    //      0.0, "No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1300.0,new Pago("P202300009"))), MetodoPago.TRANSFERENCIA));
        
    //     //agregar pago de colegiatura de octubre, noviembre y diciembre
    //     pagos.add(new Pago("P202300012", LocalDateTime.of(2023, 12, 1, 11, 0, 0), 3900.00,
    //     0.0, "No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1300.0,new Pago("P202300012")),
    //     new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1300.0,new Pago("P202300012")),
    //     new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1300.0,new Pago("P202300012"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de enero, febrero, marzo y abril
    //     pagos.add(new Pago("P202400005", LocalDateTime.of(2024, 4, 10, 9, 15, 0), 5200.00, 
    //     0.0,"No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1300.0,new Pago("P202400005")),
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY), 1300.0,new Pago("P202400005")),
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.MARCH), 1300.0,new Pago("P202400005")),
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.APRIL), 1300.0,new Pago("P202400005"))), MetodoPago.EFECTIVO));
        
    //     //agregar pago de colegiatura de mayo y junio
    //     pagos.add(new Pago("P202400009", LocalDateTime.of(2024, 6, 2, 10, 20, 0), 2600.00, 
    //     0.0,"No aplica", cajero, alumno,
    //     List.of(new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.MAY), 1300.0,new Pago("P202400009")),
    //     new DetallePago(buscarCuotaMensual(cuotasMensuales, Month.JUNE), 1300.0,new Pago("P202400009"))), MetodoPago.TRANSFERENCIA));
        
    //     return pagos;
    // }

    // private List<Pago> crearPagos2425Alumno1(Usuario cajero, Alumno alumno) throws Exception {
    //     List<CuotaMensual> cuotasMensuales = buscarCuotasMensuales(alumno, new CicloEscolar("24-25"));
    //     if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
    //         System.out.println("No se encontraron cuotas mensuales para el alumno");
    //         throw new Exception("No se encontraron cuotas mensuales para el alumno");
    //     }
        
    //     List<Cuota> cuotas = buscarCuotas(alumno, new CicloEscolar("24-25"));
    //     if (cuotas == null || cuotas.isEmpty()) {
    //         System.out.println("No se encontraron cuotas para el alumno");
    //         throw new Exception("No se encontraron cuotas para el alumno");
    //     }
        
    //     List<Pago> pagos = new ArrayList<>();
        
    //     //pago por INSCRIPCION, UNIFORMES, LIBROS
    //     pagos.add(new Pago("P202400011", LocalDateTime.of(2024, 8, 1, 10, 0, 0), 4900.00, 
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuota(Concepto.INSCRIPCION,cuotas), 2500.0,new Pago("P202400011")), // INSCRIPCION
    //             new DetallePago(buscarCuota(Concepto.UNIFORMES,cuotas), 2000.0,new Pago("P202400011")), // UNIFORMES
    //             new DetallePago(buscarCuota(Concepto.LIBROS,cuotas), 400.0,new Pago("P202400011"))   // LIBROS
    //         ), MetodoPago.EFECTIVO));
        
    //     // AGOSTO COLEGIATURA
    //     pagos.add(new Pago("P202400015", LocalDateTime.of(2024, 8, 15, 10, 0, 0), 1800.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1800.0,new Pago("P202400015"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // SEPTIEMBRE COLEGIATURA
    //     pagos.add(new Pago("P202400017", LocalDateTime.of(2024, 9, 15, 10, 0, 0), 1800.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1800.0,new Pago("P202400017"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // OCTUBRE COLEGIATURA
    //     pagos.add(new Pago("P202400019", LocalDateTime.of(2024, 10, 15, 9, 0, 0), 1800.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1800.0,new Pago("P202400019"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // NOVIEMBRE COLEGIATURA
    //     pagos.add(new Pago("P202400021", LocalDateTime.of(2024, 11, 15, 10, 0, 0), 1800.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1800.0,new Pago("P202400021"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // DICIEMBRE COLEGIATURA
    //     pagos.add(new Pago("P202400026", LocalDateTime.of(2024, 12, 16, 10, 0, 0), 1800.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1800.0,new Pago("P202400026"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // ENERO COLEGIATURA
    //     pagos.add(new Pago("P202500002", LocalDateTime.of(2025, 1, 15, 10, 0, 0), 1800.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1800.0,new Pago("P202500002"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // ABRIL: 3 colegiaturas (FEB + MAR + ABR)
    //     pagos.add(new Pago("P202500008", LocalDateTime.of(2025, 4, 2, 10, 0, 0), 5400.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY), 1800.0,new Pago("P202500008")), // FEB
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.MARCH), 1800.0,new Pago("P202500008")), // MAR
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.APRIL), 1800.0,new Pago("P202500008"))  // ABR
    //         ), MetodoPago.EFECTIVO));
        
    //     return pagos;
    // }
    
    // private List<Pago> crearPagos2425Alumno3(Usuario cajero, Alumno alumno) throws Exception {
    //     List<CuotaMensual> cuotasMensuales = buscarCuotasMensuales(alumno, new CicloEscolar("24-25"));
    //     if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
    //         System.out.println("No se encontraron cuotas mensuales para el alumno");
    //         throw new Exception("No se encontraron cuotas mensuales para el alumno");
    //     }
        
    //     List<Cuota> cuotas = buscarCuotas(alumno, new CicloEscolar("24-25"));
    //     if (cuotas == null || cuotas.isEmpty()) {
    //         System.out.println("No se encontraron cuotas para el alumno");
    //         throw new Exception("No se encontraron cuotas para el alumno");
    //     }
        
    //     List<Pago> pagos = new ArrayList<>();
        
    //     //INSCRIPCION y LIBROS (falta UNIFORMES)
    //     pagos.add(new Pago("P202400012", LocalDateTime.of(2024, 8, 3, 10, 0, 0), 5400.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuota(Concepto.INSCRIPCION,cuotas), 2500.0,new Pago("P202400012")), // INSCRIPCION
    //             new DetallePago(buscarCuota(Concepto.LIBROS,cuotas), 400.0,new Pago("P202400012"))   // LIBROS
    //         ), MetodoPago.EFECTIVO));
        
    //     // OCTUBRE : colegiatura agosto,septiembre y octubre
    //     pagos.add(new Pago("P202400020", LocalDateTime.of(2024, 10, 15, 10, 0, 0), 4500.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1500.0,new Pago("P202400020")),
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1500.0,new Pago("P202400020")),
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1500.0,new Pago("P202400020"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // NOVIEMBRE COLEGIATURA
    //     pagos.add(new Pago("P202400022", LocalDateTime.of(2024, 11, 15, 13, 0, 0), 1500.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1500.0,new Pago("P202400022"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // DICIEMBRE COLEGIATURA
    //     pagos.add(new Pago("P202400025", LocalDateTime.of(2024, 12, 15, 10, 0, 0), 1500.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1500.0,new Pago("P202400025"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // ENERO COLEGIATURA
    //     pagos.add(new Pago("P202500001", LocalDateTime.of(2025, 1, 10, 10, 0, 0), 1500.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1500.0,new Pago("P202500001"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // FEBRERO COLEGIATURA
    //     pagos.add(new Pago("P202500005", LocalDateTime.of(2025, 2, 10, 12, 0, 0), 1500.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY), 1500.0,new Pago("P202500005"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // MARZO COLEGIATURA
    //     pagos.add(new Pago("P202500006", LocalDateTime.of(2025, 3, 10, 10, 0, 0), 1500.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.MARCH), 1500.0,new Pago("P202500006"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // ABRIL COLEGIATURA
    //     pagos.add(new Pago("P202500009", LocalDateTime.of(2025, 4, 7, 10, 0, 0), 1500.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.APRIL), 1500.0,new Pago("P202500009"))
    //         ), MetodoPago.EFECTIVO));
        
    //     return pagos;
    // }
    
    // private List<Pago> crearPagos2425Alumno2(Usuario cajero, Alumno alumno) throws Exception {
    //     List<CuotaMensual> cuotasMensuales = buscarCuotasMensuales(alumno, new CicloEscolar("24-25"));
    //     if (cuotasMensuales == null || cuotasMensuales.isEmpty()) {
    //         System.out.println("No se encontraron cuotas mensuales para el alumno");
    //         throw new Exception("No se encontraron cuotas mensuales para el alumno");
    //     }
        
    //     List<Cuota> cuotas = buscarCuotas(alumno, new CicloEscolar("24-25"));
    //     if (cuotas == null || cuotas.isEmpty()) {
    //         System.out.println("No se encontraron cuotas para el alumno");
    //         throw new Exception("No se encontraron cuotas para el alumno");
    //     }
        
    //     List<Pago> pagos = new ArrayList<>();
        
    //     // INSCRIPCION, UNIFORMES y LIBROS
    //     pagos.add(new Pago("P202400013", LocalDateTime.of(2024, 8, 3, 11, 0, 0), 6100.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuota(Concepto.UNIFORMES,cuotas), 2000.0,new Pago("P202400013")), // UNIFORMES
    //             new DetallePago(buscarCuota(Concepto.LIBROS,cuotas), 400.0,new Pago("P202400013")),  // LIBROS
    //             new DetallePago(buscarCuota(Concepto.INSCRIPCION,cuotas), 2500.0,new Pago("P202400013")),  // INSCRIPCION
    //             new DetallePago(buscarCuotaMensual(buscarCuotasMensuales(alumno, new CicloEscolar("23-24")),Month.JUNE), 1200.0,new Pago("P202400013"))  // COLEGIATURA JUNIO 23-24
    //         ), MetodoPago.EFECTIVO));
        
    //     // AGOSTO COLEGIATURA
    //     pagos.add(new Pago("P202400014", LocalDateTime.of(2024, 8, 10, 10, 0, 0), 1350.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.AUGUST), 1350.0,new Pago("P202400014"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // SEPTIEMBRE COLEGIATURA
    //     pagos.add(new Pago("P202400016", LocalDateTime.of(2024, 9, 10, 10, 0, 0), 1350.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.SEPTEMBER), 1350.0,new Pago("P202400016"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // OCTUBRE COLEGIATURA
    //     pagos.add(new Pago("P202400018", LocalDateTime.of(2024, 10, 10, 10, 0, 0), 1350.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.OCTOBER), 1350.0,new Pago("P202400018"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // NOVIEMBRE COLEGIATURA
    //     pagos.add(new Pago("P202400023", LocalDateTime.of(2024, 11, 18, 10, 0, 0), 1350.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.NOVEMBER), 1350.0,new Pago("P202400023"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // DICIEMBRE COLEGIATURA
    //     pagos.add(new Pago("P202400024", LocalDateTime.of(2024, 12, 10, 10, 0, 0), 1350.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.DECEMBER), 1350.0,new Pago("P202400024"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // ENERO COLEGIATURA
    //     pagos.add(new Pago("P202500003", LocalDateTime.of(2025, 1, 16, 10, 0, 0), 1350.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.JANUARY), 1350.0,new Pago("P202500003"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // FEBRERO COLEGIATURA
    //     pagos.add(new Pago("P202500004", LocalDateTime.of(2025, 2, 10, 10, 0, 0), 1350.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.FEBRUARY), 1350.0,new Pago("P202500004"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // MARZO COLEGIATURA
    //     pagos.add(new Pago("P202500007", LocalDateTime.of(2025, 3, 20, 10, 0, 0), 1350.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.MARCH), 1350.0,new Pago("P202500007"))
    //         ), MetodoPago.EFECTIVO));
        
    //     // ABRIL COLEGIATURA
    //     pagos.add(new Pago("P202500010", LocalDateTime.of(2025, 4, 9, 10, 0, 0), 1350.00,
    //         0.0, "No aplica", cajero, alumno,
    //         List.of(
    //             new DetallePago(buscarCuotaMensual(cuotasMensuales,Month.APRIL), 1350.0,new Pago("P202500010"))
    //         ), MetodoPago.EFECTIVO));
        
    //     return pagos;
    // }
}
