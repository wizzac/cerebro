package com.ar.cerebro.cerebro.repository;

import com.ar.cerebro.cerebro.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config,Integer> {


    @Query("select c " +
            "from Config c " +
            "where c.code like :code ")
    Config getByCode(@Param("code") String code);
}
