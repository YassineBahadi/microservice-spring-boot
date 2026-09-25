package com.yassine.microservice;

import com.yassine.microservice.entities.Compte;
import com.yassine.microservice.enums.TypeCompte;
import com.yassine.microservice.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository) {
        return args->{
            compteRepository.save(
                    Compte.builder()
                            .solde(5000)
                            .dateCreation("25/09/2026")
                            .type(TypeCompte.COURANT)
                            .build()
            );

            compteRepository.save(
                    Compte.builder()
                            .solde(10000)
                            .dateCreation("25/09/2026")
                            .type(TypeCompte.EPARGNE)
                            .build()
            );

            System.out.println("Liste des comptes");

            compteRepository.findAll()
                    .forEach(compte->System.out.println(compte));
        };
    }

}
