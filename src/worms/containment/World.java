package worms.containment;

import java.util.ArrayList;
import java.util.Random;

import worms.entities.*;
import worms.model.Worm;
import worms.util.Util;
import worms.weapons.*;

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
	}
	
	
	private final double width, height, cellWidth, cellHeight;
	private ArrayList<Food> foods;
	private ArrayList<Worm> worms;
	private Projectile projectile;
	private final double[][] passableMap;

	public final double GRAVITY = 9.80665;
	
	private static final double EPS = Util.DEFAULT_EPSILON;
	
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
	
	public boolean isPassableAt(int x, int y){
		assert(x<passableMap.length);
		assert(y<passableMap[0].length);
		return passableMap[x][y];
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
		assert((entity.getPosY()-entity.getRadius())>=coordinates[0]);
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
			for(int i = upperLeftCell[1]); i < lowerRightCell[1]; i++){
				//Vardec
				double[] absoluteCoordinate = new double[2];
				double distanceToEntity;
				
				absoluteCoordinate[0] = (j * getCellWidth());
				absoluteCoordinate[1] = (i * getCellHeight());
				
				//prepare your but for #mathClusterFuck
				distanceToEntity = Math.sqrt(	Math.pow( entity.getPosX() - absoluteCoordinate[0], 2) + 
												Math.pow( entity.getPosY() - absoluteCoordinate[1], 2));
				
				//Need to think long and hard about this one in relation to it checking all 4 adjacant cells to the absolutecoordinate
				if(distanceToEntity <= (entity.getRadius() + EPS)){
					//TODO check if it is -1 or +1 and if there is a more efficient way to check these 4 cases
					if(isPassableAt(j-1, i-1)){
						if(isPassableAt(j, i-1)){
							if(isPassableAt(j, i)){
								if(! isPassableAt(j-1, i)){
									return false;
								}
							}else{return false;};	
						}else{ return false;};
					} else{return false;};
				}
			}	
		}

		
			//TODO: Stuffs. need to check here for each point except the leftmost, upmost, rightmost, and downmost edges if the entity will conver it with the radius
			//and if so, that the 4 adjacent are empty. might acutally simply be easier to not store a submap for this.
		return true;	
		}
	
	
	//TODO: Stuffs
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
		}
}
