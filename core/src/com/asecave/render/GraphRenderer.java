package com.asecave.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class GraphRenderer {

	public static GraphRenderer INSTANCE;

	private static Array<Graph> graphs = new Array<>();

	static {
		INSTANCE = new GraphRenderer();
	}

	public void render(ShapeRenderer sr) {

		int h = 100;

		int x = Gdx.graphics.getWidth() / 2 - 500;
		int y = Gdx.graphics.getHeight() / 2;
		

		for (int j = 0; j < graphs.size; j++) {
			Graph g = graphs.get(j);
			
			float min = 0f;
			float max = 1f;
//			for (int i = 0; i < g.values.length; i++) {
//				if (g.values[i] < min || min == 0f) {
//					min = g.values[i];
//				}
//				if (g.values[i] > max || max == 0f) {
//					max = g.values[i];
//				}
//			}
			
			int offset = j * h;
			sr.setColor(Color.WHITE);
			sr.rect(x, y - offset, g.values.length, -h);
			for (int i = 0; i < g.values.length; i++) {
				float a = (float) ((i + (g.values.length - g.step)) % g.values.length) / g.values.length * 0.5f + 0.5f;
				sr.setColor(new Color(1f, 1f, 1f, a));
				sr.line(x + i, y - offset, x + i, y - h * ((g.values[i] - min) / (max - min)) - offset);
			}
		}
	}

	public void addValue(int id, float v) {
		for (Graph g : graphs) {
			if (g.id == id) {
				g.addValue(v);
				return;
			}
		}
		Graph g = new Graph(id, 500);
		g.addValue(v);
		graphs.add(g);
	}

	private static class Graph {

		public int id;
		public float[] values;
		public int step;

		public Graph(int id, int capacity) {
			this.id = id;
			values = new float[capacity];
		}

		public void addValue(float v) {
			values[step] = v;
			step++;
			if (step == values.length) {
				step = 0;
			}
		}
	}
}
