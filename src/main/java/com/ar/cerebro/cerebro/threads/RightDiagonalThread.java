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
        for (int verticalHeight = 0; verticalHeight < dna.size(); verticalHeight++) { //lee el tamanio del array


            for(int hortizontalHeight = 0 ; hortizontalHeight < dna.get(verticalHeight).length();hortizontalHeight++){ //mide el largo del string
                String[] row=dna.get(hortizontalHeight).split("");
                //valida que los caracteres del adn sean validos
                if(!validLetters.contains(row[hortizontalHeight])) {
                    throw new CustomForbiddenException("Este adn es demasiado mutante");
                }

                //validar si  tengo espacio para recorrer la diagnoal hacia la derecha de este elemento
                if(verticalHeight<=dna.size()-countToValidate ) {
                    if( hortizontalHeight<=row.length - countToValidate ){
                        //teqngo espacio para recorrer esto
                        current= row[hortizontalHeight];
                        //for para recorrer la diagonal
                        for(int cant =0; cant < countToValidate; cant++){

                            //esta es la fila donde esta el valor
                            String[] diagnoalRow=dna.get(verticalHeight+cant).split("");
                            String letter= diagnoalRow[hortizontalHeight+cant];
                            if(letter.equalsIgnoreCase(current)) {
                                consecutives++;
                                if(consecutives==countToValidate){
                                    return true;
                                }
                            }else{
                                consecutives=0;
                                break;
                            }
                        }
                    }
                }
            }
        }


        return false;
    }


    @Override
    public Boolean call(){
        return validate();
    }
}
