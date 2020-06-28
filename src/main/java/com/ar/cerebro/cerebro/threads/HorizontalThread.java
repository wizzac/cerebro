package com.ar.cerebro.cerebro.threads;

import com.ar.cerebro.cerebro.security.CustomForbiddenException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class HorizontalThread extends Thread implements Validable,Callable<Boolean> {


    private List<String> dna;
    private List<String> validLetters;
    private Integer countToValidate;
    private CountDownLatch countDownLatch;


    public HorizontalThread(List<String> dna, List<String> validLetters, Integer countToValidate, CountDownLatch countDownLatch){
        super();
        this.dna=dna;
        this.validLetters=validLetters;
        this.countToValidate=countToValidate;
        this.countDownLatch=countDownLatch;
    }



    @Override
    public boolean validate() {

        Integer size=dna.size();

        if(size < countToValidate){
            throw new CustomForbiddenException("No hay sificiente adn para validar");
        }

        dna.forEach( x ->{
            int consecutives=0;
            String current="";
            String[] asArray=x.split("");
            for (int i = 0; i < x.length(); i++) {

                if(!validLetters.contains(current)){
                    throw new CustomForbiddenException("Esta cadena de adn es demasiado mutante");
                }

                if(current.equals(asArray[i])){
                    consecutives++;
                }else{
                    current=asArray[i];
                    consecutives=1;
                }

                // cortar aca ejecucion si hay N letras iguales validas

                if(consecutives == countToValidate){
                    countDownLatch.countDown();
                }

            }

        });


        return true;
    }


    @Override
    public Boolean call(){

        return validate();

    }
}