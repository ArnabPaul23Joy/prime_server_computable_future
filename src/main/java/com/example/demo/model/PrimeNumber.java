package com.example.demo.model;

import java.util.List;
import java.util.UUID;


public class PrimeNumber {

    private UUID task_id;
    private int n;
    private Long execution_time;
    private String status;


    public PrimeNumber(UUID task_id, int n, Long execution_time, String status) {
        this.task_id = task_id;
        this.n = n;
        this.execution_time = execution_time;
        this.status = status;
    }

    public UUID getTask_id() {
        return task_id;
    }


    public int getN() {
        return n;
    }


    public Long getExecution_time() {
        return execution_time;
    }

    public String getStatus() {
        return status;
    }

//    @Override
//    public String toString() {
////        id=" + Integer.toString(id) + ",
//        return "Customer [primeValue=" + Integer.toString(val) +", primeArray=" + pArray.toString()+ "]";
//    }
}
