package com.asecave.main.entity;

import com.asecave.main.QuadTree;

public class Constraint extends Entity {
	
	public Constraint(boolean collidable) {
		super(0, 0, false, collidable);
	}

	@Override
	public void update(int steps, QuadTree<Entity> entities) {
		
	}
}
