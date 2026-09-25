package com.yassine.microservice.controllers;

import com.yassine.microservice.entities.Compte;
import com.yassine.microservice.repositories.CompteRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pc
 **/
@RestController
@RequestMapping("/api/comptes")
@AllArgsConstructor
public class CompteController {
    private final CompteRepository compteRepository;

    @GetMapping
    public List<Compte> getComptes(){
        return compteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Compte getCompte(@PathVariable Long id){
        return compteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Compte saveCompte(@RequestBody Compte compte){
        return compteRepository.save(compte);
    }

    @PutMapping("/{id}")
    public Compte updateCompte(@PathVariable Long id,@RequestBody Compte compte){
        compte.setId(id);
        return compteRepository.save(compte);
    }

    @DeleteMapping("/{id}")
    public void deleteCompte(@PathVariable Long id){
        compteRepository.deleteById(id);
    }

}
