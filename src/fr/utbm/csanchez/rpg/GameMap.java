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
	GameMap(int width, int height, int n) {
		this.width = width;
		this.height = height;
		grid = new HashMap<VectPerso, Element>();

		fillWalls();

		VectPerso test = new VectPerso();

		for (int g = 1; g < 10; g++) {
			test.setX(g);
			for (int y = 1; y < 10; y++) {
				test.setY(y);
				this.grid.remove(test);
			}
		}

		createRoom(new VectPerso(10, 10), new VectPerso(1, 1));

		VectPerso[] roomSpot = new VectPerso[n];
		VectPerso size = new VectPerso();
		VectPerso bottomLeft = new VectPerso();

		for (int i = 0; i < n; i++) {
			size.randVect(15, 5);
			bottomLeft.randVect(Math.min(width - size.getX() + 1, height - size.getY() + 1), 2);
			this.createRoom(size, bottomLeft);
			roomSpot[i] = new VectPerso();
			roomSpot[i].randVectInRoom(size, bottomLeft);
		}

		buildCorridor(new VectPerso(3, 3), roomSpot[0]);

		for (int io = 0; io < n - 1; io++) {
			buildCorridor(roomSpot[io], roomSpot[io + 1]);
		}

		createRoom(new VectPerso(10, 10), new VectPerso(width - 11, height - 11));

		buildCorridor(roomSpot[n - 1], new VectPerso(width - 5, height - 5));

		this.enemyGen(3);
	}

	private void fillWalls() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				new Element("W", this, new VectPerso(i, j));
				new Element("W", this, new VectPerso(i, j));
			}
		}
	}

	private void createRoom(VectPerso size, VectPerso bottomLeft) {

		VectPerso vect = new VectPerso();

		for (int i = bottomLeft.getX(); i < bottomLeft.getX() + size.getX() - 1; i++) {
			vect.setX(i);
			for (int j = bottomLeft.getY(); j < bottomLeft.getY() + size.getY() - 1; j++) {
				vect.setY(j);
				this.grid.remove(vect);
			}
		}
	}

	private void buildCorridor(VectPerso one, VectPerso two) {
		Random rand = new Random();
		int n = rand.nextInt(2);
		VectPerso vectRemover = new VectPerso();
		if (n == 2) {
			vectRemover.setY(one.getY());
			for (int m = Math.min(one.getX(), two.getX()); m < Math.max(one.getX(), two.getX()) + 1; m++) {
				vectRemover.setX(m);
				this.grid.remove(vectRemover);
			}
			vectRemover.setX(two.getX());
			for (int l = Math.min(one.getY(), two.getY()); l < Math.max(two.getY(), one.getY()) + 1; l++) {
				vectRemover.setY(l);
				this.grid.remove(vectRemover);
			}
		} else {
			vectRemover.setX(one.getX());
			for (int o = Math.min(one.getY(), two.getY()); o < Math.max(two.getY(), one.getY()) + 1; o++) {
				vectRemover.setY(o);
				this.grid.remove(vectRemover);
			}
			vectRemover.setY(two.getY());
			for (int k = Math.min(one.getX(), two.getX()); k < Math.max(one.getX(), two.getX()) + 1; k++) {
				vectRemover.setX(k);
				this.grid.remove(vectRemover);
			}

		}
	}

	public HashMap<VectPerso, Element> getGrid() {
		return grid;
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

	public static void main(String Args[]) {
		GameMap test = new GameMap(80, 50, 8);
		Hero moi = test.createHero();
		new Chest(test, new VectPerso(2, 2));

		test.show(moi);
		while (moi.getHp() > 0) {
			moi.heroTurn();
			test.refresh();
			test.show(moi);
		}
		System.out.println("Game Over");
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
