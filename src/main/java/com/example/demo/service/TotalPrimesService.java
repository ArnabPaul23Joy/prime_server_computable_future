package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TotalPrimesService {
    private Logger logger = LoggerFactory.getLogger(PrimeGeneratorService.class);
    public Integer getTotalPrime(int left, int right){

        int totalPrimes=0;
        for(int j=left;j<=right;j++)
        {
            int count=0;
            for(int i=1;i<=j;i++)
                if(j%i==0) count++;
            if(count==2) totalPrimes++;
        }
        logger.info(Thread.currentThread().getName()+"   TotalPrimesService");
        return totalPrimes;
    }
}
