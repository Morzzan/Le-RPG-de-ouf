package fr.utbm.csanchez.rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
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
	private GameMap gm = new GameMap(10, 10);
	private Hero hero = gm.createHero();
	private InventoryManager iv = new InventoryManager(this);

	public Hero getHero() {
		return hero;
	}

	public Hero getVous() {
		return hero;
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

		JButton btnRight = new JButton("left");
		frame.getContentPane().add(btnRight, BorderLayout.EAST);
		frame.getContentPane().add(iv, BorderLayout.WEST);

		JLabel lblBienvenueDansLe = new JLabel("Bienvenue dans le jeu");
		frame.getContentPane().add(lblBienvenueDansLe, BorderLayout.NORTH);

		frame.getContentPane().add(mv, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1, 2));
		JPanel heroStatus = new JPanel();
		heroStatus.setLayout(new BoxLayout(heroStatus, BoxLayout.PAGE_AXIS));
		JLabel gameRun = new JLabel("<html><center>keskispas" + " dans ce<br/> jeu</center></html>");
		JLabel heroAttributes = new JLabel(
				"<html><center>AD : " + hero.getAd() + "<br/>Armor : " + hero.getArmor() + "</center></html>");
		heroAttributes.setHorizontalTextPosition(JLabel.CENTER);
		heroAttributes.setPreferredSize(new Dimension(frame.getWidth() / 2, frame.getHeight() / 10));
		heroStatus.add(HP);
		heroStatus.add(heroAttributes);
		bottom.add(heroStatus);
		bottom.add(gameRun);
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
			if (usedNow instanceof Bow) {
				((Bow) usedNow).fire(move);
			}
		} else {
			hero.go(move);
		}

		refreshDisplay();
		mv.askMove();
	}

	public void refreshDisplay() {
		mv.displayMap(gm);
		iv.synchronizeInventory();
		displayHeroStatus();
		iv.synchronizeInventory();
	}

	public void displayHeroStatus() {
		HP.setMaximum(hero.getHpMax());
		HP.setValue(hero.getHp());
		HP.setString(hero.getHp() + " / " + hero.getHpMax());
	}

	public MapView getMv() {
		return mv;
	}

}
