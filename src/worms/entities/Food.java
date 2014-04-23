package worms.entities;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;

public class Food extends Immovable {
	
	/**
	 * Constructor foor food. Nothing new to see here. We just put on a certain position in a world
	 * @invar The radius is always 0.2
	 * 		 | getRadius() == 0.2
	 * @param world
	 * @param x
	 * @param y
	 * @throws CoordinateOutOfBoundsException See Entity.setPosX/Y()
	 */
	public Food(World world, double x, double y) throws CoordinateOutOfBoundsException {
		this.world = world;
		this.radius = 0.2;
		setPosX(x);
		setPosY(y);
	}
}