package com.eco.alert.ecoAlert.dao;
import com.eco.alert.ecoAlert.entity.SegnalazioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SegnalazioneDao extends JpaRepository<SegnalazioneEntity, Integer> {

    List<SegnalazioneEntity> findByCittadino_Id(Integer idCittadino);

    List<SegnalazioneEntity> findByEnte_Id(Integer idEnte);

}