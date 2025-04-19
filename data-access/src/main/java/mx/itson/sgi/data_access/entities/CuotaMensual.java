package mx.itson.sgi.data_access.entities;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cuotas_mensuales")
@Data
@EqualsAndHashCode(callSuper = true)
public class CuotaMensual extends Cuota{
    @Expose
    private Double adeudoGeneradoDelMes;
    @Expose
    private Double cuotaDelMes;
    @Expose
    private Double adeudoAcumulado;
    @Expose
    private Double montoPagado;
    @Expose
    private LocalDate mes;

    public CuotaMensual() {
    }

    public CuotaMensual(Long id) {
        super(id);
    }
    
    public CuotaMensual(Double montoBase, CicloEscolar ciclo, Concepto concepto, Double adeudoGeneradoDelMes, Double adeudoAcumulado,Double montoPagado, LocalDate mes) {
        super(montoBase, ciclo, concepto);
        this.adeudoGeneradoDelMes = adeudoGeneradoDelMes;
        this.adeudoAcumulado = adeudoAcumulado;
        this.montoPagado = montoPagado;
        this.mes = mes;
        this.cuotaDelMes = montoBase;
    }

    public CuotaMensual(Double montoBase, CicloEscolar ciclo, Concepto concepto) {
        super(montoBase, ciclo, concepto);
    }
    
    public String toString(){
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
}
