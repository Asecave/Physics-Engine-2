package com.asecave.main;

import com.badlogic.gdx.math.Vector2;

public class DESolverRK4 implements DESolver {

	@Override
	public void solve(Entity e, float dt) {
		
		Vector2 nextPos = e.getPos().cpy().add(e.getVel());
//		Vector2 nextVel = 
	}

	
}
