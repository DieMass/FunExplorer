package ru.itis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itis.genetic.Population;
import ru.itis.genetic.PopulationConfig;
import ru.itis.genetic.chromosome.ChromosomeBit;
import ru.itis.genetic.chromosome.ChromosomeFloat;
import ru.itis.genetic.chromosome.ChromosomeInteger;
import ru.itis.genetic.chromosome.ChromosomeMix;

import java.util.Arrays;
import java.util.stream.LongStream;

public class GeneticTest {

	private final PopulationConfig config = PopulationConfig.builder().build();

	@Test
	public void createBitPopulation() {
		ChromosomeBit min = null;
		ChromosomeBit max = null;
		Integer chromosomeLength = 10;

		Population<ChromosomeBit> population = Population.createRandom(min, max, config,
				(localMin, localMax) -> ChromosomeBit.create(chromosomeLength));

		Assertions.assertEquals(config.getPopulationSize(), population.getIndividuals().size());
		population.getIndividuals().forEach(i -> Assertions.assertEquals(chromosomeLength, i.getGenes().length));
	}

	@Test
	public void createIntegerPopulation() {
		Integer chromosomeLength = 10;
		Long[] minArray = LongStream.range(0, chromosomeLength).map(i -> 0).boxed().toArray(Long[]::new);
		Long[] maxArray = LongStream.range(0, chromosomeLength).map(i -> 100).boxed().toArray(Long[]::new);

		ChromosomeInteger min = ChromosomeInteger.create(minArray);
		ChromosomeInteger max = ChromosomeInteger.create(maxArray);


		Population<ChromosomeInteger> population = Population.createRandom(min, max, config,
				(localMin, localMax) -> ChromosomeInteger.create(min, max));
		Assertions.assertEquals(config.getPopulationSize(), population.getIndividuals().size());
		population.getIndividuals().forEach(i -> Assertions.assertEquals(chromosomeLength, i.getGenes().length));
	}

	@Test
	public void createFloatPopulation() {
		Integer chromosomeLength = 10;
		Double[] minArray = LongStream.range(0, chromosomeLength).mapToDouble(i -> -9.5).boxed().toArray(Double[]::new);
		Double[] maxArray = LongStream.range(0, chromosomeLength).mapToDouble(i -> 100.0).boxed().toArray(Double[]::new);

		ChromosomeFloat min = ChromosomeFloat.create(minArray);
		ChromosomeFloat max = ChromosomeFloat.create(maxArray);

		Population<ChromosomeFloat> population = Population.createRandom(min, max, config,
				(localMin, localMax) -> ChromosomeFloat.create(min, max));
		Assertions.assertEquals(config.getPopulationSize(), population.getIndividuals().size());
		population.getIndividuals().forEach(i -> Assertions.assertEquals(chromosomeLength, i.getGenes().length));
	}

	@Test
	public void createMixedPopulation() {
		Integer resultChromosomeLength = 30;
		Integer chromosomeLength = 10;

		Boolean[] minArrayBoolean = LongStream.range(0, chromosomeLength).mapToObj(i -> Boolean.FALSE).toArray(Boolean[]::new);
		Boolean[] maxArrayBoolean = LongStream.range(0, chromosomeLength).mapToObj(i -> Boolean.TRUE).toArray(Boolean[]::new);
		Long[] minArrayLong = LongStream.range(0, chromosomeLength).map(i -> 0).boxed().toArray(Long[]::new);
		Long[] maxArrayLong = LongStream.range(0, chromosomeLength).map(i -> 100).boxed().toArray(Long[]::new);
		Double[] minArrayDouble = LongStream.range(0, chromosomeLength).mapToDouble(i -> -9.5).boxed().toArray(Double[]::new);
		Double[] maxArrayDouble = LongStream.range(0, chromosomeLength).mapToDouble(i -> 100.0).boxed().toArray(Double[]::new);

		ChromosomeMix min = ChromosomeMix.create(minArrayBoolean, minArrayLong, minArrayDouble);
		ChromosomeMix max = ChromosomeMix.create(maxArrayBoolean, maxArrayLong, maxArrayDouble);

		Population<ChromosomeMix> population = Population.createRandom(min, max, config,
				(localMin, localMax) -> ChromosomeMix.create(min, max));

		System.out.println(population);
		Assertions.assertEquals(config.getPopulationSize(), population.getIndividuals().size());
		population.getIndividuals().forEach(i -> Assertions.assertEquals(resultChromosomeLength, i.getGenes().length));
	}

	@Test
	public void testOneMax() {
		ChromosomeBit min = null;
		ChromosomeBit max = null;
		Integer chromosomeLength = 100;
		PopulationConfig config = PopulationConfig.builder().mutationProbability(0.1).crossingProbability(0.9).generationCount(100).build();
		Population<ChromosomeBit> population = Population.createRandom(min, max, config,
				(localMin, localMax) -> ChromosomeBit.create(chromosomeLength));

		Double best = population.run(genome -> (double) Arrays.stream(genome.getGenes()).filter(i -> i).count());

		Assertions.assertTrue(best > 95);
		Assertions.assertEquals(config.getPopulationSize(), population.getIndividuals().size());
		population.getIndividuals().forEach(i -> Assertions.assertEquals(chromosomeLength, i.getGenes().length));

	}

	@Test
	public void testEggHolder() {
		Integer chromosomeLength = 2;
		Double[] minArray = LongStream.range(0, chromosomeLength).mapToDouble(i -> -512).boxed().toArray(Double[]::new);
		Double[] maxArray = LongStream.range(0, chromosomeLength).mapToDouble(i -> 512).boxed().toArray(Double[]::new);

		ChromosomeFloat min = ChromosomeFloat.create(minArray);
		ChromosomeFloat max = ChromosomeFloat.create(maxArray);

		Population<ChromosomeFloat> population = Population.createRandom(min, max, config,
				(localMin, localMax) -> ChromosomeFloat.create(min, max));

		Double best = population.run(genome -> getValueAt(Arrays.stream(genome.getGenes()).toArray(Double[]::new)));

		Assertions.assertTrue(best > 950);
		Assertions.assertEquals(config.getPopulationSize(), population.getIndividuals().size());
		population.getIndividuals().forEach(i -> Assertions.assertEquals(chromosomeLength, i.getGenes().length));
	}

	public double getValueAt(Double[] doubles) {
		return -(-(doubles[1]+47)*Math.sin(Math.sqrt(Math.abs((doubles[0]/2)+(doubles[1]+47))))-doubles[0]*Math.sin(Math.sqrt(Math.abs(doubles[0]-(doubles[1]+47)))));
	}

}

