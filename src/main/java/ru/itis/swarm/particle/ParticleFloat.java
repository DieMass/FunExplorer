package ru.itis.swarm.particle;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс реализации частицу с координатами с плавающей точкой
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ParticleFloat extends ParticleAbstract<ParticleFloat> {
	private Double[] position;
	private Double[] speed;
	private Double[] bestPosition;

	/**
	 * Создание частицы с указанными координатами и вектором движения
	 * @param initialPosition Начальная позиция
	 * @param initialSpeed Начальная скорость
	 */
	public ParticleFloat(Double[] initialPosition, Double[] initialSpeed) {
		this.position = initialPosition;
		this.speed = initialSpeed;
	}
}
