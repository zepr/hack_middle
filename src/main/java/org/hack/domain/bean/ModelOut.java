package org.hack.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelOut {
    private double rendement;
    private double matierSeche;
    private double matiereSecheTheorique;
}
