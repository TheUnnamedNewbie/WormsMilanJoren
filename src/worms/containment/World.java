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
import worms.util.Util;

//TODO add documentation and shizzle

public class World {
	public World(double width, double height, boolean[][] map, Random random) throws IllegalMapException, IllegalSizeException {
		if(!isLegalSize(width, height)) {
			throw new IllegalSizeException();
		}
		this.width = width;
		this.height = height;
		this.projectile = null;
		foods = new ArrayList<Food>();//Do these have to be in the constructor?
		worms = new ArrayList<Worm>();//
		teams = new ArrayList<Team>();
		if(!isLegalMap(map)){
			throw new IllegalMapException();
		}
		this.passableMap = map;
		this.cellWidth = (width/(map[0].length));
		this.cellHeight = (height/(map.length));
		this.random = random;
	}
	
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
	public final double GRAVITY = 9.80665;
	private static final double EPS = Util.DEFAULT_EPSILON;
	private List<String> wormNames = Arrays.asList("Shari", "Shannon",
			"Willard", "Jodi", "Santos", "Ross", "Cora", "Jacob", "Homer",
			"Kara");
	private List<String> teamNames = Arrays.asList("MannLebtNurEinmahl", "Olympians", "Worms", "Derps", "Ulteamate", "Inteamate");
	// END FIELDS
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public double getCellWidth() {
		return this.cellWidth;
	}
	
	public double getCellHeight() {
		return this.cellHeight;
	}
	
	public Worm getCurrentWorm() {
		return this.currentWorm;
	}
	
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
		assert (worm != null) && (worm.getWorld() == null);
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
		assert (food != null) && (food.getWorld() == null);
		assert (hasAsFood(food));
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
	
	public boolean isPassableAt(double x, double y){
		assert(0.0<x); assert(x<width);
		assert(0.0<y); assert(y<height);
		return getBoolAt((int)Math.floor(x/getCellWidth()),(int)Math.floor(y/getCellHeight()));
	}
	
	public boolean getBoolAt(int x, int y) {
		//System.out.println("request bool @int:("+x+","+y+")");
		if (x<0 || y<0 || x>passableMap.length-1 || y>passableMap.length-1) {
		//System.out.println("out of bounds, returning false");
		return false;
		}
		//System.out.println("Returning bool: "+passableMap[passableMap.length-y-1][x]);
		return passableMap[passableMap.length-y-1][x];
	}
		
	
	public boolean isValidX(double posX) { 
		return (posX <= getWidth()) && (posX >= 0);
	}
	
	public boolean isValidY(double posY) {
		return (posY <= getHeight()) && (posY >= 0);
	}
	
	public boolean isValidPosition(double[] target) {
		return isValidX(target[0]) && isValidY(target[1]);
	}

	public Projectile getProjectile() {
		return this.projectile;
	}
	
	public void setProjectile(Projectile target) throws TooManyProjectilesException{
		if (target != null && getProjectile() != null){
			throw new TooManyProjectilesException();
		}
		this.projectile = target;
	}
	
	public double distance(Entity a, Entity b) {
		return distance(a.getCoordinates(), b.getCoordinates());
	}
	
	public double distance(double[] coordA, double[] coordB) {
		return Math.sqrt(Math.pow(coordA[0] - coordB[0], 2) + Math.pow(coordA[1] - coordB[1], 2));
	}
	
	public static boolean isLegalMap(boolean[][] map) {
		int length = map[0].length;
			for(boolean[] collum:map){
				if(collum.length != length){
					return false;
				}
			}
		return true;
	}
	
	public boolean isAdjacent(double[] coordinate, double radius) {
		return (!Entity.collides(coordinate, radius, this)) && Entity.collides(coordinate, radius*1.1, this);
	}
	
	public boolean isAdjacent(double[] coordinate, Entity subject) {
		return (!subject.collides(coordinate, subject.getRadius())) && subject.collides(coordinate, subject.getRadius()*1.1);
	}
	
	public boolean canExist(double[] coordinates, double radius) {
		//this is gonna be one hell of a method.
		
		assert(coordinates[0] <= getWidth());
		assert(coordinates[1] <= getHeight());
		assert(coordinates[0] >= 0);
		assert(coordinates[1] >= 0);
//		assert(entity != null);
//		assert((entity.getPosX()-entity.getRadius())<=coordinates[0]);
//		assert((entity.getPosX()+entity.getRadius())>=coordinates[0]);
//		assert((entity.getPosY()-entity.getRadius())<=coordinates[0]);
//		assert((entity.getPosY()+entity.getRadius())>=coordinates[0]);
		//only now I realise I might have gone overboard with asserting this stuff.
		
		//vardec
		double[] upperLeft = new double[2];
		double[] lowerRight = new double[2];
		int[] upperLeftCell = new int[2];
		int[] lowerRightCell = new int[2];
		
		
		//prepare to be confused with inverted coordinatesystemstuff
		upperLeft[0] = coordinates[0] - radius;
		upperLeft[1] = coordinates[1] - radius; 
		lowerRight[0] = coordinates[0] + radius;
		lowerRight[1] = coordinates[1] + radius;
//		System.out.println(upperLeft[0]);
//		System.out.println(upperLeft[1]);
//		System.out.println(lowerRight[0]);
//		System.out.println(lowerRight[1]);
		
		//can be merged with above later for more efficiency
		upperLeftCell[0] = (int)Math.floor(upperLeft[0]/getCellWidth());
		upperLeftCell[1] = (int)Math.floor(upperLeft[1]/getCellHeight());
		lowerRightCell[0] = (int)Math.ceil(lowerRight[0]/getCellWidth());
		lowerRightCell[1] = (int)Math.ceil(lowerRight[1]/getCellHeight());
		
		
		//some forstuffs comes here to populate the submap with useful things
		for(int j = upperLeftCell[0]; j < lowerRightCell[0]; j++) { //TODO check if it this is 100% correct, didn't have enough coffee yet to properly think about that
			for(int i = upperLeftCell[1]; i < lowerRightCell[1]; i++){
				//Vardec
				double[] absoluteCoordinate = new double[2];
				double distanceToEntity;
				
				absoluteCoordinate[0] = (j * getCellWidth());
				absoluteCoordinate[1] = (i * getCellHeight());
				System.out.println(absoluteCoordinate[0]);
				System.out.println(absoluteCoordinate[1]);
				System.out.println(getCellWidth());
				
				//prepare your butt for #mathClusterFuck
				distanceToEntity = Math.sqrt(	Math.pow( (double)coordinates[0] - (double)absoluteCoordinate[0], 2) + 
												Math.pow( (double)coordinates[1] - (double)absoluteCoordinate[1], 2));
				
				System.out.println((Math.sqrt(	2.0* Math.pow( 3.0 - 2, 2))));
				//Need to think long and hard about this one in relation to it checking all 4 adjacent cells to the absolutecoordinate
				System.out.println(getBoolAt(j-1,i-1));
				System.out.println(getBoolAt(j,i-1));
				System.out.println(getBoolAt(j-1,i));
				System.out.println(getBoolAt(j,i));
				System.out.println("j= " + j);
				System.out.println("i= " + i);
				if(distanceToEntity <= (radius)){
					System.out.println(distanceToEntity);
					if(getBoolAt(j-1,i-1)){
						if(getBoolAt(j,i-1)){
							if(getBoolAt(j,i)){
								if(! getBoolAt(j-1,i)){
									System.out.println("THIS SHOULD FUCKING WORK");
									return false;
								}
							} else{
								return false;}
						} else{
							return false;}
					} else{
						return false;}
				}
			}
		}

		
			//TODO: Stuffs.
			//need to check here for each point except the leftmost, upmost, rightmost, and downmost edges
			//if the entity will cover it with the radius and if so, that the 4 adjacent are empty.
			//might actually simply be easier to not store a submap for this.
		return true;	
		}
	
	

	/**
	 * Checks to see if the world is of legal size
	 * @param x The prospective width of the world
	 * @param y The prospective height of the world
	 * @return If the world is of legal size (see specifications)
	 */
	public static boolean isLegalSize(double x, double y) {
		if(x == Double.NaN) {
			return false;
		}
		if(x == Double.MAX_VALUE) {
			return false;
		}
		if(x <=0){
			return false;
		}
		
		return true;
	}
	
	
	public void createRandomWorm() {
		Team team = null;
		boolean joinTeam = random.nextBoolean();
		System.out.println("nb teams: "+getNbTeams());
		if (joinTeam) {
			int teamIndex = 0;
			if (getNbTeams() > 1)
				teamIndex = random.nextInt(getNbTeams()-1);
			if (teamIndex >= 0 && getNbTeams()>0)
				team = getTeamAt(teamIndex);
		}
		double randomAngleOrient = (random.nextDouble()*(Math.PI*2.0)) - Math.PI;
		String wormName = wormNames.get(random.nextInt(wormNames.size()-1));
		double radius = 0.25 + random.nextDouble() / 4.0;
		double[] randomPos = getRandomPosition(radius);
		print("About to create worm");
		Worm randomWorm = new Worm(wormName, randomPos[0], randomPos[1], radius, randomAngleOrient, this);
		print("successfully created");
		addAsWorm(randomWorm);
		print("Added the worm in worms");
		if (joinTeam && (getNbTeams() > 0)) {
			System.out.println("team name: "+team.getName());
			randomWorm.join(team); //No problem with nullpointer because if(condition)
		}
	}
	
	public void createRandomFood() {
		double[] randomPos = getRandomPosition(0.2);
		Food randomFood = new Food(this, randomPos[0], randomPos[1]);
		System.out.println("Created food and adding...");
		addAsFood(randomFood);
		System.out.println("Added.");
	}
	
	/**
	 * 
	 * @param deltaD
	 * @return A random adjacent position
	 */
	private double[] getRandomPosition(double deltaD) {
		double[] target = new double[]{0.0, 0.0};
		boolean isFound = false; //is a random adjacent position found? NO! Not yet.
		while (!isFound) {
			double randomAnglePos = random.nextDouble()*(Math.PI/2.0);
			double[] maxPos = getMaxPosition(randomAnglePos);
			double maxX =maxPos[0] - Math.cos(randomAnglePos)*deltaD;//Making sure they don't start out of bounds
			double maxY =maxPos[1] - Math.sin(randomAnglePos)*deltaD;
			target = new double[]{maxX, maxY};
			boolean hasEnded = false; //has this direction been depleted;
			while (!hasEnded) {
				target[0] -= Math.cos(randomAnglePos)*deltaD;
				target[1] -= Math.sin(randomAnglePos)*deltaD;
				if (!isValidPosition(target))
					hasEnded = true;
				else if (isAdjacent(target, deltaD)) {
					hasEnded = true;
					isFound = true;
				}
			}
		}
		System.out.println("found random position at"+target);
		return target;
	}

	/**
	 * Alternative random position finder
	 * @param deltaD
	 * @return
	 */
	private double[] getRandomPosition2(double deltaD) {
		double mapBounds = deltaD; //The distance we must leave between the the edges of the map for preventing creation in the impassable edge
		double[] target = new double[2];
		//System.out.println("finding...");
		while (true) {
			//System.out.println("New strip");
			double randomX = random.nextDouble()*(getWidth()-2*mapBounds)+mapBounds;
			double randomY = random.nextDouble()*(getHeight()-2*mapBounds)+mapBounds;
			boolean hasEnded = false; //has this strip been depleted;
			target = new double[]{randomX, randomY};
			while (!hasEnded) {
				target[1] -= deltaD/2.0;
				if (target[1]<mapBounds)
					hasEnded = true;
				else if (isAdjacent(target, deltaD)) {
					//System.out.println("found random position at "+"("+target[0]+","+target[1]+")");
					return target;
				}
			}
			//System.out.println("Strip depleted");
		}
		
	}
	
	
	/**
	 * @pre 0 <= angle <= Pi/2
	 * @param angle
	 * @return The position at the edge of the map when looking in the given angle
	 */
	public double[] getMaxPosition(double angle) {
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
	
	public boolean hasWinner() {
		//System.out.println("Checking winner");
		boolean activated = false;
		Team team = null;
		if (getNbWorms() == 1){
			//System.out.println("Only 1 worm, declaring winner...");
			return true;
		}
		//System.out.println("More than 1 worm. Continuing check...");
		for (Worm worm: getAllWorms()) {
			if (!activated) {
				//System.out.println("1st worm, setting team.");
				team = worm.getTeam();
				activated = true;
			} else {
				//System.out.println("checking \""+worm.getTeam()+"\" against set team...");
				if (worm.getTeam() != team || worm.getTeam() == null){
					//System.out.println("Check failed: no winner");
					return false;
				}
				//System.out.println("Check succeeded. Continuing if able");
			}
		}
		//System.out.println("Checked all worms. Declaring winning team...");
		return true;
	}
	
	public ArrayList<Worm> getWinner() {
		ArrayList<Worm> emptyOutput = new ArrayList<Worm>();
		if (hasWinner())
			return getAllWorms();
		else
			return emptyOutput;
	}
	
	public void print(String string){
		System.out.println("World: " + string);
	}

	/**
	 * The start method puts the first worm as active worm and thus initializes the chain of events
	 */
	public void start() {
		if (getNbWorms() < 2)
			throw new IllegalStateException("Not enough worms. Have at least 2");
		else if (getCurrentWorm() == null)
			setCurrentWorm(getWormAt(0));
		else throw new IllegalStateException("Game has already started");
	}
	
	/**
	 * The nextWorm function puts the next worm in line as the current worm
	 */
	public void nextWorm() {
		Worm newWorm = getWormAt((getIndexOfWorm(getCurrentWorm())+1)%getNbWorms());
		setCurrentWorm(newWorm);
		newWorm.restore();
		newWorm.heal(10);
	}
}
