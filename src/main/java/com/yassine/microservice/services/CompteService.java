package com.yassine.microservice.services;

import com.yassine.microservice.dtos.CompteDTO;

import java.util.List;

/**
 * @author pc
 **/
public interface CompteService {
    CompteDTO saveCompte(CompteDTO compteDTO);

    CompteDTO getCompte(Long id);

    List<CompteDTO> getAllComptes();

    CompteDTO updateCompte(Long id, CompteDTO compteDTO);

    void deleteCompte(Long id);
}
