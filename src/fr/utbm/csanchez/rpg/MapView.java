package fr.utbm.csanchez.rpg;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map.Entry;

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
		this.setLayout(new GridLayout(7, 7));
		for (int y = 3; y > -4; y--) {
			for (int x = -3; x < 4; x++) {
				JButton btn = new JButton();
				grid.put(new VectPerso(x, y), btn);
				add(btn);
				btn.addActionListener(mother);
			}
		}
	}

	public void displayMap(GameMap map) {
		for (Entry<VectPerso, JButton> currentBox : grid.entrySet()) {
			VectPerso realBoxPos = currentBox.getKey().add(mother.getVous().getPosition());
			if (map.getGrid().containsKey(realBoxPos)) {
				currentBox.getValue().setText(map.getGrid().get(realBoxPos).getRpz());
			} else {
				currentBox.getValue().setText("");
			}
		}
	}
}
