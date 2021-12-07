package com.example.demo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrimeGeneratorService implements PrimeServiceInterface{
//    @Autowired
//    private PrimeRepository repository;
    private Logger logger = LoggerFactory.getLogger(PrimeGeneratorService.class);

    public List<Integer> getPrimeList(int n){
        List<Integer>primeList=new ArrayList<Integer>(10);

        for(int j=2;j<=n;j++)
        {
            int count=0;
            for(int i=1;i<=j;i++)
                if(j%i==0) count++;
            if(count==2) primeList.add(j);
        }
        return primeList;
    }

    public List<String> getPrimeListInParts(int left, int right){
        List<Integer>primeList=new ArrayList<Integer>(10);

        for(int j=left;j<=right;j++)
        {
            int count=0;
            for(int i=1;i<=j;i++)
                if(j%i==0) count++;
            if(count==2) primeList.add(j);
        }

        logger.info(Thread.currentThread().getName()+"  PrimeGeneratorService");
//        Thread.currentThread().
//        repository.insertPrime( new PrimeNumber(n,primeList));
        List <String> primeListofString=new ArrayList<>();
        for(int primes: primeList) {
            primeListofString.add(Integer.toString(primes));
        }
        return primeListofString;
    }

}
