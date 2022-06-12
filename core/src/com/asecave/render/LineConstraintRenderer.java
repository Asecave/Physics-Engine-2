package com.asecave.render;

import com.asecave.main.entity.LineConstraint;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LineConstraintRenderer extends EntityRenderer {

	public static LineConstraintRenderer INSTANCE = new LineConstraintRenderer();

	public void render(ShapeRenderer sr, LineConstraint lc) {
		super.render(sr, lc);

		if (!lc.isCapsule() || detail == 1) {
			sr.set(ShapeType.Line);
			sr.setColor(Color.WHITE);
			sr.line(lc.getE1().getPos(), lc.getE2().getPos());
		} else {
			sr.set(ShapeType.Filled);
			sr.setColor(Color.BLACK);
			sr.circle(lc.getE1().getPos().x, lc.getE1().getPos().y, 0.5f, detail);
			sr.circle(lc.getE2().getPos().x, lc.getE2().getPos().y, 0.5f, detail);
			sr.rectLine(lc.getE1().getPos(), lc.getE2().getPos(), 1f);
			sr.setColor(Color.WHITE);
			sr.rectLine(lc.getE1().getPos(), lc.getE2().getPos(), 0.8f);
			sr.circle(lc.getE1().getPos().x, lc.getE1().getPos().y, 0.40f, detail);
			sr.circle(lc.getE2().getPos().x, lc.getE2().getPos().y, 0.40f, detail);

		}

	}
}
