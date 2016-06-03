package fr.utbm.csanchez.rpg;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DropButton extends JButton implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InventoryManager inventory;
	private boolean dropping;

	public DropButton(InventoryManager im) {
		super("Drop");
		this.addActionListener(this);
		inventory = im;
		dropping = false;
	}

	public void actionPerformed(ActionEvent e) {
		if (dropping) {
			dropItem(null);
		} else {
			dropping = true;
			for (JButton btn : inventory.getItemList()) {
				if (btn.isEnabled()) {
					btn.setBackground(Color.RED);
				}
			}
		}
	}

	public void dropItem(Item it) {
		if (it != null) {
			it.owner.getInventory().remove(it);
			if (it.owner instanceof Hero) {
				Hero hOwner = (Hero) it.owner;
				hOwner.setAd(hOwner.getAd() - it.getAd());
				hOwner.setHpMax(hOwner.getHpMax() - it.getHp());
				hOwner.setHp(hOwner.getHp());
				hOwner.setArmor(hOwner.getArmor() - it.getArmor());
			}
		}
		for (JButton btn : inventory.getItemList()) {
			if (btn.isEnabled()) {
				btn.setBackground(null);
			}
		}
		dropping = false;
	}

	public boolean isDropping() {
		return dropping;
	}

}
