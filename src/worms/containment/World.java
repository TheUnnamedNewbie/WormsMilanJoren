package worms.containment;

import java.util.ArrayList;
import java.util.Random;

import worms.entities.*;
import worms.model.Worm;
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
			throw new IllegalMapException;
		}
		this.passableMap = map;
		
	}
	
	public World() {
		this.width = 1;
		this.height = 1;
		this.projectile = null;
		foods = new ArrayList<Food>();
		worms = new ArrayList<Worm>();
	}
	
	
	private final double width, height;
	private ArrayList<Food> foods;
	private ArrayList<Worm> worms;
	private Projectile projectile;
	public final double GRAVITY = 9.80665;
	private double[][] passableMap;
	
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public boolean isValidX(double posX) {
		return Math.abs(posX) <= getWidth()/2.0;
	}
	
	public boolean isValidY(double posY) {
		return Math.abs(posY) <= getHeight()/2.0;
	}
	
	public Projectile getProjectile() {
		return this.projectile;
	}
	
	public void setProjectile(Projectile target) throws TooManyProjectilesException{
		if (target != null && getProjectile() != null){
			throw new IllegalArgumentException();
		}
		this.projectile = target;
	}
	
	public double distance(Entity a, Entity b) {
		return Math.sqrt(Math.pow(a.getPosX() - b.getPosX(), 2) + Math.pow(a.getPosY() - b.getPosY(), 2));
	}
	
	public static boolean isLegalMap(double[][] map) {
		int length = map[0].lenght();
			for(Double[] collum:map){
				if(collum.length() != length){
					return false
				}
			}
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
