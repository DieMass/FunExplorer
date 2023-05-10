package ru.itis.swarm.particle;

/**
 * Интерфейс для описания методов реализации частицы
 */
public interface Particle<T extends Particle<T>> {
	/**
	 * @return Получение текущего уровня приспособленности
	 */
	Double getFitness();
	/**
	 * Обновление текущего уровня приспособленности
	 * @param fitness новый уровень приспособленности
	 */
	void setFitness(Double fitness);
	/**
	 * @return Получение текущей позиции
	 */
	Double[] getPosition();
	/**
	 * @return Получение лучшего уровня приспособленности
	 */
	Double getBestFitness();
	/**
	 * Обновление лучшего уровня приспособленности
	 * @param bestFitness новый лучший уровень приспособленности
	 */
	void setBestFitness(Double bestFitness);
	/**
	 * @return Получение позиции с лучшим уровнем приспособленности
	 */
	Double[] getBestPosition();
	/**
	 * Обновление позиции с лучшим уровнем приспособленности
	 * @param bestPosition новая позиция с лучшим уровенем приспособленности
	 */
	void setBestPosition(Double[] bestPosition);
	/**
	 * @return Получение текущего вектора движения
	 */
	Double[] getSpeed();

}
