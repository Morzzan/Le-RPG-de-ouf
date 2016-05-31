package fr.utbm.csanchez.rpg;

import java.io.Serializable;

import fr.utbm.csanchez.utils.VectPerso;

public class Blast implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int blowingDate;
	private VectPerso target;
	private Hero caster;

	public Blast(Hero caster, VectPerso target, int delay) {
		this.caster = caster;
		this.target = target;
		blowingDate = caster.isOnMap.getCurrentTurn() + delay;
	}

	public void blow() {
		Element victim = caster.isOnMap.getElement(target);
		if (victim != caster && victim != null) {
			victim.takeHit(caster.getAd() * 3);
		}
	}

	public VectPerso getTarget() {
		return target;
	}

	public int getBlowingDate() {
		return blowingDate;
	}
}
