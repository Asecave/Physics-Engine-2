package com.asecave.main.entity;

import com.asecave.main.QuadTree;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	protected Vector2 pos; // in m

	protected Vector2 oldPos; // in m

	protected boolean fixed;

	private Vector2 gravity = new Vector2(0f, 0.01f);

	private Vector2[] trail;
	private int trailIndex = 0;

	public Entity() {
		this(0f, 0f);
	}

	public Entity(float posX, float posY) {
		this(posX, posY, false);
	}

	public Entity(float posX, float posY, boolean fixed) {
		this.pos = new Vector2(posX, posY);
		this.oldPos = this.pos.cpy().add(0f, 0f);
		this.fixed = fixed;
	}

	public void update(int steps, QuadTree<Entity> entities) {

		if (!fixed) {
			move(steps);
		}
		resolveCollision(entities);
	}

	private void move(int steps) {
		float vx = pos.x - oldPos.x;
		float vy = pos.y - oldPos.y;

		oldPos.set(pos);

		pos.x += vx;
		pos.y += vy;
		pos.x += gravity.x;
		pos.y += gravity.y;

		if (trail != null) {
			trail[trailIndex].set(pos);
			trailIndex++;
			if (trailIndex == trail.length) {
				trailIndex = 0;
			}
		}
	}

	private void resolveCollision(QuadTree<Entity> entities) {

	}

	public Vector2 getPos() {
		return pos;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setTrailEnabled(boolean b) {
		if (b) {
			trail = new Vector2[1000];
			for (int i = 0; i < trail.length; i++) {
				trail[i] = new Vector2();
			}
		} else {
			trail = null;
		}
	}

	public Vector2[] getTrail() {
		return trail;
	}
	
	public int getTrailIndex() {
		return trailIndex;
	}
}
