package worms.weapons;

import worms.model.Worm;

public abstract class Weapon {
	
	protected Worm worm;
	
	public Worm getWorm() {
		return this.worm;
	}
	
	public void shoot(int yield) {
		
	}
	
	public String getName() {
		return "Weapon";
	}
}
