package worms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import worms.ExhaustionException;
import worms.entities.*;
import worms.gui.game.IActionHandler;
import worms.model.programs.ParseOutcome;
import worms.programs.Program;
import worms.containment.*;

/**
 * 
 * @author Milan Sanders
 * @author Joren Vaes
 *
 */
public class Facade implements IFacade {
	
	public boolean canMove(Worm worm) {
		return worm.canMove();
	}
	
	public void move(Worm worm) throws ModelException {
		try {
			worm.step();
		} catch (ExhaustionException e) {
			throw new ModelException("Insufficient ActionPoints");
		}
	}
	
	public boolean canTurn(Worm worm, double angle) {
		return (-4*Math.PI <= (angle + worm.getOrientation())) && ((angle + worm.getOrientation()) <= 4*Math.PI)
				&& (((Math.abs(angle) / (2 * Math.PI)) * 60) <= worm.getActionPoints());
	}
	
	public void turn(Worm worm, double angle) {
		if (canTurn(worm, angle)) {
			worm.turn(angle, true);
		}
	}
	
	public double[] getJumpStep(Worm worm, double t) {
		return worm.jumpStep(worm.getActionPoints(), t);
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
		try {
			world.addAsTeam(new Team(newName, world));
		} catch (IllegalArgumentException e) {
			if (Team.isValidName(newName))
				throw new ModelException("Team already exists.");
			else
				throw new ModelException("Invalid name");
		}
	}

	
	public void addNewFood(World world) {
		world.createRandomFood();
	}

	
	public void addNewWorm(World world) {
		world.createRandomWorm();
	}

	
	public boolean canFall(Worm worm) {
		return worm.canFall();
	}

	
	public Food createFood(World world, double x, double y) {
		return new Food(world, x, y);
	}

	
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		return new World(width, height, passableMap, random);
	}

	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name) {
		return new Worm(name, x, y, radius, direction, world);
	}

	
	public void fall(Worm worm) {
		worm.fall();
	}

	public Projectile getActiveProjectile(World world) {
		return world.getProjectile();
	}

	public Worm getCurrentWorm(World world) {
		return world.getCurrentWorm();
	}

	public Collection<Food> getFood(World world) {
		return world.getAllFoods();
	}

	public int getHitPoints(Worm worm) {
		return (int)worm.getHitPoints();
	}

	public double[] getJumpStep(Projectile projectile, double t) {
		return projectile.jumpStep(projectile.getForce(), t);
	}

	public double getJumpTime(Projectile projectile, double timeStep) {
		return projectile.jumpTime(projectile.getForce(), timeStep);
	}

	
	public double getJumpTime(Worm worm, double timeStep) {
		return worm.jumpTime(worm.getActionPoints(), timeStep);
	}

	
	public int getMaxHitPoints(Worm worm) {
		return (int)worm.getMaxHitPoints();
	}

	
	public double getRadius(Food food) {
		return food.getRadius();
	}

	
	public double getRadius(Projectile projectile) {
		return projectile.getRadius();
	}

	
	public String getSelectedWeapon(Worm worm) {
		return worm.getEquipped().getName();
	}

	
	public String getTeamName(Worm worm) {
		if(worm.getTeam() == null) {
			return null;
		} else
			return worm.getTeam().getName();
	}

	
	public String getWinner(World world) {
		ArrayList<Worm> winners = world.getWinner();
		if (winners.size()==0)
			return "non-existent";
		else if (winners.get(0) == null)
			return "";
		else if (winners.size() == 1)
			return winners.get(0).getName();
		else
			return winners.get(0).getTeam().getName();
	}

	
	public Collection<Worm> getWorms(World world) {
		return world.getAllWorms();
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
		return !food.isTerminated();
	}

	
	public boolean isActive(Projectile projectile) {
		return projectile == projectile.getWorld().getProjectile();
	}

	
	public boolean isAdjacent(World world, double x, double y, double radius) {
		return world.isAdjacent(new double[]{x, y}, radius);
	}

	
	public boolean isAlive(Worm worm) {
		return !worm.isTerminated();
	}

	
	public boolean isGameFinished(World world) {
		return world.hasWinner();
	}

	
	public boolean isImpassable(World world, double x, double y, double radius) {
		return !world.canExist(new double[]{x,y}, radius);
	}

	
	public void jump(Projectile projectile, double timeStep) {
		projectile.shoot(timeStep);
	}

	
	public void jump(Worm worm, double timeStep) {
		try {
			worm.jump(timeStep);
		} catch (ExhaustionException e) {
			throw new ModelException("Insufficient ActionPoints");
		}
	}

	
	public void selectNextWeapon(Worm worm) {
		worm.cycle();
	}

	
	public void shoot(Worm worm, int yield) {
		worm.shoot(yield);
	}

	
	public void startGame(World world) throws ModelException {
		try {
			world.start();
		} catch (IllegalStateException e) {
			throw new ModelException("Have at least 2 worms in different or no teams");
		}
	}

	
	public void startNextTurn(World world) {
		world.nextWorm();
		world.setProjectile(null); //Just to be sure
	}

	public void addNewWorm(World world, Program program) {
		// TODO Auto-generated method stub
	}

	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name, Program program) {
		if (program == null) {
			return createWorm(world, x, y, direction,
			radius, name);
		} else {
			return new ProgrammedWorm(name, x, y, direction,
			radius, world, program);
		}
	}

	public ParseOutcome<?> parseProgram(String programText,
			IActionHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasProgram(Worm worm) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isWellFormed(Program program) {
		// TODO Auto-generated method stub
		return false;
	}
}
