package ru.itis.swarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Swarm {
	private Particle[] particles;
	private long[] bestPosition;
	private double bestFitness = Double.NEGATIVE_INFINITY;
	private Random random = new Random();

	public Swarm(int numParticles) {
		particles = new Particle[numParticles];
		for (int i = 0; i < numParticles; i++) {
			long[] initialParticlePosition = { random.nextInt(Constants.PARTICLE_UPPER_BOUND),
					random.nextInt(Constants.PARTICLE_UPPER_BOUND) };
			long[] initialParticleSpeed = { random.nextInt(Constants.PARTICLE_UPPER_BOUND),
					random.nextInt(Constants.PARTICLE_UPPER_BOUND) };
			particles[i] = new Particle(initialParticlePosition, initialParticleSpeed);
		}
	}

}
