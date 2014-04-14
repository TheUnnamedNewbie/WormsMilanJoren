package worms.model;

import java.util.Collection;
import java.util.Random;

import worms.ExhaustionException;
import worms.entities.*;
import worms.containment.*;

/**
 * 
 * @author Milan Sanders
 * @author Joren Vaes
 *
 */
public class Facade implements IFacade {
	
	/**
	 * Depricated constructor that does not use world
	public Worm createWorm(double x, double y, double direction, double radius, String name) {
		return new Worm(name, x, y, radius, direction);
	}
	*/
	
	public boolean canMove(Worm worm, int nbSteps) {
		return worm.canMove(nbSteps);
	}
	
	public void move(Worm worm, int nbSteps) throws ModelException {
		try {
			worm.step(nbSteps);
		} catch (ExhaustionException e) {
			throw new ModelException("Insufficient ActionPoints");
		} catch (IllegalArgumentException e) {
			throw new ModelException("nbSteps must be positive");
		}
	}
	
	public boolean canTurn(Worm worm, double angle) {
		//(-Math.PI <= (amount + getOrientation())) && ((amount + getOrientation()) <= Math.PI)
		return (-4*Math.PI <= (angle + worm.getOrientation())) && ((angle + worm.getOrientation()) <= 4*Math.PI)
				&& (((Math.abs(angle) / (2 * Math.PI)) * 60) <= worm.getActionPoints());
	}
	
	public void turn(Worm worm, double angle) {
		if (canTurn(worm, angle)) {
			worm.turn(angle, true);
		}
	}
	
//	public void jump(Worm worm) throws ModelException {
//		try {
//			worm.jump();
//		} catch (ExhaustionException e) {
//			throw new ModelException("No more actionpoints");
//		} catch (IllegalStateException e) {
//			throw new ModelException("Worm must face up");
//		}
//	}
	
//	public double getJumpTime(Worm worm) {
//		return worm.getJumpTime();
//	}
	
	public double[] getJumpStep(Worm worm, double t) {
		return worm.jumpStep(0.01, t);
	}
	
	public double getX(Worm worm) {
		return worm.getPosX();
	}
	
	public double getY(Worm worm) {
		return worm.getPosY();
	}
	
	public double getOrientation(Worm worm) {
		return worm.getOrientation();
	}
	
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}
	
	public void setRadius(Worm worm, double newRadius) throws ModelException {
		try {
			worm.setRadius(newRadius);
		} catch (IllegalArgumentException e) {
			throw new ModelException("Illegal Radius");
		}
	}
	
	public double getMinimalRadius(Worm worm) {
		return 0.25;
	}
	
	//We would have liked to avoid the typecast, but IFacade won't let us.
	public int getActionPoints(Worm worm) {
		return (int)worm.getActionPoints();
	}
	
	public int getMaxActionPoints(Worm worm) {
		return (int)worm.getMaxActionPoints();
	}
	
	public String getName(Worm worm) {
		return worm.getName();
	}
	
	public void rename(Worm worm, String newName) throws ModelException{
		try {
			worm.setName(newName);
		} catch (IllegalArgumentException e) {
			throw new ModelException("Illegal name");
		}
	}
	
	public double getMass(Worm worm) {
		return worm.getMass();
	}

	
	public void addEmptyTeam(World world, String newName) {
		// TODO Auto-generated method stub
		
	}

	
	public void addNewFood(World world) {
		// TODO Auto-generated method stub
		
	}

	
	public void addNewWorm(World world) {
		// TODO Auto-generated method stub
		
	}

	
	public boolean canFall(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean canMove(Worm worm) {
		return canMove(worm, 1);
	}

	
	public Food createFood(World world, double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		// TODO Auto-generated method stub
		return null;
	}

	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) {
		return new Worm(name, x, y, radius, direction, world);
	}

	
	public void fall(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	public Projectile getActiveProjectile(World world) {
		return world.getProjectile();
	}

	public Worm getCurrentWorm(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Food> getFood(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getHitPoints(Worm worm) {
		return (int)worm.getHitPoints();
	}

	public double[] getJumpStep(Projectile projectile, double t) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getJumpTime(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public double getJumpTime(Worm worm, double timeStep) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getMaxHitPoints(Worm worm) {
		return (int)worm.getMaxHitPoints();
	}

	
	public double getRadius(Food food) {
		return 0.2;
	}

	
	public double getRadius(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public String getSelectedWeapon(Worm worm) {
		return worm.getEquipped().getName();
	}

	
	public String getTeamName(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getWinner(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Collection<Worm> getWorms(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public double getX(Food food) {
		return food.getPosX();
	}

	
	public double getX(Projectile projectile) {
		return projectile.getPosX();
	}

	
	public double getY(Food food) {
		return food.getPosY();
	}

	
	public double getY(Projectile projectile) {
		return projectile.getPosY();
	}

	
	public boolean isActive(Food food) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean isActive(Projectile projectile) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean isAdjacent(World world, double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean isAlive(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean isGameFinished(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean isImpassable(World world, double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void jump(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		
	}

	
	public void jump(Worm worm, double timeStep) {
		// TODO Auto-generated method stub
		
	}

	
	public void move(Worm worm) {
		move(worm, 1);
	}

	
	public void selectNextWeapon(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	
	public void shoot(Worm worm, int yield) {
		// TODO Auto-generated method stub
		
	}

	
	public void startGame(World world) {
		// TODO Auto-generated method stub
		
	}

	
	public void startNextTurn(World world) {
		// TODO Auto-generated method stub
		
	}
	
}
