package ru.itis.service;

import ru.itis.dto.GeneticResultDtoRequest;
import ru.itis.dto.GeneticResultDtoResponse;
import ru.itis.dto.PopulationCreateDtoRequest;

public interface GeneticService {

    void createPopulation(PopulationCreateDtoRequest request);

    GeneticResultDtoResponse calculateResult(GeneticResultDtoRequest request);
}
