package com.asecave.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Mouse {
	
	private static Vector2 p = new Vector2();
	
	private Mouse() {
	}
	
	public static Vector2 get() {
		p.x = Gdx.input.getX() - (int) (Main.cam.viewportWidth / 2);
		p.y = Gdx.input.getY() - (int) (Main.cam.viewportHeight / 2);
		
		Main.mulWithTransformMat(p);
		
		return p;
	}
}
