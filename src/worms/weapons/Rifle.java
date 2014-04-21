package worms.weapons;

import worms.entities.Projectile;
import worms.model.Worm;

public class Rifle extends Weapon {
	public Rifle(Worm owner) {
		this.worm = owner;
		this.cost = 10;
	}
	
	public void shoot(int yield) {
		double projRadius = Math.pow(((double)(3.0/4.0) * (0.01/7800)),(double)(1.0/3.0));
		double projX = getWorm().getPosX()+(getWorm().getRadius()+projRadius)*Math.cos(getWorm().getOrientation());
		double projY = getWorm().getPosY()+(getWorm().getRadius()+projRadius)*Math.sin(getWorm().getOrientation());
		Projectile projectile = new Projectile(getWorm().getWorld(), projX, projY, projRadius, 7800, getWorm().getOrientation(), 1.5, 20);
		projectile.shoot(0.01);
	}
	
	public String getName() {
		return "Rifle";
	}
}
