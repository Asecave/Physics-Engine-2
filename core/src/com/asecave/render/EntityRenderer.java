package com.asecave.render;

import com.asecave.main.Main;
import com.asecave.main.Trail;
import com.asecave.main.entity.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public abstract class EntityRenderer {
	
	protected float trailSize = 1f;
	protected float shadowDistance = 0.5f;
	protected float shadowIntensity = 0.8f;

	public void render(ShapeRenderer sr, Entity e) {
	}
	
	protected void renderTrail(ShapeRenderer sr, Trail trail) {
		if (trail != null) {
			sr.set(ShapeType.Filled);
			for (int i = 0; i < trail.get().length; i++) {

				int j = Math.abs((i + trail.getIndex()) % trail.get().length);
				
				if (trail.get()[j].x == 0 && trail.get()[j].y == 0) {
					continue;
				}
				
				Color c = trail.getColor().cpy();
				Color bg = Main.backgroundColor.cpy();
				float n = (float) i / trail.get().length;
				c.r = c.r + (1f - n) * (bg.r - c.r);
				c.g = c.g + (1f - n) * (bg.g - c.g);
				c.b = c.b + (1f - n) * (bg.b - c.b);
				sr.setColor(c);
				sr.circle(trail.get()[j].x, trail.get()[j].y, n * trailSize, 10);
				
				int next = j - 1 >= 0 ? j - 1 : trail.get().length - 1;
				
				if (!(trail.get()[next].x == 0 && trail.get()[next].y == 0)) {
					sr.rectLine(trail.get()[j].x, trail.get()[j].y, trail.get()[next].x, trail.get()[next].y, n * trailSize * 2);
				}
			}
		}
	}
	
	protected int getDetail(float offset) {
		float scale = Main.getTransformMat().getScaleX();
		return Math.max(Math.min((int) (Math.log(scale * offset) * 10), 100), 1);
	}

	public void renderBackground(ShapeRenderer sr, Entity e) {
		renderTrail(sr, e.getTrail());
	}
}
