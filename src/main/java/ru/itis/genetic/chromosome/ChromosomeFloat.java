package ru.itis.genetic.chromosome;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.itis.genetic.exception.WrongChromosomeLengthException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс реализации хромосомы с вещественным геномом
 */
@AllArgsConstructor
@Getter
@ToString
public class ChromosomeFloat extends ChromosomeAbstract<ChromosomeFloat> {
	private Double[] genes;

	/**
	 * Получение списка генов хромосомы
	 * @return список генов
	 */
	@Override
	public Double[] getGenes() {
		return genes;
	}

	/**
	 * Создание хромосомы с указанным геномом из вещественных чисел
	 * @param genes Битовый геном
	 * @return Хромосома с геномом вещественных чисел
	 */
	public static ChromosomeFloat create(Double[] genes) {
		return new ChromosomeFloat(genes);
	}

	/**
	 * Создание хромосомы со случайным вещественным геномом
	 * @param min Хромосома с минимальными значениями генов
	 * @param max Хромосома с максимальными значениями генов
	 * @return Хромосома со случайным геномом вещественных чисел
	 * @throws WrongChromosomeLengthException При различных длинах хромосом
	 */
	public static ChromosomeFloat create(ChromosomeFloat min, ChromosomeFloat max) {
		if (min.getGenes().length != max.getGenes().length) {
			throw new WrongChromosomeLengthException();
		}

		Double[] genes = ChromosomeUtils.createDoubleArray(min.getGenes(), max.getGenes());

		return ChromosomeFloat.create(genes);
	}

	@Override
	public void mutate(Double mutationProbability, ChromosomeFloat min, ChromosomeFloat max) {
		Double[] minArray = min.getGenes();
		Double[] maxArray = max.getGenes();

		ChromosomeUtils.mutateDoubleArray(getGenes(), minArray, maxArray, mutationProbability);
	}

	@Override
	public List<ChromosomeFloat> crossing(ChromosomeFloat companion) {
		ChromosomeFloat companionCast = companion;
		return ChromosomeUtils.crossing(this.getGenes(), companionCast.getGenes()).stream()
				.map(ChromosomeFloat::create).collect(Collectors.toList());
	}


}
