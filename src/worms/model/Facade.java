package worms.model;

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
		return ((angle + worm.getOrientation()) <= 4 * Math.PI)
				&& (((angle / (2 * Math.PI)) * 60) <= worm.getActionPoints());
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
	
}
