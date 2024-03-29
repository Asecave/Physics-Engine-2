package com.asecave.main.entity;

import java.util.LinkedList;

import com.asecave.main.QuadTree;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class LineConstraint extends Constraint {

	private Entity e1;
	private Entity e2;
	private float length;
	private float strength = 0.8f;
	private float radius;

	public LineConstraint(Entity e1, Entity e2, float length) {
		this(e1, e2, length, true, 0.5f);
	}

	public LineConstraint(Entity e1, Entity e2, float length, boolean collidable, float radius) {
		super(collidable);
		this.e1 = e1;
		this.e2 = e2;
		this.length = length;
		this.radius = radius;
		setPos();
	}

	@Override
	public void satisfyConstraint(int steps, QuadTree<Entity> entities) {

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

	@Override
	protected void resolveCollision(QuadTree<Entity> entities) {

		if (!collidable)
			return;

		LinkedList<Entity> near = entities.query(getAABB());

		for (Entity e : near) {
			if (e == this || e == e1 || e == e2)
				continue;
			if (e.fixed)
				continue;
			if (!(e instanceof Circle))
				continue;

			Circle c = (Circle) e;
			Vector2 p = nearestPointOnLine(c.pos);
			float dst = p.dst(c.pos);
			float r2 = (c.getRadius() + radius);
			if (dst < r2) {

				Vector2 dist = c.pos.cpy().sub(p);
				float len = dist.len();
				Vector2 n = dist.scl(1f / len);
				float dstResolve = r2 - dst;
				c.pos.x += n.x * dstResolve;
				c.pos.y += n.y * dstResolve;

			}
		}
	}

	@Override
	public Rectangle getAABB() {
		float largestR = Circle.largest.getRadius();

		float x = Math.min(e1.pos.x, e2.pos.x) - largestR - radius;
		float y = Math.min(e1.pos.y, e2.pos.y) - largestR - radius;
		float w = Math.abs(e2.pos.x - e1.pos.x) + largestR * 2 + radius * 2;
		float h = Math.abs(e2.pos.y - e1.pos.y) + largestR * 2 + radius * 2;
		return new Rectangle(x, y, w, h);
	}

	private void setPos() {

		float dx = e2.pos.x - e1.pos.x;
		float dy = e2.pos.y - e1.pos.y;

		pos.x = e1.pos.x + dx * 0.5f;
		pos.y = e1.pos.y + dy * 0.5f;
	}

	public Vector2 nearestPointOnLine(Vector2 p) {

		Vector2 ps = e1.pos.cpy().sub(p);
		Vector2 se = e2.pos.cpy().sub(e1.pos);
		float len = se.len();

		float t = ps.dot(se) / len;
		if (t > 0f) {
			t = 0f;
		}

		if (t < -len) {
			t = -len;
		}
		return e1.pos.cpy().sub(se.nor().scl(t));
	}
	
	@Override
	public boolean doesPointIntersect(Vector2 point) {
		float dst = point.dst(nearestPointOnLine(point));
		return dst < radius;
	}

	public Entity getE1() {
		return e1;
	}

	public Entity getE2() {
		return e2;
	}

	public float getRadius() {
		return radius;
	}
}
