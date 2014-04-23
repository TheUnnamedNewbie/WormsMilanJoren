package worms.entities;

import worms.containment.World;
import be.kuleuven.cs.som.annotate.*;

/**
 * The Movable class introduces the methods for jumping, mass and radiuses
 * @author Milan Sanders
 * @author Joren Vaes
 * @invar The radius shall never exceed Double.MAX_VALUE
 * 		 | getRadius() < Double.MAX_VALUE
 * @invar The absolute value of the orientation shall never exceed 2*Pi
 * 		 | Math.abs(getRadius()) <= Math.PI
 * @invar Density shall always be larger than 0
 * 		 | getDensity() > 0
 */
public abstract class Movable extends Entity {
	
	private double orientation;
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
		assert target>0;
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
	 * It checks in increments if the movable has collided already
	 * Worm version (hence the AP)
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
	
	/**
	 * Same, but projectile version (hence the Force)
	 * @param force
	 * @param timestep
	 * @return
	 */
	public double jumpTime(double force, double timestep) {
		double time = timestep;
		while (true) {
			double[] target = jumpStep(force, time);
			if (!isValidPosition(target) || collides(target, getRadius()))
				return time;
			time += timestep;
		}
	}
	
	
/**
 * Calculates the position of a movable at a given time.
 * 
 * First, you calculate the starting speed with the direction and the force applied.
 * Then, for your Y position, you also take gravity into account.
 * @param force
 * @param time
 * @return new double[]{((force/getMass())*(1.0/2.0) * Math.cos(getOrientation())*time) + getPosX(),
 * 						((force/getMass())*(1.0/2.0) * Math.sin(getOrientation())*time) + getPosY() - ((1.0/2.0) * World.GRAVITY * time * time)}
 */
	@Raw
	public double[] jumpStep(double force, double time) {
		double[] returnCoordinates = new double[2];
		double speed;
		speed = (force/getMass())*(double)(1.0/2.0);
		//System.out.println("speed: "+speed);
		double speedX = speed * Math.cos(getOrientation());
		double speedY = speed * Math.sin(getOrientation());
		returnCoordinates[0] = (speedX*time) + getPosX();
		returnCoordinates[1] = (speedY*time) + getPosY() - ((double )(1.0/2.0) * World.GRAVITY * time * time);
		return returnCoordinates;
	}
	
	/**
	 * Same, but with AP for worms instead of force
	 * @param AP
	 * @param time
	 * @return
	 */
	@Raw
	public double[] jumpStep(long AP, double time) {
		double force;
		force = (5 * AP) + (getMass() * World.GRAVITY);
		double[] targetPos = jumpStep(force, time);
		return targetPos;
	}
	
	/**
	 * canJump() sees if you can move a decent amount in the direction you would wish to jump
	 * @return
	 */
	public boolean canJump() {
		double checkDist = getRadius(); // How far ahead a Entity must be able to move be able to justify a jump
		double targetX = getPosX()+Math.cos(getOrientation())*(getRadius()+checkDist);
		double targetY = getPosY()+Math.sin(getOrientation())*(getRadius()+checkDist);
		return !collides(new double[]{targetX, targetY}, getRadius());
	}
}
