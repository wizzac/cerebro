package com.ar.cerebro.cerebro.controller;


import com.ar.cerebro.cerebro.dto.StatsDto;
import com.ar.cerebro.cerebro.service.StatsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("stats")
public class StatsController {


    @Resource
    StatsService statsService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8" })
    public StatsDto getStat(){
        return statsService.getStats();
    }




}
