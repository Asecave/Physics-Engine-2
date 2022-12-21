package com.asecave.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Trail {

	private Vector2[] trail;
	private int index = 0;
	private Color color;
	
	public Trail(int size) {
		trail = new Vector2[size];
		for (int i = 0; i < trail.length; i++) {
			trail[i] = new Vector2();
		}
		color = Color.WHITE;
	}
	
	public void note(Vector2 pos) {
		trail[index].set(pos);
		index++;
		if (index == trail.length) {
			index = 0;
		}
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public Vector2[] get() {
		return trail;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Color getColor() {
		return color;
	}
}
