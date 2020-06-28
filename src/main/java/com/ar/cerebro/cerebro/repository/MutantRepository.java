package com.ar.cerebro.cerebro.repository;

import com.ar.cerebro.cerebro.entity.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface MutantRepository extends JpaRepository<Mutant,Integer> {


}
