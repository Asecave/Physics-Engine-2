package com.asecave.render;

import com.asecave.main.entity.Line;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LineRenderer extends EntityRenderer {

	public static LineRenderer INSTANCE = new LineRenderer();

	public void render(ShapeRenderer sr, Line l) {
		super.render(sr, l);
	    sr.setColor(Color.WHITE);
		sr.set(ShapeType.Filled);
		sr.line(l.getStart(), l.getEnd());
	}
}
