package fr.utbm.csanchez.rpg;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InventoryManager extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JButton> itemList = new ArrayList<JButton>();
	private GameManager mother;
	private DropButton drop = new DropButton(this);

	public InventoryManager(GameManager mother) {
		super();
		this.mother = mother;
		GridBagLayout layout1 = new GridBagLayout();
		setLayout(layout1);
		{
			JLabel title = new JLabel("Inventory");
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.weightx = 1;
			constraints.weighty = 1;
			constraints.gridx = 0;
			constraints.gridy = 0;
			add(title, constraints);
		}
		{
			JPanel tab = new JPanel();
			GridLayout tabLayout = new GridLayout(3, 2);
			tabLayout.setHgap(5);
			tabLayout.setVgap(5);
			tab.setLayout(tabLayout);
			for (int i = 1; i < 7; i++) {
				JButton btn = new JButton();
				btn.addActionListener(this);
				itemList.add(btn);
				tab.add(btn);
			}
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.weightx = 5;
			constraints.weighty = 5;
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.fill = GridBagConstraints.BOTH;
			add(tab, constraints);
		}
		{
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.weightx = 1;
			constraints.weighty = 1;
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			add(drop, constraints);
		}
	}

	public List<JButton> getItemList() {
		return itemList;
	}

	public void synchronizeInventory() {
		for (JButton btn : itemList) {
			btn.setEnabled(false);
			btn.setIcon(null);
			btn.setText(null);
		}
		int i = 0;
		for (Item it : mother.getHero().getInventory()) {
			it.load(itemList.get(i));
			i++;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int i = 0;
		int index = 0;
		for (JButton btn : itemList) {
			if (e.getSource() == btn) {
				index = i;
			}
			i++;
		}
		Item currentItem = mother.getHero().getInventory().get(index);
		if (drop.isDropping()) {
			drop.dropItem(currentItem);
		} else {
			currentItem.use(mother);
		}
		mother.refreshDisplay();
	}
}
