package fr.utbm.csanchez.rpg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.utbm.csanchez.utils.VectPerso;

public class Hero extends GameEntity implements Serializable, ItemContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Item> inventory;

	Hero(GameMap map, int hp, int ad, VectPerso position, int potionCount) {
		super("H", map, hp, ad, position);
		inventory = new ArrayList<Item>();
		pickUp(new Potion(null, 2));
		pickUp(new Bow(null));
		pickUp(new Wand(null));
	}

	public List<Item> getInventory() {
		return inventory;
	}

	private Potion hasPotion() {
		Potion pot = null;
		for (Item i : inventory) {
			if (i instanceof Potion)
				pot = (Potion) i;
		}
		return pot;
	}

	public void pickUp(Item newItem) {
		Potion pot = hasPotion();
		if (pot != null && newItem instanceof Potion) {
			Potion newPot = (Potion) newItem;
			pot.addPotion(newPot.getPotionCount());
		} else {
			newItem.setOwner(this);
			inventory.add(newItem);
			setHpMax(getHpMax() + newItem.getHp());
			setAd(getAd() + newItem.getAd());
			setArmor(getArmor() + newItem.getArmor());
		}
	}

	public void go(VectPerso move) {
		VectPerso newPosition = position.add(move);
		Element toHit = isOnMap.getElement(newPosition);
		if (toHit instanceof Enemy) {
			Enemy target = (Enemy) toHit;
			target.takeHit(getAd());
		}
		if (toHit instanceof Chest) {
			Chest toPoor = (Chest) toHit;
			if (hasSpace()) {
				this.pickUp(toPoor.poor());
			} else {
				if (toPoor.getInventory().get(0) instanceof Potion && hasPotion() != null) {
					this.pickUp(toPoor.poor());
				}
			}
		}
		updatePosition(move);
		isOnMap.enemyTurn();
	}

	public boolean hasSpace() {
		return (inventory.size() < 6);
	}

	public void showInventory() {
		int i = 0;
		for (Item thing : inventory) {
			i++;
			System.out.println(i + "\t" + thing.toString());
		}
	}

	public String toString() {
		return "HeroStats : HP : " + getHp() + "/" + getHpMax() + "\tAD :" + getAd() + "\tArmor : " + getArmor();
	}

}
