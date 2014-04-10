package worms.weapons;

import worms.entities.Projectile;
import worms.model.Worm;

public class Rifle extends Weapon {
	public Rifle(Worm owner) {
		this.owner = owner;
	}
	
	private final Worm owner;
	
	public void shoot(int yield) {
		//TODO do shooting maths
		Projectile projectile = new Projectile(/*param*/);
		owner.getWorld().setProjectile(projectile);
		projectile.jump();
	}
	
	public String getName() {
		return "Rifle";
	}
}
