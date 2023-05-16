package ru.itis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itis.swarm.FitnessFunction;
import ru.itis.swarm.Multiswarm;
import ru.itis.swarm.particle.ParticleFloat;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class SwarmTest {

	@Test
	public void createSwarm() {
		int swarmCount = 50;
		int particlesPerSwarm = 1000;

		Random random = new Random();
		ParticleFloat max = new ParticleFloat(new Double[]{512.0, 512.0}, new Double[]{0.0, 0.0});
		ParticleFloat min = new ParticleFloat(new Double[]{-512.0, -512.0}, new Double[]{0.0, 0.0});

		Double[] initialParticlePosition = {
				random.nextDouble() * (max.getPosition()[0] - min.getPosition()[0]) + min.getPosition()[0],
				random.nextDouble() * (max.getPosition()[1] - min.getPosition()[1]) + min.getPosition()[1],
		};
		Double[] initialParticleSpeed = {
				random.nextDouble() * (max.getPosition()[0] - min.getPosition()[0]) + min.getPosition()[0],
				random.nextDouble() * (max.getPosition()[1] - min.getPosition()[1]) + min.getPosition()[1],
		};
		Supplier<ParticleFloat> create = () -> new ParticleFloat(initialParticlePosition, initialParticleSpeed);
		FitnessFunction fitnessFunction = this::getEggHolder;

		Multiswarm<ParticleFloat> multiswarm = Multiswarm.create(swarmCount, particlesPerSwarm, fitnessFunction, create, min, max);

		Assertions.assertEquals(swarmCount, multiswarm.getSwarms().length);
		Arrays.stream(multiswarm.getSwarms()).forEach(i -> Assertions.assertEquals(particlesPerSwarm, i.getParticles().length));
	}

	@Test
	public void eggHolderTest() {
		Random random = new Random();
		ParticleFloat max = new ParticleFloat(new Double[]{512.0, 512.0}, new Double[]{0.0, 0.0});
		ParticleFloat min = new ParticleFloat(new Double[]{-512.0, -512.0}, new Double[]{0.0, 0.0});


		Supplier<ParticleFloat> create = () -> {
			Double[] initialParticlePosition = {
					random.nextDouble() * (max.getPosition()[0] - min.getPosition()[0]) + min.getPosition()[0],
					random.nextDouble() * (max.getPosition()[1] - min.getPosition()[1]) + min.getPosition()[1],
			};
			Double[] initialParticleSpeed = {
					random.nextDouble() * (max.getPosition()[0] - min.getPosition()[0]) + min.getPosition()[0],
					random.nextDouble() * (max.getPosition()[1] - min.getPosition()[1]) + min.getPosition()[1],
			};
			return new ParticleFloat(initialParticlePosition, initialParticleSpeed);
		};
		FitnessFunction fitnessFunction = this::getEggHolder;

		Multiswarm<ParticleFloat> multiswarm = Multiswarm.create(10, 100, fitnessFunction, create, min, max);

		for (int i = 0; i < 500; i++) {
			multiswarm.mainLoop();
		}

		Assertions.assertTrue(multiswarm.getBestFitness() > 959);
	}

	@Test
	public void himmelblauTest() {
		Random random = new Random();
		ParticleFloat max = new ParticleFloat(new Double[]{6.0, 6.0}, new Double[]{0.0, 0.0});
		ParticleFloat min = new ParticleFloat(new Double[]{-6.0, -6.0}, new Double[]{0.0, 0.0});


		Supplier<ParticleFloat> create = () -> {
			Double[] initialParticlePosition = {
					random.nextDouble() * (max.getPosition()[0] - min.getPosition()[0]) + min.getPosition()[0],
					random.nextDouble() * (max.getPosition()[1] - min.getPosition()[1]) + min.getPosition()[1],
			};
			Double[] initialParticleSpeed = {
					random.nextDouble() * (max.getPosition()[0] - min.getPosition()[0]) + min.getPosition()[0],
					random.nextDouble() * (max.getPosition()[1] - min.getPosition()[1]) + min.getPosition()[1],
			};
			return new ParticleFloat(initialParticlePosition, initialParticleSpeed);
		};
		FitnessFunction fitnessFunction = this::getHimmelblau;

		Multiswarm<ParticleFloat> multiswarm = Multiswarm.create(10, 10, fitnessFunction, create, min, max);

		for (int i = 0; i < 500; i++) {
			multiswarm.mainLoop();
		}

		Assertions.assertTrue(multiswarm.getBestFitness() > -0.0001);
	}

	public double getEggHolder(Double[] doubles) {
		return -(-(doubles[1] + 47) * Math.sin(Math.sqrt(Math.abs((doubles[0] / 2) + (doubles[1] + 47)))) - doubles[0] * Math.sin(Math.sqrt(Math.abs(doubles[0] - (doubles[1] + 47)))));
	}

	public double getHimmelblau(Double[] doubles) {
		return -(Math.pow(doubles[0] * doubles[0] + doubles[1] - 11, 2) + Math.pow(doubles[0] + doubles[1] * doubles[1] - 7, 2));
	}
}
