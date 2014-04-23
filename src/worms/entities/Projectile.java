package worms.entities;

import be.kuleuven.cs.som.annotate.*;
import worms.CoordinateOutOfBoundsException;
import worms.containment.*;
import worms.model.Worm;

public class Projectile extends Movable {
	
	/**
	 * Projectiles are shot from weapons by worms.
	 * They fly through the world and damage worm to the point of death.
	 * Quite cruel to ask a bunch of students to program instruments of death...
	 * 
	 * @post The projectile will exist within the bounds of the world
	 * 		 | isValidX(getPosX()) && isValidY(getPosY())
	 * 
	 * @param world | The world in which the projectile is initialised
	 * @param posX | Its X position in the world in metres
	 * @param posY | Its Y position in the world in metres
	 * @param radius | Its radius (the same for projectiles from the same type of weapon) in metres
	 * @param density | The density of the projectile in kg/m**3
	 * @param orientation | The direction in which it is fired in radians
	 * @param force | The force with which it is fired in Newton
	 * @param damage | The amount of damage it deals to a worm if it hits
	 * 
	 * @throws if the position at which you want to initialize isn't valid (originates from setPosX/Y())
	 */
	public Projectile(World world, double posX, double posY, double radius, long density, double orientation, double force, long damage) throws CoordinateOutOfBoundsException {
		this.world = world;
		System.out.println("Setting active projectile...");
		getWorld().setProjectile(this);
		System.out.println("Is active projectile? "+(this==getWorld().getProjectile()));
		setPosX(posX); // Here be exceptions
		setPosY(posY);
		setRadius(radius);
		setDensity(density);
		setOrientation(orientation);
		this.force = force;
		this.damage = damage;
	}
	
	private final double force;
	private final long damage;
	
	/**
	 * The force with which this projectile is propelled
	 * @return
	 */
	@Immutable
	public double getForce() {
		return this.force;
	}
	
	/**
	 * The damage this projectile deals to a worm if it hits
	 * @return
	 */
	@Immutable
	private long getDamage() {
		return this.damage;
	}
	
	/**
	 * Damage the worm(s, although this isn't possible) it lands on.
	 * @param timestep See IFacade.jump() for a more detailed explanation
	 * @post the worm that is hit will loose health equal to this' damage amount
	 * 		 | if (wormIsHit())
	 * 		 | 	hitWorm.damage(getDamage())
	 */
	public void shoot(double timestep) {
		if(canFire()){
			double[] target = jumpStep(force, jumpTime(force, timestep)+timestep); // + timestep because we need to be in the worm in order for distance < value to work
			for (Worm worm: getWorld().getAllWorms())
				if (getWorld().distance(target, worm.getCoordinates()) < this.getRadius()+worm.getRadius())
					worm.damage(damage);
			die();
		}
	}
	
	/**
	 * Option for expansion. The (im)possibility for firing (AP and such) is handled before this (see Worm)
	 * If the projectile is located within impassable terrain, it will simply stop where it stands.
	 * @return true, for now...
	 */
	public boolean canFire() {
		return true;
	}
	
	/**
	 * Terminates the current projectile and removes it as the active projectile for the world
	 * More specifically, the current projectile is set to null untill another shot is fired
	 * #ShotsFired
	 * @post ... (see above)
	 * 		 | this.isTerminated() && this != getWorld().getProjectile()
	 */
	public void die() {
		getWorld().setProjectile(null);
		terminate();
	}
}
