package worms.entities;

public abstract class Movable extends Entity {
	
	private double Orientation;
	private final int Density;
	
	/**
	 * 
	 * @param target
	 * 		The target orientation
	 * @throws IlligalOrientationException
	 * 		If the orientation is not of a legal type, that is, not greater than pi (inclusive) and not smaller or equal to -pi 
	 */
	public void setOrientation(double target) throws IlligalOrientationException {
		return;
	}
	
	
	/**
	 * 
	 * @return
	 * 		The value of the variable orientation
	 */
	public double getOrientation() {
		return -1;
	}
	
	/**
	 * 
	 * @return
	 *		the value of density
	 */
	@Immutable
	public double getDensity() {
		return -1;
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
