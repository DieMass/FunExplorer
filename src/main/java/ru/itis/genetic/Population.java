package ru.itis.genetic;

import lombok.*;
import ru.itis.genetic.chromosome.Chromosome;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Популяция индивидов
 *
 * @param <T>
 */
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Population<T extends Chromosome<T>> {

	private final T min;
	private final T max;
	private final PopulationConfig config;
	private final Set<T> individuals = new HashSet<>();

	/**
	 * Создание популяции
	 *
	 * @param min    минимальное значение для гена
	 * @param max    максимальное значение для гена
	 * @param config настройки популяции
	 * @param create функция создание индивида
	 * @param <T>    тип хромосомы индивида
	 * @return созданная популяция
	 */
	public static <T extends Chromosome<T>> Population<T> createRandom(T min,
																	   T max,
																	   PopulationConfig config,
																	   BiFunction<T, T, T> create) {
		Population<T> population = new Population<T>(min, max, config);

		population.individuals.addAll(IntStream.range(0, population.getConfig().getPopulationSize())
				.mapToObj(i -> create.apply(min, max))
				.collect(Collectors.toSet()));

		return population;
	}


	/**
	 * Запуск генетического алгоритма для популяции
	 *
	 * @param fitFunction Функция вычисления значения приспособленности для индивида
	 */
	public Double run(Function<T, Double> fitFunction) {
		individuals.forEach(i -> i.fit(fitFunction));
		T best = null;
		for (int i = 0; i < config.getGenerationCount(); i++) {
			best = iterate(i, fitFunction);
		}
		return best.getFitness();
	}

	private T iterate(int iteration, Function<T, Double> fitFunction) {
		List<T> list = individuals.stream().sorted(Comparator.comparingDouble(T::getFitness).reversed()).collect(Collectors.toList());
		List<T> hallOfFame = new ArrayList<>(list.subList(0, config.getHallOfFameSize()));
		Random random = new Random();
		int crossingIndividuals = Double.valueOf(config.getCrossingProbability() * config.getPopulationSize()).intValue() - config.getHallOfFameSize();
		int childrenCount = config.getPopulationSize() - config.getHallOfFameSize();

		individuals.clear();
		List<T> all = new ArrayList<>(list);
		for (int i = 0; i < crossingIndividuals; i += 2) {
			int firstIndex = random.nextInt(list.size());
			T first = list.remove(firstIndex);
			int secondIndex = random.nextInt(list.size());
			T second = list.remove(secondIndex);
			List<T> children = first.crossing(second);
			all.addAll(children);
			if (individuals.size() < childrenCount) {
				T child = children.get(0);
				child.mutate(config.getMutationProbability(), min, max);
			}
			if (individuals.size() < childrenCount) {
				T child = children.get(1);
				child.mutate(config.getMutationProbability(), min, max);
			}
		}
		all.forEach(i -> i.fit(fitFunction));
		individuals.addAll(hallOfFame);
		all.removeAll(hallOfFame);
		while (individuals.size() < config.getPopulationSize()) {
			int firstIndex = random.nextInt(all.size());
			int secondIndex = random.nextInt(all.size());
			int thirdIndex = random.nextInt(all.size());
			T first = all.get(firstIndex);
			T second = all.get(secondIndex);
			T third = all.get(thirdIndex);
			T best = Stream.of(first, second, third).max(Comparator.comparingDouble(Chromosome::getFitness)).orElseThrow();
			all.remove(best);
			individuals.add(best);
		}

		individuals.forEach(i -> i.fit(fitFunction));
		T best = individuals.stream().max(Comparator.comparingDouble(Chromosome::getFitness)).orElseThrow();
		System.out.printf("Gen %s, population: %s, fit: %s, Best: %s%n", iteration + 1, individuals.size(), best.getFitness(), best);
		return best;
	}
}
