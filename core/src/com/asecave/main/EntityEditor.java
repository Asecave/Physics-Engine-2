package com.asecave.main;

import com.asecave.main.entity.Entity;
import com.asecave.render.OutlineRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityEditor {

	private Entity selected;

	private int animationStep;

	public EntityEditor() {

	}

	public void update() {

	}

	public void render(ShapeRenderer sr) {
		if (selected != null) {
			OutlineRenderer.INSTANCE.render(sr, selected, 0.5f, Color.GOLD);
			animationStep++;
		}
	}

	public void select(Entity e) {
		this.selected = e;
		animationStep = 0;
	}
}
