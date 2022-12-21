package com.asecave.main;

import com.asecave.main.entity.Entity;
import com.asecave.render.OutlineRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class EntityEditor {

	private Entity selected;

	private float animationValue;
	private float animationStep;
	private float targetValue = 0.5f;

	public EntityEditor() {

	}

	public void update() {

	}

	public void render(ShapeRenderer sr) {
		if (selected == null)
			return;
		
		OutlineRenderer.INSTANCE.render(sr, selected, animationValue, Color.GOLD);
		animationValue = MathUtils.sin(animationStep) * targetValue * 1.5f;
		if (animationStep > MathUtils.PI * 0.5f) {
			if (animationValue > targetValue) {
				animationStep += 0.2f;
			} else {
				animationValue = targetValue;
			}
		} else {
			animationStep += 0.2f;
		}
	}

	public void select(Entity e) {
		this.selected = e;
		animationValue = 0f;
		animationStep = 0f;
	}
}
