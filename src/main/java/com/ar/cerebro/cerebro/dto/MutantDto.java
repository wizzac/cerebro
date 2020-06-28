package com.ar.cerebro.cerebro.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

@Getter @Setter
public class MutantDto {

    private Integer id;
    private List<String> dna;
    private boolean isMutant;


}
