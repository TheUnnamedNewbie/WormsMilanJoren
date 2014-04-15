package worms.entities;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;
import worms.model.Worm;
import worms.util.Util;

public abstract class Entity {

	private double posX, posY;
	protected double radius;
	private World world;
	private boolean terminated;
	protected static final double EPS = Util.DEFAULT_EPSILON;
	
	/**
	 * @post
	 * 		| new.PosX == target
	 * @param target
	 * 		the target coordinate
	 * @throws CoordinateOutOfBoundsException
	 * 		thrown if the new coordinate would put the entity in an illegal position (in other words
	 * 		outside of the legal bounds of the map)
	 */
	public void setPosX(double target) throws CoordinateOutOfBoundsException {
		if (!getWorld().isValidX(target))
			throw new CoordinateOutOfBoundsException();
		this.posX = target;
	}
	
	/**
	 * 
	 * @return 
	 * 		return the value stored in PosX
	 * 		| result == this.PosX
	 */
	public double getPosX() {
		return this.posX;
	}
	
	/**
	 * @post
	 * 		| new.PosY == target
	 * @param target
	 * 		The new coordinate PosY is to be set too
	 * @throws CoordinateOutOfBoundsException
	 * 		thrown if the new coordinate would put the entity in an illegal position (in other words
	 * 		outside of the legal bounds of the map)
	 */
	public void setPosY(double target) throws CoordinateOutOfBoundsException {
		if (!getWorld().isValidY(target))
			throw new CoordinateOutOfBoundsException();
		this.posY = target;
	}
	
	/**
	 * 
	 * @return 
	 * 		return the value stored in PosY
	 * 		| result == this.PosY
	 */
	public double getPosY() {
		return this.posY;
	}
	
	public double[] getCoordinates() {
		return new double[]{getPosX(), getPosY()};
	}
	
	public void setCoordinates(double[] target) {
		setPosX(target[0]);
		setPosY(target[1]);
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public void setRadius(double target) {
		this.radius = target;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * 
	 * @param targetX
	 * 		Coordinate X you wish to check
	 * @param targetY
	 * 		Coordinate Y you wish to check
	 * @return
	 * 		true if and only if both targetX and targetY are within the legal bounds of the X and Y coordinates resp.
	 * 		false in all other cases
	 */
	public boolean isValidPosition(double targetX, double targetY) {
		return getWorld().isValidX(targetX) && getWorld().isValidY(targetY);
	}
	
	public boolean isValidPosition(double[] coordinates) {
		return isValidPosition(coordinates[0], coordinates[1]);
	}
	
	public boolean isValidRadius(double target) {
		return target > 0.0;
	}

	public boolean isTerminated() {
		return this.terminated;
	}
	
	public void terminate() {
		this.terminated = true;
	}
	
	/**
	 * The collide method checks to see if a enitity can exist at a given position.
	 * Inheriting classes will receive more specified rules (e.g. Projectile collides with worm)
	 * @param coordinates
	 * @return
	 */
	public static boolean collides(double[] coordinates, double radius, World world) {
		if (! world.canExist(coordinates, radius))
			//Collides with map
			return true;
		for (Worm worm: world.getAllWorms())
			if (world.distance(coordinates, worm.getCoordinates()) < radius+worm.getRadius())
				return true;
		return false;
	}
}
