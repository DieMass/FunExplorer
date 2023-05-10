package ru.itis.swarm.particle;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class ParticleAbstract<T extends Particle<T>> implements Particle<T> {

    private Double fitness;
    private Double bestFitness = Double.NEGATIVE_INFINITY;
}
