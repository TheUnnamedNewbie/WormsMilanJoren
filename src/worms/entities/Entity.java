package worms.entities;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;

public abstract class Entity {

	private double posX, posY, Radius;
	private World world;
	private boolean terminated;
	
	
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
	
	public double getRadius() {
		return this.Radius;
	}
	
	public void setRadius(double target) {
		this.Radius = target;
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

	public boolean isTerminated() {
		return this.terminated;
	}
	
	public void terminate() {
		this.terminated = true;
	}
	
}
