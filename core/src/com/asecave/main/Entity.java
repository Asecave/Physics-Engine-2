package com.asecave.main;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	private Vector2 pos; // in m
	
	private Vector2 v; // in m/s
	
	private Vector2 a; // in m/s^2
	
	private float m = 0f; // in kg
	
	public Entity() {
		this(0f, 0f);
	}
	
	public Entity(float posX, float posY) {
		this(posX, posY, 1f);
	}
	
	public Entity(float posX, float posY, float mass) {
		this.pos = new Vector2(posX, posY);
		this.v = new Vector2();
		this.m = mass;
		a = new Vector2();
	}
	
	public Vector2 getPos() {
		return pos;
	}
	
	public Vector2 getVel() {
		return v;
	}
	
	public Vector2 getAcc() {
		return a;
	}
	
	public float getMass() {
		return m;
	}
	
	public void setConstraint(Constraint constraint) {
		
	}
}