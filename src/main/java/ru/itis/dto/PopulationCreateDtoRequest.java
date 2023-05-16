package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PopulationCreateDtoRequest {
    private Double[] min;
    private Double[] max;
    private Integer populationSize;
    private Integer hallOfFameSize;
    private Double mutationProbability;
    private Double crossingProbability;
    private Integer generationCount;
}
