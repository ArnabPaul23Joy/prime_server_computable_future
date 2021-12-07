package com.example.demo.nonSpringModules;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JasonResponseObject {
    Integer n_th_fibonacci;
    Integer total_primes;
    List<String> prime_numbers;
    public JasonResponseObject(Integer n_th_fibonacci,Integer total_primes, List<String> prime_numbers){
        this.n_th_fibonacci=n_th_fibonacci;
        this.total_primes=total_primes;
        this.prime_numbers=prime_numbers;
    }
}
