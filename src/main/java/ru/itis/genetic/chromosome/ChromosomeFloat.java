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
public class ChromosomeFloat extends ChromosomeAbstract<ChromosomeFloat> {
	private Double[] genes;

	@Override
	public Double[] getGenes() {
		return genes;
	}

	public static ChromosomeFloat create(Double[] genes) {
		return new ChromosomeFloat(genes);
	}

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
