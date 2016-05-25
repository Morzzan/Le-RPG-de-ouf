package fr.utbm.csanchez.rpg;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Item {
	protected ItemContainer owner;
	private String name;
	private int hp;
	private int ad;
	private int armor;

	public Item(ItemContainer owner, String name, int hp, int ad, int armor) {
		this.owner = owner;
		this.name = name;
		this.hp = hp;
		this.ad = ad;
		this.armor = armor;
	}

	public void use(InventoryManager caller) {

	}

	public void load(JButton btn) {
		Image img = null;
		try {
			img = ImageIO.read(new File(getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		btn.setIcon(new ImageIcon(img));
		btn.setEnabled(true);
	}

	public String toString() {
		return name + "\tHP = " + hp + "\tAD = " + ad + "\t Armor = " + armor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
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

	public ItemContainer getOwner() {
		return owner;
	}

	public void setOwner(ItemContainer owner) {
		this.owner = owner;
	}
}
