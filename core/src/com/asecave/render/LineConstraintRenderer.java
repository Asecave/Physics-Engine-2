package com.asecave.render;

import com.asecave.main.Main;
import com.asecave.main.entity.LineConstraint;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LineConstraintRenderer extends EntityRenderer {

	public static LineConstraintRenderer INSTANCE = new LineConstraintRenderer();

	public void render(ShapeRenderer sr, LineConstraint lc) {
		super.render(sr, lc);

		int detail = getDetail(lc.getRadius());
		if (!lc.isCapsule() || detail == 1) {
			sr.set(ShapeType.Line);
			sr.setColor(Color.WHITE);
			sr.line(lc.getE1().getPos(), lc.getE2().getPos());
		} else {
			sr.set(ShapeType.Filled);
			sr.setColor(Color.BLACK);
			sr.circle(lc.getE1().getPos().x, lc.getE1().getPos().y, lc.getRadius(), detail);
			sr.circle(lc.getE2().getPos().x, lc.getE2().getPos().y, lc.getRadius(), detail);
			sr.rectLine(lc.getE1().getPos(), lc.getE2().getPos(), lc.getRadius() * 2);
			sr.setColor(Color.WHITE);
			sr.rectLine(lc.getE1().getPos(), lc.getE2().getPos(), lc.getRadius() * 2 * 0.8f);
			sr.circle(lc.getE1().getPos().x, lc.getE1().getPos().y, lc.getRadius() * 0.8f, detail);
			sr.circle(lc.getE2().getPos().x, lc.getE2().getPos().y, lc.getRadius() * 0.8f, detail);
		}

	}

	public void renderBackground(ShapeRenderer sr, LineConstraint lc) {
		int detail = getDetail(lc.getRadius());
		if (detail > 1) {
			sr.set(ShapeType.Filled);
			sr.setColor(Main.backgroundColor.cpy().mul(shadowIntensity));
			sr.circle(lc.getE1().getPos().x + shadowDistance, lc.getE1().getPos().y + shadowDistance, lc.getRadius(), detail);
			sr.circle(lc.getE2().getPos().x + shadowDistance, lc.getE2().getPos().y + shadowDistance, lc.getRadius(), detail);
			sr.rectLine(lc.getE1().getPos().cpy().add(shadowDistance, shadowDistance), lc.getE2().getPos().cpy().add(shadowDistance, shadowDistance), lc.getRadius() * 2);
		}
	}
}
