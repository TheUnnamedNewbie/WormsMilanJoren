package worms.model;

import worms.ExhaustionException;
import be.kuleuven.cs.som.annotate.*;

/**
 * 
 * WORMS!! The class Worm contains all information and methods related to the
 * actual worms and their movements.
 * 
 * @invar The current (at stable point) action points must remain above 0 and
 *        under max action points.
 *        | isValidPoints(getActionPoints())
 * @invar The radius is always at least 0.25m
 * 		  | getRadius() >= 0.25
 * @author Milan Sanders
 * @author Joren Vaes
 * @date 18/03/2014
 * 
 */
public class Worm {

	public Worm(String name, double posX, double posY, double radius,
			double direction) {
		setPosX(posX);
		setPosY(posY);
		setRadius(radius); // Need to add a isvalidradius? (radius should not be
		// set under 0.25)
		setOrientation(direction);
		setActionPoints(getMaxActionPoints());
		setName(name);
		updateJumpData();
	}

	private double posX;
	private double posY;
	private double radius;
	private int ActionPoints;
	private double orientation;
	private String name;

	private double jumpX;
	private double jumpY;
	private double jumpTime;
	private boolean jumpLegal;
	private double jumpSpeedX;
	private double jumpSpeedY;

	private String validchar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\'\" ";

	/**
	 * @return The worm's X coordinate
	 * 		| result == this.posX
	 */
	@Basic
	public double getPosX() {
		return this.posX;
	}

	/**
	 * 
	 * @param position
	 * 		The new value the X coordinate of the worm is to be set to.
	 * @post ... 
	 * 		| new.getPosX() == position
	 */
	public void setPosX(double position) {
		this.posX = position;
		updateJumpData();
	}

	/**
	 * 
	 * @return The value of the Y coordinate of the worm.
	 * 		| result == this.posY
	 */
	@Basic
	public double getPosY() {
		return this.posY;
	}

	/**
	 * 
	 * @param position
	 * 			The new value the Y coordinate of the worm is to be set to.
	 * @post ...
	 * 		| new.getPosY() == position
	 */
	public void setPosY(double position) {
		this.posY = position;
		updateJumpData();
	}

	/**
	 * 
	 * @return the radius of the worm.
	 * 		| result == this.radius
	 */
	@Basic
	public double getRadius() {
		return radius;
	}

	/**
	 * 
	 * @param radius
	 *            The new radius for the worm
	 * @post The radius of the worm is now set the the given value radius
	 * 			   | new.getRadius() == radius
	 * @throws IllegalArgumentException
	 *             the given radius is not a legal radius.
	 *             | ! isValidRadius(radius)
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (!isValidRadius(radius)) {
			throw new IllegalArgumentException();
		} else {
			this.radius = radius;

		}
	}

//	 Note: If mass is frequently used, store in variable and edit whenever you
//	 alter radius
	
	/**
	 * 
	 * @return	the mass calculated from radius
	 * 			| result == 1062 * ((4.0 / 3.0) * Math.PI * Math.pow(getRadius() , 3)
	 */
	public double getMass() {
		return (double)1062 * (double)(4.0/3.0) *(Math.PI) * (Math.pow(getRadius(), 3));
	}

	/**
	 * 
	 * @return	the maximum amount of actionpoints this worm can have
	 * 			| result == (int)Math.round(getMass())
	 */
	public int getMaxActionPoints() {
		return (int) Math.round(getMass());
	}

	/**
	 * 
	 * @return the current amount of actionpoints of the worm.
	 * 		| result == this.ActionPoints
	 */
	@Basic
	public int getActionPoints() {
		return ActionPoints;
	}

	/**
	 * 
	 * @param points
	 *            the amount of points you want to set the worms actionpoints to
	 * @post if the amount of actionpoints was a valid amount, the new value of
	 *       actionpoints of this worm shall be equal to it
	 *       | if (isValidActionPoints(points)) {new.getActionPoints() == points}
	 */
	private void setActionPoints(int points) {
		if (isValidPoints(points))
			ActionPoints = points;
	}

	@Basic
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * @pre the value of orientation should be between -pi (exclusive) and pi (inclusive).
	 * 		| (-Math.PI <= orientation) && (orientation <= Math.PI)
	 * @param orientation
	 * 		The value the orientation of the worm is to be set to.
	 */
	public void setOrientation(double orientation) {
		this.orientation = orientation;
		updateJumpData();
	}

	/**
	 * 
	 * @return the name of the worm.
	 * 		| result == this.name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 * 		The new string the name of the worm is to be set to
	 * @throws IllegalArgumentException
	 * 		the new name contains Illegal charecters or is to short.
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (!isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}

	private double getJumpX() {
		return jumpX;
	}

	private void setJumpX(double jumpX) {
		this.jumpX = jumpX;
	}

	/**
	 * @return the jumpY
	 */
	private double getJumpY() {
		return jumpY;
	}

	/**
	 * @param jumpY
	 *            the jumpY to set
	 */
	private void setJumpY(double jumpY) {
		this.jumpY = jumpY;
	}

	/**
	 * @return the jumpTime
	 */
	public double getJumpTime() {
		return jumpTime;
	}

	/**
	 * @param jumpTime
	 *            the jumpTime to set
	 */
	private void setJumpTime(double jumpTime) {
		this.jumpTime = jumpTime;
	}

	/**
	 * @return the jumpLegal
	 */
	private boolean isJumpLegal() {
		return jumpLegal;
	}

	/**
	 * @param jumpLegal
	 *            the jumpLegal to set
	 */
	private void setJumpLegal(boolean jumpLegal) {
		this.jumpLegal = jumpLegal;
	}

	/**
	 * @return the jumpSpeedX
	 */
	private double getJumpSpeedX() {
		return jumpSpeedX;
	}

	/**
	 * @param jumpSpeedX
	 *            the jumpSpeedX to set
	 */
	private void setJumpSpeedX(double jumpSpeedX) {
		this.jumpSpeedX = jumpSpeedX;
	}

	/**
	 * @return the jumpSpeedY
	 */
	private double getJumpSpeedY() {
		return jumpSpeedY;
	}

	/**
	 * @param jumpSpeedY
	 *            the jumpSpeedY to set
	 */
	private void setJumpSpeedY(double jumpSpeedY) {
		this.jumpSpeedY = jumpSpeedY;
	}

	/**
	 * 
	 * @param radius
	 * 		the radius of which the validity is to be checked
	 * @return true is the radius is greater or equal to 0.25
	 * 		| result == (radius >= 0.25)
	 */
	private boolean isValidRadius(double radius) {
		return (radius >= 0.25);
	}
	
	/**
	 * 
	 * @param points
	 *            the amount of points of which you want to know if it is a
	 *            valid amount
	 * @return true if the given value is greater or equal to zero and less than
	 *         or equal to the max value of AP for this worm
	 *         | result == ((points >= 0) && (points <= getMaxActionPoints())
	 */
	public boolean isValidPoints(int points) {
		return ((points >= 0) && (points <= getMaxActionPoints()));
	}

	/**
	 * 
	 * @param name
	 * @return	...
	 * 			| (name.length() >= 2) && containsLegalChars() )
	 */
	private boolean isValidName(String name) {
		if (name.length() >= 2) {
			return containsLegalChars(name);
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return true when the orientation of the worm is smaller than pi.
	 *         | result == (getOrientations < Math.PI)
	 */
	private boolean isValidOrientation() {
		return ((getOrientation() < Math.PI) && (getOrientation() > 0));
	}

	private boolean containsLegalChars(String name) {
		int length = name.length();
		for (int i = 0; i < length; i++) {
			if (!validchar.contains(name.subSequence(i, i + 1))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param steps
	 * 		The amount of steps the worm should move
	 * @return
	 * 		True if the worm can move that much steps if they are positive. 
	 * 		This means that the worm has enough actionpoints, every step takes (cos(getOrientation())) + 4*sin(getOrientation()).
	 *  	| result == (getActionPoints() >= (Math.abs(Math.cos(getOrientation())) + Math.abs(Math.sin(getOrientation())) * 4 * steps)) && (steps > 0)
	 */
	public boolean canMove(int steps) {
		double currentOrientation = getOrientation();
		double stepPoints = (Math.abs(Math.cos(currentOrientation)) + Math.abs(Math.sin(currentOrientation)) * 4 * steps);
		return (getActionPoints() >= stepPoints) && (steps > 0);
	}

	/**
	 * @param steps
	 * 		The amount of steps to be moved.
	 * @throws IllegalArgumentException
	 * 		If the amount of steps is smaller than 0.
	 * 		| (steps <= 0)
	 * @throws ExhaustionException
	 * 		If the worm does not have enough actionpoints.
	 * 		| (!canMove(steps))
	 */
	public void step(int steps) throws IllegalArgumentException,
	ExhaustionException {
		if (steps <= 0)
			throw new IllegalArgumentException();
		else {
			if (!canMove(steps))
				throw new ExhaustionException();
			else {
				for (int i = 0; i < steps; i++) {
					double currentOrientation = getOrientation();
					setPosX(getPosX() + Math.cos(currentOrientation)
							* getRadius());
					setPosY(getPosY() + Math.sin(currentOrientation)
							* getRadius());
					double targetAP = getActionPoints()
							- Math.abs(Math.cos(currentOrientation))
							- Math.abs(4 * Math.sin(currentOrientation));
					setActionPoints((int) Math.ceil(targetAP));
				}
			}
		}
		updateJumpData();
	}

	/**
	 * Adds a given angle to the current orientation of the worm.
	 * 
	 * Nominally, because part of orientation
	 * 
	 * @pre amount may not be too high or too low. New angle (== current angle +
	 *      amount) must remain smaller than Pi and larger than -Pi
	 *      | (-Math.PI <= (amount + getOrientation())) && ((amount + getOrientation()) <= Math.PI)
	 * @pre If the turning is active, you must have sufficient action points to
	 *      turn.
	 *      | if (active)
	 *      | 	((Math.abs(amount) / (2 * Math.PI)) * 60) <= getActionPoints()
	 * @param amount
	 * @param active
	 *      Is true if turning was active and thus subtracts action points.
	 */
	public void turn(double amount, boolean active) {
		double newOrientation = getOrientation() + amount;
		if (newOrientation > Math.PI)
			newOrientation -= 2*Math.PI;
		if (newOrientation < -Math.PI)
			newOrientation += 2*Math.PI;
		setOrientation(newOrientation);
		if (active) {
			double targetAP = getActionPoints() - (Math
					.abs(amount) / (2 * Math.PI)) * 60;
			setActionPoints((int) Math.ceil(targetAP));
		}
		updateJumpData();
	}

	/**
	 * 
	 * @post if the worm is not allowed to jump, the value of JumpLegal gets set
	 *       to false
	 *       | if (! canJump() )
	 *       | {
	 *       |	new.getJumpLegal() == false
	 *       | }
	 * @post if the worm is allowed
	 */
	private void updateJumpData() {
		if (!canJump()) {
			setJumpLegal(false);
			calculateJump();
		} else {
			setJumpLegal(true);
			calculateJump();
		}
	}

	/**
	 * 
	 * @return true when the orientation of the worm is valid (between 0 and pi)
	 *         and the worm has action points remaining 
	 *         | result == (isValidOrientation() && getActionPoints() > 0)
	 */
	public boolean canJump() {
		return (isValidOrientation() && (getActionPoints() > 0));
	}

	private void calculateJump() {
		if (isJumpLegal()) {
			double mass, gravity, speed, force, distance;
			mass = getMass();
			gravity = 9.80665;
			force = ((5 * getActionPoints()) + (mass * gravity));
			speed = (force / (mass * 2));
			setJumpSpeedX(speed * Math.cos(getOrientation()));
			setJumpSpeedY(speed * Math.sin(getOrientation()));
			setJumpTime((Math.abs((2 * getJumpSpeedY())) / gravity));
			distance = getJumpTime() * getJumpSpeedX();
			setJumpX(getPosX() + distance);
			setJumpY(getPosY());
			return;
		} else { /*I left JumpSpeed unaltered because it is not used if jump is illegal.*/
			setJumpTime(0);
			setJumpX(getPosX());
			setJumpY(getPosY());
			setJumpSpeedX(0);
			return;
		}
	}

	/**
	 * 
	 * @throws ExhaustionException
	 * 		if the worm no longer has any actionpoitns.
	 * @post if jump is not legal, none of the coordinates should have changed. The worms actionpoints should be 0.
	 *       | if (! isJumpLegal() ) {
	 *       | 	new.getPosX == old.getPosX
	 *       | 	new.getPosY == old.getPosY
	 *       |  new.getActionPoints() == 0
	 *       | }
	 * @post if the jump was legal the new coordinates should be set to the
	 *       jumpcoordinates, and the actionpoints should be 0
	 *       | if (isJumpLegal() ) {
	 *       | 	new.getPosX() == this.getJumpX()
	 *       | 	new.getPosY() == this.getJumpY()
	 *       |  new.getActionPoints() == 0
	 *       | }
	 */
	public void jump() throws ExhaustionException, IllegalStateException {
		if (!isJumpLegal()) {
			if (isValidOrientation())
				throw new IllegalStateException();
			else
				throw new ExhaustionException();
		} else {
			setPosX(getJumpX());
			setActionPoints(0);
		}
		updateJumpData();
	}

	 /**
	 * Mandatory 'jumpTime' function as mentioned in the assignment.
	 * @return the value of jumptime
	 * 		| result == getJumpTime()
	 */
	 public double jumpTime() {
	 return getJumpTime();
	 }

	/**
	 * 
	 * @param time
	 * 		the timestamp for which the coordinates of the worm is wanted
	 * @pre the requested time shall be within the range of the jumptime, thus between 0 and jumpTime().
	 *      | time >= 0 && time <= jumpTime()
	 * @return	the coordinates of the worm at the requested time
	 * 			| result = {(getPosX() + (time * getJumpSpeedX())),
	 * 			|	(getPosY() + (time * (getJumpSpeedY() - time * 9.80665)/2))}
	 */
	public double[] jumpStep(double time) {
		double x, y;
		x = getPosX() + (time * getJumpSpeedX());
		y = getPosY() + (time * (getJumpSpeedY() - ((time * 9.80665) / 2)));
		double coordinates[] = { x, y };
		return coordinates;
	}
}
