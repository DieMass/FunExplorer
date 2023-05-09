package ru.itis.swarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Particle {
	private long[] position;
	private long[] speed;
	private double fitness;
	private long[] bestPosition;
	private double bestFitness = Double.NEGATIVE_INFINITY;

	public Particle(long[] initialPosition, long[] initialSpeed) {
		this.position = initialPosition;
		this.speed = initialSpeed;
	}
}
