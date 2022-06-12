package com.asecave.render;

import com.asecave.main.Main;
import com.asecave.main.entity.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public abstract class EntityRenderer {
	
	protected static int detail;

	public void render(ShapeRenderer sr, Entity e) {
		Vector2[] trail = e.getTrail();
		if (trail != null) {
			sr.set(ShapeType.Filled);
			for (int i = 0; i < trail.length; i++) {
				Color c = Main.backgroundColor.cpy();
				c.r = (float) ((i + (trail.length - e.getTrailIndex())) % trail.length) / trail.length * (1f - c.r) + c.r;
				sr.setColor(c);
				sr.circle(trail[i].x, trail[i].y, 1f, 10);
			}
		}
	}
	
	public static void updateDetail() {

		float scale = Main.getTransformMat().getScaleX();

		detail = Math.max(Math.min((int) (Math.log(scale) * 10), 100), 1);
	}
}
