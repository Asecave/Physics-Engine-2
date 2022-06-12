package com.asecave.render;

import com.asecave.main.entity.Circle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class CircleRenderer extends EntityRenderer {

	public static CircleRenderer INSTANCE = new CircleRenderer();

	public void render(ShapeRenderer sr, Circle c) {
		super.render(sr, c);

		if (detail == 1) {
			sr.set(ShapeType.Point);
			sr.setColor(Color.WHITE);
			sr.point(c.getPos().x, c.getPos().y, 0f);
		} else {
			sr.setColor(Color.BLACK);
			sr.set(ShapeType.Filled);
			sr.circle(c.getPos().x, c.getPos().y, c.getRadius(), detail);
			sr.setColor(Color.WHITE);
			sr.circle(c.getPos().x, c.getPos().y, c.getRadius() * 0.9f, detail);
			sr.setColor(Color.BLACK);
			sr.circle(c.getPos().x, c.getPos().y, c.getRadius() * 0.1f, detail);
		}
	}

}
