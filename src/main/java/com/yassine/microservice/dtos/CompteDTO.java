package com.yassine.microservice.dtos;

import com.yassine.microservice.enums.TypeCompte;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteDTO {
    private Long id;
    private double solde;
    private String dateCreation;
    private TypeCompte type;
}
