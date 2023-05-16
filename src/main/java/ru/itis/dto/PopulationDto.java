package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.genetic.PopulationConfig;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PopulationDto {
    private UUID id;
    private Double[] min;
    private Double[] max;
    private PopulationConfig config;
}
