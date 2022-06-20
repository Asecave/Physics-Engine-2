package com.asecave.render;

import com.asecave.main.entity.Circle;
import com.asecave.main.entity.Entity;
import com.asecave.main.entity.LineConstraint;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class OutlineRenderer {
	
	public static OutlineRenderer INSTANCE = new OutlineRenderer();

	public void render(ShapeRenderer sr, Entity e, float distance, float width, Color color) {

		sr.set(ShapeType.Line);
		sr.setColor(color);

		if (e instanceof Circle) {
			Circle c = (Circle) e;
			float r = c.getRadius() + distance;
			float max = r + width;
			while (r < max) {
				sr.circle(c.getPos().x, c.getPos().y, r, EntityRenderer.getDetail(r));
				
				r += 0.1f;
			}
		} else if (e instanceof LineConstraint) {

		}
	}
}
