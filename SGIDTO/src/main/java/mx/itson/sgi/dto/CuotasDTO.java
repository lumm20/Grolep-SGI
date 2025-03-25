package mx.itson.sgi.dto;

import lombok.Data;

@Data
public class CuotasDTO {

    Double adeudoVencido = 0.0; //este es un total de los adeudos vencidos osea todos los meses que debe
    Double adeudoColegiatura = 0.0;
    Double adeudoInscripcion = 0.0;
    Double adeudoLibros = 0.0;
    Double adeudoEventos = 0.0;
    Double adeudoAcademias = 0.0;
    Double adeudoUniformes = 0.0;

    // Constructor con todos los campos
    public CuotasDTO(Double adeudoVencido, Double adeudoColegiatura, Double adeudoInscripcion,
                     Double adeudoLibros, Double adeudoEventos, Double adeudoAcademias,
                     Double adeudoUniformes) {
        this.adeudoVencido = adeudoVencido;
        this.adeudoColegiatura = adeudoColegiatura;
        this.adeudoInscripcion = adeudoInscripcion;
        this.adeudoLibros = adeudoLibros;
        this.adeudoEventos = adeudoEventos;
        this.adeudoAcademias = adeudoAcademias;
        this.adeudoUniformes = adeudoUniformes;
    }
    
    public CuotasDTO() {
    	
    }

}
