package ru.itis.genetic.chromosome;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Класс реализации хромосомы с битовым геномом
 */
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
public class ChromosomeBit extends ChromosomeAbstract<ChromosomeBit> {
	private Boolean[] genes;
	private final UUID id = UUID.randomUUID();

	@Override
	public Boolean[] getGenes() {
		return genes;
	}

	/**
	 * Создание хромосомы со случайным битовым геномом
	 * @param genotypeSize размер хромосомы
	 * @return Хромосома с битовым геномом
	 */
	public static ChromosomeBit create(Integer genotypeSize) {
		return new ChromosomeBit(ChromosomeUtils.createBooleanArray(genotypeSize));
	}

	/**
	 * Создание хромосомы с указанным битовым геномом
	 * @param genes Битовый геном
	 * @return Хромосома с битовым геномом
	 */
	public static ChromosomeBit create(Boolean[] genes) {
		return new ChromosomeBit(genes);
	}

	@Override
	public void mutate(Double mutationProbability, ChromosomeBit min, ChromosomeBit max) {
		ChromosomeUtils.mutateBooleanArray(getGenes(), mutationProbability);
	}

	@Override
	public List<ChromosomeBit> crossing(ChromosomeBit companion) {
		return ChromosomeUtils.crossing(this.getGenes(), companion.getGenes()).stream()
				.map(ChromosomeBit::create).collect(Collectors.toList());
	}


}
