package mx.itson.sgi.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CicloEscolarDTO {

    private LocalDate inicio;
    private LocalDate fin;

}
