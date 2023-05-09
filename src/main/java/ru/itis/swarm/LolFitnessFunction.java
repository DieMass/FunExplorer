package ru.itis.swarm;

public class LolFitnessFunction implements FitnessFunction {

	@Override
	public double getFitness(long[] particlePosition) {
		long health = particlePosition[0];
		long armor = particlePosition[1];

		if (health < 0 && armor < 0) {
			return -(health * armor);
		} else if (health < 0) {
			return health;
		} else if (armor < 0) {
			return armor;
		}

		double cost = (health * 2.5) + (armor * 18);
		if (cost > 3600) {
			return 3600 - cost;
		} else {
			long fitness = (health * (100 + armor)) / 100;
			return fitness;
		}
	}
}
