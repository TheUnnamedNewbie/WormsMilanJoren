package worms.model;

import worms.ExhaustionException;
import worms.weapons.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * CHANGELOG (or, the temp commit text)
 * 
 * Made AP and HP work with long instead of int (only the typecast in math.ceil remains)
 * 
 * Implemented HP
 * 
 * Fixed the AP out of bounds bug on resize. Waiting on working GUI to fully test
 * Also went ahead and made the HP ratio remain constant (do you agree?)
 * 
 * Implemented the grow() function (please read doc for unanswered Qs)
 * 
 * Implemented weapons (Equipped with set and get)
 * 
 * dummy implemented cycling through weapons (python code to work with arrays)
 */

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
		setRadius(radius); //Q: Need to add a isvalidradius? (radius should not be set under 0.25)
						   //A: no, setRadius does this already
		setOrientation(direction);
		setActionPoints(getMaxActionPoints());
		setName(name);
		updateJumpData();
	}

	private double posX;
	private double posY;
	private double radius;
	private long ActionPoints;
	private long HitPoints;
	private double orientation;
	private String name;

	private double jumpX;
	private double jumpY;
	private double jumpTime;
	private boolean jumpLegal;
	private double jumpSpeedX;
	private double jumpSpeedY;
	private Weapon equipped;
	private Weapon[] inventory; //TODO set array getters and setters

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
	 * @post the worm's X coordinate will be the given value
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
	 * @post the worm's Y coordinate will be the given value
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
	 * @post The ratio of AP to maxAP remains the same.
	 * 			   | this.getActionPoints() // this.getMaxActionPoints() == new.getActionPoints() // new.getMaxActionPoints()
	 * @post The ratio of HP to maxHP remains the same.
	 * 			   | this.getHitPoints() // this.getMaxHitPoints() == new.getHitPoints() // new.getMaxHitPoints()
	 * @throws IllegalArgumentException
	 *             the given radius is not a legal radius.
	 *             | ! isValidRadius(radius)
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (!isValidRadius(radius)) {
			throw new IllegalArgumentException();
		} else {
			double APratio = (double) getActionPoints()/getMaxActionPoints();
			double HPratio = (double) getHitPoints()/getMaxHitPoints();
			this.radius = radius;
			setActionPoints(Math.round(getMaxActionPoints() * APratio));
			setHitPoints(Math.round(getMaxHitPoints() * HPratio));
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
	 * 			| result == Math.round(getMass())
	 */
	public long getMaxActionPoints() {
		return Math.round(getMass());
	}
	
	/**
	 * 
	 * @return	the maximum amount of hitpoints this worm can have
	 * 			| result == Math.round(getMass())
	 */
	public long getMaxHitPoints() {
		return Math.round(getMass());
	}

	/**
	 * 
	 * @return the current amount of actionpoints of the worm.
	 * 		| result == this.ActionPoints
	 */
	@Basic
	public long getActionPoints() {
		return ActionPoints;
	}

	/**
	 * 
	 * @param points
	 *            the amount of points you want to set the worms hitpoints to
	 * @post if the amount of hitpoints was a valid amount, the new value of
	 *       hitpoints of this worm shall be equal to it
	 *       | if (isValidHitPoints(points)) {new.getHitPoints() == points}
	 */
	private void setHitPoints(long points) {
		if (isValidHitPoints(points))
			HitPoints = points;
	}
	
	/**
	 * 
	 * @return the current amount of hitpoints of the worm.
	 * 		| result == this.HitPoints
	 */
	@Basic
	public long getHitPoints() {
		return HitPoints;
	}

	/**
	 * 
	 * @param points
	 *            the amount of points you want to set the worms actionpoints to
	 * @post if the amount of actionpoints was a valid amount, the new value of
	 *       actionpoints of this worm shall be equal to it
	 *       | if (isValidActionPoints(points)) {new.getActionPoints() == points}
	 */
	private void setActionPoints(long points) {
		if (isValidActionPoints(points))
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
	 * @return the currently equipped weapon
	 */
	public Weapon getEquipped() {
		return equipped;
	}
	
	/**
	 * @post The currently equipped weapon is set the given.
	 */
	public void setEquipped(Weapon weapon) {
		this.equipped = weapon;
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
	 *            the amount of actionpoints of which you want to know if it is a
	 *            valid amount
	 * @return true if the given value is greater or equal to zero and less than
	 *         or equal to the max value of actionpoints for this worm
	 *         | result == ((points >= 0) && (points <= getMaxActionPoints())
	 */
	public boolean isValidActionPoints(long points) {
		return ((points >= 0) && (points <= getMaxActionPoints()));
	}
	
	/**
	 * This method is functionally equal to isValidActionPoints,
	 * 	but this is adaptable if criteria would change to the validity of either AP or HP.
	 * @param points
	 *            the amount of hitpoints of which you want to know if it is a
	 *            valid amount
	 * @return true if the given value is greater or equal to zero and less than
	 *         or equal to the max value of hitpoints for this worm
	 *         | result == ((points >= 0) && (points <= getMaxHitPoints())
	 */
	public boolean isValidHitPoints(long points) {
		return ((points >= 0) && (points <= getMaxHitPoints()));
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
					setActionPoints((long) Math.ceil(targetAP));
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
			setActionPoints((long) Math.ceil(targetAP));
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
	
	/**
	 * The grow function increases the radius of the worm by 10%.
	 * 
	 * Q: We need to destroy the food at some point, how do we do this?
	 * 		Do we tell the worm to tell the food to destroy itself.
	 * 		Thus making the food tell the world that it is destroyed.
	 * 		If so, have grow() be grow(Food meal) and end with meal.eat() or smth like that.
	 * TODO answer Q
	 * @post The radius will be 1.1 times it's original size.
	 * 		| new.getRadius() == 1.1*this.getRadius()
	 *
	 * Q: Do we need to explicitly say by how much AP, HP, maxAP and maxHP have gone up? I think this suffices.
	 * TODO answer Q
	 * @post The ratio of AP to maxAP remains the same.
	 * 			   | this.getActionPoints() // this.getMaxActionPoints() == new.getActionPoints() // new.getMaxActionPoints()
	 * @post The ratio of HP to maxHP remains the same.
	 * 			   | this.getHitPoints() // this.getMaxHitPoints() == new.getHitPoints() // new.getMaxHitPoints()
	 * 
	 */
	public void grow() {
		setRadius(getRadius()*1.1);
	}
	
	/**
	 * or:
	 * public void grow() {
	 * 		setRadius(getRadius()*1.1);
	 * 		meal.eat();
	 * }
	 */
	
	 /**
	  * The cycle function makes the worm equip the next weapon in its inventory
	  * @post the new equipped weapon is the next one in the inventory array
	  * 	(or the first if the previously equipped weapon was the last)
	  * 	  | if(inventory.length() > 1) {
	  * 	  |	 	if (inventory.getIndex(this.getEquipped()) != len(inventory)-1)
	  * 	  |	 		inventory.getIndex(this.getEquipped()) + 1 == inventory.getIndex(new.getEquipped())
	  * 	  |	 	else
	  * 	  |	 		inventory.getIndex(new.getEquipped()) == 0
	  * 	  | }
	  */
	public void cycle() {
		int nbWeapons = inventory.length();
		if (nbWeapons > 1) {
			int currentIndex = inventory.getIndex(getEquipped());
			if (currentIndex != inventory.length()-1)
				setEquipped(inventory[currentIndex+1]);
			else
				setEquipped(inventory[0]);
		}
	}
}
