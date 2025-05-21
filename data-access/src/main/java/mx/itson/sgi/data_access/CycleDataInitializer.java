package mx.itson.sgi.data_access;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.entities.DetalleCiclo;
import mx.itson.sgi.data_access.repositories.CicloRepository;
import mx.itson.sgi.data_access.repositories.DetalleCicloRepository;

@Service
public class CycleDataInitializer {

    @Autowired
	private CicloRepository cicloRepository;
    @Autowired
    private DetalleCicloRepository detalleCicloRepository;

    @Transactional
    protected List<CicloEscolar> cargarCiclosEscolares() {
        List<CicloEscolar> ciclos = new ArrayList<>();
        CicloEscolar ciclo1 = new CicloEscolar();
        ciclo1.setId("23-24");
        ciclo1.setFechaInicio(LocalDate.of(2023, 8, 15));
        ciclo1.setFechaFin(LocalDate.of(2024, 6, 15));
        ciclos.add(cicloRepository.save(ciclo1));

        CicloEscolar ciclo2 = new CicloEscolar();
        ciclo2.setId("24-25");
        ciclo2.setFechaInicio(LocalDate.of(2024, 8, 15));
        ciclo2.setFechaFin(LocalDate.of(2025, 6, 15));
        ciclos.add(cicloRepository.save(ciclo2));
        cargarDetallesCiclos(ciclos);
        return ciclos;
    }

    private void cargarDetallesCiclos(List<CicloEscolar> ciclos){
        DetalleCiclo detalle = new DetalleCiclo(ciclos.get(0), 2300.00, 1600.00, 350.00, 800.00, 500.00, 2000.00);
        DetalleCiclo detalle2 = new DetalleCiclo(ciclos.get(1), 2500.00, 1800.00, 400.00, 900.00, 500.00, 2000.00);
        detalleCicloRepository.saveAll(List.of(detalle,detalle2));
    }

}
