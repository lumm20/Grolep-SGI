package mx.itson.sgi.data_access.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import mx.itson.sgi.data_access.entities.CicloEscolar;
import mx.itson.sgi.data_access.repositories.CicloRepository;
import mx.itson.sgi.dto.CicloEscolarDTO;

@Component
public class CicloEscolarService {

    @Autowired
	CicloRepository repository;

    
    public CicloEscolar obtenerCicloEscolarPorId(String id){
        Optional<CicloEscolar> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
    public CicloEscolar obtenerCicloEscolarPorFechas(CicloEscolar ciclo){
        Optional<CicloEscolar> optional = repository.findByFechaInicioAndFechaFin(ciclo.getFechaInicio(),ciclo.getFechaFin());
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
    public CicloEscolarDTO obtenerCicloActual(){
        Optional<CicloEscolar> optional = repository.findCicloActual();
        if(optional.isPresent()){
            CicloEscolar ciclo = optional.get();
            CicloEscolarDTO dto = new CicloEscolarDTO(ciclo.getFechaInicio().toString(),ciclo.getFechaFin().toString());
            dto.setId(ciclo.getId());
            return dto;
        }
        return null;
    }
    public List<CicloEscolarDTO> obtenerCiclos(){
        List<CicloEscolar> ciclos = (List<CicloEscolar>) repository.findAll();
        List<CicloEscolarDTO> dtos = new ArrayList<>();
        if(ciclos != null){
            ciclos.stream().forEach(c->{
                CicloEscolarDTO dto = new CicloEscolarDTO(c.getFechaInicio().toString(),c.getFechaFin().toString());
                dto.setId(c.getId());
                dtos.add(dto);
            });
            System.out.println("??");
            System.out.println(dtos);
            return dtos;
        }
        return new ArrayList<CicloEscolarDTO>();
    }

    @Transactional
    protected void cargarCiclosEscolares() {
        CicloEscolar ciclo1 = new CicloEscolar();
        ciclo1.setId("23-24");
        ciclo1.setFechaInicio(LocalDate.of(2023, 8, 15));
        ciclo1.setFechaFin(LocalDate.of(2024, 7, 15));
        repository.save(ciclo1);

        CicloEscolar ciclo2 = new CicloEscolar();
        ciclo2.setId("24-25");
        ciclo2.setFechaInicio(LocalDate.of(2024, 8, 15));
        ciclo2.setFechaFin(LocalDate.of(2025, 7, 15));
        repository.save(ciclo2);
    }
}
