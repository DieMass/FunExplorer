package ru.itis.genetic.chromosome;

import java.util.List;
import java.util.function.Function;

public interface Chromosome<T extends Chromosome<T>> {

	Comparable<?>[] getGenes();

	void fit(Function<T, Double> fitFunction);

	Double getFitness();

	void mutate(Double mutationProbability, T min, T max);

	List<T> crossing(T companion);
}
