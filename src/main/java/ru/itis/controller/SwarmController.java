package ru.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.*;
import ru.itis.service.SwarmService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/swarm")
@RequiredArgsConstructor
public class SwarmController {

    private final SwarmService service;

    @PostMapping
    public void createSwarm(@RequestBody MultiswarmCreateDtoRequest request) {
        service.createMultiswarm(request);
    }

    @GetMapping
    public List<SwarmDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{swarmId}")
    public void deleteSwarm(@PathVariable UUID swarmId) {
        service.deleteSwarm(swarmId);
    }

    @GetMapping("/{swarmId}/calculate")
    public MultiswarmResultDtoResponse getResult(@PathVariable UUID swarmId, MultiswarmResultDtoRequest request) {
        return service.getResult(swarmId, request);
    }

}
