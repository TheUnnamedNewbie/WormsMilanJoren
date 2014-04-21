package worms.entities;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;
import worms.model.Worm;
import worms.util.Util;

public abstract class Entity {

	protected double posX, posY;
	protected double radius;
	protected World world;
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
		if (!getWorld().isValidX(target)) {
			throw new CoordinateOutOfBoundsException();			
		}
		this.posX = target;
	}
	
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
	
	public void setWorldNull(){
		this.world = null;
	}
	/**
	 * The collide method checks to see if a entity can exist at a given position.
	 * Inheriting classes will receive more specified rules (e.g. Projectile collides with worm)
	 * @param coordinates
	 * @return
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
			//System.out.println("Map collision detected");
			return true;
		}
		for (Worm worm: getWorld().getAllWorms()) {
			if ((worm != this) && (getWorld().distance(coordinates, worm.getCoordinates()) < (radius+worm.getRadius())))
				return true;
		}
		return false;
	}
}