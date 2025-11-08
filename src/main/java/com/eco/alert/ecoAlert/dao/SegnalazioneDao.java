package com.eco.alert.ecoAlert.dao;
import com.eco.alert.ecoAlert.entity.SegnalazioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegnalazioneDao extends JpaRepository<SegnalazioneEntity, Integer> {
}