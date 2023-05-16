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

    @DeleteMapping("/{populationId}")
    public void deletePopulation(@PathVariable UUID populationId) {
        service.deletePopulation(populationId);
    }

    @PutMapping("/{populationId}/calculate")
    public GeneticResultDtoResponse getResult(@PathVariable UUID populationId, @RequestBody GeneticResultDtoRequest request) {
        return service.calculateResult(populationId, request);
    }
}
