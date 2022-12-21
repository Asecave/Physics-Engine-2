package com.asecave.main.entity;

import com.asecave.main.QuadTree;

public abstract class Constraint extends Entity {
	
	public Constraint(boolean collidable) {
		super(0, 0, false, collidable);
	}

	public abstract void satisfyConstraint(int steps, QuadTree<Entity> entities);
	
	@Override
	public void update(QuadTree<Entity> entities) {
		resolveCollision(entities);
	}
}
