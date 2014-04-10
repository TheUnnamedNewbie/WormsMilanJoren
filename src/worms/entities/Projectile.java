package worms.entities;

import be.kuleuven.cs.som.annotate.*;
import worms.containment.*;

public class Projectile extends Movable {
	
	public Projectile(World world, double posX, double posY, double radius, long density, double orientation, double force, long damage) {
		setPosX(posX);
		setPosY(posY);
		setRadius(radius);
		this.world = world;
		getWorld().setProjectile(this);
		setDensity(density);
		setOrientation(orientation);
		this.force = force;
		this.damage = damage;
	}
	
	private final double force;
	private final long damage;
	private final World world;
	
	@Immutable
	private double getForce() {
		return this.force;
	}
	
	@Immutable
	private long getDamage() {
		return this.damage;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void jump() {
		//TODO Copy the maths and the things.
		getWorld().setProjectile(null); //We end with this because at this point, the projectile will have hit/collided/exited
	}
}
