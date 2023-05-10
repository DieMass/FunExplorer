package ru.itis.swarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.swarm.particle.Particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Класс роя частиц
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Swarm<T extends Particle<T>> {
	private Particle[] particles;
	private Double[] bestPosition;
	private Double bestFitness = Double.NEGATIVE_INFINITY;
	private Random random = new Random();

	/**
	 *
	 * @param numParticles Количество частиц в рое
	 * @param create Функция создания новых частиц в рое
	 */
	public Swarm(int numParticles, Supplier<T> create) {
		List<T> list = new ArrayList<>();
		for (int i = 0; i < numParticles; i++) {
			list.add(create.get());
		}
		particles = list.toArray(Particle[]::new);
	}

}
