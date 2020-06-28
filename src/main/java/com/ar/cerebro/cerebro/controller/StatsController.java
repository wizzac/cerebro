package com.ar.cerebro.cerebro.controller;


import com.ar.cerebro.cerebro.dto.StatsDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stats")
public class StatsController {


    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8" })
    public List<StatsDto> getStat(){
        return null;
    }




}
