package worms.model;
import worms.ExhaustionException;
import be.kuleuven.cs.som.annotate.*;

/**
 * 
 * WORMS!! The class Worm contains all information and methods related to the actual worms and their movements.
 * 
 * @invar	The current (at stable point) action points must remain above 0 and under max action points.
 * 			| isValidPoints(getActionPoints())
 * @invar	The radius is always at least 0.25m
 * 			| getRadius() >= 0.25
 * @author Milan Sanders
 * @author Joren Vaes
 * @date 2/03/2014
 * 
 */
public class Worm {
	
	public Worm(String name, double posX, double posY, double radius, double direction) {
		setPosX(posX);
		setPosY(posY);
		setRadius(radius);
		setOrientation(direction);
		setActionPoints(getMaxActionPoints());
		setName(name);
		updateJumpData();
	}
	
	private double posX;
	private double posY;
	private double radius;
	private int ActionPoints;
	private double orientation;
	private String name;
	
	private double jumpX;
	private double jumpY;
	private double jumpTime;
	private boolean jumpLegal;
	private double jumpSpeedX;
	private double jumpSpeedY;
	
	private String validchar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\'\" ";
	
	//all position stuff Defensively
	@Basic

	public double getPosX() {
		return this.posX;
	}
	
	public void setPosX(double position) {
		this.posX = position;
		updateJumpData();
	}

	@Basic
	public double getPosY() {
		return this.posY;
	}
	
	public void setPosY(double position) {
		this.posY = position;
		updateJumpData();
	}

	@Basic
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) throws IllegalArgumentException {
		if (radius < 0.25)
			throw new IllegalArgumentException();
		this.radius = radius;
	}

	/**
	 * Note: If mass is frequently used, store in variable and edit whenever you
	 * alter radius
	 * 
	 * @return
	 */
	public double getMass() {
		return 1062 * ((4 / 3) * Math.PI * Math.pow(getRadius() , 3));
	}
	//end defensive

	//all actionpoint stuff total
	public int getMaxActionPoints() {
		return (int)Math.round(getMass());
	}

	@Basic
	public int getActionPoints() {
		return ActionPoints;
	}

	public void setActionPoints(int points) {
		assert isValidPoints(points);
		this.ActionPoints = points;
	}

	//Make private?
	public boolean isValidPoints(int points) {
		return (points >= 0) && (points <= getMaxActionPoints());
	}
	//end total

	//direction nominally

	@Basic
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * @pre ..
	 * 		| (0 <= orientation) && (orientation <= 2*Math.PI)
	 * @param orientation
	 */
	public void setOrientation(double orientation) {
		this.orientation = orientation;
		updateJumpData();
	}
	//end nominally
		
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) throws IllegalArgumentException {
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	/**
	 * 
	 * @param name
	 * @return ...
	 * 		| (name.length() >= 2) && containsLegalChars() )
	 */
	private boolean isValidName(String name) {
		if ( name.length() >= 2) {
			return containsLegalChars(name);
		}
		else {
			return false;
		}
	}
	
	
	private boolean containsLegalChars(String name) {
		int length = name.length();
		for (int i = 0; i < length; i++) {
			if ( ! validchar.contains( name.subSequence( i , i+1 ) ) ) {
				return false;
			}
		}
		return true;
	}
	
	@Immutable
	public static int roundUp(double a) {
		int b = (int)Math.round(a);
		if (a-b < 0)
			return b;
		return (b+1);
	}
	
	//defensively because position
	public void step(int steps) throws IllegalArgumentException, ExhaustionException {
		if (steps <= 0)
			throw new IllegalArgumentException();
		if (! canMove(steps))
			throw new ExhaustionException();
		for (int i = 0; i < steps; i++) {
			double currentOrientation = getOrientation();
			setPosX(getPosX() + Math.cos(currentOrientation) * getRadius());
			setPosY(getPosY() + Math.sin(currentOrientation) * getRadius());
			setActionPoints(roundUp(getActionPoints()
					- Math.abs(Math.cos(currentOrientation))
					- Math.abs(4 * Math.sin(currentOrientation))));
		}
		updateJumpData();
	}
	
	
	private boolean canMove(int steps) {
		double currentOrientation = getOrientation();
		int stepPoints = roundUp(getActionPoints()
				- Math.abs(Math.cos(currentOrientation))
				- Math.abs(4 * Math.sin(currentOrientation)));
		return (getActionPoints() >= steps*stepPoints);
	}
	
	/**
	 * Adds a given angle to the current orientation of the worm.
	 * 
	 * Nominally, because part of orientation
	 * @pre	amount may not be too high. New angle (== current angle + amount) must remain smaller than 4*Pi
	 * 		| (amount + getOrientation()) <= 4*Math.PI
	 * @pre	If the turning is active, you must have sufficient action points to turn.
	 * 		| if (active)
	 * 		| 	((amount / (2 * Math.PI)) * 60) <= getActionPoints()
	 * @param amount
	 * @param active
	 * Is true if turning was active and thus substracts action points.
	 */
	public void turn(double amount, boolean active) {
		double newOrientation = getOrientation() + amount;
		if (newOrientation > 2*Math.PI)
			newOrientation -= 2*Math.PI;
		setOrientation(newOrientation);
		if (active)
			setActionPoints(roundUp(getActionPoints()
					- (amount / (2 * Math.PI)) * 60));
		updateJumpData();
	}
	
	private void updateJumpData() {
		if (! canJump() ) {
			jumpLegal = false;
			return;
		}
		else {
			jumpLegal = true;
			calculateJump();
		}
	}
	
	private boolean canJump() {
		return (isValidOrientation() && (getActionPoints() > 0) );
	}
		
	private boolean isValidOrientation() {
		return ((getOrientation() < Math.PI ) && (getOrientation() > (2 * Math.PI)));
	}
	
	private void calculateJump() {
		double mass, gravity, speed, force, distance;
		mass = getMass();  //don't forget to change if we end up giving mass a dedicated field
		gravity = 9.80665;
		force = (( 5 * ActionPoints ) / ( mass * gravity ));
		speed = ( force / ( mass * 2));
		jumpSpeedX = speed * Math.cos(orientation);
		jumpSpeedY = speed * Math.sin(orientation);
		jumpTime = (( 2 *jumpSpeedY ) / gravity );
		distance = jumpTime * jumpSpeedX;
		jumpX = posX + distance;
		jumpY = posY;
		}
	
	public void jump() throws IllegalArgumentException, ExhaustionException {
		if (! jumpLegal) {
			if (isValidOrientation()) { 
				throw new ExhaustionException();
			}
			else {
				throw new IllegalArgumentException();
			}
		}
		else {
			posX = jumpX;
			posY = jumpY;
			ActionPoints = 0;
		}
	}
	
		
	public double jumpTime() {
		return jumpTime;
	}
	
	public double[] jumpStep(double time) {
		double x, y; 
		x = posX + (time * jumpSpeedX);
		y = posY + (time * ( jumpSpeedY - ((time * 9.80665)/2)));
		double coordinates[] = {x, y};
		return coordinates;
	}
	
}
