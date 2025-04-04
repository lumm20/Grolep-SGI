package mx.itson.sgi.data_access.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.itson.sgi.data_access.entities.Alumno;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.Concepto;
import mx.itson.sgi.data_access.entities.Cuota;
import mx.itson.sgi.data_access.entities.DetallePago;
import mx.itson.sgi.data_access.entities.MetodoPago;
import mx.itson.sgi.data_access.entities.Pago;
import mx.itson.sgi.data_access.entities.Usuario;
import mx.itson.sgi.data_access.repositories.PagoRepository;
import mx.itson.sgi.dto.AlumnoConsultaDTO;
import mx.itson.sgi.dto.DetallePagoDTO;
import mx.itson.sgi.dto.MetodosPagoDTO;
import mx.itson.sgi.dto.PagoDTO;
import mx.itson.sgi.dto.RolDTO;
import mx.itson.sgi.dto.UsuarioDTO;

@Service
public class PagoService {
    @Autowired
    private PagoRepository repository;
    @Autowired
    private CuotaService cuotaService;

    public Pago obtenerPagoPorFolio(String folio){
        Optional<Pago> pago = repository.findById(folio);
        if(pago.isPresent()){
            return pago.get();
        }
        return null;
    }

    public List<Pago> obtenerPagosPorEstudiante(String matriculaEstudiante){
        List<Pago> pago = repository.findPagosPorEstudiante(new Alumno(matriculaEstudiante));

        if(pago!= null && !pago.isEmpty()){
            return pago;
        }
        return new ArrayList<Pago>();
    }

    public List<Pago> obtenerPagosPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        List<Pago> pago = repository.findByFechaBetween(fechaFin, fechaFin);

        if(pago!= null && !pago.isEmpty()){
            return pago;
        }
        return new ArrayList<Pago>();
    }

    public void registrarPago(PagoDTO pagoDTO){
        MetodoPago metodo;
        if(pagoDTO.getMetodoPago().equals(MetodosPagoDTO.Efectivo)){
            metodo = MetodoPago.EFECTIVO;
        }else if(pagoDTO.getMetodoPago().equals(MetodosPagoDTO.Tarjeta)){
            metodo = MetodoPago.TARJETA;
        }else{
            metodo = MetodoPago.TRANSFERENCIA;
        }
        Usuario cajero = new Usuario(pagoDTO.getIdUsuario());
        Alumno alumno = new Alumno(pagoDTO.getAlumno().getMatricula());
        Pago pago = new Pago(pagoDTO.getFolio(), pagoDTO.getFecha(), pagoDTO.getMontoTotal(),
        cajero, metodo,alumno,pagoDTO.getMontoDescuento(),pagoDTO.getTipoDescuento());
        CicloEscolar ciclo = new CicloEscolar(pagoDTO.getIdCicloEscolar());
        List<DetallePagoDTO> detallesDTO = pagoDTO.getCuotasPagadas();
        List<DetallePago> detalles = convertirDetallesPagos(detallesDTO, alumno,ciclo, pago);
        pago.setDetalles(detalles);
        repository.save(pago);
        
    }
    
    private List<DetallePago> convertirDetallesPagos(List<DetallePagoDTO> dtos,Alumno alumno, CicloEscolar ciclo, Pago pago){
        List<DetallePago> detalles = new ArrayList<>();
        Cuota cuota;
        Concepto concepto;
        for (DetallePagoDTO dto : dtos) {
            concepto = Concepto.valueOf(dto.getConceptoCuota());
            cuota = cuotaService.obtenerCuotaPorAlumnoConceptoCiclo(alumno, concepto, ciclo);
            System.out.println("-------------------");
            System.out.println(cuota);
            System.out.println("-------------------");
            detalles.add(new DetallePago(cuota, dto.getMontoPagado(),pago));
        }
        return detalles;
    }

    public long getCantidadPagos(){
        return repository.count();
    }

    public Double obtenerTotalPagadoColegiatura(AlumnoConsultaDTO alumno, String ciclo){
        return repository.findTotalPagadoColegiatura(new Alumno(alumno.getMatricula()), new CicloEscolar(ciclo));
    }
}
