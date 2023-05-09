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
public class Multiswarm {
	private Swarm[] swarms;
	private long[] bestPosition;
	private double bestFitness = Double.NEGATIVE_INFINITY;
	private Random random = new Random();
	private FitnessFunction fitnessFunction;

	public Multiswarm(int numSwarms, int particlesPerSwarm, FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
		this.swarms = new Swarm[numSwarms];
		for (int i = 0; i < numSwarms; i++) {
			swarms[i] = new Swarm(particlesPerSwarm);
		}
	}

	public void mainLoop() {
		for (Swarm swarm : swarms) {
			for (Particle particle : swarm.getParticles()) {

				long[] particleOldPosition = particle.getPosition().clone();

				// Calculate the particle fitness.
				particle.setFitness(fitnessFunction.getFitness(particleOldPosition));

				// Check if a new best position has been found for the particle
				// itself, within the swarm and the multiswarm.
				if (particle.getFitness() > particle.getBestFitness()) {
					particle.setBestFitness(particle.getFitness());
					particle.setBestPosition(particleOldPosition);

					if (particle.getFitness() > swarm.getBestFitness()) {
						swarm.setBestFitness(particle.getFitness());
						swarm.setBestPosition(particleOldPosition);

						if (swarm.getBestFitness() > bestFitness) {
							bestFitness = swarm.getBestFitness();
							bestPosition = swarm.getBestPosition().clone();
						}

					}
				}

				// Updates the particle position by adding the speed to the
				// actual position.
				long[] position = particle.getPosition();
				long[] speed = particle.getSpeed();

				position[0] += speed[0];
				position[1] += speed[1];

				// Updates the particle speed.
				speed[0] = getNewParticleSpeedForIndex(particle, swarm, 0);
				speed[1] = getNewParticleSpeedForIndex(particle, swarm, 1);
			}
		}
	}

	private int getNewParticleSpeedForIndex(Particle particle, Swarm swarm, int index) {
		return (int) ((Constants.INERTIA_FACTOR * particle.getSpeed()[index])
				+ (randomizePercentage(Constants.COGNITIVE_WEIGHT)
				* (particle.getBestPosition()[index] - particle.getPosition()[index]))
				+ (randomizePercentage(Constants.SOCIAL_WEIGHT)
				* (swarm.getBestPosition()[index] - particle.getPosition()[index]))
				+ (randomizePercentage(Constants.GLOBAL_WEIGHT)
				* (bestPosition[index] - particle.getPosition()[index])));
	}

	/**
	 * Returns a random number between 0 and the value passed as argument.
	 *
	 * @param value
	 *            the value to randomize
	 * @return a random value between 0 and the one passed as argument
	 */
	private double randomizePercentage(double value) {
		return random.nextDouble() * value;
	}

}
