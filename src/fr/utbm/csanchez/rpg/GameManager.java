package fr.utbm.csanchez.rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import fr.utbm.csanchez.utils.VectPerso;

public class GameManager implements ActionListener {

	private JFrame frame;
	private JProgressBar HP = new JProgressBar();
	private MapView mv = new MapView(this);
	private GameMap gm = new GameMap(40, 40, 20, 30, 10);
	private Hero hero = gm.createHero();
	private InventoryManager iv = new InventoryManager(this);
	private JLabel gameRun = new JLabel();
	private JLabel heroAttributes = new JLabel();

	public Hero getHero() {
		return hero;
	}

	public void save() {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("Ressources/Save.ser"));
			oos.writeObject(gm);
			oos.close();
			hero = gm.getHero();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void load() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("Ressources/Save.ser"));
			try {
				gm = (GameMap) ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setVous(Hero vous) {
		this.hero = vous;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameManager window = new GameManager();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public GameManager() {
		initialize();
		mv.displayMap(gm);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(iv, BorderLayout.WEST);

		frame.getContentPane().add(mv, BorderLayout.CENTER);
		JPanel bottom = new JPanel();

		bottom.setLayout(new GridBagLayout());
		{
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.weightx = 1;
			constraints.weighty = 1;
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			bottom.add(HP, constraints);
		}
		{
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.weightx = 1;
			constraints.weighty = 1;
			constraints.gridx = 1;
			constraints.gridy = 1;
			constraints.fill = GridBagConstraints.BOTH;
			bottom.add(gameRun, constraints);
		}
		{
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.weightx = 1;
			constraints.weighty = 1;
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.fill = GridBagConstraints.BOTH;
			bottom.add(heroAttributes, constraints);
		}
		frame.getContentPane().add(bottom, BorderLayout.SOUTH);
		HP.setStringPainted(true);
		HP.setForeground(Color.RED);
		refreshDisplay();
	}

	public void actionPerformed(ActionEvent e) {
		VectPerso move = null;
		for (Entry<VectPerso, JButton> test : mv.getGrid().entrySet()) {
			if (test.getValue() == e.getSource()) {
				move = new VectPerso(test.getKey());
			}
		}
		Item usedNow = mv.getUsingNow();
		if (usedNow != null) {
			usedNow.fire(move);
		} else {
			hero.go(move);
		}
		refreshDisplay();
		if (hero.getHp() != 0) {
			mv.askMove();
		} else {
			for (JButton btn : mv.getGrid().values()) {
				btn.setEnabled(false);
			}
			gameRun.setText("Game Over");
		}
	}

	public void refreshDisplay() {
		mv.displayMap(gm);
		displayHeroStatus();
		iv.synchronizeInventory();
	}

	public void displayHeroStatus() {
		HP.setMaximum(hero.getHpMax());
		HP.setValue(hero.getHp());
		HP.setString(hero.getHp() + " / " + hero.getHpMax());
		gameRun.setText("Turn number" + gm.getCurrentTurn());
		heroAttributes.setText("<html><center>AD : " + hero.getAd() + "<br/>Armor : " + hero.getArmor() + " ("
				+ Math.round((1 - 100 / (100 + (double) hero.getArmor())) * 100.0) + "%)</center></html>");

	}

	public MapView getMv() {
		return mv;
	}

}
