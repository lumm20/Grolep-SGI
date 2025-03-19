package mx.itson.sgi.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CuotaDTO {

    String adeudoVencido; //este es un total de los adeudos vencidos osea todos los meses que debe
    String adeudoColegiatura;
    String adeudoInscripcion;
    String adeudoLibros;
    String adeudoEventos;
    String adeudoAcademias;
    String adeudoUniformes;

    // Constructor con todos los campos
    public CuotaDTO(String adeudoVencido, String adeudoColegiatura, String adeudoInscripcion,
                    String adeudoLibros, String adeudoEventos, String adeudoAcademias,
                    String adeudoUniformes) {
        this.adeudoVencido = adeudoVencido;
        this.adeudoColegiatura = adeudoColegiatura;
        this.adeudoInscripcion = adeudoInscripcion;
        this.adeudoLibros = adeudoLibros;
        this.adeudoEventos = adeudoEventos;
        this.adeudoAcademias = adeudoAcademias;
        this.adeudoUniformes = adeudoUniformes;
    }
    
    public CuotaDTO() {
    	
    }

}
