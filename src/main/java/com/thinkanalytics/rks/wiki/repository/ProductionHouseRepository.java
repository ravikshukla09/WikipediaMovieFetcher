package com.thinkanalytics.rks.wiki.repository;

import com.thinkanalytics.rks.wiki.entity.ProductionHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionHouseRepository extends JpaRepository<ProductionHouse, Integer> {
    Optional<ProductionHouse> findByName(String name);
}
