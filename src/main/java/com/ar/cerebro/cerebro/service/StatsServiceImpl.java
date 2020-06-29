package com.ar.cerebro.cerebro.service;

import com.ar.cerebro.cerebro.dto.StatsDto;
import com.ar.cerebro.cerebro.repository.MutantRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StatsServiceImpl implements StatsService {

    @Resource
    MutantRepository mutantRepository;

    @Override
    public StatsDto getStats() {
        Integer mutantCount= mutantRepository.findMutantCount();
        Integer totalCount= mutantRepository.findTotalCount();

        StatsDto stats=new StatsDto();
        stats.setCountMutantDna(mutantCount);
        stats.setCountHumanDna(totalCount-mutantCount);
        Double ratio=0d;
        if(mutantCount!=0) {
          ratio= (double) ((totalCount * 100) / mutantCount);
        }
        stats.setRatio(ratio);
        return stats;
    }
}
