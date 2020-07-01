package com.ar.cerebro.cerebro.threads;

import com.ar.cerebro.cerebro.security.CustomForbiddenException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@AllArgsConstructor
public class VerticalThread implements Validable, Callable<Boolean> {


    private List<String> dna;
    private Integer countToValidate;
    private CountDownLatch countDownLatch;


    @Override
    public boolean validate() {

        Integer size=dna.size();

        try{
            for (int i = 0; i < size; i++) {

                int consecutives=0;
                String current="";

                for(int j = 0 ; j < dna.get(i).length();j++){

                    String[] row=dna.get(j).split("");


                    if(current.equals(row[i])){
                        consecutives++;
                    }else{
                        consecutives=1;
                        current=row[i];
                    }

                    if(consecutives==countToValidate){
                        countDownLatch.countDown();
                        return true;
                    }

                }
            };
        }catch (Exception ex){
            throw ex;
        }finally {
            countDownLatch.countDown();
        }
        return false;
    }


    @Override
    public Boolean call(){
        return validate();
    }

}