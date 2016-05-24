package fr.utbm.csanchez.rpg;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.csanchez.utils.VectPerso;

public class Chest extends Element implements ItemContainer {
	private List<Item> inventory;

	public Chest(GameMap map, VectPerso position) {
		super("C", map, position);
		inventory = new ArrayList<Item>();
		inventory.add(new Potion(this, 1));
	}

	public List<Item> getInventory() {
		return inventory;
	}

	public Chest(GameMap map, VectPerso position, List<Item> contains) {
		super("C", map, position);
		inventory = contains;
	}

	public Item poor() {
		Item first = null;
		if (!inventory.isEmpty()) {
			first = inventory.get(0);
			inventory.remove(0);
		}
		if (inventory.isEmpty()) {
			this.isOnMap.rm(position);
		}
		return first;
	}
}
