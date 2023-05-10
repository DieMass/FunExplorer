package ru.itis.gradient;

import lombok.*;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Args {
	Double x;
	Double y;

	public static Args createRandom(Args min, Args max) {
		Random random = new Random();
		double x = random.nextDouble() * (max.getX() - min.getX()) + min.getX();
		double y = random.nextDouble() * (max.getY() - min.getY()) + min.getY();
		return new Args(x, y);
	}

	public Args getVector(Args point, Args delta, Function<Args, Double> function) {
		return null;

	}

	public static Args addVector(Args point, Args vector) {
		return Args.builder().x(point.getX() + vector.getX()).y(point.getY() + vector.getY()).build();
	}

	public static List<Args> addDelta(Args point, Args delta, Args min, Args max) {
		return null;
//		Stream.of(
//						Args.builder().x(delta.getX()).y(delta.getY()).build(),
//						Args.builder().x(-delta.getX()).y(delta.getY()).build(),
//						Args.builder().x(delta.getX()).y(-delta.getY()).build(),
//						Args.builder().x(-delta.getX()).y(-delta.getY()).build()
//				).map(i -> Args.builder().x(point.getX() + i.getX()).y(point.getY() + i.getY()).build())
//				.peek(i -> i.setX(i.getX() > max.getX() ? max.getX() : i.getX()))
//				.peek(i -> i.setX(i.getX() < min.getX() ? min.getX() : i.getX()))
//				.peek(i -> i.setY(i.getY() > max.getY() ? max.getY() : i.getY()))
//				.peek(i -> i.setY(i.getY() < min.getY() ? min.getY() : i.getY()))
//				.
//		;
//		IntStream.range(0, 4).mapToObj(i ->)
	}

}
