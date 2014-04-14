package worms.containment;

import be.kuleuven.cs.som.annotate.*;

import java.util.ArrayList;
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
	public World(double width, double height, boolean[][] map, Random random) throws IllegalMapException,IllegalSizeException{
		if(!isLegalSize(width, height)) {
			throw new IllegalSizeException();
		}
		this.width = width;
		this.height = height;
		this.projectile = null;
		foods = new ArrayList<Food>();//Do these have to be in the constructor?
		worms = new ArrayList<Worm>();//
		if(!isLegalMap(map)){
			throw new IllegalMapException();
		}
		this.passableMap = map;
		this.cellWidth = (width/(map.length+1));
		this.cellHeight = (height/(map[0].length+1));
		
		
		
	}
	
	public World() {
		this.width = 1;
		this.height = 1;
		this.projectile = null;
		foods = new ArrayList<Food>();
		worms = new ArrayList<Worm>();
		this.passableMap = new boolean[2][2];
		this.cellWidth = (width/(passableMap.length+1));
		this.cellHeight = (height/(passableMap[0].length+1));
	}
	
	 //FIELDS
	private final double width, height, cellWidth, cellHeight;
	private ArrayList<Food> foods;
	private ArrayList<Worm> worms;
	private Projectile projectile;
	private final boolean[][] passableMap;
	public final double GRAVITY = 9.80665;
	private static final double EPS = Util.DEFAULT_EPSILON;
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
	
	public boolean isPassableAt(double x, double y){
		assert(0.0<x); assert(x<width);
		assert(0.0<y); assert(y<height);
		return passableMap[(int)Math.floor(x/getCellWidth())][(int)Math.floor(y/getCellHeight())];
	}
	
	
	
	
	public boolean isValidX(double posX) { 
		return (posX <= getWidth()) && (posX >= 0);
	}
	
	public boolean isValidY(double posY) {
		return (posY <= getHeight()) && (posY >= 0);
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
		return Math.sqrt(Math.pow(a.getPosX() - b.getPosX(), 2) + Math.pow(a.getPosY() - b.getPosY(), 2));
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
	
	public boolean canExist(double[] coordinates, Entity entity) {
		//this is gonna be one hell of a method.
		
		assert(coordinates[0] <= getWidth());
		assert(coordinates[1] <= getHeight());
		assert(coordinates[0] >= 0);
		assert(coordinates[1] >= 0);
		assert(entity != null);
		assert((entity.getPosX()-entity.getRadius())<=coordinates[0]);
		assert((entity.getPosX()+entity.getRadius())>=coordinates[0]);
		assert((entity.getPosY()-entity.getRadius())<=coordinates[0]);
		assert((entity.getPosY()+entity.getRadius())>=coordinates[0]);
		//only now I realise I might have gone overboard with asserting this stuff.
		
		//vardec
		double[] upperLeft = new double[2];
		double[] lowerRight = new double[2];
		int[] upperLeftCell = new int[2];
		int[] lowerRightCell = new int[2];
		
		
		//prepare to be confused with inverted coordinatesystemstuff
		upperLeft[0] = entity.getPosX() - entity.getRadius();
		upperLeft[1] = entity.getPosY() - entity.getRadius(); 
		lowerRight[0] = entity.getPosX() + entity.getRadius();
		lowerRight[1] = entity.getPosY() + entity.getRadius();
		
		//can be merged with above later for more efficiency
		upperLeftCell[0] = (int)Math.floor(upperLeft[0]/getCellWidth());
		upperLeftCell[1] = (int)Math.floor(upperLeft[1]/getCellHeight());
		lowerRightCell[0] = (int)Math.floor(lowerRight[0]/getCellWidth());
		lowerRightCell[1] = (int)Math.floor(lowerRight[1]/getCellHeight());
		
		
		
		//some forstuffs comes here to populate the submap with usefull things
		for(int j = upperLeftCell[0]; j < lowerRightCell[0]; j++) { //TODO check if it this is 100% correct, didn't have enought coffee yet to propperly think about that
			for(int i = upperLeftCell[1]; i < lowerRightCell[1]; i++){
				//Vardec
				double[] absoluteCoordinate = new double[2];
				double distanceToEntity;
				
				absoluteCoordinate[0] = (j * getCellWidth());
				absoluteCoordinate[1] = (i * getCellHeight());
				
				//prepare your butt for #mathClusterFuck
				distanceToEntity = Math.sqrt(	Math.pow( entity.getPosX() - absoluteCoordinate[0], 2) + 
												Math.pow( entity.getPosY() - absoluteCoordinate[1], 2));
				
				//Need to think long and hard about this one in relation to it checking all 4 adjacant cells to the absolutecoordinate
				if(distanceToEntity <= (entity.getRadius() + EPS)){
					//TODO check if it is -1 or +1 and if there is a more efficient way to check these 4 cases
					if(passableMap[j-1][i-1]){
						if(passableMap[j][i-1]){
							if(passableMap[j][i]){
								if(! passableMap[j-1][i]){
									return false;
								}
							} else{return false;};
						} else{return false;};
					} else{return false;};
				}
			}
		}

		
			//TODO: Stuffs.
			//need to check here for each point except the leftmost, upmost, rightmost, and downmost edges
			//if the entity will cover it with the radius and if so, that the 4 adjacent are empty.
			//might acutally simply be easier to not store a submap for this.
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
}
