package fr.utbm.csanchez.rpg;

import java.io.Serializable;

import javax.swing.JButton;

public class Potion extends Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int potionCount;

	public Potion(ItemContainer owner, int number) {
		super(owner, "Potion", 0, 0, 0);
		potionCount = number;
	}

	public void use(GameManager caller) {
		if (owner instanceof Hero) {
			if (potionCount > 0) {
				((Hero) owner).setHp(((Hero) owner).getHp() + 15);
				potionCount--;
			}
			if (potionCount < 1) {
				owner.getInventory().remove(this);
			}
			((Hero) owner).isOnMap.enemyTurn();
		}
	}

	public void load(JButton btn) {
		super.load(btn);
		btn.setText(((Integer) potionCount).toString());
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
