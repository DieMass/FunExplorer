package ru.itis.swarm;

/**
 * Функциональный интерфейс вычисления уровня приспособленности
 */
public interface FitnessFunction {

    /**
     * Получить текущий уровень приспособленности частицы
     *
     * @param particlePosition Координаты частицы
     * @return числовое значение уровня
     */
    Double getFitness(Double[] particlePosition);

}