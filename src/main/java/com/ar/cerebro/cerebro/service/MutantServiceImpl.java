package com.ar.cerebro.cerebro.service;

import com.ar.cerebro.cerebro.dto.MutantDto;
import com.ar.cerebro.cerebro.entity.Config;
import com.ar.cerebro.cerebro.security.CustomForbiddenException;
import com.ar.cerebro.cerebro.threads.HorizontalThread;
import com.ar.cerebro.cerebro.threads.LeftDiagnoalThread;
import com.ar.cerebro.cerebro.threads.RightDiagonalThread;
import com.ar.cerebro.cerebro.threads.VerticalThread;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Service
public class MutantServiceImpl implements MutantService {

    @Resource
    ConfigService configService;


    @Override
    public boolean isMutant(MutantDto mutant) throws Exception {


        validate(mutant);

        Config letters = configService.getConfigByCode("VALID_LETTERS");
        Config countToValidateConfig=configService.getConfigByCode("VALIDATE_COUNT");

        List<String> validLetters= Arrays.asList(letters.getValue().split(","));
        Integer countToValidate= Integer.parseInt(countToValidateConfig.getValue());

//        ExecutorService executorService = Executors.newFixedThreadPool(countToValidate);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        HorizontalThread horizontalThread = new HorizontalThread(mutant.getDna(),validLetters,countToValidate,countDownLatch);
        VerticalThread verticalThread = new VerticalThread(mutant.getDna(),validLetters,countToValidate,countDownLatch);
        LeftDiagnoalThread leftDiagnoalThread= new LeftDiagnoalThread(mutant.getDna(),validLetters,countToValidate,countDownLatch);
        RightDiagonalThread rightDiagonalThread= new RightDiagonalThread(mutant.getDna(),validLetters,countToValidate,countDownLatch);

        List<Callable> workers = new ArrayList<>();
        workers.add(horizontalThread);
        workers.add(verticalThread);
//        workers.add(leftDiagnoalThread);
        workers.add(rightDiagonalThread);
        Boolean fut = rightDiagonalThread.call();


//        workers.forEach(x-> {
//            try {
//                x.call();
//            } catch (Exception e) {
//                throw new CustomForbiddenException("");
//            }
//        });

    //    countDownLatch.await();

//        executorService.shutdown();
//        try {
//            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
//                executorService.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            executorService.shutdownNow();
//        }

        return false;
    }


    private void validate(MutantDto mutant){
        if(mutant == null){
            throw new CustomForbiddenException("El mutante no puede ser null");
        }

        if(mutant.getDna() == null){
            throw new CustomForbiddenException("El adn no puede ser null");
        }


    }



}
