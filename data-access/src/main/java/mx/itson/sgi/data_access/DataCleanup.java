package mx.itson.sgi.data_access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataCleanup {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional
    public void truncateTables() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        
        try {
            jdbcTemplate.execute("TRUNCATE TABLE detalles_pagos");
            jdbcTemplate.execute("TRUNCATE TABLE pagos");
            jdbcTemplate.execute("TRUNCATE TABLE cuotas_mensuales");
            jdbcTemplate.execute("TRUNCATE TABLE cuotas");
            jdbcTemplate.execute("TRUNCATE TABLE ciclos_escolares");
            jdbcTemplate.execute("TRUNCATE TABLE alumnos");
            jdbcTemplate.execute("TRUNCATE TABLE usuarios");
            
        } finally {
            jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }
}
