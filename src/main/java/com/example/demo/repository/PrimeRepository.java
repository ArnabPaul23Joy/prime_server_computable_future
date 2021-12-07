package com.example.demo.repository;
import com.example.demo.model.PrimeNumber;
import com.example.demo.nonSpringModules.ListToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//import lombok.data;
@Repository
public class PrimeRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PrimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public int insertPrime(PrimeNumber primeNumber) {
        String sql = "" +
                "INSERT INTO Prime_Number (" +
                "n, execution_time, status)" +
                "VALUES (?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                primeNumber.getN(), Long.toString(primeNumber.getExecution_time()),
                primeNumber.getStatus()
        );
    }

}
