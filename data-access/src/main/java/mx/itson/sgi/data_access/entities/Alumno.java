package mx.itson.sgi.data_access.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    private String matricula;

    @Column(nullable = false)
    private String nombres;
    
    @Column(nullable = false)
    private String apellidoPaterno;
    
    @Column(nullable = false)
    private String apellidoMaterno;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Venta> compras = new ArrayList<>();
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<ColegiaturaAtrasada> colegiaturasAtrasadas = new ArrayList<>();
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pago> pagos = new ArrayList<>();
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Cuota> cuotas = new ArrayList<>();

    public Alumno(String matricula, String nombres, String apellidoPaterno, String apellidoMaterno) {
        this.matricula = matricula;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }
    
    
}
