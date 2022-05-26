package com.asecave.main;

import com.asecave.render.CircleRenderer;
import com.asecave.render.GraphRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Stage {

	private Entity[] entities;

	private BitmapFont font;
	
	private int steps = 100;
	
//	private int offsetX

	public Stage() {

		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		parameter.borderWidth = 2f;
		parameter.shadowOffsetX = 2;
		parameter.shadowOffsetY = 2;
		font = new FreeTypeFontGenerator(Gdx.files.internal("consolas.ttf")).generateFont(parameter);

		entities = new Entity[500];

		for (int i = 0; i < 1; i++) {
			entities[i] = new Circle(0, 0, 100);
		}
	}

	public void update(float dt) {
		
		dt *= 10;
		
		for (int s = 0; s < steps; s++) {
			for (int i = 0; i < entities.length; i++) {
				if (entities[i] != null) {
					entities[i].update(dt / steps);
				}
			}
		}
	}

	public void render(ShapeRenderer sr) {
		for (int i = 0; i < entities.length; i++) {
			Entity e = entities[i];
			if (e != null) {
				if (e instanceof Circle) {
					CircleRenderer.INSTANCE.render(sr, (Circle) e);
				}
			}
		}
	}

	public void renderHUD(SpriteBatch batch) {


		float epot = 0f;
		float ekin = 0f;

		for (int i = 0; i < entities.length; i++) {
			Entity e = entities[i];
			if (e != null) {

				// Epot = m*g*h

				float m = e.getMass();
				float g = e.getAcc().len();
				float h = e.getPos().dst(Mouse.get());
				epot = m * g * h;

				// Ekin = 0.5*m*v*v

				float v2 = e.getVel().x * e.getVel().x + e.getVel().y * e.getVel().y;
				ekin = 0.5f * m * v2;
			}
		}
		
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
		
		GraphRenderer.INSTANCE.render(sr);
	}
}
