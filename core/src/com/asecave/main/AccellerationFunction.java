package com.asecave.main;

import com.badlogic.gdx.math.Vector2;

public class AccellerationFunction {
	
	

	protected Vector2 getAccAtPos(Vector2 pos) {
		return Mouse.get().cpy().sub(pos).nor().scl(9.81f);
	}
}
