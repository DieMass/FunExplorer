package ru.itis.swarm;

public class Constants {

	/**
	 * Фактор инерции побуждает частицу продолжать движение в текущем направлении.
	 */
	public static final double INERTIA_FACTOR = 0.729;

	/**
	 * Когнитивный вес побуждает частицу двигаться к своему наилучшему историческому положению.
	 */
	public static final double COGNITIVE_WEIGHT = 1.49445;

	/**
	 * Социальный вес побуждает частицу двигаться к наилучшей позиции,
	 * обнаруженной любым из ее товарищей по рою.
	 */
	public static final double SOCIAL_WEIGHT = 1.49445;

	/**
	 * Глобальный вес побуждает частицу двигаться к наилучшей позиции, найденной любой частицей в любом рое.
	 */
	public static final double GLOBAL_WEIGHT = 0.3645;

	/**
	 * Приватный конструктор для служебного класса.
	 */
	private Constants() {
	}

}
