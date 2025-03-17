package mx.sgi.presentacion.interfaces;

import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.CuotaDTO;

import java.util.List;

public interface IServicioCuotas {

    public List<CuotaDTO> obtenerCuotasAlumno(String matricula)  throws Exception;

    public List<ColegiaturaAtrasadaDTO> obtenerColegiaturasAtrasadas(String matricula)  throws Exception;

}
