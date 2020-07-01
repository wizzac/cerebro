package com.ar.cerebro.cerebro.controller;


import com.ar.cerebro.cerebro.dto.MutantDto;
import com.ar.cerebro.cerebro.dto.StatsDto;
import com.ar.cerebro.cerebro.service.MutantService;
import org.hibernate.annotations.Synchronize;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("mutant")
public class MutantController {

    @Resource
    MutantService mutantService;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8" })
    public boolean isMutant(@RequestBody MutantDto mutant) throws Exception{
       return mutantService.isMutant(mutant);
    }


}
