package ru.itis.service;

import ru.itis.dto.GeneticResultDtoRequest;
import ru.itis.dto.GeneticResultDtoResponse;
import ru.itis.dto.PopulationCreateDtoRequest;
import ru.itis.dto.PopulationDto;

import java.util.List;
import java.util.UUID;

public interface GeneticService {

    UUID createPopulation(PopulationCreateDtoRequest request);

    List<PopulationDto> getAll();

    PopulationDto get(UUID populationId);

    GeneticResultDtoResponse calculateResult(UUID populationId, GeneticResultDtoRequest request);

    void deletePopulation(UUID populationId);
}
