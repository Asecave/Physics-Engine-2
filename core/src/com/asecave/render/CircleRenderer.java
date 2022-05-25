package com.asecave.render;

import com.asecave.main.Circle;
import com.asecave.main.Mouse;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class CircleRenderer {
	
	public static CircleRenderer INSTANCE;

	static {
		INSTANCE = new CircleRenderer();
	}

	public void render(ShapeRenderer sr, Circle c) {
		
//		Gdx.gl.glClearDepthf(1f);
//	    Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
//
//	    //3. set the function to LESS
//	    Gdx.gl.glDepthFunc(GL20.GL_LESS);
//
//	    //4. enable depth writing
//	    Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
//
//	    //5. Enable depth writing, disable RGBA color writing
//	    Gdx.gl.glDepthMask(true);
//	    Gdx.gl.glColorMask(false, false, false, false);
//	    
//	    sr.rect(c.getPos().x - c.getRadius(), c.getPos().y - c.getRadius(), c.getRadius() * 2, c.getRadius() * 2, Color.WHITE, Color.GRAY, Color.BLACK, Color.GRAY);
//	    
//	    Gdx.gl.glColorMask(true, true, true, true);
//	    Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
//	    Gdx.gl.glDepthFunc(GL20.GL_EQUAL);
	    
	    
	    sr.setColor(Color.WHITE);
		sr.set(ShapeType.Filled);
		sr.circle(c.getPos().x, c.getPos().y, c.getRadius(), 50);
		sr.setColor(Color.BLUE);
		sr.line(c.getPos(), c.getPos().cpy().add(c.getVel()));
		sr.setColor(Color.GREEN);
		sr.line(c.getPos(), c.getPos().cpy().add(c.getAcc()));
	}
}
