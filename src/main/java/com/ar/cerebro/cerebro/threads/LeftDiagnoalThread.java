package com.ar.cerebro.cerebro.threads;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@AllArgsConstructor
public class LeftDiagnoalThread extends Thread implements Validable {


    private List<String> dna;
    private List<String> validLetters;
    private Integer countToValidate;
    private CountDownLatch countDownLatch;


    @Override
    public boolean validate() {
        Integer consecutives=0;
        String current="";
        Integer size=dna.size();



        dna.forEach( x ->{

        });



        return false;
    }


    @Override
    public void run(){

    }
}