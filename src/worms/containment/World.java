package worms.containment;

import be.kuleuven.cs.som.annotate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import worms.IllegalMapException;
import worms.IllegalSizeException;
import worms.TooManyProjectilesException;
import worms.entities.*;
import worms.model.Worm;


/**
 * 
 * @invar
 * 		there is always exactly one projectile active at any given moment.
 * @invar
 * 		the map is always of a legal type
 * 		| isLegalMap(passableMap)
 * @invar
 * 		the width and height are always positive values, and never exceed Double.MAX_VALUE
 * 		| isLegalSize(width, height)
 * @invar
 * 		the cellwidth and cellheight are always equal to the maps width and height devided by the number of ellements in the width and height of the passable map
 * 		| getCellWidth() == (width/map[0].length)
 * 		| getCellHeight() == (height/map.length) 
 * @invar
 * 		the arraylists foods, worms, and teams only contain objects of the type food, worm, and team respectively 
 * 		| for(Entity element: foods) {element.isInstanceOf(food)}
 * 		| for(Entity element: worms) {element.isInstanceOf(worm)}
 * 		| for(Object element: teams) {element.isInstanceOf(team)}
 * @invar
 * 		all objects within the arraylists foods and worms shall never have coordinates outside of the map or in impassable terrain
 * 		|for(Entity element: foods) {canExist(element.getCoordinates(), element.getRadius()) && isLegalPosition(element.getCoordinates())} 
 * 		|for(Entity element: worms) {canExist(element.getCoordinates(), element.getRadius()) && isLegalPosition(element.getCoordinates())} 
 * @invar
 * 		no two worms shall ever occupy the same region of space in the world
 * 		|for(Entity element1: worms) {for(Entity element2: worms) {distance(element1, element2) >= (element1.getRadius() + element2.getRadius())}}
 * TODO more invars
 * @author Joren
 * @author Milan
 * 
 *
 */
public class World {
	
	/**
	 * Constructor for a world
	 * 
	 * @param width The width of a world in metres
	 * @param height The height of the world in  metres
	 * @param map The boolean double array that gives passable and imapssable rectangles
	 * @param random The random seed with which we need to generate random elements
	 * @throws IllegalMapException | !isLegalMap(map)
	 * @throws IllegalSizeException| !isLegalSize(width, height)
	 */
	public World(double width, double height, boolean[][] map, Random random) throws IllegalMapException, IllegalSizeException {

		if(!isLegalSize(width, height)) {
			throw new IllegalSizeException();
		}
		this.width = width;
		this.height = height;
		this.projectile = null;
		foods = new ArrayList<Food>();
		worms = new ArrayList<Worm>();
		teams = new ArrayList<Team>();
		if(!isLegalMap(map)){
			throw new IllegalMapException();
		}
		this.passableMap = map;
		this.cellWidth = (width/(map[0].length));
		this.cellHeight = (height/(map.length));
		this.random = random;
	}
	
	/**
	 * Standard constructor
	 */
	public World() {
		this.width = 1;
		this.height = 1;
		this.projectile = null;
		foods = new ArrayList<Food>();
		worms = new ArrayList<Worm>();
		this.passableMap = new boolean[2][2];
		this.cellWidth = (width/(passableMap[0].length+1));
		this.cellHeight = (height/(passableMap.length+1));
	}
	
	 //FIELDS
	private final double width, height, cellWidth, cellHeight;
	private Random random;
	private ArrayList<Food> foods;
	private ArrayList<Worm> worms;
	private ArrayList<Team> teams;
	private Projectile projectile;
	private final boolean[][] passableMap;
	private Worm currentWorm = null;
	public static final double GRAVITY = 9.80665;
	private List<String> wormNames = Arrays.asList("Shari", "Shannon",
			"Willard", "Jodi", "Santos", "Ross", "Cora", "Jacob", "Homer",
			"Kara");
	// END FIELDS
	
	/**
	 * gives the value for width
	 * @return
	 * 		|result == this.width
	 */
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Gives the value for height
	 * @return
	 * 		|result == this.height
	 */
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * returns the value for cellwidth
	 * @return
	 * 		|result == this.cellWidth
	 */
	public double getCellWidth() {
		return this.cellWidth;
	}
	
	/**
	 * Returns teh value for cellHeights
	 * @return
	 * 		|result == this.cellHeight
	 */
	public double getCellHeight() {
		return this.cellHeight;
	}
	
	/**
	 * Returns the current worm
	 * @return
	 * 		|result == this.currentWorm
	 */
	public Worm getCurrentWorm() {
		return this.currentWorm;
	}
	
	/**
	 * Sets the current worm to the given worm
	 * @post
	 * 		|this.getCurrentWorm() == worm
	 */
	public void setCurrentWorm(Worm worm) {
		this.currentWorm = worm;
	}
	
	//Begin things with worms
	
	/**
	 * Return the worm of this world at the given index.
	 * 
	 * @param  index
	 *         The index of the worm to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of worms of this world.
	 *       | (index < 1) || (index > getNbWorms())
	 */
	@Basic
	public Worm getWormAt(int index) throws IndexOutOfBoundsException {
		return worms.get(index);
	}
	
	/**
	 * Return the number of worms of this world.
	 * 
	 * @return
	 * 		| result == worms.size();
	 */
	@Basic
	public int getNbWorms() {
		return worms.size();
	}
	
	/**
	 * Check whether this world can have the given worm
	 * as one of its worms.
	 * 
	 * @param  worm
	 *         The worm to check.
	 * @return True if and only if the given worm is effective, and
	 *         if that worm can have this world as its world.
	 *       | result ==
	 *       |   (worm != null) &&
	 *       |   worm.canHaveAsWorld(this)
	 */
	public boolean canHaveAsWorm(Worm worm) {
		return (worm != null);
	}
	
	/**
	 * Check whether this world can have the given worm
	 * as one of its worms at the given index.
	 * 
	 * @param  worm
	 *         The worm to check.
	 * @param  index
	 *         The index to check.
	 * @return False if the given index is not positive or exceeds
	 *         the number of worms of this world + 1.
	 *       | if ( (index < 1) || (index > getNbWorms()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this world cannot have the
	 *         given worm as one of its worms.
	 *       | else if (! canHaveAsWorm(worm))
	 *       |   then result == false
	 *         Otherwise, true if and only if the given worm is
	 *         not already registered at another index.
	 *       | else result ==
	 *       |   for each I in 1..getNbWorms():
	 *       |     ( (I == index) || (getWormAt(I) != worm) )
	 */
	public boolean canHaveAsWormAt(Worm worm, int index) {
		if ((index < 1) || (index > getNbWorms() + 1))
			return false;
		if (!canHaveAsWorm(worm))
			return false;
		for (int pos = 1; pos <= getNbWorms(); pos++)
			if ((pos != index) && (getWormAt(pos) == worm))
				return false;
		return true;
	}
	
	/**
	 * Check whether this world has a proper arraylist of worms.
	 * 
	 * @return True if and only if this world can have each of its
	 *         worms at their index, and if each of these worms
	 *         references this world as their home (awww).
	 *       | for each index in 1..getNbWorms():
	 *       |   canHaveAsWormAt(getWormAt(index),index) &&
	 *       |   (getWormAt(index).getWorm() == this)
	 */
	public boolean hasProperWorms() {
		for (int index = 1; index <= getNbWorms(); index++) {
			if (!canHaveAsWormAt(getWormAt(index), index))
				return false;
			if (getWormAt(index).getWorld() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Check whether this world has the given worm as one of
	 * its worm.
	 *
	 * @param  worm
	 *         The worm to check.
	 * @return True if and only if this world has the given worm
	 *         as one of its worms at some index.
	 *       | result ==
	 *       |   for some index in 1..getNbWorms():
	 *       |     getWormAt(index).equals(worm)
	 */
	public boolean hasAsWorm(Worm worm) {
		return worms.contains(worm);
	}
	
	/**
	 * Return the index at which the given worm is registered
	 * in the list of worms for this world.
	 *  
	 * @param  worm
	 *         The worm to search for.
	 * @return If this world has the given worm as one of its
	 *         worms, that worm is registered at the resulting
	 *         index. Otherwise, the resulting value is -1.
	 *       | if (hasAsWorm(worm))
	 *       |    then getWormAt(result) == worm
	 *       |    else result == -1
	 */
	public int getIndexOfWorm(Worm worm) {
		return worms.indexOf(worm);
	}
	
	/**
	 * Return a list of all the worms of this world.
	 * 
	 * @return The size of the resulting list is equal to the number of
	 *         worms of this world.
	 *       | result.size() == getNbWorms()
	 * @return Each element in the resulting list is the same as the
	 *         worm of this world at the corresponding index.
	 *       | for each index in 0..result-size()-1 :
	 *       |   result.get(index) == getWormAt(index+1)
	 */
	public ArrayList<Worm> getAllWorms() {
		return new ArrayList<Worm>(worms);
	}
	
	/**
	 * Add the given worm at the end of the arraylist of
	 * worms of this world.
	 * 
	 * @param  worm
	 *         The worm to be added.
	 * @pre    The given worm is effective and already references
	 *         this world as its world.
	 *       | (worm != null) && (worm.getWorld() == this)
	 * @pre    This world does not not yet have the given worm
	 *         as one of its worms.
	 *       | ! hasAsWorm(worm)
	 * @post   The number of worms of this world is incremented
	 *         by 1.
	 *       | new.getNbWorms() == getNbWorms() + 1
	 * @post   This world has the given worm as its new last
	 *         worm.
	 *       | new.getWormAt(getNbWorms()+1) == worm
	 */
	public void addAsWorm(Worm worm) {
		assert (worm != null) && (worm.getWorld() == this);
		assert !hasAsWorm(worm);
		worms.add(worm);
	}
	
	/**
	 * Remove the given worm from the worms of this world.
	 * 
	 * @param  worm
	 *         The worm to be removed.
	 * @pre    The given worm is effective and does not have any
	 *         world.
	 *       | (worm != null) && (worm.getWorld() == null)
	 * @pre    This world has the given worm as one of
	 *         its worms.
	 *       | hasAsWorm(worm)
	 * @post   The number of worms of this world is decremented
	 *         by 1.
	 *       | new.getNbWorms() == getNbWorms() - 1
	 * @post   This world no longer has the given worm as
	 *         one of its worms.
	 *       | (! new.hasAsWorm(worm))
	 * @post   All worms registered beyond the removed worm
	 *         shift one position to the left.
	 *       | for each index in getIndexOfWorm(worm)+1..getNbWorms():
	 *       |   new.getWormAt(index-1) == getWormAt(index) 
	 */
	public void removeAsWorm(Worm worm) {
		System.out.println("removing worm " + worm.getName() + " from the game");
		assert (worm != null); //&& (worm.getWorld() == null); this cannot be called by die since die uses the getworld call
		assert (hasAsWorm(worm));
		worms.remove(worm);
	}
	
	//Begin things with foods
	/**
	 * Return the food of this world at the given index.
	 * 
	 * @param  index
	 *         The index of the food to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of foods of this world.
	 *       | (index < 1) || (index > getNbFoods())
	 */
	@Basic
	public Food getFoodAt(int index) throws IndexOutOfBoundsException {
		return foods.get(index);
	}
	
	/**
	 * Return the number of foods of this world.
	 * @return
	 * 		| result == this.foods.size()
	 */
	@Basic
	public int getNbFoods() {
		return foods.size();
	}
	

	/**
	 * Check whether this world can have the given food
	 * as one of its foods.
	 * 
	 * @param  food
	 *         The food to check.
	 * @return True if and only if the given food is effective, and
	 *         if that food can have this world as its world.
	 *       | result ==
	 *       |   (food != null) &&
	 *       |   food.canHaveAsWorld(this)
	 */
	public boolean canHaveAsFood(Food food) {
		return (food != null);
	}
	
	/**
	 * Check whether this world can have the given food
	 * as one of its foods at the given index.
	 * 
	 * @param  food
	 *         The food to check.
	 * @param  index
	 *         The index to check.
	 * @return False if the given index is not positive or exceeds
	 *         the number of foods of this world + 1.
	 *       | if ( (index < 1) || (index > getNbFoods()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this world cannot have the
	 *         given food as one of its foods.
	 *       | else if (! canHaveAsFood(food))
	 *       |   then result == false
	 *         Otherwise, true if and only if the given food is
	 *         not already registered at another index.
	 *       | else result ==
	 *       |   for each I in 1..getNbFoods():
	 *       |     ( (I == index) || (getFoodAt(I) != food) )
	 */
	public boolean canHaveAsFoodAt(Food food, int index) {
		if ((index < 1) || (index > getNbFoods() + 1))
			return false;
		if (!canHaveAsFood(food))
			return false;
		for (int pos = 1; pos <= getNbFoods(); pos++)
			if ((pos != index) && (getFoodAt(pos) == food))
				return false;
		return true;
	}
	
	/**
	 * Check whether this world has a proper arraylist of foods.
	 * 
	 * @return True if and only if this world can have each of its
	 *         foods at their index, and if each of these foods
	 *         references this world as their home (awww).
	 *       | for each index in 1..getNbFoods():
	 *       |   canHaveAsFoodAt(getFoodAt(index),index) &&
	 *       |   (getFoodAt(index).getFood() == this)
	 */
	public boolean hasProperFoods() {
		for (int index = 1; index <= getNbFoods(); index++) {
			if (!canHaveAsFoodAt(getFoodAt(index), index))
				return false;
			if (getFoodAt(index).getWorld() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Check whether this world has the given food as one of
	 * its food.
	 *
	 * @param  food
	 *         The food to check.
	 * @return True if and only if this world has the given food
	 *         as one of its foods at some index.
	 *       | result ==
	 *       |   for some index in 1..getNbFoods():
	 *       |     getFoodAt(index).equals(food)
	 */
	public boolean hasAsFood(Food food) {
		return foods.contains(food);
	}
	
	/**
	 * Return the index at which the given food is registered
	 * in the list of foods for this world.
	 *  
	 * @param  food
	 *         The food to search for.
	 * @return If this world has the given food as one of its
	 *         foods, that food is registered at the resulting
	 *         index. Otherwise, the resulting value is -1.
	 *       | if (hasAsFood(food))
	 *       |    then getFoodAt(result) == food
	 *       |    else result == -1
	 */
	public int getIndexOfFood(Food food) {
		return foods.indexOf(food);
	}
	
	/**
	 * Return a list of all the foods of this world.
	 * 
	 * @return The size of the resulting list is equal to the number of
	 *         foods of this world.
	 *       | result.size() == getNbFoods()
	 * @return Each element in the resulting list is the same as the
	 *         food of this world at the corresponding index.
	 *       | for each index in 0..result-size()-1 :
	 *       |   result.get(index) == getFoodAt(index+1)
	 */
	public ArrayList<Food> getAllFoods() {
		return new ArrayList<Food>(foods);
	}
	
	/**
	 * Add the given food at the end of the arraylist of
	 * foods of this world.
	 * 
	 * @param  food
	 *         The food to be added.
	 * @pre    The given food is effective and already references
	 *         this world as its world.
	 *       | (food != null) && (food.getWorld() == this)
	 * @pre    This world does not not yet have the given food
	 *         as one of its foods.
	 *       | ! hasAsFood(food)
	 * @post   The number of foods of this world is incremented
	 *         by 1.
	 *       | new.getNbFoods() == getNbFoods() + 1
	 * @post   This world has the given food as its new last
	 *         food.
	 *       | new.getFoodAt(getNbFoods()+1) == food
	 */
	public void addAsFood(Food food) {
		assert (food != null) && (food.getWorld() == this);
		assert !hasAsFood(food);
		foods.add(food);
	}
	
	/**
	 * Remove the given food from the foods of this world.
	 * 
	 * @param  food
	 *         The food to be removed.
	 * @pre    The given food is effective and does not have any
	 *         world.
	 *       | (food != null) && (food.getWorld() == null)
	 * @pre    This world has the given food as one of
	 *         its foods.
	 *       | hasAsFood(food)
	 * @post   The number of foods of this world is decremented
	 *         by 1.
	 *       | new.getNbFoods() == getNbFoods() - 1
	 * @post   This world no longer has the given food as
	 *         one of its foods.
	 *       | (! new.hasAsFood(food))
	 * @post   All foods registered beyond the removed food
	 *         shift one position to the left.
	 *       | for each index in getIndexOfFood(food)+1..getNbFoods():
	 *       |   new.getFoodAt(index-1) == getFoodAt(index) 
	 */
	public void removeAsFood(Food food) {
		assert (food != null);
		assert (hasAsFood(food));
		if(food.getWorld() != null) {
			food.setWorldNull();
		}
		foods.remove(food);
	}
	
	/**
	 * Return the team of this world at the given index.
	 * 
	 * @param  index
	 *         The index of the team to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of teams of this world.
	 *       | (index < 1) || (index > getNbTeams())
	 */
	@Basic
	public Team getTeamAt(int index) throws IndexOutOfBoundsException {
		return teams.get(index);
	}
	
	/**
	 * Return the number of teams of this world.
	 * 
	 *@return	the size of the arraylist of teams
	 *		| result == this.teams.size()
	 */
	@Basic
	public int getNbTeams() {
		return teams.size();
	}
	
	/**
	 * Check whether this world can have the given team
	 * as one of its teams.
	 * 
	 * @param  team
	 *         The team to check.
	 * @return True if and only if the given team is effective, and
	 *         if that team can have this world as its world.
	 *       | result ==
	 *       |   (team != null) &&
	 *       |   team.canHaveAsWorld(this)
	 */
	public boolean canHaveAsTeam(Team team) {
		return (team != null);
	}
	
	/**
	 * Check whether this world can have the given team
	 * as one of its teams at the given index.
	 * 
	 * @param  team
	 *         The team to check.
	 * @param  index
	 *         The index to check.
	 * @return False if the given index is not positive or exceeds
	 *         the number of teams of this world + 1.
	 *       | if ( (index < 1) || (index > getNbTeams()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this world cannot have the
	 *         given team as one of its teams.
	 *       | else if (! canHaveAsTeam(team))
	 *       |   then result == false
	 *         Otherwise, true if and only if the given team is
	 *         not already registered at another index.
	 *       | else result ==
	 *       |   for each I in 1..getNbTeams():
	 *       |     ( (I == index) || (getTeamAt(I) != team) )
	 */
	public boolean canHaveAsTeamAt(Team team, int index) {
		if ((index < 1) || (index > getNbTeams() + 1))
			return false;
		if (!canHaveAsTeam(team))
			return false;
		for (int pos = 1; pos <= getNbTeams(); pos++)
			if ((pos != index) && (getTeamAt(pos) == team))
				return false;
		return true;
	}
	
	/**
	 * Check whether this world has a proper arraylist of teams.
	 * 
	 * @return True if and only if this world can have each of its
	 *         teams at their index, and if each of these teams
	 *         references this world as their world.
	 *       | for each index in 1..getNbTeams():
	 *       |   canHaveAsTeamAt(getTeamAt(index),index) &&
	 *       |   (getTeamAt(index).getTeam() == this)
	 */
	public boolean hasProperTeams() {
		for (int index = 1; index <= getNbTeams(); index++) {
			if (!canHaveAsTeamAt(getTeamAt(index), index))
				return false;
			if (getTeamAt(index).getWorld() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Check whether this world has the given team as one of
	 * its team.
	 *
	 * @param  team
	 *         The team to check.
	 * @return True if and only if this world has the given team
	 *         as one of its teams at some index.
	 *       | result ==
	 *       |   for some index in 1..getNbTeams():
	 *       |     getTeamAt(index).equals(team)
	 */
	public boolean hasAsTeam(Team team) {
		return teams.contains(team);
	}
	
	/**
	 * Return the index at which the given team is registered
	 * in the list of teams for this world.
	 *  
	 * @param  team
	 *         The team to search for.
	 * @return If this world has the given team as one of its
	 *         teams, that team is registered at the resulting
	 *         index. Otherwise, the resulting value is -1.
	 *       | if (hasAsTeam(team))
	 *       |    then getTeamAt(result) == team
	 *       |    else result == -1
	 */
	public int getIndexOfTeam(Team team) {
		return teams.indexOf(team);
	}
	
	/**
	 * Return a list of all the teams of this world.
	 * 
	 * @return The size of the resulting list is equal to the number of
	 *         teams of this world.
	 *       | result.size() == getNbTeams()
	 * @return Each element in the resulting list is the same as the
	 *         team of this world at the corresponding index.
	 *       | for each index in 0..result-size()-1 :
	 *       |   result.get(index) == getTeamAt(index+1)
	 */
	public ArrayList<Team> getAllTeams() {
		return new ArrayList<Team>(teams);
	}
	
	/**
	 * Add the given team at the end of the arraylist of
	 * teams of this world.
	 * 
	 * @param  team
	 *         The team to be added.
	 * @pre    The given team is effective and already references
	 *         this world as its world.
	 *       | (team != null) && (team.getWorld() == this)
	 * @pre    This world does not not yet have the given team
	 *         as one of its teams.
	 *       | ! hasAsTeam(team)
	 * @post   The number of teams of this world is incremented
	 *         by 1.
	 *       | new.getNbTeams() == getNbTeams() + 1
	 * @post   This world has the given team as its new last
	 *         team.
	 *       | new.getTeamAt(getNbTeams()+1) == team
	 */
	public void addAsTeam(Team team) {
		assert (team != null) && (team.getWorld() == this);
		assert !hasAsTeam(team);
		teams.add(team);
	}
	
	/**
	 * Remove the given team from the teams of this world.
	 * 
	 * @param  team
	 *         The team to be removed.
	 * @pre    The given team is effective and does not have any
	 *         world.
	 *       | (team != null) && (team.getWorld() == null)
	 * @pre    This world has the given team as one of
	 *         its teams.
	 *       | hasAsTeam(team)
	 * @post   The number of teams of this world is decremented
	 *         by 1.
	 *       | new.getNbTeams() == getNbTeams() - 1
	 * @post   This world no longer has the given team as
	 *         one of its teams.
	 *       | (! new.hasAsTeam(team))
	 * @post   All teams registered beyond the removed team
	 *         shift one position to the left.
	 *       | for each index in getIndexOfTeam(team)+1..getNbTeams():
	 *       |   new.getTeamAt(index-1) == getTeamAt(index) 
	 */
	public void removeAsTeam(Team team) {
		assert (team != null) && (team.getWorld() == null);
		assert (hasAsTeam(team));
		teams.remove(team);
	}
	
	/**
	 * Checks if the map is passable at the given coordinates (These coordinates are doubles). 
	 * @pre
	 * 		the coordinates are within the legal bounds of the map, ie, the x coordinate is larger than 0, and smaller than the total width
	 * 		and the y coordinate too is larger than 0, and smaller than the height of the map.
	 * 		| x > 0.0
	 * 		| y > 0.0
	 * 		| x < width
	 * 		| y < height
	 * 
	 * @param x
	 * 		The x-coordinate (Double!)
	 * @param y
	 * 		The y-coordinate (Double!)
	 * @return
	 * 		| result == getBoolAt((int)Math.floor(x/getCellWidth()),(int)Math.floor(y/getCellHeight()))
	 * 		
	 */
	public boolean isPassableAt(double x, double y){
		assert(0.0<x); assert(x<width);
		assert(0.0<y); assert(y<height);
		return getBoolAt((int)Math.floor(x/getCellWidth()),(int)Math.floor(y/getCellHeight()));
	}
	
	
	/**
	 * returns the passablemapvalues for the coordinates (x, y). Important: inverts the y axis
	 * i.e. bigger value for y is higher upwards.
	 * @param x
	 * @param y
	 * @return
	 * 		| this.passableMap[passableMap.length - y - 1][x]
	 */
	public boolean getBoolAt(int x, int y) {
		return passableMap[passableMap.length-y-1][x];
	}
		
	/**
	 * checks for legality of the x value
	 * @param posX
	 * 		the value for x that is to be checked
	 * @return
	 * 		true if posX is located between 0 and the width of the map, both inclusive.
	 * 		| result == (posX <= this.getWidth()) && (posX>=0)
	 */
	public boolean isValidX(double posX) { 
		return (posX <= getWidth()) && (posX >= 0);
	}
	
	/**
	 * checks if the Y position is valid
	 * @param posY
	 * 		the value of the y coordinate to be checked
	 * @return
	 * 		true if posY is located between 0 and the height of the map. Both are inclusive.
	 * 		| result == (posX <= this.getHeight()) && (posY >= 0)
	 */
	public boolean isValidY(double posY) {
		return (posY <= getHeight()) && (posY >= 0);
	}
	
	/**
	 * Checks validity of the coordinates given
	 * @param target
	 * 		the coordinates to be checked - notation [x, y]
	 * @return
	 * 		true if x is a valid x position, and y is a valid y position.
	 * 		| result == isValidX(target[0]) && isValidY(target[1])
	 */
	public boolean isValidPosition(double[] target) {
		return isValidX(target[0]) && isValidY(target[1]);
	}

	/**
	 * returns the projectile currently in the world
	 * @return
	 * 		| result == this.projectile
	 */
	public Projectile getProjectile() {
		return this.projectile;
	}
	
	
	/**
	 * sets the projectile of the world to the one provided, given there is not an active projectile in the world (ie, the projectile stored by the world is a null object)
	 * @param target
	 * 		the projectile the projectile to be set as the current active projectile.
	 * @post
	 * 		if no exception was thrown, the projectile stored by the world is now equal to the one provided.
	 * 		| new.getProjectile() == target
	 * @throws TooManyProjectilesException
	 * 		if both projectiles (the one stored by the world and the one provided) are both active, both null objects, or equal to eachother
	 * 		| if (target == null && this.getProjectile() == null)
	 * 		| if (target != null && this.getProjectile() != null)
	 * 		| if (target == this.getProjectile)
	 */
	public void setProjectile(Projectile target) throws TooManyProjectilesException{
		if (target != null && getProjectile() != null && target != getProjectile()){
			throw new TooManyProjectilesException();
		}
		this.projectile = target;
	}
	
	/**
	 * calculates the distance between to entity objects
	 * @param a
	 * 		the first entity
	 * @param b
	 * 		the second entity
	 * @return
	 * 		| result == Math.sqrt(Math.pow(a.getPosX() - b.getPosX()) + Math.pow(a.getPosY() - b.getPosY()))
	 */
	public double distance(Entity a, Entity b) {
		return distance(a.getCoordinates(), b.getCoordinates());
	}
	
	/**
	 * calculates the distance between the two provided coordinatessets.
	 * @param coordA
	 * @param coordB
	 * @return
	 * 		| Math.sqrt(Math.pow(coordA[0] - coordB[0], 2) + Math.pow(coordA[1] - coordB[1], 2))
	 */
	public double distance(double[] coordA, double[] coordB) {
		return Math.sqrt(Math.pow(coordA[0] - coordB[0], 2) + Math.pow(coordA[1] - coordB[1], 2));
	}
	
	/**
	 * Checks if the provided bolean array of arrays is a legal map, IE, each second level array is the same lenght.
	 * @param map
	 * @return
	 * 		true if the length of each array in the array is the same.
	 * 		| result == for(boolean[] collum:map){collum.length == map[0].length}
	 */
	public static boolean isLegalMap(boolean[][] map) {
		int length = map[0].length;
			for(boolean[] collum:map){
				if(collum.length != length){
					return false;
				}
			}
		return true;
	}
	
	/**
	 * returns if an object placed at the coordinates provided by the double array are adjacent to impas. terrain, given the radius provided.
	 * @param coordinate
	 * 		the coordinates at which adjacentness is to be checked.
	 * @param radius
	 * 		the radius of the object you are cheking adjacentness for
	 * @return
	 * 		if the object doesnt colide at its acutal radius, but does at 1.1* that radius
	 * 		| result == (!Entity.collides(coordinate, radius, this)) && Entity.collides(coordinate, radius*1.1, this)
	 */
	public boolean isAdjacent(double[] coordinate, double radius) {
		return (!Entity.collides(coordinate, radius, this)) && Entity.collides(coordinate, radius*1.1, this);
	}
	
	/**
	 * returns if an object placed at the coordinates provided by the double array are adjacent to impas. terrain, given the radius provided.
	 * @param coordinate
	 * 		the coordinates at which adjacentness is to be checked.
	 * @param subject
	 * 		The subject that is to hypothetically be placed at cooridnate.
	 * @return
	 * 		if the object doesnt colide at its acutal radius, but does at 1.1* that radius
	 * 		| result == (!subject.collides(coordinate, subject.getRadius())) && subject.collides(coordinate, subject.getRadius()*1.1)
	 */
	public boolean isAdjacent(double[] coordinate, Entity subject) {
		return (!subject.collides(coordinate, subject.getRadius())) && subject.collides(coordinate, subject.getRadius()*1.1);
	}
	
	/**
	 * Checks if a object with radius radius and coordinates coordinates could legally exist at the provided coordinates, ie, not cover any impassable terrain.
	 * @pre
	 * 		the coordinates are of a legal kind, IE, they are withing valid map bounds
	 * 		|isValidPosition(coordinates)
	 * @param coordinates
	 * 		the coordinates to be checked at
	 * @param radius
	 * 		the radius of the object to be checked for Existential right.
	 * @return
	 * 		true if all the passable pixels covered by the object are passable, false in all other cases.
	 * 		|	for(int i = 0; i<getWidth(); i++){
	 * 		|		for(int j = 0; j<getHeight(); j++){
	 * 		|			if((distance({i, j}, coordinates) < radius) && (getBoolAt({i, j}) == false)){
	 * 		|				result == false;
	 * 		|			}
	 *		|		}
	 *		|	}
	 *		|	result == true;
	 */
	public boolean canExist(double[] coordinates, double radius) {
		assert(coordinates[0] <= getWidth());
		assert(coordinates[1] <= getHeight());
		assert(coordinates[0] >= 0);
		assert(coordinates[1] >= 0);

		double[] upperLeft = new double[2];
		double[] lowerRight = new double[2];
		int[] upperLeftCell = new int[2];
		int[] lowerRightCell = new int[2];
		
		upperLeft[0] = coordinates[0] - radius;
		upperLeft[1] = coordinates[1] - radius; 
		lowerRight[0] = coordinates[0] + radius;
		lowerRight[1] = coordinates[1] + radius;

		upperLeftCell[0] = (int)Math.floor(upperLeft[0]/getCellWidth());
		upperLeftCell[1] = (int)Math.floor(upperLeft[1]/getCellHeight());
		lowerRightCell[0] = (int)Math.ceil(lowerRight[0]/getCellWidth());
		lowerRightCell[1] = (int)Math.ceil(lowerRight[1]/getCellHeight());

		if (!isValidPosition(upperLeft) || !isValidPosition(lowerRight))
			return false;

		for(int j = upperLeftCell[0]; j < lowerRightCell[0]; j++) { 
			for(int i = upperLeftCell[1]; i < lowerRightCell[1]; i++){
				double[] absoluteCoordinate = new double[2];
				double distanceToEntity;
				absoluteCoordinate[0] = (j * getCellWidth());
				absoluteCoordinate[1] = (i * getCellHeight());

				distanceToEntity = Math.sqrt(	Math.pow( (double)coordinates[0] - (double)absoluteCoordinate[0], 2) + 
												Math.pow( (double)coordinates[1] - (double)absoluteCoordinate[1], 2));

				if(distanceToEntity <= (radius)){
					if(getBoolAt(j-1,i-1)){
						if(getBoolAt(j,i-1)){
							if(getBoolAt(j,i)){
								if(! getBoolAt(j-1,i)){
									return false;
								}
							} else{return false;}
						} else{return false;}
					} else{	return false;}
				}
			}
		}
		return true;	
		}
	
	

	/**
	 * Checks to see if the world is of legal size
	 * @param x The prospective width of the world
	 * @param y The prospective height of the world
	 * @return If the world is of legal size: both coordinates are not Double.MAX_VALUE, are bigger than 0, and are numbers (so not Double.NaN)
	 * 		| x != Double.MAX_VALUE
	 * 		| y != Double.MAX_VALUE
	 * 		| x > 0
	 * 		| y > 0
	 * 		| y == y
	 * 		| x == x
	 */
	public static boolean isLegalSize(double x, double y) {
		if(x != x) { //Testing for NaN
			return false;
		}
		if(x == Double.MAX_VALUE) {
			return false;
		}
		if(x <=0){
			return false;
		}
		if(y != y) { //testing for nan
			return false;
		}
		if(y == Double.MAX_VALUE) {
			return false;
		}
		if(y <=0){
			return false;
		}
		return true;
	}
	
	/**
	 * Creates a random worm using the same values the GUI used in the 1st part.
	 * The naming uses a random value and so overlapping might be possible.
	 * Renaming is still possible and recommended for a better gaming experience
	 */
	public void createRandomWorm() {
		Team team = null;
		boolean joinTeam = random.nextBoolean();
		if (joinTeam) {
			int teamIndex = 0;
			if (getNbTeams() > 1)
				teamIndex = random.nextInt(getNbTeams());
			if (teamIndex >= 0 && getNbTeams()>0)
				team = getTeamAt(teamIndex);
		}
		double randomAngleOrient = (random.nextDouble()*(Math.PI*2.0)) - Math.PI;
		String wormName = wormNames.get(random.nextInt(wormNames.size()-1));
		double radius = 0.25 + random.nextDouble() / 4.0;
		double[] randomPos = getRandomPosition(radius);
		System.out.println("x: " + randomPos[0] + " y: " + randomPos[1]);
		Worm randomWorm = new Worm(wormName, randomPos[0], randomPos[1], radius, randomAngleOrient, this);
		addAsWorm(randomWorm);
		if (joinTeam && (getNbTeams() > 0)) {
			System.out.println("team name: "+team.getName());
			randomWorm.join(team); 
		}
	}
	
	public void createRandomFood() {
		double[] randomPos = getRandomPosition(0.2);
		Food randomFood = new Food(this, randomPos[0], randomPos[1]);
		addAsFood(randomFood);
	}

	/**
	 * Random position finder
	 * @param deltaD
	 * @return
	 */
	private double[] getRandomPosition(double deltaD) {
		double mapBounds = deltaD; 
		double[] target = new double[2];
		while (true) {
			double randomX = random.nextDouble()*(getWidth()-2*mapBounds)+mapBounds;
			double randomY = random.nextDouble()*(getHeight()-2*mapBounds)+mapBounds;
			boolean hasEnded = false; 
			target = new double[]{randomX, randomY};
			while (!hasEnded) {
				target[1] -= deltaD;
				if (target[1]<mapBounds)
					hasEnded = true;
				else if (isAdjacent(target, deltaD)) {
					return target;
				}
			}
		}
		
	}
	
	
	/**
	 * @pre 0 <= angle <= Pi/2
	 * @param angle
	 * @return The position at the edge of the map when looking in the given angle
	 */
	public double[] getMaxPosition(double angle) {
		assert angle <= Math.PI/2; assert 0.0 < angle;
		double diagonalAngle = Math.tan(getHeight()/getWidth());
		if (angle < diagonalAngle) {
			double maxY = getWidth()*Math.tan(angle);
			return new double[]{getWidth(),maxY};
		} else if (angle > diagonalAngle) {
			double maxX = getHeight()/Math.tan(angle);
			return new double[]{maxX,getHeight()};
		} else
			return new double[]{getWidth(), getHeight()};
	}
	
	/**
	 * The method returns if the world has a winner or not.
	 * 
	 * @return
	 * 		True if one of the following conditions are met:
	 * 			Only one worm is left on the map
	 * 			All of the worms remaining on the playingfield are of the same team (this does not apply to worms with no team at all)
	 * 		| result == 
	 * 		| 		(getNbWorms() == 1) ||
	 * 		|		for(Worm worm: getAllWorms()) {worm.getTeam() == getAllWorms()[0]}
	 * 			
	 */
	public boolean hasWinner() {
		boolean activated = false;
		Team team = null;
		if (getNbWorms() == 1){
			return true;
		}
		for (Worm worm: getAllWorms()) {
			if (!activated) {
				team = worm.getTeam();
				activated = true;
			} else {
				if (worm.getTeam() != team || worm.getTeam() == null){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns an arraylist of the winners of the game
	 * @return
	 * 		| if(hasWinner){result == getAllWorms()}
	 * 		| else {result == new ArrayList<Worm>();
	 */
	public ArrayList<Worm> getWinner() {
		if (hasWinner())
			return getAllWorms();
		else
			return new ArrayList<Worm>();
	}
	
	/**
	 * print function for the simple purpose of use in debugging.
	 * #Python3
	 * @param string
	 * 		string to be printed.
	 */
	public void print(String string){
		System.out.println("World: " +string);
	}

	/**
	 * The start method puts the first worm as active worm and thus initializes the chain of events
	 * @throws IllegalStateException
	 * 		if there are not enought worms in the world to start the game, i.e. there are less than 2 worms that are not in the same team.
	 * 		| hasWinner()
	 * @throws IllegalStateException
	 * 		if the game has already started (the world has an active worm), and has no winner. 
	 * 		| getCuurentWorm() != null
	 * 
	 */
	public void start() {
		if (hasWinner())
			throw new IllegalStateException("Not enough worms. Have at least 2");
		else if (getCurrentWorm() == null)
			setCurrentWorm(getWormAt(0));
		else throw new IllegalStateException("Game has already started");
	}
	
	/**
	 * The nextWorm function puts the next worm in line as the current worm. this worm has also had it's actionpoits restored, and 10 health added.
	 * This is the nextTurn function
	 * @post
	 * 		the active worm is the next one in the list. If the old active worm was the last in the list, the new active worm is the first in the list.
	 * 		| new.getCurrentWorm() == getWormAt(getIndexOfWorm(old.getCurrentWorm()) + 1) % getNbWorms()
	 * @post
	 * 		the new active worm has 10 more hitpoints than it had before the call of the method (unless it was less than 10 hitpoints under the max hitpoints,
	 * 		in which case it will have the maximum allowed hitpoints)
	 * 		| if(new.getCurrentWorm().getMaxHitpoints >= (new.getCurrentWorm().getHitPoints() + 10)) {
	 * 		|		final.getCurrentWorm().getHitPoints() == new.getCurrentWorm().getHitPoints() + 10 }
	 * 		| else { final.getCurrentWorm().getHitPoints() == new.getCurrentWorm().getMaxHitPoints() }
	 * @post
	 * 		the new active worm has full actionpoints
	 * 		| final.getCurrentWorm().getActionPoints() == new.getCurrentWorm().getMaxActionPoints()
	 */
	public void nextWorm() {
		Worm newWorm = getWormAt((getIndexOfWorm(getCurrentWorm())+1)%getNbWorms());
		setCurrentWorm(newWorm);
		newWorm.restore();
		newWorm.heal(10);
	}
	
	/**
	 * isLegalPosition returns true if the entity is within the bounds of the world.
	 * not to be confused with isValidPosition
	 * 
	 * @param coordinates
	 * @param radius
	 * @return
	 * 		false if an entity placed at the coordinates with the provided radius would be at least partially out of bounds.
	 * 		| result == (coordinates[0] + radius < this.getWidth()) &&
	 * 		|			(coordinates[0] - radius >= 0) &&
	 * 		|			(coordinates[1] + radius < this.getHeight()) &&
	 * 		|			(coordinates[1] - radius >= 0) 
	 */
	public boolean isLegalPosition(double[] coordinates, double radius) {
		if((coordinates[0] + radius >= this.getWidth()) || (coordinates[0] - radius < 0)
			|| (coordinates[1] + radius >= this.getHeight()) || (coordinates[1] - radius < 0)) {
			return false;
		} else {return true;}
	}
}
