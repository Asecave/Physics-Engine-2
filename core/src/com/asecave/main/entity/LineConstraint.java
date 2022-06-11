package com.asecave.main.entity;

import com.asecave.main.QuadTree;

public class LineConstraint extends Constraint {

	private Entity e1;
	private Entity e2;
	private float length;

	public LineConstraint(Entity e1, Entity e2, float length) {
		this.e1 = e1;
		this.e2 = e2;
		this.length = length;
		
		setPos();
	}

	@Override
	public void update(int steps, QuadTree<Entity> entities) {
		
		if (e1.isFixed() && e2.isFixed()) {
			return;
		}
		
		float dx = e2.getPos().x - e1.getPos().x;
		float dy = e2.getPos().y - e1.getPos().y;
		float dist = (float) Math.sqrt(dx * dx + dy * dy);
		float dif = length - dist;
		float nx = dx / dist;
		float ny = dy / dist;

		if (e1.isFixed() && !e2.isFixed()) {
			e2.getPos().x += nx * dif;
			e2.getPos().y += ny * dif;
		} else if (!e1.isFixed() && e2.isFixed()) {
			e1.getPos().x -= nx * dif;
			e1.getPos().y -= ny * dif;
		} else {
			e1.getPos().x -= nx * dif * 0.5f;
			e1.getPos().y -= ny * dif * 0.5f;
			e2.getPos().x += nx * dif * 0.5f;
			e2.getPos().y += ny * dif * 0.5f;
		}
		
		setPos();
	}
	
	private void setPos() {

		float dx = e2.getPos().x - e1.getPos().x;
		float dy = e2.getPos().y - e1.getPos().y;
		
		pos.x = e1.getPos().x + dx * 0.5f;
		pos.y = e1.getPos().y + dy * 0.5f;
	}

	public Entity getE1() {
		return e1;
	}

	public Entity getE2() {
		return e2;
	}
}
