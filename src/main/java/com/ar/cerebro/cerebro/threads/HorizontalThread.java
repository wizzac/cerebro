package com.ar.cerebro.cerebro.threads;

import com.ar.cerebro.cerebro.security.CustomForbiddenException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@AllArgsConstructor
public class HorizontalThread implements Validable,Callable<Boolean> {


    private List<String> dna;
    private Integer countToValidate;
    private CountDownLatch countDownLatch;


    @Override
    public boolean validate() {

        Integer size=dna.size();

        try {

            for (int x = 0; x < size; x++) {
                int consecutives = 0;
                String current = "";
                String[] asArray = dna.get(x).split("");

                for (int i = 0; i < dna.get(x).length(); i++) {

                    if (current.equals(asArray[i])) {
                        consecutives++;
                    } else {
                        current = asArray[i];
                        consecutives = 1;
                    }

                    // cortar aca ejecucion si hay N letras iguales validas

                    if (consecutives == countToValidate) {
                        countDownLatch.countDown();
                        return true;
                    }

                }

            }
        }catch (Exception ex){
            countDownLatch.countDown();
            throw ex;
        }

        countDownLatch.countDown();
        return false;
    }


    @Override
    public Boolean call(){

        return validate();

    }
}