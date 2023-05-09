package ru.itis.swarm;

public interface FitnessFunction {
	double getFitness(long[] particlePosition);
}