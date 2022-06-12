package com.asecave.main.entity;

import java.util.LinkedList;

import com.asecave.main.QuadTree;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Circle extends Entity {

	private float radius = 0f;
	protected float theta;
	protected float oldTheta;

	protected static Circle largest;

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

	public float getTheta() {
		return theta;
	}

	@Override
	protected void move() {
		super.move();

		float da = theta - oldTheta;
		oldTheta = theta;
		theta += da;
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
					}
				}
			}
		}
	}

	@Override
	public Rectangle getAABB() {
		float x = pos.x - largest.radius - radius;
		float y = pos.y - largest.radius - radius;
		return new Rectangle(x, y, largest.radius * 2 + radius * 2, largest.radius * 2 + radius * 2);
	}
}
