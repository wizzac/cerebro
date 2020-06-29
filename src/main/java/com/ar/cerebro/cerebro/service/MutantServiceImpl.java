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
import org.springframework.stereotype.Service;

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
            CountDownLatch countDownLatch = new CountDownLatch(1);

            List<Callable> workers = new ArrayList<>();

            HorizontalThread horizontalThread = new HorizontalThread(mutant.getDna(), validLetters, countToValidate, countDownLatch);
            VerticalThread verticalThread = new VerticalThread(mutant.getDna(), validLetters, countToValidate, countDownLatch);
            LeftDiagnoalThread leftDiagnoalThread = new LeftDiagnoalThread(mutant.getDna(), validLetters, countToValidate, countDownLatch);
            RightDiagonalThread rightDiagonalThread = new RightDiagonalThread(mutant.getDna(), validLetters, countToValidate, countDownLatch);

            Future<Boolean> horizontalResponse=executorService.submit(horizontalThread);
            Future<Boolean> verticalResponse=executorService.submit(verticalThread);
            Future<Boolean> leftDiagnoalResponse=executorService.submit(leftDiagnoalThread);
            Future<Boolean> rightDiagonalResponse=executorService.submit(rightDiagonalThread);


            countDownLatch.await();

            try {
                result = horizontalResponse.get();
            }catch (Exception ex){
                throw ex;
            }

        }else{
            result= duplicate.isMutant();
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
                        if (!validLetters.contains(row[i])) {
                            throw new CustomForbiddenException("Esta cadena de adn es demasiado mutante");
                        }
                    }
                }else{
                    throw new CustomForbiddenException("El adn no puede ser null");
                }
            }
        });




    }



}
