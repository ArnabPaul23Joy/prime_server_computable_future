package com.example.demo.api;

//import com.example.demo.model.PrimeNumber;
//import com.example.demo.repository.PrimeRepository;
import com.example.demo.model.PrimeNumber;
import com.example.demo.nonSpringModules.JasonResponseObject;
import com.example.demo.repository.PrimeRepository;
import com.example.demo.service.FibonacciGeneratorService;
import com.example.demo.service.PrimeGeneratorService;
import com.example.demo.service.TotalPrimesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RequestMapping ("addperson")
@RestController
public class PrimeController {

    private ExecutorService executorService = Executors.newFixedThreadPool(100);
    @Autowired
    private PrimeGeneratorService pGenService;
    @Autowired
    private TotalPrimesService totalPrimesService;
    @Autowired
    private PrimeRepository repository;

//    public PrimeController(PrimeGeneratorService pGenService){
//        this.pGenService=pGenService;
//    }
    @GetMapping
    @ResponseBody
    @Async
    public String getPrimes(@RequestParam("n") String num) throws Exception{
        Long start=System.currentTimeMillis();

        String jsonStr="{\"eror\": \"Error Ocurred\"}";
        String status="success";
        int n=Integer.parseInt(num);
        int left = 2;
        int gap = Math.max(1, n / 10);
        int right = left;
        List<CompletableFuture<List<String>>> primeFutures = new ArrayList<CompletableFuture<List<String>>>();

        List<CompletableFuture<Integer>> totalPrimeFutures = new ArrayList<CompletableFuture<Integer>>();
        List<CompletableFuture<Integer>> fibnacchiFutures=new ArrayList<CompletableFuture<Integer>>();
        try{
            fibnacchiFutures.add(CompletableFuture.supplyAsync(()->new FibonacciGeneratorService().fibo(n),executorService));
            while(left <= n && left <= right) {
                final int fst = left;
                final int fen = right;
//            logger.info("range to calculate: " + fst + " --- " + fen);
                primeFutures.add(CompletableFuture
                        .supplyAsync(() -> pGenService.getPrimeListInParts(fst, fen), executorService));
                totalPrimeFutures.add(CompletableFuture.supplyAsync(()->totalPrimesService.getTotalPrime(fst,fen),executorService));
                left = right + 1;
                right = Math.min(right + gap, n);
            }

            CompletableFuture<Void> allPrimeListFutures = CompletableFuture.allOf(
                    primeFutures.toArray(new CompletableFuture[primeFutures.size()])
            );
            CompletableFuture<Void> allTotalPrimeFutures = CompletableFuture.allOf(
                    totalPrimeFutures.toArray(new CompletableFuture[totalPrimeFutures.size()])
            );

            CompletableFuture<Void> allFibFutures = CompletableFuture.allOf(
                    fibnacchiFutures.toArray(new CompletableFuture[fibnacchiFutures.size()])
            );
            CompletableFuture<List<List<String>>> allPrimeListContentsFuture = allPrimeListFutures.thenApply(v -> {
                return primeFutures.stream()
                        .map(primeFuture -> primeFuture.join())
                        .collect(Collectors.toList());
            });

            CompletableFuture<List<Integer>> allTotPrimeContentsFuture = allTotalPrimeFutures.thenApply(v -> {
                return totalPrimeFutures.stream()
                        .map(totalPrimeFuture -> totalPrimeFuture.join())
                        .collect(Collectors.toList());
            });


            CompletableFuture<List<Integer>> allFibContentsFuture = allFibFutures.thenApply(v -> {
                return fibnacchiFutures.stream()
                        .map(fibnacchiFuture -> fibnacchiFuture.join())
                        .collect(Collectors.toList());
            });
            List<String>primeList= new ArrayList<>();
            int primeCount=0;
            int fibnacchiNumber=0;
            List<List<String>> futureResults = null;
            List<Integer> futureTotalResults = null;
            List<Integer> futureFibResults = null;
            futureResults = allPrimeListContentsFuture.get();
            for (List<String> primeCountFromFuture : futureResults) {
                for(String listElements :primeCountFromFuture){
                    primeList.add(listElements);
                }
            }
            futureTotalResults=allTotPrimeContentsFuture.get();
            for(Integer pCount: futureTotalResults){
                primeCount+=pCount;
            }
            futureFibResults=allFibContentsFuture.get();
            for(Integer fCount: futureFibResults){
                fibnacchiNumber=fCount;
            }
            ObjectMapper mapper = new ObjectMapper();

            JasonResponseObject jasonResObj=new JasonResponseObject(fibnacchiNumber,primeCount,primeList);
            Gson gson = new Gson();
            jsonStr= gson.toJson(jasonResObj);

        }catch (Exception e){
            jsonStr="{\"eror\":"+e.getClass()+"\"}";
            status="failure";
        }

        finally {
            try{
                UUID uid= UUID.randomUUID();
                Long end=System.currentTimeMillis();
                Long eTime=end-start;
                PrimeNumber primeNumber=new PrimeNumber(uid,n,eTime,status);
                repository.insertPrime(primeNumber);
            }
            catch(Exception e){
                jsonStr="{\"eror\":"+e.getClass()+"\"}";
            }
            finally {
                return jsonStr;
            }
        }
    }
}
