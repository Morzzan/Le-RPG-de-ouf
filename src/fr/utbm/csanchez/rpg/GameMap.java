package fr.utbm.csanchez.rpg;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Stack;

import fr.utbm.csanchez.utils.VectPerso;

/**
 * 
 * @author morzzan
 * 
 */
public class GameMap {
	private HashMap<VectPerso, Element> grid;
	private int height;
	private int width;

	/**
	 * 
	 * @param width
	 * @param height
	 */
	GameMap(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new HashMap<VectPerso, Element>();
		createWalls();
		this.enemyGen(3);
	}

	public HashMap<VectPerso, Element> getGrid() {
		return grid;
	}

	private void createWalls() {
		for (int i = 0; i < width; i++) {
			new Element("█", this, new VectPerso(i, 0));
			new Element("█", this, new VectPerso(i, height - 1));
		}
		for (int j = 0; j < height; j++) {
			new Element("█", this, new VectPerso(0, j));
			new Element("█", this, new VectPerso(width - 1, j));
		}
	}

	public Hero createHero() {
		return new Hero(this, 100, 100, new VectPerso(3, 3), 2);
	}

	public void refresh() {
		List<VectPerso> enemyPosition = new Stack<VectPerso>();
		for (Entry<VectPerso, Element> e : grid.entrySet()) {
			if (e.getValue() instanceof Enemy) {
				enemyPosition.add(e.getKey());
			}
		}
		for (VectPerso k : enemyPosition) {
			grid.get(k).evolve();
		}
	}

	public Element getElement(VectPerso v) {
		return grid.get(v);
	}

	public void setElement(VectPerso v, Element toSet) {
		grid.put(v, toSet);
	}

	public void rm(VectPerso v) {
		grid.remove(v);
	}

	public void put(VectPerso v, Element e) {
		grid.put(v, e);
	}

	private void enemyGen(int enemyNumber) {
		VectPerso v = null;
		Random rand = new Random();
		for (int i = 0; i < enemyNumber; i++) {
			do {
				v = new VectPerso(rand.nextInt(this.width - 1), rand.nextInt(this.height - 1));
			} while (this.getElement(v) != null);
			new Enemy(this, v);
		}
	}

	public void show(Hero hero) {
		VectPerso v = new VectPerso();
		for (int i = height - 1; i >= 0; i--) {
			v.setY(i);
			String line = "";
			for (int j = 0; j < width; j++) {
				v.setX(j);
				Element element = grid.get(v);
				if (element == null)
					line += ".";
				else
					line += element.getRpz();
			}
			System.out.println(line);
		}
		System.out.println(hero.toString());
		hero.showInventory();
	}
}
