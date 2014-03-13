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
 * @date 2/03/2014
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

	// all position stuff Defensively
	@Basic
	/**
	 * @return 
	 * 		| result == this.posX
	 */
	public double getPosX() {
		return this.posX;
	}

	/**
	 * 
	 * @param position
	 * @post | new.getPosX() == position
	 */
	public void setPosX(double position) {
		this.posX = position;
		updateJumpData();
	}

	@Basic
	/**
	 * 
	 * @return
	 * 		| result == this.posY
	 */
	public double getPosY() {
		return this.posY;
	}

	/**
	 * 
	 * @param position
	 * @post | new.getPosY() == position
	 */
	public void setPosY(double position) {
		this.posY = position;
		updateJumpData();
	}

	@Basic
	/**
	 * 
	 * @return
	 * 		| result == this.radius
	 */
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
	 *             the given radius is not a legal radius
	 *             | ! isValidRadius(radius)
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (!isValidRadius(radius)) {
			throw new IllegalArgumentException();
		} else {
			this.radius = radius;

		}
	}

	/**
	 * Note: If mass is frequently used, store in variable and edit whenever you
	 * alter radius
	 * 
	 * @return	the mass calculated from radius
	 * 			| result == 1062 * ((4 / 3) * Math.PI * Math.pow(getRadius() , 3)
	 */
	public double getMass() {
		return 1062 * ((4 / 3) * Math.PI * Math.pow(getRadius(), 3));
	}

	// end defensive

	// all actionpoint stuff total

	/**
	 * 
	 * @return	the maximum amount of actionpoints this worm can have
	 * 			| result == (int)Math.round(getMass())
	 */
	public int getMaxActionPoints() {
		return (int) Math.round(getMass());
	}

	@Basic
	/**
	 * 
	 * @return the current amount of actionpoints of the worm.
	 * 		| result == this.ActionPoints
	 */
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

	// Make private?

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

	// end total

	// direction nominally

	@Basic
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * @pre .. | (0 <= orientation) && (orientation <= 2*Math.PI)
	 * @param orientation
	 */
	public void setOrientation(double orientation) {
		this.orientation = orientation;
		updateJumpData();
	}

	// end nominally

	public String getName() {
		return this.name;
	}

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

	private boolean isValidRadius(double radius) {
		return (radius >= 0.25);
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

	private boolean containsLegalChars(String name) {
		int length = name.length();
		for (int i = 0; i < length; i++) {
			if (!validchar.contains(name.subSequence(i, i + 1))) {
				return false;
			}
		}
		return true;
	}

	@Immutable
	public static int roundUp(double a) {
		int b = (int) Math.round(a);
		if (a - b < 0){
			return b;
		}
		return (b + 1);
	}

	// defensively because position
	/**
	 * Defensive because part of position
	 * TODO: Documentation
	 * @param steps
	 * @throws IllegalArgumentException
	 * @throws ExhaustionException
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
					setActionPoints((int) Math.round(targetAP));
				}
			}
		}
		updateJumpData();
	}

	/**
	 * TODO: Documentation
	 * @param steps
	 * @return
	 */
	public boolean canMove(int steps) {
		double currentOrientation = getOrientation();
		double stepPoints = (getActionPoints()
				- Math.abs(Math.cos(currentOrientation))
				- Math.abs(Math.sin(currentOrientation)) * 4);
		return (getActionPoints() >= steps * stepPoints)
				&& (getActionPoints() > 0);
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
		if (newOrientation > 2 * Math.PI)
			newOrientation -= 2 * Math.PI;
		if (newOrientation < -2 * Math.PI)
			newOrientation += 2 * Math.PI;
		//newOrientation %= 2*Math.PI; ? does modulo work with double? Does it work with negatives the way we want to?
		setOrientation(newOrientation);
		if (active) {
			double targetAP = getActionPoints() - (Math
					.abs(amount) / (2 * Math.PI)) * 60;
			setActionPoints((int) Math.round(targetAP));
		}
		updateJumpData();
	}

	/**
	 * 
	 * @post if the worm is not allowed to jump, the value of JumpLegal gets set
	 *       to false
	 *       | if (! canJump() ) {new.getJumpLegal() == false}
	 * @post if the worm is allowed to jump, the values of jump will be
	 */
	private void updateJumpData() {
		if (!canJump()) {
			setJumpLegal(false);
			calculateJump();
			// return; What's the point?
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

	/**
	 * 
	 * @return true when the orientation of the worm is smaller than pi.
	 *         | result == (getOrientations < Math.PI)
	 */
	private boolean isValidOrientation() {
		return ((getOrientation() < Math.PI) && (getOrientation() > 0));
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
	 * @post if jump is not legal, none of the coordinates should have changed
	 *       | if (! isJumpLegal() ) {
	 *       | 	new.getPosX == old.getPosX
	 *       | 	new.getPosY == old.getPosY
	 *       | }
	 * @post if the orientation was valid, the actionpoints should be set to 0
	 *       | if (isValidOrienations()) {
	 *       | 	new.getActionPoints() == 0
	 *       | }
	 * @post if the jump was legal the new coordinates should be set to the
	 *       jumpcoordinates
	 *       | if (isJumpLegal() ) {
	 *       | 	new.getPosX() == this.getJumpX()
	 *       | 	new.getPosY() == this.getJumpY()
	 *       | }
	 */
	public void jump() throws ExhaustionException, IllegalStateException {
		updateJumpData();
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

	// /**
	// * Does this function have a point?
	// * @return the value of jumptime
	// * | result == getJumpTime()
	// */
	// public double jumpTime() {
	// return getJumpTime();
	// }

	/**
	 * 
	 * @param time
	 * @pre the requested time shall be within the range of the jumptime, in
	 *      other words
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
