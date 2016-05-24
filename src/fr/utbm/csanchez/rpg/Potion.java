package fr.utbm.csanchez.rpg;

public class Potion extends Item {
	private int potionCount;

	public Potion(ItemContainer owner, int number) {
		super(owner, "Potions", 0, 0, 0);
		potionCount = number;
	}

	public void use() {
		if (owner instanceof Hero) {
			if (potionCount > 0) {
				((Hero) owner).setHp(((Hero) owner).getHp() + 15);
				potionCount--;
			}
			if (potionCount < 1) {
				owner.getInventory().remove(this);
			}
		}
	}

	public int getPotionCount() {
		return potionCount;
	}

	public void addPotion(int potionCount) {
		this.potionCount = this.potionCount + potionCount;
	}

	public String toString() {
		return getName() + " " + potionCount;
	}
}
