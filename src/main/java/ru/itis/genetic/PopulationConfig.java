package ru.itis.genetic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PopulationConfig {
	@Builder.Default
	private final Integer populationSize = 1000;
	@Builder.Default
	private final Integer hallOfFameSize = 50;
	@Builder.Default
	private final Double mutationProbability = 0.9;
	@Builder.Default
	private final Double crossingProbability = 0.9;
	@Builder.Default
	private final Integer generationCount = 1000;
}
