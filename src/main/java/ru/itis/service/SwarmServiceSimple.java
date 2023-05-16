package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.stereotype.Service;
import ru.itis.dto.MultiswarmCreateDtoRequest;
import ru.itis.dto.MultiswarmResultDtoRequest;
import ru.itis.dto.MultiswarmResultDtoResponse;
import ru.itis.swarm.FitnessFunction;
import ru.itis.swarm.Multiswarm;
import ru.itis.swarm.particle.ParticleFloat;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SwarmServiceSimple implements SwarmService {

    private Multiswarm<ParticleFloat> multiswarm;

    public void createMultiswarm(MultiswarmCreateDtoRequest dto) {
        Random random = new Random();
        ParticleFloat max = new ParticleFloat(dto.getMax(), null);
        ParticleFloat min = new ParticleFloat(dto.getMin(), null);

        Supplier<ParticleFloat> create = () -> {
            Double[] initialParticlePosition = IntStream.range(0, dto.getMax().length)
                    .mapToObj(i -> random.nextDouble() * (max.getPosition()[i] - min.getPosition()[i]) + min.getPosition()[i])
                    .toArray(Double[]::new);
            Double[] initialParticleSpeed = IntStream.range(0, dto.getMax().length)
                    .mapToObj(i -> random.nextDouble() * (max.getPosition()[i] - min.getPosition()[i]) + min.getPosition()[i])
                    .toArray(Double[]::new);
            return new ParticleFloat(initialParticlePosition, initialParticleSpeed);
        };
        Argument[] arguments = IntStream.range(0, dto.getMax().length).mapToObj(i -> "x" + i).map(Argument::new).toArray(Argument[]::new);
        Expression expression = ExpressionUtils.createExpression(dto.getExpression(), arguments);
        FitnessFunction fitnessFunction = (args) -> {
            for (int i = 0; i < arguments.length; i++) {
                arguments[i].setArgumentValue(args[i]);
            }
            return expression.calculate();
        };
        multiswarm = Multiswarm.create(dto.getNumSwarms(), dto.getParticlesPerSwarm(), fitnessFunction, create, min, max);
    }

    public MultiswarmResultDtoResponse getResult(MultiswarmResultDtoRequest request) {
        for (int i = 0; i < request.getLoopCount(); i++) {
            multiswarm.mainLoop();
        }
        return MultiswarmResultDtoResponse.builder().bestPosition(multiswarm.getBestPosition()).bestFitness(multiswarm.getBestFitness()).build();
    }
}
