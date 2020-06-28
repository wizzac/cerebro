package com.ar.cerebro.cerebro.service;

import com.ar.cerebro.cerebro.entity.Config;
import org.springframework.stereotype.Service;

@Service
public interface ConfigService {

    Config getConfigByCode(String code);
}
