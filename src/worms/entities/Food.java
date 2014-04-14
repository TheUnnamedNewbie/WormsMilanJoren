package worms.entities;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;

public class Food extends Immovable {

	public Food(World world, double x, double y) throws CoordinateOutOfBoundsException {
		this.radius = 0.2;
	}
	
	private final double radius;
}
