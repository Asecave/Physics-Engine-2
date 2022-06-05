package com.asecave.main;

import java.util.LinkedList;

import com.asecave.render.CircleRenderer;
import com.asecave.render.GraphRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Stage {

	private QuadTree<Entity> entities;

	private BitmapFont font;

	private int steps = 500;

	private DESolver deSolver;

	public Stage() {

		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		parameter.borderWidth = 2f;
		parameter.shadowOffsetX = 2;
		parameter.shadowOffsetY = 2;
		font = new FreeTypeFontGenerator(Gdx.files.internal("consolas.ttf")).generateFont(parameter);

		entities = new QuadTree<>(new Rectangle(-1000, -1000, 2000, 2000), 4);

		for (int i = 0; i < 1; i++) {
			entities.insert(new Circle(i * 10, 0, 1));
		}

		deSolver = new DESolverEuler(new AccellerationFunction());

	}

	public void update(float dt) {

		LinkedList<Entity> all = entities.getAll();

		for (int s = 0; s < steps; s++) {
			for (Entity e : all) {
				deSolver.solve(e, dt / steps);
			}
		}
		entities.update();
	}

	public void render(ShapeRenderer sr) {

		Vector2 topLeft = new Vector2(-Main.cam.viewportWidth / 2, -Main.cam.viewportHeight / 2);
		Vector2 bottomRight = new Vector2(Main.cam.viewportWidth / 2, Main.cam.viewportHeight / 2);
		Main.mulWithTransformMat(topLeft);
		Main.mulWithTransformMat(bottomRight);
		float w = bottomRight.x - topLeft.x;
		float h = bottomRight.y - topLeft.y;
		Rectangle screenSpace = new Rectangle(topLeft.x, topLeft.y, w, h);

		LinkedList<Entity> visible = entities.query(screenSpace);

		for (Entity e : visible) {
			if (e instanceof Circle) {
				CircleRenderer.INSTANCE.render(sr, (Circle) e);
			}
		}

		entities.render(sr);
	}

	public void renderHUD(SpriteBatch batch) {

		int x = -Gdx.graphics.getWidth() / 2 + 10;
		int y = Gdx.graphics.getHeight() / 2 - 10;

		font.draw(batch,
				"FPS: " + Gdx.graphics.getFramesPerSecond() + " / MSPF: " + (Gdx.graphics.getDeltaTime() * 1000), x, y);

		GraphRenderer.INSTANCE.addValue(0, Gdx.graphics.getDeltaTime() * 10);

	}

	public void renderHUD(ShapeRenderer sr) {

		GraphRenderer.INSTANCE.render(sr);
	}

	public void mouseClicked(Vector2 pos) {
		entities.insert(new Circle(pos.x, pos.y, 10));
	}
}
