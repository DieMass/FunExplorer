package ru.itis.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneticResultDtoResponse {
    private Double[] bestChromosome;
    private Double bestFitness;
}
