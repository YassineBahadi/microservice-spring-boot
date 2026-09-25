package com.yassine.microservice.projections;

import com.yassine.microservice.entities.Compte;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author pc
 **/
@Projection(name = "compteProjection",types = Compte.class)
public interface CompteProjection {
    Long getId();
    double getSolde();
    String getDateCreation();
}
