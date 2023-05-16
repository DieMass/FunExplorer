package ru.itis.service;

import ru.itis.dto.MultiswarmCreateDtoRequest;
import ru.itis.dto.MultiswarmResultDtoRequest;
import ru.itis.dto.MultiswarmResultDtoResponse;

public interface SwarmService {

    void createMultiswarm(MultiswarmCreateDtoRequest dto);

    MultiswarmResultDtoResponse getResult(MultiswarmResultDtoRequest request);
}
