package com.ar.cerebro.cerebro.service;

import com.ar.cerebro.cerebro.entity.Config;
import com.ar.cerebro.cerebro.repository.ConfigRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    ConfigRepository configRepository;

    @Override
    public Config getConfigByCode(String code) {
        return configRepository.getByCode(code);
    }
}
