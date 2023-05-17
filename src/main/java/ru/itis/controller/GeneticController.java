package ru.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.*;
import ru.itis.service.GeneticService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/genetic")
@RequiredArgsConstructor
public class GeneticController {

    private final GeneticService service;

    @PostMapping
    public UUID createSwarm(@RequestBody PopulationCreateDtoRequest request) {
        return service.createPopulation(request);
    }

    @GetMapping
    public List<PopulationDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{populationId}")
    public PopulationDto getAll(@PathVariable UUID populationId) {
        return service.get(populationId);
    }

    @DeleteMapping("/{populationId}")
    public void deletePopulation(@PathVariable UUID populationId) {
        service.deletePopulation(populationId);
    }

    @PostMapping("/{populationId}/calculate")
    public GeneticResultDtoResponse getResult(@PathVariable UUID populationId, @RequestBody GeneticResultDtoRequest request) {
        return service.calculateResult(populationId, request);
    }
}
