package com.asecave.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter implements InputProcessor {

	private FrameBuffer fb;
	private ShapeRenderer sr;
	private SpriteBatch batch;
	static OrthographicCamera cam;
	private ShapeRenderer hudsr;
	private FrameBuffer hudfb;
	
	private static final float SUPER_SAMPLING_FACTOR = 2f;
	
	private Stage stage;
	
	private Vector2 lastMousePos;
	
	private static Matrix4 transformationMatrix;
	
	public static Color backgroundColor;

	@Override
	public void create() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);
		hudsr = new ShapeRenderer();
		hudsr.setAutoShapeType(true);
		transformationMatrix = sr.getTransformMatrix();
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(this);
		
		backgroundColor = new Color(0.2f, 0.2f, 0.2f, 1f);
		
		sr.scale(10f, 10f, 1f);
	}

	@Override
	public void render() {
		
		cam.update();
		
		stage.update(Gdx.graphics.getDeltaTime());
		
		fb.begin();
		ScreenUtils.clear(backgroundColor);
		sr.setProjectionMatrix(cam.combined);
		sr.begin();
		stage.render(sr);
		sr.end();
		fb.end();
		
		hudfb.begin();
		ScreenUtils.clear(0f, 0f, 0f, 0f);
		hudsr.setProjectionMatrix(cam.combined);
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
		hudfb = new FrameBuffer(Format.RGBA8888, width, height, false);
	}

	@Override
	public boolean keyDown(int keycode) {
		stage.keyPressed(keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		stage.keyTyped(character);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		lastMousePos = Mouse.get().cpy();
		stage.mouseClicked(Mouse.get().cpy());
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector2 delta = Mouse.get().cpy().sub(lastMousePos);
		sr.translate(delta.x, delta.y, 0f);
		transformationMatrix = sr.getTransformMatrix();
		lastMousePos.set(Mouse.get());
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		
		Vector2 mouseBefore = Mouse.get().cpy();
		
		sr.scale(1f - 0.1f * amountY, 1f - 0.1f * amountY, 1f);
		transformationMatrix = sr.getTransformMatrix();
		
		Vector2 delta = Mouse.get().cpy().sub(mouseBefore);
		sr.translate(delta.x, delta.y, 0f);

		transformationMatrix = sr.getTransformMatrix();
		return false;
	}
	
	public static Vector2 mulWithTransformMat(Vector2 vec) {
		Vector3 translation = transformationMatrix.getTranslation(new Vector3());
		Vector3 scale = transformationMatrix.getScale(new Vector3());
		vec.x -= translation.x;
		vec.y -= translation.y;
		vec.x *= 1 / scale.x;
		vec.y *= 1 / scale.y;
		return vec;
	}
}
