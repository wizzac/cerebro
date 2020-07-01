package com.ar.cerebro.cerebro.repository;

import com.ar.cerebro.cerebro.entity.Mutant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface MutantRepository extends JpaRepository<Mutant,Integer> {


    @Query("select m " +
            "from Mutant m " +
            "where m.hashCode = :hash")
    @Cacheable
    Mutant getByHashCode(@Param("hash")Integer hash);


    @Query(value = "select count(id) from mutant  where is_mutant = TRUE ",nativeQuery = true)
    Integer findMutantCount();

    @Query(value="select count(id) from mutant  ",nativeQuery = true)
    Integer findTotalCount();

}
