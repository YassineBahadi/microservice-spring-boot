package com.yassine.microservice.entities;

import com.yassine.microservice.enums.TypeCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pc
 **/
@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Compte {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private double solde;
    private String dateCreation;
    @Enumerated(EnumType.STRING)
    private TypeCompte type;
}
