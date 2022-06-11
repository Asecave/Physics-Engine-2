package com.asecave.render;

import com.asecave.main.entity.Circle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class CircleRenderer extends EntityRenderer {
	
	public static CircleRenderer INSTANCE = new CircleRenderer();

	public void render(ShapeRenderer sr, Circle c) {
		super.render(sr, c);
	    sr.setColor(Color.WHITE);
		sr.set(ShapeType.Filled);
		sr.circle(c.getPos().x, c.getPos().y, c.getRadius(), 50);
	}

}
