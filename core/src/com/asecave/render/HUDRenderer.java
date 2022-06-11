package com.asecave.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;

public class HUDRenderer {

	public static HUDRenderer INSTANCE = new HUDRenderer();

	private BitmapFont font;

	private Array<Element> elements = new Array<>();

	public HUDRenderer() {
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		parameter.borderWidth = 2f;
		parameter.shadowOffsetX = 2;
		parameter.shadowOffsetY = 2;
		font = new FreeTypeFontGenerator(Gdx.files.internal("consolas.ttf")).generateFont(parameter);
	}

	public void render(SpriteBatch batch) {

		int x = -Gdx.graphics.getWidth() / 2 + 10;
		int y = Gdx.graphics.getHeight() / 2 + 15;

		for (Element e : elements) {
			font.draw(batch, e.value, x, y -= 25);
		}
	}

	public void set(String key, String value) {
		for (Element e : elements) {
			if (e.key.equals(key)) {
				e.value = value;
				return;
			}
		}
		Element e = new Element(key, value);
		elements.add(e);
	}

	private class Element {

		public String key;
		public String value;

		public Element(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}
}
