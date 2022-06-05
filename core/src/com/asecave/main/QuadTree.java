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
	private T[] content;
	private boolean divided = false;
	private int containing = 0;

	private QuadTree<T> nw;
	private QuadTree<T> ne;
	private QuadTree<T> sw;
	private QuadTree<T> se;

	private QuadTree<T> root;
	private QuadTree<T> parent;

	public QuadTree(Rectangle boundary) {
		this(boundary, 1);
	}

	private QuadTree(Rectangle boundary, int capacity, QuadTree<T> root, QuadTree<T> parent) {
		this(boundary, capacity);
		this.root = root;
		this.parent = parent;
	}

	@SuppressWarnings("unchecked")
	public QuadTree(Rectangle boundary, int capacity) {
		this.boundary = boundary;
		this.capacity = capacity;
		content = (T[]) new Entity[capacity];
		root = this;
	}

	private void subdivide() {
		float x = boundary.x;
		float y = boundary.y;
		float hw = boundary.width / 2;
		float hh = boundary.height / 2;
		nw = new QuadTree<>(new Rectangle(x, y, hw, hh), capacity, root, this);
		ne = new QuadTree<>(new Rectangle(x + hw, y, hw, hh), capacity, root, this);
		sw = new QuadTree<>(new Rectangle(x, y + hh, hw, hh), capacity, root, this);
		se = new QuadTree<>(new Rectangle(x + hw, y + hh, hw, hh), capacity, root, this);
		divided = true;

		for (int i = 0; i < containing; i++) {
			insert(content[i]);
			content[i] = null;
		}

		containing = 0;
	}

	public boolean insert(T e) {

		if (!boundary.contains(e.getPos())) {
			return false;
		}

		if (containing < capacity && !divided) {
			content[containing] = e;
			containing++;
			return true;
		} else {
			if (!divided) {
				subdivide();
			}
			if (nw.insert(e)) {
				return true;
			} else if (ne.insert(e)) {
				return true;
			} else if (sw.insert(e)) {
				return true;
			} else if (se.insert(e)) {
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
			if (divided) {
				nw.subQuery(range, l);
				ne.subQuery(range, l);
				sw.subQuery(range, l);
				se.subQuery(range, l);
			} else {
				for (int i = 0; i < containing; i++) {
					T e = content[i];
					if (range.contains(e.getPos().x, e.getPos().y)) {
						l.add(e);
					}
				}
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
	}

	public LinkedList<T> getAll() {
		return subGetAll(new LinkedList<T>());
	}

	private LinkedList<T> subGetAll(LinkedList<T> l) {
		if (divided) {
			nw.subGetAll(l);
			ne.subGetAll(l);
			sw.subGetAll(l);
			se.subGetAll(l);
		} else {
			for (int i = 0; i < containing; i++) {
				l.add(content[i]);
			}
		}
		return l;
	}

	public void update() {
		if (divided) {
			nw.update();
			ne.update();
			sw.update();
			se.update();
		} else {
			for (int i = 0; i < containing; i++) {
				if (!boundary.contains(content[i].getPos())) {
					T e = content[i];
					removeFromContent(e);
					root.insert(e);
				}
			}
		}
	}

	public void remove(T e) {
		if (!boundary.contains(e.getPos())) {
			return;
		} else {
			if (divided) {
				nw.remove(e);
				ne.remove(e);
				sw.remove(e);
				se.remove(e);
			} else {
				removeFromContent(e);
			}
		}
	}

	private void removeFromContent(T e) {
		for (int i = 0; i < containing; i++) {
			if (content[i] == e) {
				content[i] = null;
				for (int j = i + 1; j < containing; j++) {
					content[j - 1] = content[j];
				}
				containing--;
				break;
			}
		}
		if (containing == 0 && this != root) {
			parent.updateDivisions();
		}
	}

	private void updateDivisions() {
		if (divided) {
			if (!(nw.containing > 0 || ne.containing > 0 || sw.containing > 0 || se.containing > 0 || nw.divided
					|| ne.divided || sw.divided || se.divided)) {
				divided = false;
				if (this != root) {
					parent.updateDivisions();
				}
			}
		}
	}
}
