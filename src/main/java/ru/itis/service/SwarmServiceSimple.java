package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.stereotype.Service;
import ru.itis.dto.*;
import ru.itis.swarm.FitnessFunction;
import ru.itis.swarm.Multiswarm;
import ru.itis.swarm.particle.ParticleFloat;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SwarmServiceSimple implements SwarmService {

    private final Map<UUID, Multiswarm<ParticleFloat>> multiswarms = new HashMap<>();

    public UUID createMultiswarm(MultiswarmCreateDtoRequest dto) {
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

        UUID id = UUID.randomUUID();
        multiswarms.put(id, Multiswarm.create(dto.getNumSwarms(), dto.getParticlesPerSwarm(), fitnessFunction, create, min, max));

        return id;
    }

    @Override
    public List<SwarmDto> getAll() {
        return multiswarms.entrySet().stream().map(i -> {
            Multiswarm<ParticleFloat> multiswarm = i.getValue();
            return map(multiswarm, i.getKey());
        }).collect(Collectors.toList());
    }

    @Override
    public SwarmDto get(UUID swarmId) {
        return map(multiswarms.get(swarmId), swarmId);
    }

    private SwarmDto map(Multiswarm<ParticleFloat> multiswarm, UUID id) {
        return SwarmDto.builder()
                .id(id)
                .numSwarms(multiswarm.getSwarms().length)
                .particlesPerSwarm(multiswarm.getSwarms()[0].getParticles().length)
                .min(multiswarm.getMin().getPosition())
                .max(multiswarm.getMax().getPosition())
                .build();
    }

    public MultiswarmResultDtoResponse getResult(UUID swarmId, MultiswarmResultDtoRequest request) {
        Multiswarm<ParticleFloat> multiswarm = multiswarms.get(swarmId);
        for (int i = 0; i < request.getLoopCount(); i++) {
            multiswarm.mainLoop();
        }
        return MultiswarmResultDtoResponse.builder().bestPosition(multiswarm.getBestPosition()).bestFitness(multiswarm.getBestFitness()).build();
    }

    @Override
    public void deleteSwarm(UUID swarmId) {
        multiswarms.remove(swarmId);
    }
}
