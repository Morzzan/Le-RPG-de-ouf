package fr.utbm.csanchez.rpg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private Hero you = gm.createHero();

	public Hero getVous() {
		return you;
	}

	public void setVous(Hero vous) {
		this.you = vous;
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

		JButton btnRight = new JButton("right");
		frame.getContentPane().add(btnRight, BorderLayout.WEST);

		JButton btnLeft = new JButton("left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HP.setValue(HP.getValue() + 1);
			}
		});
		frame.getContentPane().add(btnLeft, BorderLayout.EAST);

		JLabel lblBienvenueDansLe = new JLabel("Bienvenue dans le jeu");
		frame.getContentPane().add(lblBienvenueDansLe, BorderLayout.NORTH);

		frame.getContentPane().add(mv, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1, 2));
		JPanel heroStatus = new JPanel();
		heroStatus.setLayout(new BoxLayout(heroStatus, BoxLayout.PAGE_AXIS));
		JLabel gameRun = new JLabel("keskispas dans ce jeu");
		JLabel heroAttributes = new JLabel("ad , ap, etc");
		heroStatus.add(HP);
		heroStatus.add(heroAttributes);
		bottom.add(heroStatus);
		bottom.add(gameRun);
		frame.getContentPane().add(bottom, BorderLayout.SOUTH);
		HP.setStringPainted(true);
		HP.setForeground(Color.RED);
		displayHeroStatus();

	}

	public void actionPerformed(ActionEvent e) {
		VectPerso move = null;
		for (java.util.Map.Entry<VectPerso, JButton> test : mv.getGrid().entrySet()) {
			if (test.getValue() == e.getSource()) {
				move = new VectPerso(test.getKey());
			}
		}
		you.go(move);
		gm.refresh();
		mv.displayMap(gm);
		displayHeroStatus();
	}

	public void displayHeroStatus() {
		HP.setMaximum(you.getHpMax());
		HP.setValue(you.getHp());
		HP.setString(you.getHp() + " / " + you.getHpMax());
	}

}
