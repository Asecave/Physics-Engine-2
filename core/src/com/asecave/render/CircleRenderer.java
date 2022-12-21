package com.asecave.render;

import com.asecave.main.Main;
import com.asecave.main.entity.Circle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class CircleRenderer extends EntityRenderer {

	public static CircleRenderer INSTANCE = new CircleRenderer();

	public void render(ShapeRenderer sr, Circle c) {
		trailSize = c.getRadius() / 2;
		super.render(sr, c);

		int detail = getDetail(c.getRadius());
		if (detail == 1) {
			sr.set(ShapeType.Point);
			sr.setColor(Color.WHITE);
			sr.point(c.getPos().x, c.getPos().y, 0f);
		} else {
			sr.set(ShapeType.Filled);
			sr.setColor(Color.BLACK);
			sr.circle(c.getPos().x, c.getPos().y, c.getRadius(), detail);
			sr.setColor(Color.WHITE);
			sr.circle(c.getPos().x, c.getPos().y, c.getRadius() - 0.4f, detail);
			sr.setColor(Color.BLACK);
			sr.circle(c.getPos().x, c.getPos().y, c.getRadius() * 0.1f, detail);
		}
	}

	public void renderBackground(ShapeRenderer sr, Circle c) {
		int detail = getDetail(c.getRadius());
		if (detail > 1) {
			sr.set(ShapeType.Filled);
			sr.setColor(Main.backgroundColor.cpy().mul(shadowIntensity));
			sr.circle(c.getPos().x + shadowDistance, c.getPos().y + shadowDistance, c.getRadius(), detail);
			if (c.getRadius() < shadowDistance) {
				sr.rectLine(c.getPos(), c.getPos().cpy().add(shadowDistance, shadowDistance), c.getRadius() / 2);
			}
		}
		super.renderBackground(sr, c);
	}

}
