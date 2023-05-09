package ru.itis.genetic.chromosome;

import lombok.Getter;
import lombok.ToString;

import java.util.function.Function;

@Getter
@ToString
public abstract class ChromosomeAbstract<T extends ChromosomeAbstract<T>> implements Chromosome<T> {
	private Double fitness;

	@Override
	public void fit(Function<T, Double> fitFunction) {
		T thiss = (T) this;
		fitness = fitFunction.apply(thiss);
	}
}
