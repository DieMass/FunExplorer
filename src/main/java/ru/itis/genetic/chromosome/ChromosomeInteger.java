package ru.itis.genetic.chromosome;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.itis.genetic.exception.WrongChromosomeLengthException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@ToString
public class ChromosomeInteger extends ChromosomeAbstract<ChromosomeInteger> {
	private Long[] genes;

	@Override
	public Long[] getGenes() {
		return genes;
	}

	public static ChromosomeInteger create(Long[] genes) {
		return new ChromosomeInteger(genes);
	}

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
