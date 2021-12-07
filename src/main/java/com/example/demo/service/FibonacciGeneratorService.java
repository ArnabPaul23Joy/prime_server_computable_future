package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FibonacciGeneratorService {
    private Logger logger = LoggerFactory.getLogger(FibonacciGeneratorService.class);
    public FibonacciGeneratorService(){
    }
    public int fibo(int n){
        int a, b, c = 0;
        a = 0;
        b = 1;
        for (int i = 2; i <= n; i++) {
            c = (a + b) % 100000;
            a = b;
            b = c;
        }
        logger.info(Thread.currentThread().getName()+"   FibonacciGeneratorService");

        return c;
    }
    public int fiboNoDP(int n1, int n){
        if(n1==1 || n1==0){
            return n1;
        }
        return (fiboNoDP(n1-1,n)+fiboNoDP(n1-2,n))%1000000;
    }
}
