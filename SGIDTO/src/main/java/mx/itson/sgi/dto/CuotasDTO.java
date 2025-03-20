package mx.itson.sgi.dto;

import lombok.Data;

@Data
public class CuotasDTO {

    private String adeudoVencido; //este es un total de los adeudos vencidos osea todos los meses que debe
    private String adeudoColegiatura;
    private String adeudoInscripcion;
    private String adeudoLibros;
    private String adeudoEventos;
    private String adeudoAcademias;
    private String adeudoUniformes;
    private String beca;
    private DescuentoDTO descuento;

    // Constructor con todos los campos
    public CuotasDTO(String adeudoVencido, String adeudoColegiatura, String adeudoInscripcion,
                     String adeudoLibros, String adeudoEventos, String adeudoAcademias,
                     String adeudoUniformes, String beca, DescuentoDTO descuento) {
        this.adeudoVencido = adeudoVencido;
        this.adeudoColegiatura = adeudoColegiatura;
        this.adeudoInscripcion = adeudoInscripcion;
        this.adeudoLibros = adeudoLibros;
        this.adeudoEventos = adeudoEventos;
        this.adeudoAcademias = adeudoAcademias;
        this.adeudoUniformes = adeudoUniformes;
        this.beca = beca;
        this.descuento = descuento;
    }

}
