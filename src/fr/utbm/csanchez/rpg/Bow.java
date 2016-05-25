package fr.utbm.csanchez.rpg;

import fr.utbm.csanchez.utils.VectPerso;

public class Bow extends Item {

	public Bow(ItemContainer owner) {
		super(owner, "Bow", 0, 0, 0);
	}

	public String toString() {
		return getName();
	}

	public void use(GameManager caller) {
		caller.getMv().askTarget(this);
	}

	public void fire(VectPerso direction) {
		Hero hOwner = null;
		if (direction.getX() != 0)
			direction.setX(direction.getX() / Math.abs(direction.getX()));
		if (direction.getY() != 0)
			direction.setY(direction.getY() / Math.abs(direction.getY()));
		if (owner instanceof Hero)
			hOwner = ((Hero) owner);
		VectPerso arrow = hOwner.getPosition().add(direction);
		while (hOwner.isOnMap.getElement(arrow) == null) {
			arrow = arrow.add(direction);
		}
		Element target = hOwner.isOnMap.getElement(arrow);
		target.setHp(target.getHp() - hOwner.getAd() * 50 / (100 + target.getArmor()));
		hOwner.isOnMap.enemyTurn();
	}
}
