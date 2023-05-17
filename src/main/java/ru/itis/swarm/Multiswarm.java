package ru.itis.swarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.swarm.particle.Particle;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Класс мультироя с несколькими независимыми роями частиц
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Multiswarm<T extends Particle<T>> {

	private T min;
	private T max;
	private Swarm<T>[] swarms;
	private Double[] bestPosition;
	private Double bestFitness = Double.NEGATIVE_INFINITY;
	private final Random random = new Random();
	private FitnessFunction fitnessFunction;

	/**
	 * Создание мультироя
	 *
	 * @param numSwarms         количество роёв в мультирое
	 * @param particlesPerSwarm количество частиц в каждом рое
	 * @param fitnessFunction   функция вычисления уровня приспособленности
	 * @param create            функция создания новой частицы
	 * @param min               минимальные значения границы области поиска
	 * @param max               максимальные значения границы области поиска
	 * @return Мультирой
	 */
	public static <T extends Particle<T>> Multiswarm<T> create(int numSwarms,
															   int particlesPerSwarm,
															   FitnessFunction fitnessFunction,
															   Supplier<T> create,
															   T min,
															   T max) {
		Multiswarm<T> multiswarm = Multiswarm.builder()
				.fitnessFunction(fitnessFunction)
				.swarms(new Swarm[numSwarms])
				.bestFitness(Double.NEGATIVE_INFINITY)
				.min(min)
				.max(max)
				.build();
		for (int i = 0; i < numSwarms; i++) {
			multiswarm.getSwarms()[i] = new Swarm<>(particlesPerSwarm, create);
		}
		return multiswarm;
	}

	/**
	 *	Итерация основного цикла вычислений
	 */
	public void mainLoop(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
		mainLoop();
	}

	public void mainLoop() {
		for (Swarm<T> swarm : swarms) {
			for (Particle particle : swarm.getParticles()) {

				Double[] particleOldPosition = particle.getPosition().clone();

				particle.setFitness(fitnessFunction.getFitness(particleOldPosition));

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

				Double[] position = particle.getPosition();
				Double[] speed = particle.getSpeed();

				position[0] += speed[0];
				position[1] += speed[1];

				position[0] = position[0] > max.getPosition()[0] ? max.getPosition()[0]
						: position[0] < min.getPosition()[0] ? min.getPosition()[0] : position[0];

				position[1] = position[1] > max.getPosition()[0] ? max.getPosition()[1]
						: position[1] < min.getPosition()[0] ? min.getPosition()[1] : position[1];

				speed[0] = getNewParticleSpeedForIndex(particle, swarm, 0);
				speed[1] = getNewParticleSpeedForIndex(particle, swarm, 1);
			}
		}
	}

	/**
	 * Возвращает новую скорость по одной из координат для текущей частицы
	 *
	 * @param particle текущая частица
	 * @param swarm    текущий рой
	 * @param index    номер координаты частицы
	 * @return Изменённая согласно весам скорость по одной из координат
	 */
	private Double getNewParticleSpeedForIndex(Particle particle, Swarm<T> swarm, int index) {
		return (Constants.INERTIA_FACTOR * particle.getSpeed()[index])
				+ (randomizePercentage(Constants.COGNITIVE_WEIGHT)
				* (particle.getBestPosition()[index] - particle.getPosition()[index]))
				+ (randomizePercentage(Constants.SOCIAL_WEIGHT)
				* (swarm.getBestPosition()[index] - particle.getPosition()[index]))
				+ (randomizePercentage(Constants.GLOBAL_WEIGHT)
				* (bestPosition[index] - particle.getPosition()[index]));
	}

	/**
	 * Возвращает случайное число между 0 и значением, переданным в качестве аргумента.
	 *
	 * @param value значение для рандомизации
	 * @return случайное значение от 0 до значения, переданного в качестве аргумента
	 */
	private double randomizePercentage(double value) {
		return random.nextDouble() * value;
	}

}
