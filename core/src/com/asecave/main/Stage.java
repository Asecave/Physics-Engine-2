package com.asecave.main;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.asecave.main.entity.Circle;
import com.asecave.main.entity.Constraint;
import com.asecave.main.entity.Entity;
import com.asecave.main.entity.LineConstraint;
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
	private LinkedList<Entity> visible;

	private int constraintSteps = 15;

	private int debug;

	private Rectangle bounds;
	private Rectangle screenSpace;

	private EntityEditor entitiyEditor;

	public Stage() {

		bounds = new Rectangle(-10000, -10000, 20000, 20000);
		screenSpace = new Rectangle();
		entities = new QuadTree<>(bounds, 4);

		HUDRenderer.INSTANCE.set("fps", "");
		HUDRenderer.INSTANCE.set("steps", "");
		HUDRenderer.INSTANCE.set("entity_count", "");
		HUDRenderer.INSTANCE.set("visible_entities", "");
		HUDRenderer.INSTANCE.set("entity_update_time", "");
		HUDRenderer.INSTANCE.set("quadtree_update_time", "");
		HUDRenderer.INSTANCE.set("render_time", "");

		entitiyEditor = new EntityEditor();

		Circle c1 = new Circle(-50, 0, 1f);
		Circle c2 = new Circle(50, 0, 1f);

		LineConstraint lc1 = new LineConstraint(c1, c2, 100);

		c1.setFixed(true);
		c2.setFixed(true);

		entities.insert(c1);
		entities.insert(c2);
		entities.insert(lc1);

		for (int j = 0; j < 20; j++) {
			for (int i = 0; i < 200; i++) {
				Circle c = new Circle(j * 3 - 10, -5 - 2 * i, 1f);
				entities.insert(c);
			}
		}
	}

	public void update() {

		HUDRenderer.INSTANCE.set("fps",
				"FPS: " + Gdx.graphics.getFramesPerSecond() + " / MSPF: " + (Gdx.graphics.getDeltaTime() * 1000));
		HUDRenderer.INSTANCE.set("steps", "Constraint steps: " + constraintSteps);

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

		for (Entity e : all) {
			e.update(entities);
		}

		for (int s = 0; s < constraintSteps; s++) {
			for (Entity e : all) {
				if (e instanceof Constraint) {
					((Constraint) e).satisfyConstraint(constraintSteps, entities);
				}
			}
		}

		HUDRenderer.INSTANCE.set("entity_update_time",
				"Entity update time: " + (System.nanoTime() - start) / 1E6f + "ms");
		start = System.nanoTime();

		entities.update();

		HUDRenderer.INSTANCE.set("quadtree_update_time",
				"Quadtree update time:  " + (System.nanoTime() - start) / 1E6f + "ms");

		entitiyEditor.update();
	}

	public void render(ShapeRenderer sr) {

		long start = System.nanoTime();

		visible = entities.getAll();
		for (int i = 0; i < visible.size(); i++) {
			Entity e = visible.get(i);
			if (!screenSpace.overlaps(e.getAABB())) {
				visible.remove(e);
				i--;
			}
		}
		HUDRenderer.INSTANCE.set("visible_entities", "Visible: " + visible.size());

		Collections.sort(visible, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				return Integer.compare(e1.getZ(), e2.getZ());
			}
		});

		for (Entity e : visible) {
			if (e instanceof Circle) {
				CircleRenderer.INSTANCE.renderBackground(sr, (Circle) e);
			} else if (e instanceof LineConstraint) {
				LineConstraintRenderer.INSTANCE.renderBackground(sr, (LineConstraint) e);
			}
			if (debug >= 4) {
				sr.set(ShapeType.Filled);
				sr.setColor(Color.FOREST);
				Rectangle r = e.getAABB();
				sr.rectLine(r.x, r.y, r.x + r.width, r.y, .1f);
				sr.rectLine(r.x, r.y, r.x, r.y + r.height, .1f);
				sr.rectLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height, .1f);
				sr.rectLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height, .1f);
			}
		}

		entitiyEditor.render(sr);

		for (Entity e : visible) {
			if (e instanceof Circle) {
				CircleRenderer.INSTANCE.render(sr, (Circle) e);
			} else if (e instanceof LineConstraint) {
				LineConstraintRenderer.INSTANCE.render(sr, (LineConstraint) e);
			}
		}

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

		HUDRenderer.INSTANCE.set("render_time", "Render time: " + (System.nanoTime() - start) / 1E6f + "ms");
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

	public void mouseClicked(final Vector2 pos) {

		Entity clicked = null;

		for (Entity e : visible) {
			if (e.doesPointIntersect(pos))
				clicked = e;
		}

		entitiyEditor.select(clicked);
	}

	public void cycleDebugMode() {
		debug++;
		if (debug > 4) {// TODO: render collision box
			debug = 0;
		}
	}

	public void keyTyped(char c) {
		if (c == 'c') {

//			int size = 5;
//			
//			Circle c1 = new Circle(Mouse.get().x, Mouse.get().y, 1);
//			Circle c2 = new Circle(Mouse.get().x + size, Mouse.get().y, 1);
//			Circle c3 = new Circle(Mouse.get().x + size, Mouse.get().y + size, 1);
//			Circle c4 = new Circle(Mouse.get().x, Mouse.get().y + size, 1);
//			
//			LineConstraint lc1 = new LineConstraint(c1, c2, size);
//			LineConstraint lc2 = new LineConstraint(c2, c3, size);
//			LineConstraint lc3 = new LineConstraint(c3, c4, size);
//			LineConstraint lc4 = new LineConstraint(c4, c1, size);
//			LineConstraint lc5 = new LineConstraint(c1, c3, (float) Math.sqrt(size * size * 2));
//			
//			c1.setCollidable(false);
//			c2.setCollidable(false);
//			c3.setCollidable(false);
//			c4.setCollidable(false);
//			
//			entities.insert(c1);
//			entities.insert(c2);
//			entities.insert(c3);
//			entities.insert(c4);
//			entities.insert(lc1);
//			entities.insert(lc2);
//			entities.insert(lc3);
//			entities.insert(lc4);
//			entities.insert(lc5);

			Circle circle = new Circle(Mouse.get().x, Mouse.get().y, 3);
			circle.setTrailEnabled(true);
			entities.insert(circle);
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
