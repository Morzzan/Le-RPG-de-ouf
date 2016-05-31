package fr.utbm.csanchez.rpg;

import fr.utbm.csanchez.utils.VectPerso;

public class Wand extends Item {

	public Wand(ItemContainer owner) {
		super(owner, "Wand", 0, 0, 0);
	}

	public void use(GameManager caller) {
		caller.getMv().askBlow(this, 4);
	}

	public void fire(VectPerso move) {
		Hero hOwner = ((Hero) owner);
		hOwner.isOnMap.getdAL().getToOccur().add(new Blast(hOwner, hOwner.position.add(move), 2));
		hOwner.isOnMap.enemyTurn();
	}
}
