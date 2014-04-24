package worms.weapons;


import worms.model.Worm;

public class Bazooka extends Weapon {
	
	/**
	 * the constructor of a object of Bazooka
	 * @param worm
	 * 		the worm that is to own this bazooka object.
	 */
public Bazooka(Worm worm) {
		this.worm = worm;
		this.cost = 50;
		this.name = "Bazooka";
	}
	
	/**
	 * creates an object of the projectile type with the neccisary parameters in the world.
	 * @param yield
	 * 		the yield the weapon is to be fired at
	 */
	public void shoot(int yield) {
		super.shoot(yield, 0.3, 2.5 + (7 * (double)(yield/100.0)));
	}
}
