package fr.utbm.csanchez.rpg;

import java.util.LinkedList;
import java.util.List;

public class WaitingBlasts {
	private List<Blast> toOccur = new LinkedList<Blast>();

	public List<Blast> getToOccur() {
		return toOccur;
	}

	public void timeToBlow(int currentTurn) {
		List<Blast> notOccured=new LinkedList<Blast>();
		for (Blast d : toOccur) {
			if (d.getBlowingDate() == currentTurn) {
				d.blow();
			}
			else{
				notOccured.add(d);
			}
		}
		toOccur=notOccured;
	}
}
