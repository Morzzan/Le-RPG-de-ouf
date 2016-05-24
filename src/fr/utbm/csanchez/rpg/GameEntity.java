package fr.utbm.csanchez.rpg;

import fr.utbm.csanchez.utils.VectPerso;

public class GameEntity extends Element {
	private int hpMax;
	protected int hp;
	private int ad;
	private int armor;

	public GameEntity(String rpz, GameMap map, int hp, int ad, VectPerso position) {
		super(rpz, map, position);
		this.hp = hp;
		hpMax = hp;
		this.ad = ad;
	}

	public void updatePosition(VectPerso move) {
		VectPerso newPosition = position.add(move);
		if (isOnMap.getElement(newPosition) == null) {
			isOnMap.rm(position);
			isOnMap.put(newPosition, this);
			position = newPosition;
		}
	}

	public void go(VectPerso move) {
		updatePosition(move);
	}

	public EnemyStatus getStatus() {
		return EnemyStatus.awake;
	}

	public void setStatus(EnemyStatus E) {
	}

	public int getHp() {
		return hp;
	}

	public VectPerso getPosition() {
		return position;
	}

	public void setHp(int hp) {
		this.hp = hp;
		if (this.hp <= 0) {
			this.hp = 0;
			die();
		}
		if (this.hp > hpMax)
			this.hp = hpMax;
	}

	public void die() {
		isOnMap.rm(position);
	}

	public int getAd() {
		return ad;
	}

	public void setAd(int ad) {
		this.ad = ad;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}
}
