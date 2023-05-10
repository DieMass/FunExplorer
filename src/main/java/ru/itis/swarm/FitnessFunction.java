package ru.itis.swarm;

/**
 * Функциональный интерфейс вычисления уровня приспособленности
 */
public interface FitnessFunction {
	/**
	 * @param particlePosition Координаты частицы
	 * @return Текущий уровень приспособленности частицы
	 */
	Double getFitness(Double[] particlePosition);
}