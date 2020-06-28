package com.ar.cerebro.cerebro.threads;

import com.ar.cerebro.cerebro.security.CustomForbiddenException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@AllArgsConstructor
public class VerticalThread extends Thread implements Validable, Callable<Boolean> {


    private List<String> dna;
    private List<String> validLetters;
    private Integer countToValidate;
    private CountDownLatch countDownLatch;


    @Override
    public boolean validate() {

        Integer size=dna.size();

        if(size < countToValidate){
            throw new CustomForbiddenException("No hay suficiente adn para validar");
        }


        for (int i = 0; i < dna.size(); i++) {

            int consecutives=0;
            String current="";

            for(int j = 0 ; j < dna.get(i).length();j++){

                String[] row=dna.get(j).split("");

                if(!validLetters.contains(row[j])) {
                    throw new CustomForbiddenException("Este adn es demasiado mutante");
                }

                if(current.equals(row[i])){
                    consecutives++;
                }else{
                    consecutives=1;
                    current=row[i];
                }

                if(consecutives==countToValidate){
                    return true;
                }

            }
        };


        return false;
    }


    @Override
    public Boolean call(){
        return validate();
    }

}