package mx.itson.sgi.data_access.entities;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

import jakarta.persistence.Column;
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
    @Column
    private Double adeudo;
    @Expose
    private Double montoPagado;
    @Expose
    private LocalDate mes;

    public CuotaMensual() {
    }

    public CuotaMensual(Long id) {
        super(id);
    }
    
    public CuotaMensual(Double montoBase, CicloEscolar ciclo, Concepto concepto, 
    Double adeudoGeneradoDelMes, Double adeudoAcumulado,Double montoPagado, LocalDate mes) {
        super(montoBase, ciclo, concepto);
        // this.adeudoGeneradoDelMes = adeudoGeneradoDelMes;
        // this.adeudoAcumulado = adeudoAcumulado;
        this.montoPagado = montoPagado;
        this.mes = mes;
        // this.cuotaDelMes = montoBase;
    }
    
    public CuotaMensual(Double montoBase, CicloEscolar ciclo, Concepto concepto, Double adeudo, 
    Double montoPagado, LocalDate mes) {
        super(montoBase, ciclo, concepto);
        this.adeudo = adeudo;
        this.montoPagado = montoPagado;
        this.mes = mes;
        // this.cuotaDelMes = montoBase;
    }

    public CuotaMensual(Double montoBase, CicloEscolar ciclo, Concepto concepto) {
        super(montoBase, ciclo, concepto);
    }
    
    public String toString(){
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .excludeFieldsWithoutExposeAnnotation()
        .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
        .create();
        return gson.toJson(this);
    }
}

class LocalDateSerializer implements JsonSerializer<LocalDate> {
    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
