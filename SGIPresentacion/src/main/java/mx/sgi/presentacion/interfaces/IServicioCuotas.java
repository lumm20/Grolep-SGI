package mx.sgi.presentacion.interfaces;

import mx.itson.sgi.dto.CicloEscolarDTO;
import mx.itson.sgi.dto.ColegiaturaAtrasadaDTO;
import mx.itson.sgi.dto.CuotasDTO;

import java.util.List;

public interface IServicioCuotas {

    public CuotasDTO obtenerCuotasAlumno(String matricula, String cicloEscolar)  throws Exception;

    public List<ColegiaturaAtrasadaDTO> obtenerColegiaturasAtrasadas(String matricula, CicloEscolarDTO cicloEscolar)  throws Exception;

}
