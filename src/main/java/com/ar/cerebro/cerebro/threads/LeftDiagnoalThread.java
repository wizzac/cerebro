package com.ar.cerebro.cerebro.threads;

import com.ar.cerebro.cerebro.security.CustomForbiddenException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.function.BooleanSupplier;

@AllArgsConstructor
public class LeftDiagnoalThread implements Validable,Callable<Boolean> {



    private List<String> dna;
    private Integer countToValidate;
    private CountDownLatch countDownLatch;

    @Override
    public boolean validate() {

        Integer size=dna.size();


        int consecutives=0;
        String current="";

//        try{
//            //tomar todos los elementos
//            for (int verticalHeight = 0; verticalHeight  < size  ; verticalHeight++) { //lee el tamaño del array
//
//                for(int hortizontalHeight = dna.get(verticalHeight).length()  ; hortizontalHeight >  0 ;hortizontalHeight--){ //mide el largo del string
//
//                    String[] row=dna.get(verticalHeight).split("");
//
//                    //validar si  tengo espacio para recorrer la diagnoal hacia la izquierda de este elemento
//                    if(verticalHeight>=size-countToValidate ) {
//                        if( hortizontalHeight>=row.length - countToValidate ){
//                            //teqngo espacio para recorrer esto
//                            current= row[hortizontalHeight-1];
//                            //for para recorrer la diagonal
//                            for(int cant =0; cant<=countToValidate; cant++){
//
//                                //esta es la fila donde esta el valor
//                                String[] diagnoalRow=dna.get(verticalHeight+cant).split("");
//                                String letter= diagnoalRow[hortizontalHeight - cant - 1];
//                                if(letter.equalsIgnoreCase(current)) {
//                                    consecutives++;
//                                    if(consecutives==countToValidate){
//                                        countDownLatch.countDown();
//                                        return true;
//                                    }
//                                }else{
//                                    consecutives=0;
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }catch (Exception ex){
//            countDownLatch.countDown();
//            throw ex;
//        }
//
        countDownLatch.countDown();
        return false;
    }


    @Override
    public Boolean call(){
        return validate();
    }
}
