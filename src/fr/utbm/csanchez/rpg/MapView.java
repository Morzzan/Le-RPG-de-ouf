package fr.utbm.csanchez.rpg;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.utbm.csanchez.utils.VectPerso;

public class MapView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HashMap<VectPerso, JButton> getGrid() {
		return grid;
	}

	private HashMap<VectPerso, JButton> grid = new HashMap<VectPerso, JButton>();
	private GameManager mother;
	private Item usingNow;

	public MapView(GameManager mother) {
		super();
		this.mother = mother;
		this.setLayout(new GridLayout(15, 15));
		for (int y = 7; y > -8; y--) {
			for (int x = -7; x < 8; x++) {
				JButton btn = new JButton();
				grid.put(new VectPerso(x, y), btn);
				add(btn);
				btn.addActionListener(mother);
				askMove();
			}
		}
	}

	public void askMove() {
		usingNow = null;
		for (JButton b : this.grid.values()) {
			b.setEnabled(false);
		}
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				VectPerso currentBox = new VectPerso(x, y);
				JButton btn = this.grid.get(currentBox);
				if (btn != null && btn.getText() != "█")
					btn.setEnabled(true);
			}
		}
	}

	public void askTarget(Item it) {
		usingNow = it;
		for (Entry<VectPerso, JButton> currentBox : grid.entrySet()) {
			JButton btn = currentBox.getValue();
			VectPerso pos = currentBox.getKey();
			if (pos.getX() == 0 ^ pos.getY() == 0) {
				btn.setEnabled(true);
			} else {
				btn.setEnabled(false);
			}
		}
	}

	public void askBlow(Item it, int range) {
		usingNow = it;
		for (Entry<VectPerso, JButton> currentBox : grid.entrySet()) {
			JButton btn = currentBox.getValue();
			VectPerso pos = currentBox.getKey();
			if (pos.getX() <= range && pos.getX() >= -range && pos.getY() <= range && pos.getY() >= -range) {
				btn.setEnabled(true);
			} else {
				btn.setEnabled(false);
			}
		}
	}

	public void displayMap(GameMap map) {
		ImageIcon imageHero = new ImageIcon("Ressources/sprite2_hero.png");
		ImageIcon imageEnemy = new ImageIcon("Ressources/sprite_enemy.png");
		ImageIcon imageWall = new ImageIcon("Ressources/sprite2_wall.png");
		ImageIcon imageGround = new ImageIcon("Ressources/sprite2_ground.png");
		ImageIcon imageChest = new ImageIcon("Ressources/chest0.png");
		for (Entry<VectPerso, JButton> currentBox : grid.entrySet()) {
			currentBox.getValue().setIcon(null);
		}
		for (Entry<VectPerso, JButton> currentBox : grid.entrySet()) {
			VectPerso pos = currentBox.getKey();
			VectPerso realBoxPos = realBoxPos(pos);
			currentBox.getValue().setBackground(null);
			if (map.getGrid().containsKey(realBoxPos)) {
				switch (map.getGrid().get(realBoxPos).getRpz()) {
				case "H":
					currentBox.getValue().setIcon(imageHero);
					currentBox.getValue().setMargin(new Insets(0, 0, 0, 0));
					break;
				case "E":
					currentBox.getValue().setIcon(imageEnemy);
					break;
				case "W":
					currentBox.getValue().setIcon(imageWall);
					break;
				case "C":
					currentBox.getValue().setIcon(imageChest);
					break;
				}
			} else {
				currentBox.getValue().setText("");
			}
		}
		VectPerso hpos = mother.getHero().getPosition();
		List<Blast> actionList = map.getdAL().getToOccur();
		for (Blast d : actionList) {
			VectPerso target = d.getTarget().minus(hpos);
			JButton tricked = grid.get(target);
			if (tricked != null) {
				tricked.setBackground(Color.RED);
			}

		}
	}

	private VectPerso realBoxPos(VectPerso pos) {
		VectPerso realBoxPos = pos.add(mother.getHero().getPosition());
		return realBoxPos;
	}

	public Item getUsingNow() {
		return usingNow;
	}

	public void setUsingNow(Item usingNow) {
		this.usingNow = usingNow;
	}
}
