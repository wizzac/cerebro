package com.ar.cerebro.cerebro.service;

import com.ar.cerebro.cerebro.dto.MutantDto;
import com.ar.cerebro.cerebro.entity.Config;
import com.ar.cerebro.cerebro.entity.Mutant;
import com.ar.cerebro.cerebro.repository.MutantRepository;
import com.ar.cerebro.cerebro.security.CustomForbiddenException;
import com.ar.cerebro.cerebro.threads.HorizontalThread;
import com.ar.cerebro.cerebro.threads.LeftDiagnoalThread;
import com.ar.cerebro.cerebro.threads.RightDiagonalThread;
import com.ar.cerebro.cerebro.threads.VerticalThread;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Synchronized;
import org.hibernate.annotations.Synchronize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MutantServiceImpl implements MutantService {

    @Resource
    ConfigService configService;

    @Resource
    MutantRepository mutantRepository;

    @Override
    public boolean isMutant(MutantDto mutant) throws Exception {

        Config letters = configService.getConfigByCode("VALID_LETTERS");
        Config countToValidateConfig=configService.getConfigByCode("VALIDATE_COUNT");
        List<String> validLetters= Arrays.asList(letters.getValue().split(","));

        Integer countToValidate= Integer.parseInt(countToValidateConfig.getValue());
        validate(mutant,validLetters,countToValidate);

        mutant.setDna(mutant.getDna().stream().map(String::toUpperCase).collect(Collectors.toList()));
        String[] array =  mutant.getDna().toArray(new String[0]);

        Integer hashCode =Arrays.hashCode(array);
        Mutant duplicate= mutantRepository.getByHashCode(hashCode);

        boolean result=true;

        if(duplicate==null) {

            ExecutorService executorService = Executors.newFixedThreadPool(countToValidate);
            CountDownLatch countDownLatch = new CountDownLatch(4);

            List<Future<Boolean>> tasks = new ArrayList<>();

            HorizontalThread horizontalThread = new HorizontalThread(mutant.getDna(), countToValidate, countDownLatch);
            VerticalThread verticalThread = new VerticalThread(mutant.getDna(), countToValidate, countDownLatch);
            LeftDiagnoalThread leftDiagnoalThread = new LeftDiagnoalThread(mutant.getDna(), countToValidate, countDownLatch);
            RightDiagonalThread rightDiagonalThread = new RightDiagonalThread(mutant.getDna(), countToValidate, countDownLatch);

            Future<Boolean> horizontalResponse=executorService.submit(horizontalThread);
            Future<Boolean> verticalResponse=executorService.submit(verticalThread);
            Future<Boolean> leftDiagnoalResponse=executorService.submit(leftDiagnoalThread);
            Future<Boolean> rightDiagonalResponse=executorService.submit(rightDiagonalThread);

            tasks.add(horizontalResponse);
            tasks.add(verticalResponse);
            tasks.add(leftDiagnoalResponse);
            tasks.add(rightDiagonalResponse);

            countDownLatch.await();

            Boolean isMutant=false;
            try {
                for(Future<Boolean> fut : tasks ){
                    if(fut.get()){
                        isMutant= true;
                    }
                }
            executorService.shutdown();
            }catch (Exception ex){
                throw ex;
            }

            result= isMutant;

            Mutant probableMutant = new Mutant();
            probableMutant.setDna(mutant.getDna().toString());
            probableMutant.setHashCode(hashCode);
            probableMutant.setMutant(isMutant);

          validateAndInsert(probableMutant,hashCode);

        }else{
            result= duplicate.isMutant();
        }

        if(!result){
            throw new CustomForbiddenException("No es un mutante");
        }
        return result;
    }


    private void validate(MutantDto mutant,List<String> validLetters,Integer countToValidate){
        if(mutant == null){
            throw new CustomForbiddenException("El mutante no puede ser null");
        }

        if(mutant.getDna() == null){
            throw new CustomForbiddenException("El adn no puede ser null");
        }


        if(mutant.getDna().size() < countToValidate){
            throw new CustomForbiddenException("No hay suficiente adn para validar");
        }

        mutant.getDna().forEach(x ->{
            if(x.length() != mutant.getDna().size()){
                throw new CustomForbiddenException("El patron de adn no es cuadrado");
            }else{
                if(x!=null) {
                    for (int i = 0; i < x.length(); i++) {
                        String[] row = x.split("");
                        if (!validLetters.contains(row[i].toUpperCase())) {
                            throw new CustomForbiddenException("Esta cadena de adn es demasiado mutante");
                        }
                    }
                }else{
                    throw new CustomForbiddenException("El adn no puede ser null");
                }
            }
        });


    }

    @Synchronized
    private void validateAndInsert(Mutant mutant, Integer hashCode){
        Mutant duplicate= mutantRepository.getByHashCode(hashCode);
        if(duplicate == null) {
            mutantRepository.save(mutant);
        }
    }


}
