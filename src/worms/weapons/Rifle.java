package worms.weapons;


import worms.model.Worm;

public class Rifle extends Weapon {
	
	
	/**
	 * constructor for a Rifle object
	 * @param owner
	 * 			The worm that is to own the Rifle.
	 * 
	 */
	public Rifle(Worm owner) {
		this.worm = owner;
		this.cost = 10;
		this.name = "Rifle";
	}
	
	/**
	 * A method to fire the Rifle, with a yield yield. It does this by calculating basic parameters and then spawning an object of the type Projectile
	 * @param yield
	 * 		the strength the weapon is to be shot at.
	 */
	public void shoot(int yield) {
		super.shoot(yield, 0.01, 1.5);
	}
}
