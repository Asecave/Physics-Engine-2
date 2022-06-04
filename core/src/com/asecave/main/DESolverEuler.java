package com.asecave.main;

public class DESolverEuler extends DESolver {

	public DESolverEuler(AccellerationFunction af) {
		super(af);
	}

	@Override
	public void solve(Entity e, float dt) {
		
		e.getPos().x += e.getVel().x * dt;
		e.getPos().y += e.getVel().y * dt;
		
		e.getVel().add(e.getAcc().cpy().scl(dt));
		
		e.getAcc().set(af.getAccAtPos(e.getPos()));
	}
}
