package com.asecave.main;

public class LineConstraint extends Constraint {

	private Entity e1;
	private Entity e2;
	private float length;

	public LineConstraint(Entity e1, Entity e2, float length) {
		this.e1 = e1;
		this.e2 = e2;
		this.length = length;
	}

	@Override
	public void update(float dt, QuadTree<Entity> entities) {
		
		if (e1.isFixed() && e2.isFixed()) {
			return;
		}
		
		float dx = e2.getPos().x - e1.getPos().x;
		float dy = e2.getPos().y - e1.getPos().y;
		float dist2 = dx * dx + dy * dy;
		float len2 = length * length;
		float dif = len2 - dist2;
		float percent = dif / dist2 / 2;

		float offsetX = dx * percent;
		float offsetY = dy * percent;

		if (e1.isFixed() && !e2.isFixed()) {
			e2.getPos().x += offsetX;
			e2.getPos().y += offsetY;
		} else if (!e1.isFixed() && e2.isFixed()) {
			e1.getPos().x -= offsetX;
			e1.getPos().y -= offsetY;
		} else {
			e1.getPos().x -= offsetX;
			e1.getPos().y -= offsetY;
			e2.getPos().x += offsetX;
			e2.getPos().y += offsetY;
		}
	}

	public Entity getE1() {
		return e1;
	}

	public Entity getE2() {
		return e2;
	}
}
