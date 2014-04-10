package worms.containment;

import java.util.ArrayList;

import worms.entities.*;
import worms.model.Worm;
import worms.weapons.*;

public class World {
	public World(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	private final double width, height;
	private ArrayList<Food> foods;
	private ArrayList<Worm> worms;
	private Projectile projectile;
	
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
	
	public void setProjectile(Projectile target) throws IllegalArgumentException{
		if (target != null && getProjectile() != null)
			throw new IllegalArgumentException();
		this.projectile = target;
	}
	
	public double distance(Entity a, Entity b) {
		return Math.sqrt(Math.pow(a.getPosX() - b.getPosX(), 2) + Math.pow(a.getPosY() - b.getPosY(), 2));
	}
}
