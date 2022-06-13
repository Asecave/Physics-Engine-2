package com.asecave.main.entity;

import com.asecave.main.QuadTree;
import com.asecave.main.Trail;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	protected Vector2 pos;
	protected Vector2 oldPos;
	protected Vector2 oldV;
	protected boolean fixed;
	protected boolean collidable;
	
	private final static Vector2 gravity = new Vector2(0f, 0.01f);
	private int z;
	private Trail trail;
	
	private static int lowestZ = 0;
	private static int highestZ = 0;

	public Entity() {
		this(0f, 0f);
	}

	public Entity(float posX, float posY) {
		this(posX, posY, false);
	}

	public Entity(float posX, float posY, boolean fixed) {
		this(posX, posY, fixed, true);
	}
	
	public Entity(float posX, float posY, boolean fixed, boolean collidable) {
		this.pos = new Vector2(posX, posY);
		this.oldPos = this.pos.cpy();
		this.oldV = this.oldPos.cpy();
		this.fixed = fixed;
		this.collidable = collidable;
		z = ++highestZ;
	}

	public void update(QuadTree<Entity> entities) {

		if (!fixed) {
			move();
		}
		resolveCollision(entities);
	}

	protected void move() {
		float vx = pos.x - oldPos.x;
		float vy = pos.y - oldPos.y;
		
		oldV.set(vx, vy);

		oldPos.set(pos);

		pos.x += vx;
		pos.y += vy;
		pos.x += gravity.x;
		pos.y += gravity.y;
		
		if (trail != null) {
			trail.note(pos);
		}
	}
	
	public Rectangle getAABB() {
		return new Rectangle(pos.x, pos.y, 0, 0);
	}

	protected void resolveCollision(QuadTree<Entity> entities) {
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
			trail = new Trail(50);
		} else {
			trail = null;
		}
	}

	public Trail getTrail() {
		return trail;
	}
	
	public int getZ() {
		return z;
	}
	
	public void moveToForeground() {
		z = ++highestZ;
	}

	public void moveToBackground() {
		z = --lowestZ;
	}
	
	public void setCollidable(boolean b) {
		collidable = b;
	}
}
