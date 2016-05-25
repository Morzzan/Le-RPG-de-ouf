package fr.utbm.csanchez.rpg;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.csanchez.utils.VectPerso;

public class Hero extends GameEntity implements ItemContainer {
	private List<Item> inventory;

	Hero(GameMap map, int hp, int ad, VectPerso position, int potionCount) {
		super("H", map, hp, ad, position);
		inventory = new ArrayList<Item>();
		pickUp(new Potion(null, 2));
		pickUp(new Bow(null));
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
			target.setHp(target.getHp() - getAd() * 100 / (100 + target.getArmor()));
		}
		if (toHit instanceof Chest && hasSpace()) {
			Chest toPoor = (Chest) toHit;
			Item newItem = toPoor.poor();
			this.pickUp(newItem);
			System.out.println(newItem.toString() + " picked up !");
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
