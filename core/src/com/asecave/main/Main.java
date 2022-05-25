package com.asecave.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

	private FrameBuffer fb;
	private ShapeRenderer sr;
	private SpriteBatch batch;
	static OrthographicCamera cam;
	private ShapeRenderer hudsr;
	private FrameBuffer hudfb;
	
	private static final float SUPER_SAMPLING_FACTOR = 2f;
	
	private Stage stage;

	@Override
	public void create() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		hudsr = new ShapeRenderer();
		hudsr.setAutoShapeType(true);
		
		stage = new Stage();
	}

	@Override
	public void render() {
		
		cam.update();
		
		stage.update(Gdx.graphics.getDeltaTime());
		
		fb.begin();
		ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1f);
		sr.setProjectionMatrix(cam.combined);
		sr.begin();
		stage.render(sr);
		sr.end();
		fb.end();
		
		hudfb.begin();
		hudsr.begin();
		stage.renderHUD(hudsr);
		hudsr.end();
		hudfb.end();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(fb.getColorBufferTexture(), -cam.viewportWidth / 2, -cam.viewportHeight / 2, cam.viewportWidth, cam.viewportHeight);
		batch.draw(hudfb.getColorBufferTexture(), -cam.viewportWidth / 2, -cam.viewportHeight / 2, cam.viewportWidth, cam.viewportHeight);
		stage.renderHUD(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		sr.dispose();
		fb.dispose();
		batch.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		
		createFrameBuffer(width, height);
		cam.viewportWidth = width;
		cam.viewportHeight = height;
	}
	
	private void createFrameBuffer(int width, int height) {
		if (fb != null) {
			fb.dispose();
		}
		fb = new FrameBuffer(Format.RGB888, (int) (width * SUPER_SAMPLING_FACTOR), (int) (height * SUPER_SAMPLING_FACTOR), false);
		if (hudfb != null) {
			hudfb.dispose();
		}
		hudfb = new FrameBuffer(Format.RGBA8888, (int) (width * SUPER_SAMPLING_FACTOR), (int) (height * SUPER_SAMPLING_FACTOR), false);
	}
}
