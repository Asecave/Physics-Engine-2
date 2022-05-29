package com.asecave.main;

import com.badlogic.gdx.math.Vector2;

public class DESolverEuler implements DESolver {

	@Override
	public void solve(Entity e, float dt) {
		
		e.getPos().x += e.getVel().x * dt;
		e.getPos().y += e.getVel().y * dt;
		
		e.getVel().add(e.getAcc().cpy().scl(dt));
		
		e.getAcc().set(getAccAtPos(e.getPos()));
	}
	
	private Vector2 getAccAtPos(Vector2 pos) {
		return Mouse.get().cpy().sub(pos).nor().scl(9.81f);
	}

}
