package com.asecave.main;

import java.util.LinkedList;

import com.asecave.render.CircleRenderer;
import com.asecave.render.GraphRenderer;
import com.asecave.render.HUDRenderer;
import com.asecave.render.LineConstraintRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Stage {

	private QuadTree<Entity> entities;

	private int objectSteps = 10;
	private int constraintSteps = 50;

	private int debug;

	private Rectangle bounds;
	private Rectangle screenSpace;

	public Stage() {

		bounds = new Rectangle(-10000, -10000, 20000, 20000);
		screenSpace = new Rectangle();
		entities = new QuadTree<>(bounds, 4);

		Circle c1 = new Circle(0, 0, 1);
		c1.setFixed(true);
		Circle c2 = new Circle(0.1f, -10, 1);
		LineConstraint lineConstraint1 = new LineConstraint(c1, c2, 10);
//		Circle c3 = new Circle(0.001f, -20, 1);
//		c3.setTrailEnabled(true);
//		LineConstraint lineConstraint2 = new LineConstraint(c2, c3, 10);

		entities.insert(c1);
		entities.insert(c2);
//		entities.insert(c3);
		entities.insert(lineConstraint1);
//		entities.insert(lineConstraint2);

	}

	public void update(float dt) {

		HUDRenderer.INSTANCE.set("fps",
				"FPS: " + Gdx.graphics.getFramesPerSecond() + " / MSPF: " + (Gdx.graphics.getDeltaTime() * 1000));
		HUDRenderer.INSTANCE.set("steps", "Object steps: " + objectSteps + " / Constraint steps: " + constraintSteps);

		Vector2 topLeft = new Vector2(-Main.cam.viewportWidth / 2, -Main.cam.viewportHeight / 2);
		Vector2 bottomRight = new Vector2(Main.cam.viewportWidth / 2, Main.cam.viewportHeight / 2);
		Main.mulWithTransformMat(topLeft);
		Main.mulWithTransformMat(bottomRight);
		float w = bottomRight.x - topLeft.x;
		float h = bottomRight.y - topLeft.y;

		screenSpace.set(topLeft.x, topLeft.y, w, h);

		long start = System.nanoTime();

		LinkedList<Entity> all = entities.getAll();

		HUDRenderer.INSTANCE.set("entity_count", "Entities: " + all.size());

		for (int s = 0; s < objectSteps; s++) {
			for (Entity e : all) {
				if (!(e instanceof Constraint)) {
					e.update(dt / objectSteps, entities);
				}
			}
		}

		for (int s = 0; s < constraintSteps; s++) {
			for (Entity e : all) {
				if (e instanceof Constraint) {
					e.update(dt / constraintSteps, entities);
				}
			}
		}

		HUDRenderer.INSTANCE.set("entity_update_time",
				"Entity update time:         " + (System.nanoTime() - start) / 1E6f + "ms");
		start = System.nanoTime();

		entities.update();

		HUDRenderer.INSTANCE.set("quadtree_update_time",
				"Quadtree update time:      " + (System.nanoTime() - start) / 1E6f + "ms");
	}

	public void render(ShapeRenderer sr) {

		long start = System.nanoTime();

		LinkedList<Entity> visible = entities.query(screenSpace);

		HUDRenderer.INSTANCE.set("find_visible",
				"Determine visible objects: " + (System.nanoTime() - start) / 1E6f + "ms");
		start = System.nanoTime();

		for (Entity e : visible) {
			if (e instanceof Circle) {
				CircleRenderer.INSTANCE.render(sr, (Circle) e);
			} else if (e instanceof LineConstraint) {
				LineConstraintRenderer.INSTANCE.render(sr, (LineConstraint) e);
			}
		}

		HUDRenderer.INSTANCE.set("render_visible",
				"Render visible objects:    " + (System.nanoTime() - start) / 1E6f + "ms");

		if (debug >= 3) {
			entities.render(sr);
		}

		if (!bounds.contains(screenSpace)) {
			sr.set(ShapeType.Line);
			int size = 1000;
			for (int i = 0; i < (1f - Main.backgroundColor.r) * size; i++) {
				Color c = Main.backgroundColor.cpy();
				c.r = 1f;
				c.r -= (float) i / size;
				sr.setColor(c);
				sr.rect(bounds.x - i, bounds.y - i, bounds.width + i * 2, bounds.height + i * 2);
			}
		}
	}

	public void renderHUD(SpriteBatch batch) {

		if (debug >= 1) {
			HUDRenderer.INSTANCE.render(batch);
		}
	}

	public void renderHUD(ShapeRenderer sr) {

		if (debug >= 2) {
			GraphRenderer.INSTANCE.addValue(0, Gdx.graphics.getDeltaTime() * 10);

			GraphRenderer.INSTANCE.render(sr);
		}
	}

	public void mouseClicked(Vector2 pos) {

	}

	public void cycleDebugMode() {
		debug++;
		if (debug > 3) {
			debug = 0;
		}
	}

	public void keyTyped(char c) {
		if (c == 'c') {
			entities.insert(new Circle(Mouse.get().x + (float) Math.random() - 0.5f,
					Mouse.get().y + (float) Math.random() - 0.5f, 2));
		}
	}

	public void keyPressed(int keycode) {
		if (keycode == Keys.F11) {
			if (Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setWindowedMode(960, 640);
			} else {
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			}
		} else if (keycode == Keys.F3) {
			cycleDebugMode();
		} else if (keycode == Keys.ESCAPE) {
			if (Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setWindowedMode(960, 640);
			} else {
				Gdx.app.exit();
			}
		}
	}
}
