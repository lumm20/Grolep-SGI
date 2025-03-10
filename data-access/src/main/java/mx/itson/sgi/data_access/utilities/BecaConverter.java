package mx.itson.sgi.data_access.utilities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import mx.itson.sgi.data_access.entities.Beca;

@Converter(autoApply = true)
public class BecaConverter implements AttributeConverter<Beca, String> {
    
    @Override
    public String convertToDatabaseColumn(Beca beca) {
        return beca == null ? null : beca.getTipo();
    }
    
    @Override
    public Beca convertToEntityAttribute(String tipo) {
        if (tipo == null) {
            return null;
        }
        
        for (Beca beca : Beca.values()) {
            if (beca.getTipo().equals(tipo)) {
                return beca;
            }
        }
        
        return Beca.NINGUNA;
    }
}
