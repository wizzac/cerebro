package com.ar.cerebro.cerebro.service;

import com.ar.cerebro.cerebro.dto.StatsDto;
import org.springframework.stereotype.Service;

@Service
public interface StatsService {

    StatsDto getStats();
}
