package com.yassine.microservice.controllers;

import com.yassine.microservice.dtos.CompteDTO;
import com.yassine.microservice.entities.Compte;
import com.yassine.microservice.repositories.CompteRepository;
import com.yassine.microservice.services.CompteService;
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

    private final CompteService compteService;



    @GetMapping
    public List<CompteDTO> getComptes() {
        return compteService.getAllComptes();
    }

    @GetMapping("/{id}")
    public CompteDTO getCompte(@PathVariable Long id) {
        return compteService.getCompte(id);
    }

    @PostMapping
    public CompteDTO saveCompte(@RequestBody CompteDTO compteDTO) {
        return compteService.saveCompte(compteDTO);
    }

    @PutMapping("/{id}")
    public CompteDTO updateCompte(
            @PathVariable Long id,
            @RequestBody CompteDTO compteDTO) {

        return compteService.updateCompte(id, compteDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCompte(@PathVariable Long id) {
        compteService.deleteCompte(id);
    }

}
