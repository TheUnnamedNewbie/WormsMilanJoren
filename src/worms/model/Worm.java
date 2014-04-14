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
 * 	F		exception			IllegalSizeException --- MILAN: work your magic please
 * 	F		exception			TooManyProjectilesException --- MILAN: work your magic please
 * 			isLegalMap()		World
 * 	UF		canExist			World
 * 
 * 
 * World:
 * 		worked some stuff on the constructor
 * 			Added this.projectile = null
 * 			arraylistrelated stuff
 * 		isLegalSize seems to be finished.
 * 		added a second constructor that calls with default values
 * 	    world now stores the gravity constant for that world.	
 * 		have started adding the basic stuffs for the map and checking if maps are legal or not
 * 		edited isValidX and isValidY
 * 	
 * #
 * #11/04 JOREN
 * #
 * 
 * Added isPassableAt method to world
 * 
 * Finished (I think, but will have to check later since it's 6am and I don't trust my brain at this time of day) canExist
 * Good call, there were blatant errors in the code (eclipse gave me red flags)
 * 
 * on 14-4-2014 Milan wrote:
 * Made getters/setter for World.worms, World.foods and Worms.inventory
 * fixed exceptions
 * implemented jump (it should work, but tell me if you see anything)
 */


/**
 * MAIN TODO
 * 
 * World: Make cellWidth and cellHeight constants?
 */


/**
 * TA QUESTIONS
 * 
 * thought this might not be to bad an idea, you think of something we should ask the TA, just type it down here
 * 
 * JOREN: does the construction of an arraylist have to happen in the constructor of the object that stores that arraylist
 * JOREN: can we "assume" that the passablemap is a square, IE for every double[] in passablemap can we assume that it is the same length?
 * 				NOTE: writing checker anyways, becuase why the hell not.
 * JOREN: Maybe it's not a bad Idea to turn cellWidth and cellHeight in world into constants?
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
		this.world = world;
		setDensity(1062);
	}

	private double radius;
	private long ActionPoints, HitPoints;
	private String name;

	
	private int currentWeapon;
	private ArrayList<Weapon> inventory;
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
	
	//Begin Inventory stuff
	
	/**
	 * Return the weapon of this worm at the given index.
	 * 
	 * @param  index
	 *         The index of the weapon to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of inventory of this worm.
	 *       | (index < 1) || (index > getNbWeapons())
	 */
	@Basic
	public Weapon getWeaponAt(int index) throws IndexOutOfBoundsException {
		return inventory.get(index);
	}
	
	/**
	 * Return the number of inventory of this worm.
	 */
	@Basic
	public int getNbWeapons() {
		return inventory.size();
	}
	
	/**
	 * Check whether this worm can have the given weapon
	 * as one of its inventory.
	 * 
	 * @param  weapon
	 *         The weapon to check.
	 * @return True if and only if the given weapon is effective, and
	 *         if that weapon can have this worm as its worm.
	 *       | result ==
	 *       |   (weapon != null) &&
	 *       |   weapon.canHaveAsWorm(this)
	 */
	public boolean canHaveAsWeapon(Weapon weapon) {
		return (weapon != null);
	}
	
	/**
	 * Check whether this worm can have the given weapon
	 * as one of its inventory at the given index.
	 * 
	 * @param  weapon
	 *         The weapon to check.
	 * @param  index
	 *         The index to check.
	 * @return False if the given index is not positive or exceeds
	 *         the number of inventory of this worm + 1.
	 *       | if ( (index < 1) || (index > getNbWeapons()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this worm cannot have the
	 *         given weapon as one of its inventory.
	 *       | else if (! canHaveAsWeapon(weapon))
	 *       |   then result == false
	 *         Otherwise, true if and only if the given weapon is
	 *         not already registered at another index.
	 *       | else result ==
	 *       |   for each I in 1..getNbWeapons():
	 *       |     ( (I == index) || (getWeaponAt(I) != weapon) )
	 */
	public boolean canHaveAsWeaponAt(Weapon weapon, int index) {
		if ((index < 1) || (index > getNbWeapons() + 1))
			return false;
		if (!canHaveAsWeapon(weapon))
			return false;
		for (int pos = 1; pos <= getNbWeapons(); pos++)
			if ((pos != index) && (getWeaponAt(pos) == weapon))
				return false;
		return true;
	}
	
	/**
	 * Check whether this worm has a proper arraylist of weapons.
	 * 
	 * @return True if and only if this worm can have each of its
	 *         inventory at their index, and if each of these inventory
	 *         references this worm as their owner.
	 *       | for each index in 1..getNbWeapons():
	 *       |   canHaveAsWeaponAt(getWeaponAt(index),index) &&
	 *       |   (getWeaponAt(index).getWeapon() == this)
	 */
	public boolean hasProperWeapons() {
		for (int index = 1; index <= getNbWeapons(); index++) {
			if (!canHaveAsWeaponAt(getWeaponAt(index), index))
				return false;
			if (getWeaponAt(index).getWorm() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Check whether this worm has the given weapon as one of
	 * its weapon.
	 *
	 * @param  weapon
	 *         The weapon to check.
	 * @return True if and only if this worm has the given weapon
	 *         as one of its inventory at some index.
	 *       | result ==
	 *       |   for some index in 1..getNbWeapons():
	 *       |     getWeaponAt(index).equals(weapon)
	 */
	public boolean hasAsWeapon(Weapon weapon) {
		return inventory.contains(weapon);
	}
	
	/**
	 * Return the index at which the given weapon is registered
	 * in the list of weapons for this worm.
	 *  
	 * @param  weapon
	 *         The weapon to search for.
	 * @return If this worm has the given weapon as one of its
	 *         inventory, that weapon is registered at the resulting
	 *         index. Otherwise, the resulting value is -1.
	 *       | if (hasAsWeapon(weapon))
	 *       |    then getWeaponAt(result) == weapon
	 *       |    else result == -1
	 */
	public int getIndexOfWeapon(Weapon weapon) {
		return inventory.indexOf(weapon);
	}
	
	/**
	 * Return a list of all the inventory of this worm.
	 * 
	 * @return The size of the resulting list is equal to the number of
	 *         inventory of this worm.
	 *       | result.size() == getNbWeapons()
	 * @return Each element in the resulting list is the same as the
	 *         weapon of this worm at the corresponding index.
	 *       | for each index in 0..result-size()-1 :
	 *       |   result.get(index) == getWeaponAt(index+1)
	 */
	public ArrayList<Weapon> getAllWeapons() {
		return new ArrayList<Weapon>(inventory);
	}
	
	/**
	 * Add the given weapon at the end of the arraylist of
	 * inventory of this worm.
	 * 
	 * @param  weapon
	 *         The weapon to be added.
	 * @pre    The given weapon is effective and already references
	 *         this worm as its worm.
	 *       | (weapon != null) && (weapon.getWorm() == this)
	 * @pre    This worm does not not yet have the given weapon
	 *         as one of its inventory.
	 *       | ! hasAsWeapon(weapon)
	 * @post   The number of inventory of this worm is incremented
	 *         by 1.
	 *       | new.getNbWeapons() == getNbWeapons() + 1
	 * @post   This worm has the given weapon as its new last
	 *         weapon.
	 *       | new.getWeaponAt(getNbWeapons()+1) == weapon
	 */
	public void addAsWeapon(Weapon weapon) {
		assert (weapon != null) && (weapon.getWorm() == this);
		assert !hasAsWeapon(weapon);
		inventory.add(weapon);
	}
	
	/**
	 * Remove the given weapon from the inventory of this worm.
	 * 
	 * @param  weapon
	 *         The weapon to be removed.
	 * @pre    The given weapon is effective and does not have any
	 *         worm.
	 *       | (weapon != null) && (weapon.getWorm() == null)
	 * @pre    This worm has the given weapon as one of
	 *         its inventory.
	 *       | hasAsWeapon(weapon)
	 * @post   The number of inventory of this worm is decremented
	 *         by 1.
	 *       | new.getNbWeapons() == getNbWeapons() - 1
	 * @post   This worm no longer has the given weapon as
	 *         one of its inventory.
	 *       | (! new.hasAsWeapon(weapon))
	 * @post   All inventory registered beyond the removed weapon
	 *         shift one position to the left.
	 *       | for each index in getIndexOfWeapon(weapon)+1..getNbWeapons():
	 *       |   new.getWeaponAt(index-1) == getWeaponAt(index) 
	 */
	public void removeAsWeapon(Weapon weapon) {
		assert (weapon != null) && (weapon.getWorm() == null);
		assert (hasAsWeapon(weapon));
		inventory.remove(weapon);
	}
	
	//End Inventory stuff
	
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
	public void jump(double timestep) throws ExhaustionException, IllegalStateException {
		double[] target = jumpStep(getActionPoints(), jumpTime(getActionPoints(), timestep));
		setPosX(target[0]); setPosY(target[1]);
		setActionPoints(0);
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
		ArrayList<Food> foods = getWorld().getAllFoods();
		for (Food food : foods) {
			if (getWorld().distance(this, food) < (0.2 + getRadius())) {
				grow();
				getWorld().removeAsFood(food);
			}
		}
	}
	
	public void shoot(int yield) {
		long APcost = 999999999; //What's the max positive integer again?
		if (getEquipped().getName() == "Rifle") //work with getClass() things
			APcost = 10;
		if (getEquipped().getName() == "Bazooka")
			APcost = 50;
		if (isValidActionPoints(getActionPoints()-APcost))
			getEquipped().shoot(yield);
			setActionPoints(getActionPoints()-APcost);
	}
	
	public void damage(long amount) {
		//TODO implement
		// This kills the worm and makes the world remove this
	}
}
