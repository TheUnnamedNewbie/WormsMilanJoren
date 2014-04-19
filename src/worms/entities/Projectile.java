package worms.entities;

import be.kuleuven.cs.som.annotate.*;
import worms.containment.*;
import worms.model.Worm;

public class Projectile extends Movable {
	
	public Projectile(World world, double posX, double posY, double radius, long density, double orientation, double force, long damage) {
		this.world = world;
		getWorld().setProjectile(this);
		setPosX(posX);
		setPosY(posY);
		setRadius(radius);
		setDensity(density);
		setOrientation(orientation);
		this.force = force;
		this.damage = damage;
	}
	
	private final double force;
	private final long damage;
	private final World world;
	
	@Immutable
	public double getForce() {
		return this.force;
	}
	
	@Immutable
	private long getDamage() {
		return this.damage;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void shoot(double timestep) {
		if(canFire()){
			double[] target = jumpStep(force, jumpTime(force, timestep)+timestep); // + timestep because we need to be in the worm in order for distance < value to work
			setPosX(target[0]); setPosY(target[1]);
			for (Worm worm: getWorld().getAllWorms())
				if (getWorld().distance(this, worm) < this.getRadius()+worm.getRadius())
					worm.damage(damage);
			getWorld().setProjectile(null); //We end with this because at this point, the projectile will have hit/collided/exited
			terminate();
		}
	}
	
	//TODO finish this stuff
	public boolean canFire() {
		return false;
	}
}
