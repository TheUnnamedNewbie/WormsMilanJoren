package worms.entities;

public abstract class Entity {

	private double PosX, PosY, Radius;
	
	
	/**
	 * @post
	 * 		| new.PosX == target
	 * @param target
	 * 		the target coordinate
	 * @throws CoordinateOutOfBoundsException
	 * 		thrown if the new coordinate would put the entity in an illigal position (in other words
	 * 		outside of the legal bounds of the map)
	 */
	public void setPosX(double target) throws CoordinateOutOfBoundsException {
		return;
	}
	
	/**
	 * 
	 * @return 
	 * 		return the value stored in PosX
	 * 		| result == this.PosX
	 */
	public double getPosX() {
		return -1;
	}
	
	/**
	 * @post
	 * 		| new.PosY == target
	 * @param target
	 * 		The new coordinate PosY is to be set too
	 * @throws CoordinateOutOfBoundsException
	 * 		thrown if the new coordinate would put the entity in an illigal position (in other words
	 * 		outside of the legal bounds of the map)
	 */
	public void setPosY(double target) throws CoordinateOutOfBoundsException {
		return;
	}
	
	/**
	 * 
	 * @return 
	 * 		return the value stored in PosY
	 * 		| result == this.PosY
	 */
	public double getPosY() {
		return -1;
	}
	
	/**
	 * 
	 * @param targetX
	 * 		Coordinate X you wish to check
	 * @param targetY
	 * 		Coordinate Y you wish to check
	 * @return
	 * 		true if and only if both targetX and targetY are withing the legal bounds of the X and Y coordinates resp.
	 * 		false in all other cases
	 */
	public boolean isValidPosition(double targetX, double targetY) {
		return false;
	}
	

	
}
