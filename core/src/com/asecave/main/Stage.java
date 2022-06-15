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
//			Circle last = new Circle(j * 40f, -j * 10, 1);
//			entities.insert(last);
//			last.setFixed(true);
//			for (int i = 0; i < 50; i++) {
//				Circle c = new Circle(length * (i + 1) + j * 40f, -j * 10, 1);
//				LineConstraint lc1 = new LineConstraint(last, c, length);
//				entities.insert(c);
//				entities.insert(lc1);
//				last = c;
//			}
//		}

//		Entity[][] circles = new Entity[100][100];
//		int length = 3;
//		for (int i = 0; i < circles.length; i++) {
//			for (int j = 0; j < circles[0].length; j++) {
//				circles[i][j] = new Circle(length * i, -j * length, 1);
//				circles[i][j].setCollidable(false);
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
//					lc1.setCollidable(false);
//					entities.insert(lc1);
//				}
//				if (i < circles.length - 1) {
//					LineConstraint lc2 = new LineConstraint(circles[i][j], circles[i + 1][j], length);
//					lc2.setCollidable(false);
//					entities.insert(lc2);
//				}
////				if (i < circles.length - 1 && j < circles[0].length - 1) {
////					LineConstraint lc2 = new LineConstraint(circles[i][j + 1], circles[i + 1][j], (float) Math.sqrt(length * length * 2));
////					entities.insert(lc2);
////				}
//			}
//
//		}

//		Circle c1 = new Circle(0, 0, 2);
//		c1.setFixed(true);
//		Circle c2 = new Circle(1, -20, 2);
//		Circle c3 = new Circle(0, -40, 2);
//		c3.setTrailEnabled(true);
//		
//		LineConstraint lc1 = new LineConstraint(c1, c2, 20);
//		LineConstraint lc2 = new LineConstraint(c2, c3, 20);
//		
//		entities.insert(c1);
//		entities.insert(c2);
//		entities.insert(c3);
//		entities.insert(lc1);
//		entities.insert(lc2);

//		Circle c1 = new Circle(0, 0, 1);
//		Circle c2 = new Circle(10, 10, 1);
//		LineConstraint lc1 = new LineConstraint(c1, c2, 10);
//		
//		c1.setFixed(true);
//		
//		entities.insert(c1);
//		entities.insert(c2);
//		entities.insert(lc1);

//		int w = 20;
//		int h = 20;
//		int r = 2;
//		for (int i = 0; i < w; i++) {
//			for (int j = 0; j < h; j++) {
//				if (i == 0 || j == 0 || i == w - 1 || j == h - 1) {
//					Circle c = new Circle(i * r * 2, j * r * 2, r);
//					c.setFixed(true);
//					entities.insert(c);
//				}
//			}
//		}

//		int l = 5;
//		Circle c1f = new Circle(0, 0, 1);
//		Circle c2f = new Circle(2, 0, 1);
//		Circle c3f = new Circle(4, 0, 1);
//		Circle c4f = new Circle(6, 0, 1);
//		Circle c5f = new Circle(8, 0, 1);
//		Circle c1 = new Circle(0, l, 1);
//		Circle c2 = new Circle(2, l, 1);
//		Circle c3 = new Circle(4, l, 1);
//		Circle c4 = new Circle(6, l, 1);
//		Circle c5 = new Circle(8 + l, 0, 1);
//		
//		c1f.setFixed(true);
//		c2f.setFixed(true);
//		c3f.setFixed(true);
//		c4f.setFixed(true);
//		c5f.setFixed(true);
//
//		LineConstraint lc1 = new LineConstraint(c1f, c1, l);
//		LineConstraint lc2 = new LineConstraint(c2f, c2, l);
//		LineConstraint lc3 = new LineConstraint(c3f, c3, l);
//		LineConstraint lc4 = new LineConstraint(c4f, c4, l);
//		LineConstraint lc5 = new LineConstraint(c5f, c5, l);
//
//		entities.insert(c1f);
//		entities.insert(c2f);
//		entities.insert(c3f);
//		entities.insert(c4f);
//		entities.insert(c5f);
//		entities.insert(c1);
//		entities.insert(c2);
//		entities.insert(c3);
//		entities.insert(c4);
//		entities.insert(c5);
//		entities.insert(lc1);
//		entities.insert(lc2);
//		entities.insert(lc3);
//		entities.insert(lc4);
//		entities.insert(lc5);

		Circle c01 = new Circle(0, 0, 1);
		Circle c02 = new Circle(20, 20, 1);
		Circle c03 = new Circle(40, 20, 1);
		Circle c04 = new Circle(20, 40, 1);
		Circle c05 = new Circle(0, 40, 1);
		Circle c06 = new Circle(20, 60, 1);
		Circle c07 = new Circle(40, 60, 1);
		Circle c08 = new Circle(20, 80, 1);
		Circle c09 = new Circle(-20, 100, 1);
		Circle c10 = new Circle(-20, 102, 1);
		Circle c11 = new Circle(60, 102, 1);
		Circle c12 = new Circle(60, 100, 1);

		c01.setFixed(true);
		c02.setFixed(true);
		c03.setFixed(true);
		c04.setFixed(true);
		c05.setFixed(true);
		c06.setFixed(true);
		c07.setFixed(true);
		c08.setFixed(true);
		c09.setFixed(true);
		c10.setFixed(true);
		c11.setFixed(true);
		c12.setFixed(true);

		LineConstraint lc01 = new LineConstraint(c01, c02, 1f, true, true, 1f);
		LineConstraint lc02 = new LineConstraint(c03, c04, 1f, true, true, 1f);
		LineConstraint lc03 = new LineConstraint(c05, c06, 1f, true, true, 1f);
		LineConstraint lc04 = new LineConstraint(c07, c08, 1f, true, true, 1f);
		LineConstraint lc05 = new LineConstraint(c09, c10, 1f, true, true, 1f);
		LineConstraint lc07 = new LineConstraint(c11, c12, 1f, true, true, 1f);
		LineConstraint lc06 = new LineConstraint(c10, c11, 1f, true, true, 1f);

		entities.insert(c01);
		entities.insert(c02);
		entities.insert(c03);
		entities.insert(c04);
		entities.insert(c05);
		entities.insert(c06);
		entities.insert(c07);
		entities.insert(c08);
		entities.insert(c09);
		entities.insert(c10);
		entities.insert(c11);
		entities.insert(c12);
		entities.insert(lc01);
		entities.insert(lc02);
		entities.insert(lc03);
		entities.insert(lc04);
		entities.insert(lc05);
		entities.insert(lc06);
		entities.insert(lc07);
	}

	public void update(float dt) {

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
	}

	public void render(ShapeRenderer sr) {

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
		if (debug > 4) {// TODO: render collision box
			debug = 0;
		}
	}

	public void keyTyped(char c) {
		if (c == 'c') {
			
			int size = 8;
			
			Circle c1 = new Circle(Mouse.get().x, Mouse.get().y, 1);
			Circle c2 = new Circle(Mouse.get().x + 2, Mouse.get().y, 1);
			Circle c3 = new Circle(Mouse.get().x + 2, Mouse.get().y + 2, 1);
			Circle c4 = new Circle(Mouse.get().x, Mouse.get().y + 2, 1);
			
			LineConstraint lc1 = new LineConstraint(c1, c2, size);
			LineConstraint lc2 = new LineConstraint(c2, c3, size);
			LineConstraint lc3 = new LineConstraint(c3, c4, size);
			LineConstraint lc4 = new LineConstraint(c4, c1, size);
			LineConstraint lc5 = new LineConstraint(c1, c3, (float) Math.sqrt(size * size * 2));
			
			c1.setTrailEnabled(true);
			entities.insert(c1);
			entities.insert(c2);
			entities.insert(c3);
			entities.insert(c4);
			entities.insert(lc1);
			entities.insert(lc2);
			entities.insert(lc3);
			entities.insert(lc4);
			entities.insert(lc5);
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
