package worms.weapons;

import worms.entities.Projectile;
import worms.model.Worm;

public class Rifle extends Weapon {
	public Rifle(Worm owner) {
		this.owner = owner;
	}
	
	private final Worm owner;
	
	public void shoot(int yield) {
		double projRadius = Math.pow(((double)(3/4) * (0.01/7800)),(double)(1/3));
		double projX = owner.getPosX()+(owner.getRadius()*projRadius)*Math.cos(owner.getOrientation());
		double projY = owner.getPosY()+(owner.getRadius()*projRadius)*Math.sin(owner.getOrientation());
		Projectile projectile = new Projectile(owner.getWorld(), projX, projY, projRadius, 7800, owner.getOrientation(), 1.5, 20);
		projectile.jump();
	}
	
	public String getName() {
		return "Rifle";
	}
}
