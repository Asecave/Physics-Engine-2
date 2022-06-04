package com.asecave.main;

import java.util.LinkedList;

import com.asecave.render.GraphRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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

		entities = new QuadTree<>(new Rectangle(-1000, -1000, 2000, 2000), 1);
		
		for (int i = 0; i < 10; i++) {
			entities.insert(new Circle(i * 10, 0, 1));
		}
		
		deSolver = new DESolverEuler(new AccellerationFunction());
		
	}

	public void update(float dt) {
		
//		for (int s = 0; s < steps; s++) {
//			for (int i = 0; i < entities.length; i++) {
//				if (entities[i] != null) {
//					deSolver.solve(entities[i], dt / steps);
//				}
//			}
//		}
	}

	public void render(ShapeRenderer sr) {
//		for (int i = 0; i < entities.length; i++) {
//			Entity e = entities[i];
//			if (e != null) {
//				if (e instanceof Circle) {
//					CircleRenderer.INSTANCE.render(sr, (Circle) e);
//				}
//			}
//		}
		
		Rectangle range = new Rectangle(Mouse.get().x - 100, Mouse.get().y - 100, 200, 200);
		
		sr.setColor(Color.GREEN);
		sr.rect(range.x, range.y, range.width, range.height);
		
		LinkedList<Entity> found = entities.query(range);
		
		sr.set(ShapeType.Filled);
		while (!found.isEmpty()) {
			Entity e = found.getFirst();
			sr.circle(e.getPos().x, e.getPos().y, 10);
			found.remove();
		}
		
		entities.render(sr);
	}

	public void renderHUD(SpriteBatch batch) {


		float epot = 0f;
		float ekin = 0f;

//		for (int i = 0; i < entities.length; i++) {
//			Entity e = entities[i];
//			if (e != null) {
//
//				// Epot = m*g*h
//
//				float m = e.getMass();
//				float g = e.getAcc().len();
//				float h = e.getPos().dst(Mouse.get());
//				epot += m * g * h;
//
//				// Ekin = 0.5*m*v*v
//
//				float v2 = e.getVel().x * e.getVel().x + e.getVel().y * e.getVel().y;
//				ekin += 0.5f * m * v2;
//			}
//		}
		
		float etotal = epot + ekin;

		int x = -Gdx.graphics.getWidth() / 2 + 10;
		int y = Gdx.graphics.getHeight() / 2 - 10;

		font.draw(batch,
				"FPS: " + Gdx.graphics.getFramesPerSecond() + " / MSPF: " + (Gdx.graphics.getDeltaTime() * 1000), x, y);
		font.draw(batch, "Epot: " + (int) (epot * 10f) / 10f + "J", x, y -= 25);
		font.draw(batch, "Ekin: " + (int) (ekin * 10f) / 10f + "J", x, y -= 25);
		font.draw(batch, "Etotal: " + (int) ((etotal) * 10f) / 10f + "J", x, y -= 25);
		
		GraphRenderer.INSTANCE.addValue(0, Gdx.graphics.getDeltaTime() * 10);
		GraphRenderer.INSTANCE.addValue(1, epot / 10000f);
		GraphRenderer.INSTANCE.addValue(2, ekin / 10000f);
		GraphRenderer.INSTANCE.addValue(3, etotal / 10000f);
		
	}
	
	public void renderHUD(ShapeRenderer sr) {
		
//		GraphRenderer.INSTANCE.render(sr);
	}
	
	public void mouseClicked(Vector2 pos) {
		entities.insert(new Circle(pos.x, pos.y, 10));
	}
}
