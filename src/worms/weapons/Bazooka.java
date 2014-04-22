package worms.weapons;

import worms.entities.Projectile;
import worms.model.Worm;

public class Bazooka extends Weapon {
	
public Bazooka(Worm worm) {
		this.worm = worm;
		this.cost = 50;
		this.name = "Bazooka";
	}
	
	public void shoot(int yield) {
		double projRadius = Math.pow(((double)(3.0/4.0) * (0.3/7800)), (double) (1.0/3.0));
		double projX = worm.getPosX() + (worm.getRadius() + projRadius) * Math.cos(worm.getOrientation());
		double projY = worm.getPosY() + (worm.getRadius() + projRadius) * Math.sin(worm.getOrientation());
		Projectile projectile = new Projectile(worm.getWorld(), projX, projY, projRadius,
				7800, worm.getOrientation(), 2.5 + (7 * (double)(yield/100.0)), 80);
		worm.getWorld().setProjectile(projectile);
	}
}
