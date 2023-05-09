package ru.itis.genetic.chromosome;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.itis.genetic.exception.WrongChromosomeLengthException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс реализации хромосомы с целочисленным геномом
 */
@AllArgsConstructor
@Getter
@ToString
public class ChromosomeInteger extends ChromosomeAbstract<ChromosomeInteger> {
	private Long[] genes;

	@Override
	public Long[] getGenes() {
		return genes;
	}

	/**
	 * Создание хромосомы с указанным целочисленным геномом
	 * @param genes Битовый геном
	 * @return Хромосома с целочисленным геномом
	 */
	public static ChromosomeInteger create(Long[] genes) {
		return new ChromosomeInteger(genes);
	}

	/**
	 * Создание хромосомы со случайным целочисленным геномом
	 * @param min Хромосома с минимальными значениями генов
	 * @param max Хромосома с максимальными значениями генов
	 * @return Хромосома со случайным геномом целых чисел
	 * @throws WrongChromosomeLengthException При различных длинах хромосом
	 */
	public static ChromosomeInteger create(ChromosomeInteger min, ChromosomeInteger max) {
		if (min.getGenes().length != max.getGenes().length) {
			throw new WrongChromosomeLengthException();
		}

		Long[] genes = ChromosomeUtils.createLongArray(min.getGenes(), max.getGenes());

		return ChromosomeInteger.create(genes);
	}

	@Override
	public void mutate(Double mutationProbability, ChromosomeInteger min, ChromosomeInteger max) {
		ChromosomeUtils.mutateLongArray(getGenes(), min.getGenes(), max.getGenes(), mutationProbability);
	}

	@Override
	public List<ChromosomeInteger> crossing(ChromosomeInteger companion) {
		return ChromosomeUtils.crossing(this.getGenes(), companion.getGenes()).stream()
				.map(ChromosomeInteger::create).collect(Collectors.toList());
	}
}
