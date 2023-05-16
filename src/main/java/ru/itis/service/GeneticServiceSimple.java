package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.stereotype.Service;
import ru.itis.dto.GeneticResultDtoRequest;
import ru.itis.dto.GeneticResultDtoResponse;
import ru.itis.dto.PopulationCreateDtoRequest;
import ru.itis.dto.PopulationDto;
import ru.itis.genetic.Population;
import ru.itis.genetic.PopulationConfig;
import ru.itis.genetic.chromosome.Chromosome;
import ru.itis.genetic.chromosome.ChromosomeAbstract;
import ru.itis.genetic.chromosome.ChromosomeFloat;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GeneticServiceSimple implements GeneticService {

    private final Map<UUID, Population<ChromosomeFloat>> populations = new HashMap<>();

    @Override
    public UUID createPopulation(PopulationCreateDtoRequest request) {
        PopulationConfig config = PopulationConfig.builder()
                .populationSize(request.getPopulationSize())
                .generationCount(request.getGenerationCount())
                .hallOfFameSize(request.getHallOfFameSize())
                .crossingProbability(request.getCrossingProbability())
                .mutationProbability(request.getMutationProbability())
                .build();
        ChromosomeFloat min = ChromosomeFloat.create(request.getMin());
        ChromosomeFloat max = ChromosomeFloat.create(request.getMax());

        UUID id = UUID.randomUUID();
        populations.put(id, Population.createRandom(min, max, config, (localMin, localMax) -> ChromosomeFloat.create(min, max)));
        return id;
    }

    @Override
    public List<PopulationDto> getAll() {
        return populations.entrySet().stream().map(i -> {
            Population<ChromosomeFloat> population = i.getValue();
            return map(population, i.getKey());
        }).collect(Collectors.toList());
    }

    @Override
    public PopulationDto get(UUID populationId) {
        return map(populations.get(populationId), populationId);
    }

    private PopulationDto map(Population<ChromosomeFloat> population, UUID id) {
        return PopulationDto.builder()
                .id(id)
                .min(population.getMin().getGenes())
                .max(population.getMax().getGenes())
                .config(population.getConfig())
                .build();
    }

    @Override
    public GeneticResultDtoResponse calculateResult(UUID populationId, GeneticResultDtoRequest request) {
        Population<ChromosomeFloat> population = populations.get(populationId);

        Argument[] arguments = IntStream.range(0, population.getMax().getGenes().length).mapToObj(i -> "x" + i).map(Argument::new).toArray(Argument[]::new);
        Expression expression = ExpressionUtils.createExpression(request.getExpression(), arguments);
        Function<ChromosomeFloat, Double> function = (chromosome -> {
            Double[] args = chromosome.getGenes();
            for (int i = 0; i < arguments.length; i++) {
                arguments[i].setArgumentValue(args[i]);
            }
            return expression.calculate();
        });

        Double answer = population.run(function);
        ChromosomeFloat best = population.getIndividuals().stream().max(Comparator.comparingDouble(ChromosomeAbstract::getFitness)).orElseThrow();
        return GeneticResultDtoResponse.builder().bestFitness(answer).bestChromosome(best.getGenes()).build();
    }

    @Override
    public void deletePopulation(UUID populationId) {
        populations.remove(populationId);
    }
}
