package worms.entities;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;

public class Food extends Immovable {

	public Food(World world, double x, double y) throws CoordinateOutOfBoundsException {
		this.world = world;
		this.radius = 0.2;
		this.posX = x;
		this.posY = y;
	}
}
