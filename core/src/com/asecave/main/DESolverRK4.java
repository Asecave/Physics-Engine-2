package com.asecave.main;

import com.badlogic.gdx.math.Vector2;

public class DESolverRK4 extends DESolver {

	public DESolverRK4(AccellerationFunction af) {
		super(af);
	}

	@Override
	public void solve(Entity e, float dt) {
		
		Vector2 k1 = getVelAt(e.getPos(), e.getVel(), 0f);
		System.out.println(k1);
		Vector2 k2 = getVelAt(e.getPos(), k1, dt / 2);
		System.out.println(k2);
		Vector2 k3 = getVelAt(e.getPos(), k2, dt / 2);
		Vector2 k4 = getVelAt(e.getPos(), k3, dt);
		
		float avgx =  dt * (1f / 6) * (k1.x + 2 * k2.x + 2 * k3.x + k4.x);
		float avgy =  dt * (1f / 6) * (k1.y + 2 * k2.y + 2 * k3.y + k4.y);

		e.getPos().add(new Vector2(avgx, avgy).scl(dt));
		e.getVel().add(getAccAt(e.getPos()));
		e.getAcc().set(getAccAt(e.getPos()));
	}
	
	private Vector2 getVelAt(Vector2 pos, Vector2 vel, float dt) {
		Vector2 acc = getAccAt(pos);
		return vel.cpy().add(acc.scl(dt));
	}

	private Vector2 getAccAt(Vector2 pos) {
		return Mouse.get().cpy().sub(pos).nor().scl(9.81f);
	}
}
