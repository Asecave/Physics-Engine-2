package com.asecave.render;

import com.asecave.main.Circle;
import com.asecave.main.Mouse;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class CircleRenderer {
	
	public static CircleRenderer INSTANCE;

	static {
		INSTANCE = new CircleRenderer();
	}

	public void render(ShapeRenderer sr, Circle c) {
		
	    sr.setColor(Color.WHITE);
		sr.set(ShapeType.Filled);
		sr.circle(c.getPos().x, c.getPos().y, c.getRadius(), 50);
		sr.setColor(Color.BLUE);
		sr.line(c.getPos(), c.getPos().cpy().add(c.getVel()));
		sr.setColor(Color.GREEN);
		sr.line(c.getPos(), c.getPos().cpy().add(c.getAcc()));
	}
}
