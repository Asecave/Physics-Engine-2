package com.asecave.main.entity;

public class Circle extends Entity {
	
	private float radius = 0f;

	public Circle() {
	}
	
	public Circle(float posX, float posY, float radius) {
		super(posX, posY);
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}
}
