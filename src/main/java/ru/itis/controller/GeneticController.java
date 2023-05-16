package ru.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.*;
import ru.itis.service.GeneticService;

@RestController
@RequestMapping("/api/genetic")
@RequiredArgsConstructor
public class GeneticController {

    private final GeneticService service;

    @PostMapping
    public void createSwarm(@RequestBody PopulationCreateDtoRequest request) {
        service.createPopulation(request);
    }

    @PutMapping("/calculate")
    public GeneticResultDtoResponse getResult(@RequestBody GeneticResultDtoRequest request) {
        return service.calculateResult(request);
    }
}
