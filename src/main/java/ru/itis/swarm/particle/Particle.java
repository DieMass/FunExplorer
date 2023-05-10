package ru.itis.swarm.particle;

/**
 * Интерфейс для работы с методами частицы
 */
public interface Particle<T extends Particle<T>> {

    /**
     * Получение текущего уровня приспособленности
     *
     * @return числовое значение уровня
     */
    Double getFitness();

    /**
     * Обновление текущего уровня приспособленности
     *
     * @param fitness новый уровень приспособленности
     */
    void setFitness(Double fitness);

    /**
     * Получение текущей позиции
     *
     * @return числовое значение текущей позиции
     */
    Double[] getPosition();

    /**
     * Получение лучшего уровня приспособленности
     *
     * @return числовое значение уровня приспособленности
     */
    Double getBestFitness();

    /**
     * Обновление лучшего уровня приспособленности
     *
     * @param bestFitness новый лучший уровень приспособленности
     */
    void setBestFitness(Double bestFitness);

    /**
     * Получение позиции с лучшим уровнем приспособленности
     *
     * @return числовые значения позиции
     */
    Double[] getBestPosition();

    /**
     * Обновление позиции с лучшим уровнем приспособленности
     *
     * @param bestPosition новая позиция с лучшим уровенем приспособленности
     */
    void setBestPosition(Double[] bestPosition);

    /**
     * Получение текущего вектора движения
     *
     * @return числовые значения вектора
     */
    Double[] getSpeed();

}
