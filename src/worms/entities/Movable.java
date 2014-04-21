package worms.entities;

import worms.containment.World;
import be.kuleuven.cs.som.annotate.*;

public abstract class Movable extends Entity {
	
	private double orientation;
	protected long density; //Q: why not a private? A: gives errors. Must fix this, but is persistend accross all classes (e.g. world is always redefined)
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
		//System.out.println(target);
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
	
	/**
	 * 
	 * @return	the mass calculated from radius
	 * 			| result == 1062 * ((4.0 / 3.0) * Math.PI * Math.pow(getRadius() , 3)
	 */
	public double getMass() {
		return getDensity() * (double)(4.0/3.0) * Math.PI * (Math.pow(getRadius(), 3));
	}
	
	/**
	 * The jumpTime method tells us how long a jump takes in seconds.
	 * @return
	 */
	public double jumpTime(long AP, double timestep) {
		double time = timestep;
		while (true) {
			double[] target = jumpStep(AP, time);
			if (!isValidPosition(target) || collides(target, getRadius()))
				return time;
			time += timestep;
		}
	}
	
	public double jumpTime(double force, double timestep) {
		double time = timestep;
		while (true) {
			double[] target = jumpStep(force, time);
			if (!isValidPosition(target) || collides(target, getRadius()))
				return time;
			time += timestep;
		}
	}
	
	@Raw
	public double[] jumpStep(double force, double time) {
		double[] returnCoordinates = new double[2];
		double speed;
		speed = (force/getMass())*(double)(1.0/2.0);
		jumpSpeed[0] = speed * Math.cos(getOrientation());
//		System.out.println("speedX: "+jumpSpeed[0]);
		jumpSpeed[1] = speed * Math.sin(getOrientation());
		returnCoordinates[0] = (jumpSpeed[0]*time) + getPosX();
		this.getWorld();
		returnCoordinates[1] = ((jumpSpeed[1]*time) - ((1.0/2.0) * World.GRAVITY * time * time)) + getPosY();
		return returnCoordinates;
	}
	
	@Raw
	public double[] jumpStep(long AP, double time) {
		double force;
		force = (5 * AP) + (getMass() * World.GRAVITY);
		return jumpStep(force, time);
	}
	
//	/**
//	 * I think we no longer need this, canJump is better.
//	 * @return true if the object cannot jump, and time = 0 or if the object can jump and time > 0 and time <= JumpTime(), false all other times
//	 * 		| if ((canJump() == false) && (time > -EPS) && (time < EPS)) 
//	 * 		|	result = true
//	 * 		| if ((canJump() == true) && (time > 0) && (time <= jumpTime()))
//	 * 		|	result = true
//	 * 		| else
//	 * 		|	result = false
//	 * @param time
//	 * 		time you want to check
//	 *  
//	 */
//	public boolean isLegalTime(double time) {
//		if ((canJump() == false) && (time > -EPS) && (time < EPS)){
//			return true;
//		}
//		if ((canJump() == true) && (time > 0) && (time <= jumpTime(0.01))) {
//			return true;
//		}
//		return false;
//	}
	
	
	/**
	 * canJump() sees if you can move a decent amount in the direction you would wish to jump
	 * @return
	 */
	public boolean canJump() {
		double checkDist = getRadius(); // How far ahead a Entity must be able to move be able to justify a jump
		double targetX = getPosX()+Math.cos(getOrientation())*(getRadius()+checkDist);
		double targetY = getPosY()+Math.sin(getOrientation())*(getRadius()+checkDist);
		boolean out = !collides(new double[]{targetX, targetY}, getRadius());
		System.out.println("Can jump? "+out);
		return out;
	}
}
