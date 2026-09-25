package com.yassine.microservice.mappers;

import com.yassine.microservice.dtos.CompteDTO;
import com.yassine.microservice.entities.Compte;

/**
 * @author pc
 **/
public class CompteMapper {

    public static CompteDTO fromCompte(Compte compte) {
        return CompteDTO.builder()
                .id(compte.getId())
                .solde(compte.getSolde())
                .dateCreation(compte.getDateCreation())
                .type(compte.getType())
                .build();
    }

    public static Compte fromCompteDTO(CompteDTO compteDTO) {
        return Compte.builder()
                .id(compteDTO.getId())
                .solde(compteDTO.getSolde())
                .dateCreation(compteDTO.getDateCreation())
                .type(compteDTO.getType())
                .build();
    }
}
