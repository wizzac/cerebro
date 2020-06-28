package com.ar.cerebro.cerebro.repository;

import com.ar.cerebro.cerebro.entity.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface StatsRepository extends JpaRepository<Stats,Integer> {
}
