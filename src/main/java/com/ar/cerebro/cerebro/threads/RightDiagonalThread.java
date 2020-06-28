package com.ar.cerebro.cerebro.threads;

import com.ar.cerebro.cerebro.security.CustomForbiddenException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.function.BooleanSupplier;

@AllArgsConstructor
public class RightDiagonalThread extends Thread implements Validable, Callable<Boolean> {



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

        int consecutives=0;
        String current="";

//tomar todos los elementos
        for (int i = 0; i < dna.size(); i++) {
//evaluar cada string
            for(int j = 0 ; j < dna.get(i).length();j++){
                String[] fila=dna.get(j).split("");

                if(!validLetters.contains(fila[j])) {
                    throw new CustomForbiddenException("Este adn es demasiado mutante");
                }

                //validar si  tengo espacio para recorrer la diagnoal hacia la derecha de este elemento
                if(i<=dna.size()-countToValidate  ) {

                }

                    //validar si tengo espacio para recorrer la diagnoal hacia la izquieqrda de este elemento
//                if(){
//
//                }

            }
        };





        return false;
    }


    @Override
    public Boolean call(){
            return validate();
    }
}
