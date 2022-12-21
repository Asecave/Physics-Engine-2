package com.asecave.render;

import com.asecave.main.entity.Circle;
import com.asecave.main.entity.Entity;
import com.asecave.main.entity.LineConstraint;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class OutlineRenderer {

	public static OutlineRenderer INSTANCE = new OutlineRenderer();

	public void render(ShapeRenderer sr, Entity e, float width, Color color) {

		sr.set(ShapeType.Filled);
		sr.setColor(color);

		if (e instanceof Circle) {
			Circle c = (Circle) e;
			float r = c.getRadius();
			float max = r + width;
			sr.circle(c.getPos().x, c.getPos().y, max, EntityRenderer.getDetail(r));
		} else if (e instanceof LineConstraint) {
			LineConstraint lc = (LineConstraint) e;
			float r = lc.getRadius();
			float max = r + width;
			sr.circle(lc.getE1().getPos().x, lc.getE1().getPos().y, max, EntityRenderer.getDetail(r));
			sr.circle(lc.getE2().getPos().x, lc.getE2().getPos().y, max, EntityRenderer.getDetail(r));
			sr.rectLine(lc.getE1().getPos(), lc.getE2().getPos(), max * 2);
		}
	}
}
