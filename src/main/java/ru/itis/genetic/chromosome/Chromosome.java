package ru.itis.genetic.chromosome;

import java.util.List;
import java.util.function.Function;

/**
 * Интерфейс для описания методов реализации хромосомы
 */
public interface Chromosome<T extends Chromosome<T>> {


	/**
	 * Получение списка генов хромосомы
	 * @return Список генов
	 */
	Comparable<?>[] getGenes();

	/**
	 * Вычисление уровня приспособленности
	 * @param fitFunction Функция вычисления значения приспособленности
	 */
	void fit(Function<T, Double> fitFunction);

	/**
	 * @return Получение вычисленного уровня приспособленности
	 */
	Double getFitness();

	/**
	 * Мутация генов с указанной вероятностью
	 * @param mutationProbability Вероятность мутации гена
	 * @param min Минимальное значение для случайной мутации
	 * @param max Максимальное значение для случайной мутации
	 */
	void mutate(Double mutationProbability, T min, T max);

	/**
	 * Функция скрещивания с другой хромосомой
	 * @param companion Хромосома для скрещивания
	 * @return Новая измененная хромосома
	 */
	List<T> crossing(T companion);
}
