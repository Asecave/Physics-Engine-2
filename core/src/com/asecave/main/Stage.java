package com.asecave.main;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.asecave.main.entity.Circle;
import com.asecave.main.entity.Constraint;
import com.asecave.main.entity.Entity;
import com.asecave.main.entity.LineConstraint;
import com.asecave.render.CircleRenderer;
import com.asecave.render.EntityRenderer;
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

	private int objectSteps = 1;
	private int constraintSteps = 15;

	private int debug;

	private Rectangle bounds;
	private Rectangle screenSpace;

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

//		Circle c1 = new Circle(0, 0, 1);
//		Circle c2 = new Circle(10, 0, 1);
//		Circle c3 = new Circle(0, 10, 1);
//		Circle c4 = new Circle(10, 10, 1);
//		
//		c1.setFixed(true);
//		
//		LineConstraint lc1 = new LineConstraint(c1, c2, 10);
//		LineConstraint lc2 = new LineConstraint(c2, c3, 10);
//		LineConstraint lc3 = new LineConstraint(c3, c1, 10);
//		LineConstraint lc4 = new LineConstraint(c2, c4, 10);
//		LineConstraint lc5 = new LineConstraint(c3, c4, 10);
//
//		entities.insert(c1);
//		entities.insert(c2);
//		entities.insert(c3);
//		entities.insert(c4);
//		entities.insert(lc1);
//		entities.insert(lc2);
//		entities.insert(lc3);
//		entities.insert(lc4);
//		entities.insert(lc5);

//		for (int j = 0; j < 50; j++) {
//			int length = 20;
//			Circle last = new Circle(j * 39.234324f, 0, 1);
//			entities.insert(last);
//			last.setFixed(true);
//			for (int i = 0; i < 50; i++) {
//				Circle c = new Circle(length * (i + 1) + j * 39.234324f, 0, 1);
//				LineConstraint lc1 = new LineConstraint(last, c, length);
//				entities.insert(c);
//				entities.insert(lc1);
//				last = c;
//			}
//		}

//		Entity[][] circles = new Entity[10][10];
//		int length = 3;
//		for (int i = 0; i < circles.length; i++) {
//			for (int j = 0; j < circles[0].length; j++) {
//				circles[i][j] = new Circle(length * i, -j * length, 1);
//				entities.insert(circles[i][j]);
//			}
//		}
//
//		for (int i = 0; i < circles.length; i++) {
//			for (int j = 0; j < circles[0].length; j++) {
//
//				if (j == 0) {
//					circles[i][j].setFixed(true);
//				}
//
//				if (j < circles[0].length - 1) {
//					LineConstraint lc1 = new LineConstraint(circles[i][j], circles[i][j + 1], length);
//					entities.insert(lc1);
//				}
//				if (i < circles.length - 1) {
//					LineConstraint lc2 = new LineConstraint(circles[i][j], circles[i + 1][j], length);
//					entities.insert(lc2);
//				}
//				if (i < circles.length - 1 && j < circles.length - 1) {
//					LineConstraint lc2 = new LineConstraint(circles[i][j + 1], circles[i + 1][j], (float) Math.sqrt(length * length * 2));
//					entities.insert(lc2);
//				}
//			}
//
//		}
//
//		circles[5][5].moveToForeground();

		
		
//		Circle c1 = new Circle(0, 0, 20);
//		c1.setFixed(true);
//		Circle c2 = new Circle(1, -200, 20);
//		Circle c3 = new Circle(0, -400, 20);
//		c3.setTrailEnabled(true);
//		
//		LineConstraint lc1 = new LineConstraint(c1, c2, 200);
//		LineConstraint lc2 = new LineConstraint(c2, c3, 200);
//		
//		entities.insert(c1);
//		entities.insert(c2);
//		entities.insert(c3);
//		entities.insert(lc1);
//		entities.insert(lc2);

		
		
		Circle c1 = new Circle(0, 0, 1);
		Circle c2 = new Circle(1000, 100, 1);
		LineConstraint lc1 = new LineConstraint(c1, c2, 1000);
		
		entities.insert(c1);
		entities.insert(c2);
		entities.insert(lc1);
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

		screenSpace.set(topLeft.x - 100, topLeft.y - 100, w + 200, h + 200);

		long start = System.nanoTime();

		LinkedList<Entity> all = entities.getAll();

		HUDRenderer.INSTANCE.set("entity_count", "Entities: " + all.size());

		for (int s = 0; s < objectSteps; s++) {
			for (Entity e : all) {
				if (!(e instanceof Constraint)) {
					e.update(objectSteps, entities);
				}
			}
		}

		for (int s = 0; s < constraintSteps; s++) {
			for (Entity e : all) {
				if (e instanceof Constraint) {
					e.update(constraintSteps, entities);
				}
			}
		}

		HUDRenderer.INSTANCE.set("entity_update_time",
				"Entity update time: " + (System.nanoTime() - start) / 1E6f + "ms");
		start = System.nanoTime();

		entities.update();

		HUDRenderer.INSTANCE.set("quadtree_update_time",
				"Quadtree update time:  " + (System.nanoTime() - start) / 1E6f + "ms");
	}

	public void render(ShapeRenderer sr) {
		
		EntityRenderer.updateDetail();

		long start = System.nanoTime();

		LinkedList<Entity> visible = entities.query(screenSpace);
		HUDRenderer.INSTANCE.set("visible_entities", "Visible: " + visible.size());

		Collections.sort(visible, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				if (e1.getZ() > e2.getZ()) {
					return 1;
				} else if (e1.getZ() == e2.getZ()) {
					return 0;
				} else {
					return -1;
				}
			}
		});

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
