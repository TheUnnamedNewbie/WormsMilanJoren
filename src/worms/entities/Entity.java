package worms.entities;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;
import worms.model.Worm;
import worms.util.Util;

public abstract class Entity {

	private double posX, posY;
	protected double radius;
	protected World world;
	private boolean terminated;
	protected static final double EPS = Util.DEFAULT_EPSILON;
	
	/**
	 * Sets a new X position if it is a valid one
	 * 
	 * @post
	 * 		| new.PosX == target
	 * @param target
	 * 		the target coordinate
	 * @throws CoordinateOutOfBoundsException
	 * 		thrown if the new coordinate would put the entity in an illegal position (in other words
	 * 		outside of the legal bounds of the map)
	 * 		 | !isValidX(target)
	 */
	public void setPosX(double target) throws CoordinateOutOfBoundsException {
		if (!getWorld().isValidX(target)) {
			throw new CoordinateOutOfBoundsException();			
		}
		this.posX = target;
	}
	
	/**
	 * Similar to the method above, but for use in prospective worlds.
	 * Either before the entity is initialized, or if it wants to switch worlds
	 * 
	 * DEV NOTE: Is this useful? The Y-equivalent isn't implemented.
	 * 
	 * @param target
	 * @param world
	 * @throws CoordinateOutOfBoundsException
	 */
	public void setPosX(double target, World world) throws CoordinateOutOfBoundsException {
		if (!world.isValidX(target)) {
			throw new CoordinateOutOfBoundsException();			
		}
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
	 * 		 | !isValidY(target)
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
	
	/**
	 * A combination of getX/Y
	 * @return a new double[2] with the coordinates
	 */
	public double[] getCoordinates() {
		return new double[]{getPosX(), getPosY()};
	}
	
	/**
	 * A combination of setX/Y
	 * @param target The coordinate in the form of a double[2]
	 */
	public void setCoordinates(double[] target) {
		setPosX(target[0]);
		setPosY(target[1]);
	}
	
	/**
	 * 
	 * @return The radius
	 */
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * @post The radius of the entity wil equal the target
	 * 		 | new.getRadius() == target
	 * @param target The radius to be set
	 * @throws If the radius is invalid
	 * 		 | !isValidRadius(target)
	 */
	public void setRadius(double target) {
		if (! isValidRadius(target))
			throw new IllegalArgumentException();
		this.radius = target;
	}
	
	/**
	 * 
	 * @return The World
	 */
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
	
	/**
	 * We use a lot of double arrays for coordinates so this shortens a lot of implementations
	 * @param coordinates
	 * @return
	 */
	public boolean isValidPosition(double[] coordinates) {
		return isValidPosition(coordinates[0], coordinates[1]);
	}
	
	/**
	 * Checks if the radius is valid
	 * @param target
	 * @return target > 0.0
	 */
	public boolean isValidRadius(double target) {
		return target > 0.0;
	}

	/**
	 * Is the entity terminated?
	 * @return
	 */
	public boolean isTerminated() {
		return this.terminated;
	}
	
	/**
	 * Terminate the entity
	 * @post
	 * 		 | new.isTerminated() == true
	 */
	public void terminate() {
		this.terminated = true;
	}
	
	/**
	 * Sets the world to null
	 * (for use with world switching and completely purging the entity from existence)
	 */
	public void setWorldNull(){
		this.world = null;
	}
	/**
	 * The collide method checks to see if a entity can exist at a given position.
	 * @param coordinates
	 * @return The entity can exist in the current location and is not overlapping with any other entities
	 */
	public static boolean collides(double[] coordinates, double radius, World world) {
		if (! world.canExist(coordinates, radius))
			return true;
		for (Worm worm: world.getAllWorms()) {
			if ((world.distance(coordinates, worm.getCoordinates()) < radius+worm.getRadius()))
				return true;
		}
		return false;
	}
	
	/**
	 * The non-static version is used with the attempted moving/jumping/falling of existing worms.
	 * The checking to see if you collide with worms does not take the current worm into account.
	 * @param coordinates
	 * @param radius
	 * @return
	 */
	public boolean collides(double[] coordinates, double radius) {
		if (! getWorld().canExist(coordinates, radius)) {
			return true;
		}
		for (Worm worm: getWorld().getAllWorms()) {
			if ((worm != this) && (getWorld().distance(coordinates, worm.getCoordinates()) < (radius+worm.getRadius())))
				return true;
		}
		return false;
	}
}