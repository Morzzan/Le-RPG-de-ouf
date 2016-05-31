package fr.utbm.csanchez.rpg;

import java.util.Random;

import fr.utbm.csanchez.utils.VectPerso;

public class Enemy extends GameEntity {
	public EnemyStatus status;

	public Enemy(GameMap map, VectPerso v) {
		super("E", map, 20, 15, v);
		this.status = getRandomStatus();
	}

	public EnemyStatus getStatus() {
		return status;
	}

	public void setStatus(EnemyStatus status) {
		this.status = status;
	}

	public void die() {
		isOnMap.rm(getPosition());
		Random rand = new Random();
		if (rand.nextInt(3) == 0) {
			new Chest(isOnMap, getPosition());
		}
	}

	public void evolve() {
		if (status == EnemyStatus.patroling)
			patrol();
		StatusEvolution();
		if (lookAround(1) != null)
			this.setStatus(EnemyStatus.patroling);
	}

	private void StatusEvolution() {
		Random rand = new Random();
		int evolution = rand.nextInt(20);
		if (evolution == 0) {
			EnemyStatus newStatus = getRandomStatus();
			setStatus(newStatus);
		}
	}

	private VectPerso lookAround(int radius) {
		VectPerso hero = null;
		VectPerso v = new VectPerso();
		for (int x = -radius; x <= radius; x++) {
			v.setX(position.getX() + x);
			for (int y = -radius; y <= radius; y++) {
				v.setY(position.getY() + y);
				if (isOnMap.getElement(v) instanceof Hero) {
					hero = new VectPerso(x, y);
				}
			}
		}
		return hero;
	}

	public EnemyStatus getRandomStatus() {
		Random rand = new Random();
		EnemyStatus newStatus = EnemyStatus.values()[rand.nextInt(EnemyStatus.values().length)];
		return newStatus;
	}

	public void go(VectPerso move) {
		VectPerso newPosition = position.add(move);
		Element toHit = isOnMap.getElement(newPosition);
		if (toHit instanceof Hero) {
			Hero target = (Hero) toHit;
			target.takeHit(getAd());
		}
		updatePosition(move);
	}

	private void patrol() {
		VectPerso around = lookAround(4);
		Random rand = new Random();
		if (around == null || rand.nextInt(10) < 3) {
			randomPatrol();
		} else {
			VectPerso move = close(around);
			go(move);
		}
	}

	private VectPerso close(VectPerso target) {
		VectPerso move = null;
		if (Math.abs(target.getX()) >= Math.abs(target.getY())) {
			move = new VectPerso(target.getX() / Math.abs(target.getX()), 0);
		} else {
			move = new VectPerso(0, target.getY() / Math.abs(target.getY()));
		}
		return move;
	}

	private void randomPatrol() {
		int choice;
		VectPerso move;
		Random rand = new Random();
		choice = rand.nextInt(4);
		switch (choice) {
		case 0:
			move = new VectPerso(1, 0);
			break;
		case 1:
			move = new VectPerso(-1, 0);
			break;
		case 2:
			move = new VectPerso(0, 1);
			break;
		default:
			move = new VectPerso(0, -1);
		}
		this.updatePosition(move);
	}
}
