package worms.entities;

import worms.containment.*;

public class Projectile extends Movable {
	
	public Projectile(World world) {
		this.world = world;
	}
	
	private final World world;
	
	public World getWorld() {
		return this.world;
	}
	
	public void jump() {
		getWorld().setProjectile(null);
	}
}
