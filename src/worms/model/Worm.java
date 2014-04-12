package worms.model;

import worms.ExhaustionException;
import worms.entities.*;
import worms.weapons.*;
import worms.containment.*;
import be.kuleuven.cs.som.annotate.*;

import java.util.ArrayList;

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
 * 
 * dummy implemented cycling through weapons (python code to work with arrays)
 * 
 * #
 * #10/04 JOREN
 * #
 * 
 * Changed the weapons array to an arraylist
 * Simplified the cycle method a lot
 * changed the storing of the current equipped weapon to storing just the index.
 * added necessary setters and getters.
 * 
 * 
 * #
 * #10/04 MILAN
 * #
 * 
 * 
 * fixed typos (in the code and the documentation);
 * Implemented setEquipped;
 * Implemented the eat() method;
 * Implemented position things in world and entity.
 * 
 * #
 * #12/04 JOREN
 * #
 * 
 * Added:
 *  F/UF	addition      		Class
 * 	
 * 	UF		jump() 				movable
 * 			jump()				worm
 * 			jump()				projectile
 * 			isLegalTime() 		movable
 * 	UF		canJump()			movable
 * 	UF		jumpStep()			movable
 * 			CONSTRUCTOR(2nd)	World
 * 			isLegalSize()		World
 * 	UF		exception			IllegalSizeException --- MILAN: work your magic please
 * 	UF		exception			TooManyProjectilesException --- MILAN: work your magic please
 * 			isLegalMap()		World
 * 
 * World:
 * 		worked some stuff on the constructor
 * 			Added this.projectile = null
 * 			arraylistrelated stuff
 * 		isLegalSize seems to be finished.
 * 		added a second constructor that calls with default values
 * 	    world now stores the gravity constant for that world.	
 * 		have started adding the basic stuffs for the map and checking if maps are legal or not
 */



/**
 * TA QUESTIONS
 * 
 * thought this might not be to bad an idea, you think of something we should ask the TA, just type it down here
 * 
 * JOREN: does the construction of an arraylist have to happen in the constructor of the object that stores that arraylist
 * JOREN: can we "assume" that the passablemap is a square, IE for every double[] in passablemap can we assume that it is the same length?
 * 				NOTE: writing checker anyways, becuase why the hell not.
 * 
 * 
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
public class Worm extends Movable {

	public Worm(String name, double posX, double posY, double radius,
			double direction, World world) {
		setPosX(posX);
		setPosY(posY);
		setRadius(radius);
		setOrientation(direction);
		setActionPoints(getMaxActionPoints());
		setName(name);
		updateJumpData();
		this.world = world;
		setDensity(1062);
	}

	private long ActionPoints, HitPoints;
	private String name;

	
	private int currentWeapon;
	private ArrayList<Weapon> inventory; //TODO set array getters and setters, arraylist has to be added to constructor
	private final World world;

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
	
	/**
	 * 
	 * @return	the mass calculated from radius
	 * 			| result == 1062 * ((4.0 / 3.0) * Math.PI * Math.pow(getRadius() , 3)
	 */
	public double getMass() {
		return getDensity() * (double)(4.0/3.0) * Math.PI * (Math.pow(getRadius(), 3));
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
	 * 		the new name contains Illegal characters or is to short.
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
	 * This the actual weapon in inventory.
	 * @return the currently equipped weapon
	 */
	public Weapon getEquipped() {
		return inventory.get(currentWeapon);
	}
	
	/**
	 * @post The currently equipped weapon is set to the given.
	 */
	public void setEquipped(Weapon weapon) {
		setCurrentWeapon(inventory.indexOf(weapon));
	}

	/**
	 * This is the index of Equipped in inventory.
	 */
	public int getCurrentWeapon(){
		return this.currentWeapon;
	}
	
	public void setCurrentWeapon(int index) {
		currentWeapon = index;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * 
	 * @param radius
	 * 		the radius of which the validity is to be checked
	 * @return true is the radius is greater or equal to 0.25
	 * 		| result == (radius >= 0.25)
	 */
	public boolean isValidRadius(double radius) {
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
		return name.matches("[A-Z][a-zA-Z0-9\\s'\"]+");
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
	}
	/**
	 * 
	 * @return true when the orientation of the worm is valid (between 0 and pi)
	 *         and the worm has action points remaining 
	 *         | result == (isValidOrientation() && getActionPoints() > 0)
	 */
	public boolean canJump() {
		}

	private void calculateJump() {
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
		if(canJump()) {
			super.Jump(getActionPoints());
			setActionPoints(0);
		}
	}

	 /**
	 * Mandatory 'jumpTime' function as mentioned in the assignment.
	 * @return the value of jumptime
	 * 		| result == getJumpTime()
	 */
	 public double jumpTime() {
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
	 * public void grow(Food meal) {
	 * 		setRadius(getRadius()*1.1);
	 * 		meal.eat();
	 * }
	 */
	
	 /**
	  * The cycle function makes the worm equip the next weapon in its inventory
	  * @post the new equipped weapon is the next one in the inventory array
	  * 	(or the first if the previously equipped weapon was the last)
	  * 	  | new.getCurrentWeapon == (this.getCurrentWeapon() + 1) % inventory.size()
	  */
	public void cycle() {
		int maxIndex = inventory.size();
		setCurrentWeapon((getCurrentWeapon() + 1)%maxIndex);
	}
	
	/**
	 * The eat method queries the foods in the world whether the worm can eat any and, if so, does.
	 */
	public void eat() {
		Food[] foods = getWorld().allFood();
		for (Food food : foods) {
			if (getWorld().distance(this, food) < (0.2 + getRadius())) {
				food.eat(); //or, grow(food);
				grow();
			}
		}
	}
	
	public void shoot(int yield) {
		long APcost = 999999999;
		if (getEquipped().getName() == "Rifle")
			APcost = 10;
		if (getEquipped().getName() == "Bazooka")
			APcost = 50;
		if (isValidActionPoints(getActionPoints()-APcost))
			getEquipped().shoot(yield);
			setActionPoints(getActionPoints()-APcost);
	}
}
