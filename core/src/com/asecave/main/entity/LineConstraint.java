package com.asecave.main.entity;

import com.asecave.main.QuadTree;

public class LineConstraint extends Constraint {

	private Entity e1;
	private Entity e2;
	private float length;
	private float strength = 1f;
	private boolean capsule;
	
	public LineConstraint(Entity e1, Entity e2, float length) {
		this(e1, e2, length, true, true);
	}

	public LineConstraint(Entity e1, Entity e2, float length, boolean capsule, boolean collidable) {
		super(collidable);
		this.e1 = e1;
		this.e2 = e2;
		this.length = length;
		this.capsule = capsule;
		setPos();
	}

	@Override
	public void update(int steps, QuadTree<Entity> entities) {
		
		if (e1.isFixed() && e2.isFixed()) {
			return;
		}
		
		float dx = e2.pos.x - e1.pos.x;
		float dy = e2.pos.y - e1.pos.y;
		float dist = (float) Math.sqrt(dx * dx + dy * dy);
		float dif = length - dist;
		float nx = dx / dist;
		float ny = dy / dist;

		if (e1.isFixed() && !e2.isFixed()) {
			e2.pos.x += nx * dif * strength;
			e2.pos.y += ny * dif * strength;
		} else if (!e1.isFixed() && e2.isFixed()) {
			e1.pos.x -= nx * dif * strength;
			e1.pos.y -= ny * dif * strength;
		} else {
			e1.pos.x -= nx * dif * 0.5f * strength;
			e1.pos.y -= ny * dif * 0.5f * strength;
			e2.pos.x += nx * dif * 0.5f * strength;
			e2.pos.y += ny * dif * 0.5f * strength;
		}
		
		setPos();
	}
	
	private void setPos() {

		float dx = e2.pos.x - e1.pos.x;
		float dy = e2.pos.y - e1.pos.y;
		
		pos.x = e1.pos.x + dx * 0.5f;
		pos.y = e1.pos.y + dy * 0.5f;
	}

	public Entity getE1() {
		return e1;
	}

	public Entity getE2() {
		return e2;
	}
	
	public boolean isCapsule() {
		return capsule;
	}

	public void setCapsule(boolean capsule) {
		this.capsule = capsule;
	}
}
