package worms.entities;

import be.kuleuven.cs.som.annotate.*;

public abstract class Movable extends Entity {
	
	private double Orientation;
	protected long density;
	
	/**
	 * 
	 * @param target
	 * 		The target orientation
	 * @throws IlligalOrientationException
	 * 		If the orientation is not of a legal type, that is, not greater than pi (inclusive) and not smaller or equal to -pi 
	 */
	public void setOrientation(double target) {
		assert isValidOrientation(target);
		this.Orientation = target;
	}
	
	/**
	 * 
	 * @return
	 * 		The value of the variable orientation
	 */
	public double getOrientation() {
		return this.Orientation;
	}
	
	/**
	 * 
	 * @return
	 *		the value of density
	 */
	@Immutable
	public double getDensity() {
		return this.density;
	}
	
	protected void setDensity(long target) {
		this.density = target;
	}
	
	public boolean isValidOrientation(double target) {
		return Math.abs(target) <= Math.PI;
	}
	
	/**
	 * MATHS GO HERE
	 */
	public void Jump(){
		return;
	}
	
	/**
	 * MATHS GO HERE
	 * @return
	 */
	public double JumpTime() {
		return -1;
	}
	
	
	/**
	 * MATHS GO HERE
	 * @param time
	 * @return
	 */
	public double JumpStep(double time) {
		return -1;
	}
	
	/**
	 * STUFF GOES HERE
	 * @return
	 */
	public boolean CanJump() {
		return false;
	}
}
