package com.yassine.microservice.services;

import com.yassine.microservice.dtos.CompteDTO;
import com.yassine.microservice.entities.Compte;
import com.yassine.microservice.mappers.CompteMapper;
import com.yassine.microservice.repositories.CompteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pc
 **/
@Service
@AllArgsConstructor
public class CompteServiceImpl implements CompteService{
    private final CompteRepository compteRepository;

    @Override
    public CompteDTO saveCompte(CompteDTO compteDTO) {

        Compte compte = CompteMapper.fromCompteDTO(compteDTO);

        Compte savedCompte = compteRepository.save(compte);

        return CompteMapper.fromCompte(savedCompte);
    }

    @Override
    public CompteDTO getCompte(Long id) {

        Compte compte = compteRepository.findById(id)
                .orElse(null);

        if (compte == null) {
            return null;
        }

        return CompteMapper.fromCompte(compte);
    }

    @Override
    public List<CompteDTO> getAllComptes() {

        return compteRepository.findAll()
                .stream()
                .map(CompteMapper::fromCompte)
                .toList();
    }

    @Override
    public CompteDTO updateCompte(Long id, CompteDTO compteDTO) {

        Compte compte = CompteMapper.fromCompteDTO(compteDTO);

        compte.setId(id);

        Compte updatedCompte = compteRepository.save(compte);

        return CompteMapper.fromCompte(updatedCompte);
    }

    @Override
    public void deleteCompte(Long id) {

        compteRepository.deleteById(id);
    }
}
