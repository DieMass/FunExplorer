package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultiswarmCreateDtoRequest {
    private int numSwarms;
    private int particlesPerSwarm;
    private String expression;
    private Double[] min;
    private Double[] max;
}
