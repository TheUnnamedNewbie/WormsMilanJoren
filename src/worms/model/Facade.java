package worms.model;

import java.util.Collection;
import java.util.Random;

import worms.ExhaustionException;

/**
 * 
 * @author Milan Sanders
 * @author Joren Vaes
 *
 */
public class Facade implements IFacade {
	
	public Worm createWorm(double x, double y, double direction, double radius, String name) {
		return new Worm(name, x, y, radius, direction);
	}
	
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
	
	public void jump(Worm worm) throws ModelException {
		try {
			worm.jump();
		} catch (ExhaustionException e) {
			throw new ModelException("No more actionpoints");
		} catch (IllegalStateException e) {
			throw new ModelException("Worm must face up");
		}
	}
	
	public double getJumpTime(Worm worm) {
		return worm.getJumpTime();
	}
	
	public double[] getJumpStep(Worm worm, double t) {
		return worm.jumpStep(t);
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
	
	public int getActionPoints(Worm worm) {
		return worm.getActionPoints();
	}
	
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
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

	@Override
	public void addEmptyTeam(World world, String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewFood(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewWorm(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canFall(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMove(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Food createFood(World world, double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fall(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Projectile getActiveProjectile(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Worm getCurrentWorm(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Food> getFood(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHitPoints(Worm worm) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[] getJumpStep(Projectile projectile, double t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getJumpTime(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getJumpTime(Worm worm, double timeStep) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxHitPoints(Worm worm) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRadius(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRadius(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSelectedWeapon(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTeamName(Worm worm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWinner(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Worm> getWorms(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getX(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getX(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY(Food food) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY(Projectile projectile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isActive(Food food) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActive(Projectile projectile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdjacent(World world, double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAlive(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGameFinished(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isImpassable(World world, double x, double y, double radius) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void jump(Projectile projectile, double timeStep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jump(Worm worm, double timeStep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectNextWeapon(Worm worm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shoot(Worm worm, int yield) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startGame(World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startNextTurn(World world) {
		// TODO Auto-generated method stub
		
	}
	
}
