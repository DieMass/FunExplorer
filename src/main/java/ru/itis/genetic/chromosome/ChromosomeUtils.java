package ru.itis.genetic.chromosome;

import ru.itis.genetic.exception.MutationProbabilityOutOfBoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class ChromosomeUtils {

	public static Boolean[] createBooleanArray(Integer genotypeSize) {
		Random random = new Random();

		return IntStream.range(0, genotypeSize).mapToObj(i -> random.nextBoolean()).toArray(Boolean[]::new);
	}

	public static Long[] createLongArray(Long[] min, Long[] max) {
		Random random = new Random();

		return IntStream.range(0, min.length)
				.mapToLong(i -> random.longs(min[i], max[i]).findFirst().getAsLong()).boxed()
				.toArray(Long[]::new);
	}

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

	public static void mutateBooleanArray(Boolean[] booleans, Double mutationProbability) {
		mutateArrayTemplate(booleans, booleans, booleans, mutationProbability, (random, args) -> random.nextBoolean());
	}

	public static void mutateLongArray(Long[] longs, Long[] min, Long[] max, Double mutationProbability) {
		mutateArrayTemplate(longs, min, max, mutationProbability, (random, args) -> random.longs(args.get(0), args.get(1)).findFirst().orElseThrow());
	}

	public static void mutateDoubleArray(Double[] longs, Double[] min, Double[] max, Double mutationProbability) {
		mutateArrayTemplate(longs, min, max, mutationProbability, (random, args) -> random.doubles(args.get(0), args.get(1)).findFirst().orElseThrow());
	}

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
