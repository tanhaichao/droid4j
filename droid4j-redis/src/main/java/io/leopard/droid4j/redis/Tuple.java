package io.leopard.droid4j.redis;

public class Tuple {
	private String element;
	private double score;

	public Tuple() {

	}

	public Tuple(String element, double score) {
		this.element = element;
		this.score = score;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
