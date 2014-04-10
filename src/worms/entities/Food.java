package worms.entities;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;

public class Food extends Immovable {

	public Food(World world, double x, double y) throws CoordinateOutOfBoundsException {
		
	}
	
	/**
	 * The eat function consumes the food, removing it from the world.
	 */
	public void eat() {
		getWorld().removeAsFood(self);
		terminate();
	}
}
