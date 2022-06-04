package com.asecave.main;

public abstract class DESolver {
	
	protected AccellerationFunction af;
	
	public DESolver(AccellerationFunction af) {
		this.af = af;
	}
	
	public abstract void solve(Entity e, float dt);
}
