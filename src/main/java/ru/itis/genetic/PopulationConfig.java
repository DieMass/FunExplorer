package ru.itis.genetic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Класс для настройки популяции
 */
@Getter
@AllArgsConstructor
@Builder
public class PopulationConfig {
	/**
	 * Размер популяции. Количество индивидов
	 */
	@Builder.Default
	private final Integer populationSize = 1000;
	/**
	 * Количество отбираемых лучших индивидов для следующего поколения
	 */
	@Builder.Default
	private final Integer hallOfFameSize = 50;
	/**
	 * Вероятность мутации генома
	 */
	@Builder.Default
	private final Double mutationProbability = 0.9;
	/**
	 * Вероятность скрещивания хромосом
	 */
	@Builder.Default
	private final Double crossingProbability = 0.9;
	/**
	 * Количество поколений (итераций)
	 */
	@Builder.Default
	private final Integer generationCount = 1000;
}
