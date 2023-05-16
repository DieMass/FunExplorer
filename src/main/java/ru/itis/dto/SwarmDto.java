package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SwarmDto {
    private UUID id;
    private Double[] min;
    private Double[] max;
    private int numSwarms;
    private int particlesPerSwarm;
}
