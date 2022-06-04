package com.asecave.main;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class QuadTree<T extends Entity> {

	private Rectangle boundary;
	private final int capacity;
	private T[] e;
	private boolean divided = false;
	private int containing = 0;

	private QuadTree<T> nw;
	private QuadTree<T> ne;
	private QuadTree<T> sw;
	private QuadTree<T> se;

	public QuadTree(Rectangle boundary) {
		this(boundary, 1);
	}

	@SuppressWarnings("unchecked")
	public QuadTree(Rectangle boundary, int capacity) {
		this.boundary = boundary;
		this.capacity = capacity;
		e = (T[]) new Entity[capacity];
	}

	private void subdivide() {
		float x = boundary.x;
		float y = boundary.y;
		float hw = boundary.width / 2;
		float hh = boundary.height / 2;
		nw = new QuadTree<>(new Rectangle(x, y, hw, hh), capacity);
		ne = new QuadTree<>(new Rectangle(x + hw, y, hw, hh), capacity);
		sw = new QuadTree<>(new Rectangle(x, y + hh, hw, hh), capacity);
		se = new QuadTree<>(new Rectangle(x + hw, y + hh, hw, hh), capacity);
		divided = true;
	}

	public boolean insert(T o) {

		if (!boundary.contains(o.getPos())) {
			return false;
		}

		if (containing < capacity) {
			e[containing] = o;
			containing++;
			return true;
		} else {
			if (!divided) {
				subdivide();
			}
			if (nw.insert(o)) {
				return true;
			} else if (ne.insert(o)) {
				return true;
			} else if (sw.insert(o)) {
				return true;
			} else if (se.insert(o)) {
				return true;
			}
			return false;
		}
	}

	public LinkedList<T> query(Rectangle range) {
		return subQuery(range, new LinkedList<T>());
	}

	private LinkedList<T> subQuery(Rectangle range, LinkedList<T> l) {
		if (!boundary.overlaps(range)) {
			return l;
		} else {
			for (int i = 0; i < e.length; i++) {
				if (e[i] != null) {
					if (range.contains(e[i].getPos().x, e[i].getPos().y)) {
						l.add(e[i]);
					}
				}
			}

			if (divided) {
				nw.subQuery(range, l);
				ne.subQuery(range, l);
				sw.subQuery(range, l);
				se.subQuery(range, l);
			}

			return l;
		}
	}

	public void render(ShapeRenderer sr) {
		sr.set(ShapeType.Line);
		sr.setColor(Color.WHITE);
		sr.rect(boundary.x, boundary.y, boundary.width, boundary.height);
		if (divided) {
			nw.render(sr);
			ne.render(sr);
			sw.render(sr);
			se.render(sr);
		}
		for (int i = 0; i < e.length; i++) {
			if (e[i] != null) {
				sr.circle(e[i].getPos().x, e[i].getPos().y, 2);
			}
		}
	}
}
