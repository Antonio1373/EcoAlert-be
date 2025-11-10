package com.eco.alert.ecoAlert.dao;

import com.eco.alert.ecoAlert.entity.UtenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteDao extends JpaRepository<UtenteEntity, Integer> {

    UtenteEntity findByEmail(String email);
}