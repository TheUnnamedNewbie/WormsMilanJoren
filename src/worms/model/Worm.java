package worms.model;

import worms.CoordinateOutOfBoundsException;
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
 * 	F		exception			IllegalSizeException
 * 	F		exception			TooManyProjectilesException
 * 			isLegalMap()		World
 * 	UF		canExist			World
 * 
 * 
 * World:
 * 		worked some stuff on the constructor
 * 			Added this.projectile = null
 * 			arraylist related stuff
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
 * 
 * 15-4-2014:
 * overloaded distance to work w/ coordinate arrays
 * Made things collide with worms.
 * Made worms eat after jump()
 * set and position through use of coordinate array (Entity)
 * Implemented step() with use of isAdjacent.
 * Made collides() require a radius for use with isAdjacent (the radius*1.1 would otherwise require the creation of new object)
 * Moved collides() to Entity and made static (fixed implementations of collides())
 * implemented Worm.damage()
 * Created Movable.canJump()
 * Implemented fall()
 * Edited canExist to work with coordinates and radius
 * Renamed Radius to radius in Entity and thus fixed all the redefining of radius
 * made cost a field in weapon
 * initialized the worm with a rifle and a bazooka
 * started on team:
 * 		Made getters/setters for members
 * 		Made Worm be able to join a team
 * 		Checks name of team (IllArgExc)
 * 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// * !!IMPORTANT TO UNDERSTAND Placed the origin at bottom left and defined y-axis positive upward to prevent weird stuff with:	//
// * (the length of the list is reasons why)																					//
// * angles (clockwise == counterclockwise)																						//
// * positions (up is down)																										//
// * 		falling (this isn't VVVVVV)																							//
// * 		Jumping (g = -9.8)																									//
// * 		Moving																												//
// * 		And God knows what else...																							//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 * Basically, instead of using passableMap[y][x] (oh yeah, another thing. The y coordinate is first) instead use getBoolAt(int x, int y)
 * this first rows (y) then columns (x) thing gave weird shit at defining cellwidth and cellheight. I fixed it.
 * 
 * Implemented random worm and food creation and their helper methods (getRandomPosition(radius) and getMaxPosition(angle))
 * Implemented winners and linked to facade (getWinner and isGameFinished==true if there is one)
 */


/**
 * MAIN TODO
 * 
 * 
 * Facade: jump for projectile and worm can be condensed to one jump for movable, or am I overlooking something obvious here?
 * 
 * 
 * World: Make cellWidth and cellHeight constants?
 * 
 * FIX:
 * 		worms.gui.GameState 						
 * 				LN 53 - a method call on facade.createWorm gives an error. 
 * 		worms.gui.game.GameState					
 * 				LN 60 - a method call to getCommandStack gives errors 
 * 		worms.gui.game.PlayGameScreenDebugPainter	
 * 				LN 36 - a getJumpTime method fails (calles on a object of the facade class)
 * 		worms.gui.game.PlayGameScreenPainter		
 * 				LN 216 - same getJumpTime as above.
 * 		worms.gui.game.Screen 									
 * 				- Holymotherofgod....
 * 		worms.gui.game.WormsGUI						
 * 				LN 57 - Undefined constructor
 * 		worms.gui.game.WormsGUI 					
 * 				LN 58 - switch to screen is not aplicable to menuscreen
 * 		worms.gui.game.commands.Jump 				
 * 				LN 38 - getJumpTime
 *				LN 47 - jump not applicable to worms
 *		worms.gui.game.commands.Move 				
 *				LN 31 - canMove not applicable to worms
 * 				LN 66 - getFacade().move not appliccable with arguments
 * 		worms.gui.game.game.PlayGameScreen
 * 				Toomannyerrors
 * 		worms.gui.game.game.PlayGameScreenDebugPainter
 * 				TooManyErrors
 * 		worms.gui.game.game.PlayGameScreenPainter
 * 				TooManyErrors
 * 		worms.gui.game.game.commands.Command
 * 				LN 30 - getWorld() not defined for type PlayGameScreen
 * 				LN 56 - updateSprites not defined for type PlayGameScreen
 *				LN 58 - gameFinished not defined for type PlayGameScreen
 *
 *
 */


/**
 * TA QUESTIONS
 * 
 * thought this might not be to bad an idea, you think of something we should ask the TA, just type it down here
 * 
 * JOREN: does the construction of an arraylist have to happen in the constructor of the object that stores that arraylist
 * JOREN: can we "assume" that the passablemap is a square *more like 'full'*, IE for every double[] in passablemap can we assume that it is the same length?
 * 				NOTE: writing checker anyways, because why the hell not.
 * JOREN: Maybe it's not a bad Idea to turn cellWidth and cellHeight in world into constants?
 * MILAN: Can you move through a worm? Not stand in one, just moving through.
 * MILAN: Exceptions, am I doing it right? With the serial and such.
 * 
 */
/**
 * 
 * WORMS!! Are violently erupting!
 * The class Worm contains all information and methods related to the
 * actual worms and their movements.
 * 
 * @invar The current (at stable point) action and hit points must remain valid
 *        | isValidActionPoints(getActionPoints())
 *        | isValidHitPoints(getHitPoints())
 * @invar The radius is always at least 0.25m, and never exceed Double.MAX_VALUE
 * 		  | getRadius() >= 0.25
 * 		  | getRadius() < Double.MAX_VALUE
 * @invar The worm has valid coordinates
 * @invar 
 * @author Milan Sanders
 * @author Joren Vaes
 * @date 18/03/2014
 * 
 */
public class Worm extends Movable {
	
	/**
	 * Creates a new worm with, given legal parameters, the requested values.
	 * @param name
	 * 		The name you wish to give your worm.
	 * @param posX
	 * 		The Position along the x axis you wish your worm to have (origin in the leftbottom corner)
	 * @param posY
	 * 		The Position along the y axis you wish your worm to have (origin in the leftbottom corner)
	 * @param radius
	 * 		The radius the worm should have
	 * 		| radius >= 0.25 && radius == Double.NaN && radius < Double.MAX_VALUE
	 * @param direction
	 * 		The direction in which you want your worm to start looking
	 * @param world
	 * 		The world in which the worm will be initialized
	 */
	public Worm(String name, double posX, double posY, double radius,
			double direction, World world) throws CoordinateOutOfBoundsException, IllegalArgumentException {
		this.world = world;
		setPosX(posX);
		setPosY(posY);
		setRadius(radius);
		setOrientation(direction);
		setName(name);
		setDensity(1062);
		setActionPoints(getMaxActionPoints());
		setHitPoints(getMaxHitPoints());
		inventory = new ArrayList<Weapon>();
		addAsWeapon(new Rifle(this));
		addAsWeapon(new Bazooka(this));
	}

	private long ActionPoints, HitPoints;
	private String name;
	private int currentWeapon;
	private ArrayList<Weapon> inventory;
	private Team team;

	/**
	 * 
	 * @param radius
	 *            The new radius for the worm
	 * @post The radius of the worm is now set the the given value radius
	 * 			   | new.getRadius() == radius
	 * @post the amount of AP remains the same, unless it violates the MaxAP
	 * 		 | if (this.getActionPoints() > new.getMaxActionPoints())
	 * 		 |	new.getActionPoints() == new.getMaxActionPoints()
	 * 		 | else
	 * 		 | 	this.getActionPoints() == new.getActionPoints()
	 * @post the amount of HP remains the same, unless it violates the MaxHP
	 * 		 | if (this.getHitPoints() > new.getMaxHitPoints())
	 * 		 |	new.getHitPoints() == new.getMaxHitPoints()
	 * 		 | else
	 * 		 | 	this.getHitPoints() == new.getHitPoints()
	 * @throws IllegalArgumentException
	 *             the given radius is not a legal radius.
	 *             | ! isValidRadius(radius)
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (!isValidRadius(radius)) {
			throw new IllegalArgumentException();
		} else {
			this.radius = radius;
			if (getActionPoints() > getMaxActionPoints())
				setActionPoints(getMaxActionPoints());
			if (getHitPoints() > getMaxHitPoints())
				setHitPoints(getMaxHitPoints());
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
	@Basic
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 * 		The new string the name of the worm is to be set to
	 * @throws IllegalArgumentException
	 * 		the new name contains Illegal characters or is to short.
	 * 		 | ! isValidName(name)
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (!isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	/**
	 * This the actual weapon in inventory.
	 * @return the currently equipped weapon
	 * 		| result == this.inventory.get(currentWeapon)
	 */
	public Weapon getEquipped() {
		return inventory.get(currentWeapon);
	}
	
	/**
	 * set the equipped weapon as 
	 * 
	 */
	public void setEquipped(Weapon weapon) {
		setCurrentWeapon(inventory.indexOf(weapon));
	}

	/**
	 * This is the index of Equipped in inventory.
	 * @return
	 * 		the index the current weapon is at
	 * 		| result == this.currentWeapon
	 */
	@Basic
	public int getCurrentWeapon(){
		return this.currentWeapon;
	}
	
	/**
	 * the index of the current weapon is set to the given
	 * @param index
	 * 		the value current weapon is to be set to
	 * @post 
	 * 		the value if the current weapon is set to index
	 * 		| new.getCurrentWeapon == index
	 */
	public void setCurrentWeapon(int index) {
		currentWeapon = index;
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
	 * Return the size of inventory of this worm.
	 * @return
	 * 		the size of the inventory stored in this worm.
	 * 		| result == this.inventory.size()
	 * 		
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
	 *       | if ( (index < 0) || (index > getNbWeapons()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this worm cannot have the
	 *         given weapon as one of its inventory.
	 *       | else if (! canHaveAsWeapon(weapon))
	 *       |   then result == false
	 *         Otherwise, true if and only if the given weapon is
	 *         not already registered at another index.
	 *       | else result ==
	 *       |   for each I in 0..getNbWeapons()-1:
	 *       |     ( (I == index) || (getWeaponAt(I) != weapon) )
	 */
	public boolean canHaveAsWeaponAt(Weapon weapon, int index) {
		if ((index < 0) || (index > getNbWeapons() + 1))
			return false;
		if (!canHaveAsWeapon(weapon))
			return false;
		for (int pos = 0; pos < getNbWeapons(); pos++)
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
		for (int index = 0; index < getNbWeapons(); index++) {
			if (!canHaveAsWeaponAt(getWeaponAt(index), index)){
				return false;
			}
			if (getWeaponAt(index).getWorm() != this) {
				return false;
			}
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
		if(!hasAsWeapon(weapon)) {
		}
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
	 * Makes the worm join the team, if the given team is legal.
	 * @param team
	 * 		The team the worm has to join, if it is a legal team
	 * @post
	 * 		if the team is a legal team (when isProperTeam(team) returns true), the worm will join the team
	 * 		else, nothing happens
	 *		| if ( isProperTeam(team) ) 
	 *		|		{team.hasAsWorm(this)
	 *		|		 this.getTeam() == team}
	 */
	public void join(Team team) {
		if (isProperTeam(team)) {
			team.addAsWorm(this);
			this.team = team;
		}
	}
	
	/**
	 * Check if the team is a legal team:
	 * 		the team shall not be a null object
	 * 		the team shall be part of the same world as the worm that askes for the propperness of the team
	 * @param team
	 * 		the team that is to be evaluated
	 * @return
	 * 		true if the team is not a null object, and when the team is in the same world as the worm that calls the method.
	 * 		| result == ( team != null ) && (team.getWorld() == this.getWorld)
	 */
	public boolean isProperTeam(Team team) {
		return (team != null && team.getWorld() == this.getWorld());
	}
	
	/**
	 * 
	 * @return
	 * 		Returns the team this worm is member of
	 * 		| result == this.team
	 * 
	 */
	@Basic
	public Team getTeam() {
		return this.team;
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
	 * Checks if the name starts with an uppercase letter
	 * and further only contains alphanumeric characters,
	 * single and double quotation marks and spaces.
	 * 
	 * This statement uses regular expressions and the 'formal' documentation tries to make this somewhat comprehendable.
	 * A bit of AppleScript syntax trickled through in the for loop...
	 * @param name The name that needs to be checked
	 * @return [A..Z].contains(name[0]) && (for every char in name[1..]: [a..zA..Z0..9\ \'\"].contains(char))
	 */
	private boolean isValidName(String name) {
		return name.matches("[A-Z][a-zA-Z0-9\\s'\"]+");
	}

	/**
	 * @param steps
	 * 		The amount of steps the worm should move
	 * @return
	 * 		True if the worm can move a step. 
	 * 		This means that the worm has enough actionpoints, every step takes (cos(getOrientation())) + 4*sin(getOrientation()).
	 *  	| result == (getActionPoints() >= (Math.abs(Math.cos(getOrientation())) + Math.abs(Math.sin(getOrientation())) * 4))
	 */
	public boolean canMove() {
		double currentOrientation = getOrientation();
		double stepPoints = (Math.abs(Math.cos(currentOrientation)) + Math.abs(Math.sin(currentOrientation)) * 4);
		return (getActionPoints() >= stepPoints);
	}
	
	/**
	 * The step method moves the worm to a fitting location as per the assignment
	 * (too much to explain in the comments)
	 * 
	 * Note: We basically look for the directions in which the worm can move the farthest
	 * and then we see which one of these directions has the lowest divergence.
	 * 
	 * minD is used to minimize the amount of calculations. No point in finding less ideal positions to move to, right?
	 * 
	 * @throws ExhaustionException Thrown when you do not have sufficient actionpoints to complete the step
	 * @post The worm has moved somewhere between 0.1 and getRadius() within the directional bounds getOrientation() +or- 0.7875
	 * 		 Or he hasn't moved at all (if !canMove()).
	 * @post If the worm is looking at the edge of the map and is standing 0.01 (==deltaD) from it, it will die.
	 * 		 This is something that only happens if the worm tried to step off the map, or it got really unlucky and ended up 0.01 away from the map
	 */
	public void step() throws ExhaustionException {
		if (canMove()) {
			double deltaT = 0.0175; //infinitesimal angle to see how far one can move.
			double maxT = 0.7875; // max divergence
			double minD = 0.1;
			double[] temp = new double[]{0.0, getOrientation()}; //temp is the furthest the worm can move and that direction
			for (double count = 0.0; count < maxT; count += deltaT) {
				double maxDistClockwise = maxDist(getOrientation() + count, minD);
				if (maxDistClockwise == getRadius()) {
					temp = new double[] {getRadius(), getOrientation() + count};
					break;
				} else {
					if (maxDistClockwise > temp[0]) {
						temp = new double[] {maxDistClockwise, getOrientation() + count };
						minD = maxDistClockwise;
					}
				}
				double maxDistCounterClockwise = maxDist(getOrientation() - count, minD);
				if (maxDistCounterClockwise == getRadius()) {
					temp = new double[] {getRadius(), getOrientation() - count };
					break;
				} else {
					if (maxDistCounterClockwise > temp[0]){
						temp = new double[] {maxDistCounterClockwise, getOrientation() - count };
						minD = maxDistCounterClockwise;
					}
				}
			}
			setPosX(getPosX() + Math.cos(temp[1]) * temp[0]);
			setPosY(getPosY() + Math.sin(temp[1]) * temp[0]);
			double[] coordinatesPlus = new double[]{getPosX()+Math.cos(getOrientation())*0.01,getPosY()+Math.sin(getOrientation())*0.01};
			if (!isValidPosition(getCoordinates()) || !this.getWorld().isLegalPosition(coordinatesPlus, this.getRadius()*1.1)) //The large circle shall prevail! Close enough?
				die();
			eat();
		} else throw new ExhaustionException();
	}
	
	/**
	 * maxDist returns the farthest a worm can move in a given direction
	 * @param angle
	 * @param minD
	 * @return
	 */
	public double maxDist(double angle, double minD) {
		double deltaD = 0.01; //infinitesimal distance to check how far you can move in every direction.
		for (double dist = getRadius(); dist > minD; dist -= deltaD) {
			double targetX = getPosX()+Math.cos(angle)*dist;
			double targetY = getPosY()+Math.sin(angle)*dist;
			if (getWorld().isAdjacent(new double[]{targetX, targetY}, this))
				return dist;
		}
		return 0.0;
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
		assert (Math.abs(amount + getOrientation()) <= 2 * Math.PI);
		assert isValidActionPoints((long)Math.ceil(getActionPoints()
				- (Math.abs(amount) / (2 * Math.PI)) * 60));
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
	 * @post if jump is not possible, none of the coordinates should have changed. The worms actionpoints should also remain unchanged.
	 *       | if (! canJump() ) {
	 *       | 	new.getPosX == old.getPosX
	 *       | 	new.getPosY == old.getPosY
	 *       |  new.getActionPoints() == this.getActionPoints()
	 *       | }
	 * @post if the jump was possible the new coordinates should be set to the
	 *       jumpstep at the jumptime, and the actionpoints should be 0
	 *       | if (isJumpLegal() ) {
	 *       | 	new.getCoordinates() == jumpStep(getActionPoints(), jumpTime(getActionPoints(), timestep))
	 *       |  new.getActionPoints() == 0
	 *       | }
	 * Because there is some penetration into impassable terrain after the jump
	 * We can use this as a means to see if the worm has jumped off the map.
	 */
	public void jump(double timestep) throws ExhaustionException, IllegalStateException {
		if (getActionPoints() <= 0) {
			throw new ExhaustionException();
		}
		else if(getActionPoints() >= 0 && canJump()){
			double[] target = jumpStep(getActionPoints(), jumpTime(getActionPoints(), timestep));
			setPosX(target[0]); setPosY(target[1]);
			setActionPoints(0);
			eat();
		} else if (!this.getWorld().isLegalPosition(this.getCoordinates(), this.getRadius())) {
			die();
		}
	}
	
	/**
	 * checks to see if it can fall, makes it fall and damages it accordingly
	 * NOTE: A worm can never legally fall.
	 * @post
	 * 		if the worm can fall (canFall == true) the worm will fall for the amount calculated by fallTime(). 
	 *		the worm will then end in Y position that is fallDist(falltime) lower than the original y coordinate.
	 */
	public void fall() {
		if (canFall()) {
			double fallTime = fallTime();
			if (fallTime == Double.MAX_VALUE) {
				System.out.println("Falltime= "+ fallTime);
				die();
			} else {
				double fallDist = Math.abs(getPosY() - fallDist(fallTime));
				damage((long) Math.floor(fallDist) * 3);
				setPosY(getPosY() - fallDist);
			}
		}
		if (!this.getWorld().isLegalPosition(this.getCoordinates(), this.getRadius())) {
			die();
		}
	} 
	
	/**
	 * The distance (positive) a worm falls after a given time.
	 * @param time The time at which point you want to check the fallen distance
	 * @return the distance fallen after a amount of time
	 * 		| result == ( 0.5 * World.GRAVITY * time * time)
	 */
	public double fallDist(double time) {
		 this.getWorld(); // WHY is this here?
		return ((1.0/2.0) * World.GRAVITY * time * time);
	}
	
	/**
	 * The time for how long a worm can fall before hitting impassable terrain
	 * @return
	 */
	public double fallTime() {	
		double timestep = 0.01;
		double time = 0.0;
		while (true) {
			double target = getPosY() - fallDist(time);
			if (!isValidPosition(getPosX(), target))
				return Double.MAX_VALUE;
			if (!isValidPosition(getPosX(), target) || getWorld().isAdjacent(new double[]{getPosX(), target}, this) || (collides(new double[]{getPosX(),target}, getRadius())))
				return time;
			time += timestep;
		}
	}
	
	/**
	 * 
	 * @return
	 * 		true if the worm has no adjacent terrain and it doesn't collide in it's current position.
	 * 		| result == (!getWorld().isAdjacent(getCoordinates(), this) && !collides(getCoordinates(), getRadius()))
	 */
	public boolean canFall() {
		return (!getWorld().isAdjacent(getCoordinates(), this) && !collides(getCoordinates(), getRadius()));
	}
	
	/**
	 * The grow function increases the radius of the worm by 10%.
	 * @post The radius will be 1.1 times it's original size.
	 * 		| new.getRadius() == 1.1*this.getRadius()
	 * AP/HP are handled by setRadius()
	 * 
	 */
	public void grow() {
		setRadius(getRadius()*1.1);
	}
	
	 /**
	  * The cycle function makes the worm equip the next weapon in its inventory
	  * @post the new equipped weapon is the next one in the inventory array
	  * 	(or the first if the previously equipped weapon was the last)
	  * 	  | new.getCurrentWeapon == (this.getCurrentWeapon() + 1) % inventory.size()
	  */
	public void cycle() {
		setCurrentWeapon((getCurrentWeapon() + 1)%inventory.size());
	}
	
	/**
	 * The eat method queries the foods in the world whether the worm can eat any and, if so, does.
	 * @post
	 * 		for all foods in the world, if the food is within .2 plus the radius of the worm, the worm will grow, and the food will be terminated
	 * 		|	for(Food food:this.getWorld().getAllFoods()){
	 * 		|		if (getWorld().distance(this, food) < (0.2 + getRadius)) {
	 * 		|			grow(); getWorld().removeAsFood(food);food.terminate();
	 */
	public void eat() {
		ArrayList<Food> foods = getWorld().getAllFoods();
		for (Food food : foods) {
			if (getWorld().distance(this, food) < (0.2 + getRadius())) {
				grow();
				getWorld().removeAsFood(food);
				food.terminate();
			}
		}
	}
	
	/**
	 * Makes the worm shoot its equipped weapon with given yield if applicable
	 * @param yield
	 * 		the yield the weapon is to be shot at
	 * @post
	 * 		if the amount of actionpoints - the cost firing the weapon (calculated with getCost() called on the equipped weapon)
	 * 		is a valid amount, the weapon will be shot with the requested yield, and the actionpoints will be subtracted the cost of firing
	 * 		| if(isValidActionPoints(getActionPoints()-APcost)) {
	 * 		|	new.getActionPoints() == this.getActionPoints()-getEquipped().getCost()
	 */
	public void shoot(int yield) {
		long APcost = getEquipped().getCost();
		if (isValidActionPoints(getActionPoints()-APcost)){
			getEquipped().shoot(yield);
			setActionPoints(getActionPoints()-APcost);}
	}
	
	/**
	 * Damages the worm for the given amount and kills it if necessary
	 * @param amount
	 * 		The amount of damage to be done.
	 * @post
	 * 		If the worm had sufficient hitpoints (more than amount), it's hitpoints are now decreased by amount. In all other cases, the worm is now dead.
	 * 		| if (this.getHitPoints() > amount) {new.getHitPoints() == old.getHitPoints() - amount}
	 * 		| else {this.isTerminated() == true)
	 */
	public void damage(long amount) {
		System.out.println("BOOM HEADSHOT!");
		if (amount > 0) {
			long targetHP = getHitPoints() - amount;
			if (isValidHitPoints(targetHP))
				setHitPoints(targetHP);
			else if (targetHP <= 0) {
				die();
			}
		}
	}
	
	/**
	 * Heals the worm the requested amount, or until it is fully healed, whatever comes first.
	 * @param amount
	 * 		The amount of health to be added
	 * @post
	 * 		if the worm's health is more than amount lower than it's maximum health, amount is to be added to it.
	 * 		Else, the worm's health is now equal to maxhealth
	 * 		| if((this.getHitPoints() + amount < this.getMaxHitPoints()) {new.getHitPoints == old.getHitPoints() + amount)}
	 * 		| else(this.getHitPoints() == this.getMaxHitPoints())}
	 */
	public void heal(long amount) {
		if (amount > 0) {
			long targetHP = getHitPoints() + amount;
			if (isValidHitPoints(targetHP))
				setHitPoints(targetHP);
			else if (targetHP >= getMaxHitPoints()) {
				setHitPoints(getMaxHitPoints());
			}
		}
	}
	
	/**
	 * 
	 * restores the AP of a worm to full. Used at beginning of turn.
	 * @post the ActionPoitns of the worm equals the maximum amount of actionpoints
	 * 		 | new.getActionPoints() == this.getMaxActionPoints()
	 */
	public void restore() {
		setActionPoints(getMaxActionPoints());
	}

	/**
	 * The Die method terminates the worm after removing it from its world and ends the current turn if it is the current worm that dies.
	 * @post
	 * 		if (getWorld().getCurrentWorm() == this) {getWorld().nextWorm()}
	 * 		getWorld()==null	
	 * 		getWorld().hasAsWorm(this) == false
	 * 		this.terminated == true
	 */
	private void die() {
		if (getWorld().getCurrentWorm()==this)
			getWorld().nextWorm();
		getWorld().removeAsWorm(this);
		setWorldNull();
		terminate();
	}
}
