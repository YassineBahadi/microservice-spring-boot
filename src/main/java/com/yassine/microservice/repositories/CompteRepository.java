package com.yassine.microservice.repositories;

import com.yassine.microservice.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author pc
 **/
public interface CompteRepository extends JpaRepository<Compte,Long> {
}
