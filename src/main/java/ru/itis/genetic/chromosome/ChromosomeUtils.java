package ru.itis.genetic.chromosome;

import ru.itis.genetic.exception.MutationProbabilityOutOfBoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * Утилитарный класс для работы с хромосомами
 */
public class ChromosomeUtils {

	/**
	 * Создание случайной хромосомы из бит
	 * @param genotypeSize длинна генома
	 * @return массив из случайных бит заданной длинны
	 */
	public static Boolean[] createBooleanArray(Integer genotypeSize) {
		Random random = new Random();

		return IntStream.range(0, genotypeSize).mapToObj(i -> random.nextBoolean()).toArray(Boolean[]::new);
	}

	/**
	 * Создание случайной хромосомы из целых чисел в заданных пределах
	 * @param min набор минимумов для генерации
	 * @param max набор минимумов для генерации
	 * @return массив случайных чисел, каждое из которых находится в заданном ему пределе
	 */
	public static Long[] createLongArray(Long[] min, Long[] max) {
		Random random = new Random();

		return IntStream.range(0, min.length)
				.mapToLong(i -> random.longs(min[i], max[i]).findFirst().getAsLong()).boxed()
				.toArray(Long[]::new);
	}

	/**
	 * Создание случайной хромосомы из вещественных чисел в заданных пределах
	 * @param min набор минимумов для генерации
	 * @param max набор максимумов для генерации
	 * @return массив случайных чисел, каждое из которых находится в заданном ему пределе
	 */
	public static Double[] createDoubleArray(Double[] min, Double[] max) {
		Random random = new Random();

		return IntStream.range(0, min.length)
				.mapToDouble(i -> random.doubles(min[i], max[i]).findFirst().getAsDouble()).boxed()
				.toArray(Double[]::new);
	}

	private static void mutationProbabilityCheck(Double mutationProbability) {
		if (mutationProbability > 1 || mutationProbability < 0) {
			throw new MutationProbabilityOutOfBoundException();
		}
	}

	private static <T> void mutateArrayTemplate(T[] objects, T[] min, T[] max,
												Double mutationProbability,
												BiFunction<Random, List<T>, T> producer) {
		mutationProbabilityCheck(mutationProbability);

		Random random = new Random();

		for (int i = 0; i < objects.length; i++) {
			if (random.nextDouble() < mutationProbability) {
				objects[i] = producer.apply(random, List.of(min[i], max[i]));
			}
		}
	}


	/**
	 * Мутация хромосомы из бит с определенной вероятностью
	 * @param booleans Хромосома из бит
	 * @param mutationProbability Вероятность мутации (mutationProbability в [0, 1])
	 * @throws MutationProbabilityOutOfBoundException При неверном значении вероятности мутации
	 */
	public static void mutateBooleanArray(Boolean[] booleans, Double mutationProbability) {
		mutateArrayTemplate(booleans, booleans, booleans, mutationProbability, (random, args) -> random.nextBoolean());
	}


	/**
	 * Мутация хромосомы из целых чисел с определенной вероятностью
	 * @param longs хромосома из целых чисел
	 * @param min ограничивающий набор минимумов для мутации каждого гена
	 * @param max ограничивающий набор максимумов для мутации каждого гена
	 * @param mutationProbability вероятность мутации гена (mutationProbability в [0, 1])
	 * @throws MutationProbabilityOutOfBoundException При неверном значении вероятности мутации
	 */
	public static void mutateLongArray(Long[] longs, Long[] min, Long[] max, Double mutationProbability) {
		mutateArrayTemplate(longs, min, max, mutationProbability, (random, args) -> random.longs(args.get(0), args.get(1)).findFirst().orElseThrow());
	}

	/**
	 * Мутация генома из целых чисел с определенной вероятностью
	 * @param longs геном из вещественных чисел
	 * @param min ограничивающий набор минимумов для мутации генома
	 * @param max ограничивающий набор максимумов для мутации генома
	 * @param mutationProbability вероятность мутации гена (mutationProbability в [0, 1])
	 * @throws MutationProbabilityOutOfBoundException При неверном значении вероятности мутации
	 */
	public static void mutateDoubleArray(Double[] longs, Double[] min, Double[] max, Double mutationProbability) {
		mutateArrayTemplate(longs, min, max, mutationProbability, (random, args) -> random.doubles(args.get(0), args.get(1)).findFirst().orElseThrow());
	}

	/**
	 * Случайное скрещивание двух хромосом
	 * @param first первая хромосома
	 * @param second вторая хромосома
	 * @param <T> тип хромосомы
	 * @return измененные скрещенные хромосомы
	 */
	public static <T> List<T[]> crossing(T[] first, T[] second) {
		T[] firstChild = Arrays.copyOf(first, first.length);
		T[] secondChild = Arrays.copyOf(first, first.length);
		Random random = new Random();

		for (int i = 0; i < first.length; i++) {
			if (random.nextBoolean()) {
				firstChild[i] = second[i];
			} else {
				secondChild[i] = second[i];
			}
		}

		return List.of(firstChild, secondChild);
	}
}
