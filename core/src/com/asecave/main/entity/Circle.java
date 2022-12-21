package com.asecave.main.entity;

import java.util.LinkedList;

import com.asecave.main.QuadTree;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Circle extends Entity {

	private float radius = 0f;

	public static Circle largest;

	public Circle() {
	}

	public Circle(float posX, float posY, float radius) {
		super(posX, posY);
		this.radius = radius;
		if (largest == null || largest.radius < radius) {
			largest = this;
		}
	}

	public float getRadius() {
		return radius;
	}

	@Override
	protected void resolveCollision(QuadTree<Entity> entities) {

		if (collidable) {

			Rectangle r = getAABB();

			LinkedList<Entity> near = entities.query(r);
			for (Entity e : near) {
				if (e != this) {
					if (e instanceof Circle) {
						Circle c = (Circle) e;
						float dst = pos.dst(c.pos);
						float r2 = (c.radius + radius);
						if (dst < r2) {
							Vector2 n = c.pos.cpy().sub(pos).nor();
							float dstResolve = r2 - dst;
							if (fixed) {
								c.pos.add(n.scl(dstResolve));
							} else if (!fixed && !c.fixed) {
								pos.sub(n.scl(dstResolve / 2));
								c.pos.add(n.scl(dstResolve / 2));
							}
						}
					} else if (e instanceof LineConstraint) {
						LineConstraint constr = (LineConstraint) e;
						Vector2 p = constr.nearestPointOnLine(pos);
						float dst = p.dst(pos);
						float r2 = (constr.getRadius() + radius);
						if (dst < r2) {

							Vector2 dist = pos.cpy().sub(p);
							float len = dist.len();
							Vector2 n = dist.scl(1f / len);
							float dstResolve = r2 - dst;
							pos.x += n.x * dstResolve;
							pos.y += n.y * dstResolve;
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean doesPointIntersect(Vector2 point) {
		return point.dst(pos) < radius;
	}

	@Override
	public Rectangle getAABB() {
		float x = pos.x - largest.radius - radius;
		float y = pos.y - largest.radius - radius;
		return new Rectangle(x, y, largest.radius * 2 + radius * 2, largest.radius * 2 + radius * 2);
	}
}
