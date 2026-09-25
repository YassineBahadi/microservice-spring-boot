package com.yassine.microservice.controllers;

import com.yassine.microservice.dtos.CompteDTO;
import com.yassine.microservice.services.CompteService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author pc
 **/
@Controller
@AllArgsConstructor
public class CompteGraphQLController {
    private final CompteService compteService;

    @QueryMapping
    public List<CompteDTO> comptes() {
        return compteService.getAllComptes();
    }

    @QueryMapping
    public CompteDTO compte(@Argument Long id) {
        return compteService.getCompte(id);
    }

    @MutationMapping
    public CompteDTO saveCompte(
            @Argument Double solde,
            @Argument String dateCreation,
            @Argument String type) {

        CompteDTO compteDTO = CompteDTO.builder()
                .solde(solde)
                .dateCreation(dateCreation)
                .type(com.yassine.microservice.enums.TypeCompte.valueOf(type))
                .build();

        return compteService.saveCompte(compteDTO);
    }

    @MutationMapping
    public Boolean deleteCompte(@Argument Long id) {

        compteService.deleteCompte(id);
        return true;
    }
}
