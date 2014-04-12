package worms.entities;

import worms.util.Util;
import be.kuleuven.cs.som.annotate.*;

public abstract class Movable extends Entity {
	
	private double orientation, jumpTime;
	protected long density; //Q: why not a private?
	//[0] = x, [1] = y.
	private double[] jumpSpeed = new double[2];
	
	/**
	 * 
	 * @param target
	 * 		The target orientation
	 * @throws IlligalOrientationException
	 * 		If the orientation is not of a legal type, that is, not greater than pi (inclusive) and not smaller or equal to -pi 
	 */
	public void setOrientation(double target) {
		assert isValidOrientation(target);
		this.orientation = target;
	}
	
	/**
	 * 
	 * @return
	 * 		The value of the variable orientation
	 */
	public double getOrientation() {
		return this.orientation;
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
	
	public double getMass() {
		return -1;
	}
	
	public abstract double jumpTime(); //TODO see if we can make one main method for both worms and projectiles of if separate methods are needed.
	
	
	
	//TODO check how we should implement constants 
	/**
	 * MATHS GO HERE
	 */
	public void jump(long AP){
		double force, speed;	
		force = (5 * AP) + (getMass() * this.getWorld().GRAVITY);
		speed = (force/getMass())*(((double)1)/2);
		jumpSpeed[0] = speed * Math.cos(getOrientation()); 
		jumpSpeed[1] = speed * Math.sin(getOrientation());
		
		
		
		return;
	}
	
	/**
	 * MATHS GO HERE
	 * @return
	 */
	public double jumpTime() {
		return -1;
	}
	
	
	/**
	 * MATHS GO HERE
	 * @param time
	 * @return
	 */
	public double jumpStep(double time) {
		if(!isLegalTime(time)){
			time = endOfJump();
		}
		double[] returnCoordinates = new double[2];
		returnCoordinates[0] = (jumpSpeed[0]*time) + getPosX();
		returnCoordinates[1] = ((jumpSpeed[1]*time) - ((1.0/2.0) * this.getWorld().GRAVITY * time * time)) + getPosY();
		return returnCoordinates;
	}
	
	
	/**
	 * TODO boolean shizzle
	 * @return true if the object cannot jump, and time = 0 or if the object can jump and time > 0 and time <= JumpTime(), false all other times
	 * 		| if ((canJump() == false) && (time > -EPS) && (time < EPS)) 
	 * 		|	result = true
	 * 		| if ((canJump() == true) && (time > 0) && (time <= jumpTime()))
	 * 		|	result = true
	 * 		| else
	 * 		|	result = false
	 * @param time
	 * 		time you want to check
	 *  
	 */
	public boolean isLegalTime(double time) {
		if ((canJump() == false) && (time > -EPS) && (time < EPS)){
			return true;
		}
		if ((canJump() == true) && (time > 0) && (time <= jumpTime())) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * STUFF GOES HERE
	 * @return
	 */
	public boolean canJump() { //TODO Milan, work your magic!
		return false;
	}
}
