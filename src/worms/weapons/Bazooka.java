package worms.weapons;

import worms.entities.Projectile;
import worms.model.Worm;

public class Bazooka extends Weapon {
	
public Bazooka(Worm owner) {
		this.worm = owner;
		this.cost = 50;
	}

	private Worm owner;
	
	public void shoot(int yield) {
		double projRadius = Math.pow(((double) (3 / 4) * (0.3 / 7800)), (double) (1 / 3));
		double projX = owner.getPosX() + (owner.getRadius() + projRadius)
				* Math.cos(owner.getOrientation());
		double projY = owner.getPosY() + (owner.getRadius() + projRadius)
				* Math.sin(owner.getOrientation());
		Projectile projectile = new Projectile(owner.getWorld(), projX, projY, projRadius,
				7800, owner.getOrientation(), 2.5 + (7 * (yield / 100.0)), 80);
		projectile.shoot(0.01);
	}
	
	public String getName() {
		return "Bazooka";
	}
}
