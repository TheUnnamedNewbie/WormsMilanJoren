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
	 *		| !(Math.abs(target) <= Math.Pi)
	 * @post
	 * 		the new orientation is equal to targer
	 * 		| new.getOrientation() == target
	 */
	public void setOrientation(double target) {
		assert isValidOrientation(target);
		this.orientation = target;
	}
	
	/**
	 * 
	 * @return
	 * 		The value of the variable orientation
	 * 		| result == this.orientation
	 */
	public double getOrientation() {
		return this.orientation;
	}
	
	/**
	 * 
	 * @return
	 *		the value of density
	 *		| result == this.density
	 */
	@Immutable
	public double getDensity() {
		return this.density;
	}
	
	/**
	 * Setter for the density of the entity.
	 * @param target
	 * 		the target density.
	 * @pre
	 * 		the target value is greater than 0
	 * 		| targer > 0
	 * @result
	 * 		the new density equals target
	 * 		| new.density == target
	 */
	protected void setDensity(long target) {
		assert target>0;
		this.density = target;
	}
	
	/**
	 * Checker for the validity of the provided orientation
	 * @param target
	 * 		the orientation value to be checked
	 * @return
	 * 		true if target is greater than or equal to -Math.Pi and lesser than or equal Math.Pi
	 * 		| result == (target >= -Math.PI) || (target <= Math.PI)
	 */
	public boolean isValidOrientation(double target) {
		return Math.abs(target) <= Math.PI;
	}
	
	/**
	 * Getter for the mass of the entity
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
	 * @param AP
	 * 		the amount of actionpoints to be used up by the jump
	 * @param timestep
	 * 		the value (in seconds) of the size in steps between two calculated time values
	 * @return
	 * 		The longest value of time the jump can take without either leaving the map, entering impassable terrain
	 * 		or coliding with another worm.
	 * 		| result == time if((isValidPosition(jumpStep(AP, time)) && 
	 * 		|					!collides(jumpStep(AP, time), getRadius())) && )
	 * 		|    				(!isValidPosition(jumpStep(AP, time + timestep)) || 
	 * 		|					collides(jumpStep(AP, time + timestep), getRadius()))
	 * 		
	 */
	public double jumpTime(long AP, double timestep) {
		double time = timestep;
		while (true) {
			double[] target = jumpStep(AP, time);
			if (!isValidPosition(target) || collides(target, getRadius())){
				if(!getWorld().isLegalPosition(target, getRadius()))
						return time;
				return time - timestep;
			}
			time += timestep;
		}
	}
	
	/**
	 * Same, but projectile version (hence the Force)
	 * @param force
	 * 		the force at which the projectile is to be launched
	 * @param timestep
	 * 		the value (in seconds) of the size in steps between two calculated time values
	 * @return
	 * 		The longest value of time the jump can take without either leaving the map, entering impassable terrain
	 * 		or coliding with another worm.
	 * 		| result == time if((isValidPosition(jumpStep(force, time)) && 
	 * 		|					!collides(jumpStep(force, time), getRadius())) && )
	 * 		|    				(!isValidPosition(jumpStep(force, time + timestep)) || 
	 * 		|					collides(jumpStep(force, time + timestep), getRadius()))
	 */
	public double jumpTime(double force, double timestep) {
		double time = timestep;
		while (true) {
			double[] target = jumpStep(force, time);
			if (!isValidPosition(target) || collides(target, getRadius())){
				if(!getWorld().isLegalPosition(target, getRadius()))
						return time;
				return time - timestep;
			}
			time += timestep;
		}
	}
	
	
/**
 * Calculates the position of a movable at a given time.
 * 
 * First, you calculate the starting speed with the direction and the force applied.
 * Then, for your Y position, you also take gravity into account.
 * @param force
 * 		the force at which the jump is executed
 * @param time
 * 		the time for which the location is to be calculated
 * @return 
 * 		| result == { ((force/getMass())*(1.0/2.0) * Math.cos(getOrientation())*time) + getPosX(),
 * 		|			  ((force/getMass())*(1.0/2.0) * Math.sin(getOrientation())*time) + getPosY() - ((1.0/2.0) * World.GRAVITY * time * time)}
 */
	@Raw
	public double[] jumpStep(double force, double time) {
		double[] returnCoordinates = new double[2];
		double speed;
		speed = (force/getMass())*(double)(1.0/2.0);
		double speedX = speed * Math.cos(getOrientation());
		double speedY = speed * Math.sin(getOrientation());
		returnCoordinates[0] = (speedX*time) + getPosX();
		returnCoordinates[1] = (speedY*time) + getPosY() - ((double )(1.0/2.0) * World.GRAVITY * time * time);
		return returnCoordinates;
	}
	
	/**
	 * Same, but with AP for worms instead of force
* First, you calculate the starting speed with the direction and the force applied.
 * Then, for your Y position, you also take gravity into account.
 * @param AP
 * 		the amount of actionpoints to be used in the jump
 * @param time
 * 		the time for which the location is to be calculated
 * @return 
 * 		| result == jumpStep((5 * AP) + (getMass() * World.GRAVITY), time)
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
		double checkDist = getRadius();
		double targetX = getPosX()+Math.cos(getOrientation())*(getRadius()+checkDist);
		double targetY = getPosY()+Math.sin(getOrientation())*(getRadius()+checkDist);
		return !collides(new double[]{targetX, targetY}, getRadius());
	}
}
