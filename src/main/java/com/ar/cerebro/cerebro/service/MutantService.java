package com.ar.cerebro.cerebro.service;

import com.ar.cerebro.cerebro.dto.MutantDto;
import org.springframework.stereotype.Service;

@Service
public interface MutantService {

     boolean isMutant(MutantDto mutant) throws Exception;
}
