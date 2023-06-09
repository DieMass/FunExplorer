package ru.itis.service;

import ru.itis.dto.*;

import java.util.List;
import java.util.UUID;

public interface SwarmService {

    UUID createMultiswarm(MultiswarmCreateDtoRequest dto);

    List<SwarmDto> getAll();

    SwarmDto get(UUID swarmId);

    MultiswarmResultDtoResponse getResult(UUID swarmId, MultiswarmResultDtoRequest request);

    void deleteSwarm(UUID swarmId);
}
