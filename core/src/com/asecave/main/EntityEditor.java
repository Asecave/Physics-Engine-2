package com.asecave.main;

import com.asecave.main.entity.Entity;

public class EntityEditor {
	
	private Entity selected;
	
	private long animationStart;
	
	public EntityEditor() {
		
	}
	
	public void select(Entity e) {
		this.selected = e;
		animationStart = System.currentTimeMillis();
	}
}
