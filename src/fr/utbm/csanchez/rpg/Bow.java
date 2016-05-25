package fr.utbm.csanchez.rpg;


import fr.utbm.csanchez.utils.VectPerso;

public class Bow extends Item {

	public Bow(ItemContainer owner) {
		super(owner, "Bow", 0, 0, 0);
	}

	public String toString() {
		return getName();
	}

	public void use(InventoryManager caller) {
		Hero owner = (Hero) this.owner;
		//InterfaceVitef ask = new InterfaceVitef();
		System.out.println("Ou tirer avec l'arc ?");
		//VectPerso fire = ask.askMove("move");
		//VectPerso arrow = owner.getPosition().add(fire);
		//while (owner.isOnMap.getElement(arrow) == null) {
		//	arrow = arrow.add(fire);
		}
		//Element target = owner.isOnMap.getElement(arrow);
		//target.setHp(target.getHp() - owner.getAd() * 50 / (100 + target.getArmor()));

	}
}
