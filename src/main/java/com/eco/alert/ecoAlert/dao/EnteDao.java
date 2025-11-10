package com.eco.alert.ecoAlert.dao;

import com.eco.alert.ecoAlert.entity.EnteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnteDao extends JpaRepository<EnteEntity, Integer> {

}
