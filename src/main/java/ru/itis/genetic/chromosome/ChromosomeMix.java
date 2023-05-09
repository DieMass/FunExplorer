package ru.itis.genetic.chromosome;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.itis.genetic.exception.WrongChromosomeLengthException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Класс реализации хромосомы с геномом различных типов
 */
@AllArgsConstructor
@Getter
@ToString
public class ChromosomeMix extends ChromosomeAbstract<ChromosomeMix> {
	private Boolean[] booleans;
	private Long[] longs;
	private Double[] doubles;

	@Override
	public Comparable<?>[] getGenes() {
		Comparable<?>[] result = new Comparable<?>[booleans.length + longs.length + doubles.length];
		System.arraycopy(booleans, 0, result, 0, booleans.length);
		System.arraycopy(longs, 0, result, booleans.length, longs.length);
		System.arraycopy(doubles, 0, result, booleans.length + longs.length, doubles.length);
		return result;
	}

	/**
	 * Создание хромосомы с указанным геномом различных типов
	 * @param booleans Битовый геном
	 * @param longs Целочисленный геном
	 * @param doubles Вещественный геном
	 * @return Хромосома с геномом различных типов
	 */
	public static ChromosomeMix create(Boolean[] booleans, Long[] longs, Double[] doubles) {
		return new ChromosomeMix(booleans, longs, doubles);
	}

	/**
	 * Создание хромосомы со случайным геномом различных типов
	 * @param min Хромосома с минимальными значениями генов
	 * @param max Хромосома с максимальными значениями генов
	 * @return Хромосома со случайным геномом различных типов
	 * @throws WrongChromosomeLengthException При различных длинах хромосом
	 */
	public static ChromosomeMix create(ChromosomeMix min, ChromosomeMix max) {
		if (min.getGenes().length != max.getGenes().length ||
			min.getBooleans().length != max.getBooleans().length ||
			min.getLongs().length != max.getLongs().length ||
			min.getDoubles().length != max.getDoubles().length) {
			throw new WrongChromosomeLengthException();
		}

		Boolean[] booleans = ChromosomeUtils.createBooleanArray(min.getBooleans().length);
		Long[] longs = ChromosomeUtils.createLongArray(min.getLongs(), max.getLongs());
		Double[] doubles = ChromosomeUtils.createDoubleArray(min.getDoubles(), max.getDoubles());

		return ChromosomeMix.create(booleans, longs, doubles);
	}

	@Override
	public void mutate(Double mutationProbability, ChromosomeMix min, ChromosomeMix max) {
		ChromosomeUtils.mutateBooleanArray(getBooleans(), mutationProbability);
		ChromosomeUtils.mutateLongArray(getLongs(), min.getLongs(), max.getLongs(), mutationProbability);
		ChromosomeUtils.mutateDoubleArray(getDoubles(), min.getDoubles(), max.getDoubles(), mutationProbability);
	}

	@Override
	public List<ChromosomeMix> crossing(ChromosomeMix companion) {
		List<Boolean[]> booleans = ChromosomeUtils.crossing(getBooleans(), companion.getBooleans());
		List<Long[]> longs = ChromosomeUtils.crossing(getLongs(), companion.getLongs());
		List<Double[]> doubles = ChromosomeUtils.crossing(getDoubles(), companion.getDoubles());

		return IntStream.range(0, 2).mapToObj(i -> ChromosomeMix.create(booleans.get(i), longs.get(i), doubles.get(i))).collect(Collectors.toList());
	}
}
