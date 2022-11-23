package com.SecuCom.SecuCom.repository;

import com.SecuCom.SecuCom.model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateurs,Long> {
    Utilisateurs findByNomUtilisateur(String nomUtilisateur);
}
