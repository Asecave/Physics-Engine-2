package com.asecave.render;

import com.asecave.main.entity.LineConstraint;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LineConstraintRenderer extends EntityRenderer {

	public static LineConstraintRenderer INSTANCE = new LineConstraintRenderer();

	public void render(ShapeRenderer sr, LineConstraint lc) {
		super.render(sr, lc);
	    sr.setColor(Color.WHITE);
		sr.set(ShapeType.Filled);
		sr.line(lc.getE1().getPos(), lc.getE2().getPos());
	}
}
