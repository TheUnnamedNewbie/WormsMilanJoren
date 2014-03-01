/**
 * 
 * @author milan
 * 
 */
public class Worm {
	private double posX;

	@Basic
	public double getPosX() {
		return this.posX;
	}

	private double posY;

	@Basic
	public double getPosY() {
		return this.posY;
	}

	private double radius;

	@Basic
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Note: If mass is frequently used, store in variable and edit whenever you
	 * alter radius
	 * 
	 * @return
	 */
	public double getMass() {
		return 1062 * ((4 / 3) * Math.Pi * getRadius() ^ 3);
	}

	public int getMaxActionPoints() {
		return Math.IntRoundOff(getMass());
	}

	private int ActionPoints;

	@Basic
	public int getActionPoint() {
		return ActionPoints;
	}

	public void setActionPoints(int points) {
		assert isValidPoints(points);
		this.ActionPoints = points;
	}

	public boolean isValidPoints(int points) {
		return (points >= 0) && (points <= getMaxActionPoints());
	}

	private double orientation;

	@Basic
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * @pre .. | (0 <= orient) && (orient <= 2*Math.Pi)
	 * @param orient
	 */
	public void setOrientation(double orient) {
		this.orientation = orient;
	}
}
