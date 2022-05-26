package com.asecave.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Mouse {
	
	private static Vector2 p = new Vector2();
	private static Matrix4 transformationMatrix = new Matrix4();
	
	private Mouse() {
	}
	
	public static Vector2 get() {
		p.x = Gdx.input.getX() - (int) (Main.cam.viewportWidth / 2);
		p.y = Gdx.input.getY() - (int) (Main.cam.viewportHeight / 2);
		
		mulWithProjMat(p);
		
		return p;
	}
	
	public static void updateTransformationMatrix(Matrix4 mat) {
		transformationMatrix = mat;
	}
	
	public static Vector2 mulWithProjMat(Vector2 vec) {
		Vector3 translation = transformationMatrix.getTranslation(new Vector3());
		Vector3 scale = transformationMatrix.getScale(new Vector3());
		vec.x -= translation.x;
		vec.y -= translation.y;
		vec.x *= 1 / scale.x;
		vec.y *= 1 / scale.y;
		return vec;
	}
}
