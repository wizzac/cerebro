package com.ar.cerebro.cerebro.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter @Setter
public class StatsDto {

    private Integer countMutantDna;
    private Integer countHumanDna;
    private Double ratio;


}
