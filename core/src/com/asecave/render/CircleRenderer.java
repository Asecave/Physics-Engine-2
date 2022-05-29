package com.asecave.render;

import com.asecave.main.Circle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class CircleRenderer {
	
	public static CircleRenderer INSTANCE;

	static {
		INSTANCE = new CircleRenderer();
	}

	public void render(ShapeRenderer sr, Circle c) {
		
	    sr.setColor(Color.WHITE);
		sr.set(ShapeType.Filled);
		sr.circle(c.getPos().x, c.getPos().y, c.getRadius(), 50);
	}
}
