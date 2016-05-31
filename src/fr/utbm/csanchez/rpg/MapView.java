package fr.utbm.csanchez.rpg;

import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
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

	public MapView(GameManager mother) {
		super();
		this.mother = mother;
		this.setLayout(new GridLayout(31, 31));
		for (int y = 15; y > -16; y--) {
			for (int x = -15; x < 16; x++) {
				JButton btn = new JButton();
				grid.put(new VectPerso(x, y), btn);
				add(btn);
				btn.addActionListener(mother);
			}
		}
	}

	public void displayMap(GameMap map) {

		ImageIcon imageHero = new ImageIcon("Ressources/case/sprite2_hero.png");
		ImageIcon imageEnemy = new ImageIcon("Ressources/case/sprite_enemy.png");
		ImageIcon imageWall = new ImageIcon("Ressources/case/sprite2_wall.png");
		ImageIcon imageGround = new ImageIcon("Ressources/case/sprite2_ground.png");

		for (Entry<VectPerso, JButton> currentBox : grid.entrySet()) {
			currentBox.getValue().setIcon(null);
		}

		for (Entry<VectPerso, JButton> currentBox : grid.entrySet()) {
			VectPerso realBoxPos = currentBox.getKey().add(mother.getVous().getPosition());
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
				}

			} else {
				currentBox.getValue().setText("");
			}
		}
	}
}
