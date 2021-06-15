package it.polito.tdp.PremierLeague.model;

public class Arco {
	
	private Player vertex1;
	private Player vertex2; 
	private double weight;
	
	public Arco(Player vertex1, Player vertex2, double weight) {
		super();
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.weight = weight;
	}

	public Player getVertex1() {
		return vertex1;
	}

	public void setVertex1(Player vertex1) {
		this.vertex1 = vertex1;
	}

	public Player getVertex2() {
		return vertex2;
	}

	public void setVertex2(Player vertex2) {
		this.vertex2 = vertex2;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Arco [vertex1=" + vertex1 + ", vertex2=" + vertex2 + ", weight=" + weight + "]";
	}
	
	

}
