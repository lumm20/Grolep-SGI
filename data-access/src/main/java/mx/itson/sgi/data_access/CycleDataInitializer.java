package mx.itson.sgi.data_access;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.repositories.CicloRepository;

@Service
public class CycleDataInitializer {

    @Autowired
	private CicloRepository cicloRepository;
    @Transactional
    protected List<CicloEscolar> cargarCiclosEscolares() {
        List<CicloEscolar> ciclos = new ArrayList<>();
        CicloEscolar ciclo1 = new CicloEscolar();
        ciclo1.setId("23-24");
        ciclo1.setFechaInicio(LocalDate.of(2023, 8, 15));
        ciclo1.setFechaFin(LocalDate.of(2024, 7, 15));
        ciclos.add(cicloRepository.save(ciclo1));

        CicloEscolar ciclo2 = new CicloEscolar();
        ciclo2.setId("24-25");
        ciclo2.setFechaInicio(LocalDate.of(2024, 8, 15));
        ciclo2.setFechaFin(LocalDate.of(2025, 7, 15));
        ciclos.add(cicloRepository.save(ciclo2));
        return ciclos;
    }

}
