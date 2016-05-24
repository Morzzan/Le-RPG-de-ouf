package fr.utbm.csanchez.rpg;

import fr.utbm.csanchez.utils.VectPerso;

/**
 * 
 * @author morzzan
 * 
 */
public class Element {
	protected String rpz;
	protected GameMap isOnMap;
	protected VectPerso position;

	Element(String type, GameMap map, VectPerso position) {
		this.rpz = type;
		isOnMap = map;
		this.position = position;
		map.setElement(position, this);
	}

	public VectPerso getPosition() {
		return null;
	}

	public int getArmor() {
		return 0;
	}

	public int getAd() {
		return 0;
	}

	public String getRpz() {
		return rpz;
	}

	public void setRpz(String rpz) {
		this.rpz = rpz;
	}

	public void evolve() {
	}

	public void setHp(int Hp) {
	}

	public int getHp() {
		return 0;
	}

	@Override
	public String toString() {
		return "Element [rpz=" + rpz + "]";
	}
}
