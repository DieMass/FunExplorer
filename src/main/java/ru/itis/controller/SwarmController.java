package ru.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MultiswarmCreateDtoRequest;
import ru.itis.dto.MultiswarmResultDtoRequest;
import ru.itis.dto.MultiswarmResultDtoResponse;
import ru.itis.service.SwarmService;

@RestController
@RequestMapping("/api/swarm")
@RequiredArgsConstructor
public class SwarmController {

    private final SwarmService service;

    @PostMapping
    public void createSwarm(@RequestBody MultiswarmCreateDtoRequest request) {
        service.createMultiswarm(request);
    }

    @GetMapping("/calculate")
    public MultiswarmResultDtoResponse getResult(MultiswarmResultDtoRequest request) {
        return service.getResult(request);
    }

}
