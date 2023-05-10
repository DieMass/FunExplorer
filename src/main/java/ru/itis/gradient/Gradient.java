package ru.itis.gradient;

public class Gradient {

	public static void main(String[] args) {
		Args min = Args.builder().x(-512.0).y(-512.0).build();
		Args max = Args.builder().x(512.0).y(512.0).build();
		Args delta = Args.builder().x(0.00001).y(0.00001).build();
		Args vector = Args.builder().x(0.0).y(0.0).build();

		int iterations = 100;

		for (int i = 0; i < iterations; i++) {
			Args point = Args.createRandom(min, max);
//			vector.
		}
	}
}
