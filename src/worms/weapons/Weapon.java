package worms.weapons;

import worms.entities.Projectile;
import worms.model.Worm;

/**
 * An abstract class to discribe the basic parameters shared by all weapons
 * @invar the weapon always has a valid worm
 * 		| this.getWorm() != null;
 * @author Joren
 *
 */
public abstract class Weapon {
	
	protected Worm worm;
	protected long cost;
	protected String name;
	
	/**
	 * returns the worm of this weapon
	 * @return
	 * 		| result == this.worm
	 */
	public Worm getWorm() {
		return this.worm;
	}
	
	/**
	 * Fires the weapon. Is never called, as it is overridden by the methods within it's children
	 * @param yield
	 * 		the yield of the shot to be fired	
	 */
	public  void shoot(int yield) {	
	}
	
	
	/**
	 * fires the weapon. Calculates the 
	 */
	public void shoot(int yield, double weaponMass, double weaponForce) {
		double projRadius = Math.pow(((double)(3.0/4.0) * (weaponMass/7800)), (double) (1.0/3.0));
		double projX = worm.getPosX() + (worm.getRadius() + projRadius) * Math.cos(worm.getOrientation());
		double projY = worm.getPosY() + (worm.getRadius() + projRadius) * Math.sin(worm.getOrientation());
		Projectile projectile = new Projectile(worm.getWorld(), projX, projY, projRadius,
				7800, worm.getOrientation(), weaponForce, 80);
		worm.getWorld().setProjectile(projectile);	
	}
	
	
	/**
	 * returns the name of the worm
	 * @return
	 * 		| result == this.name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * retruns the cost of a shot
	 * @return
	 * 		| result == this.cost
	 */
	public long getCost() {
		return this.cost;
	}
}
