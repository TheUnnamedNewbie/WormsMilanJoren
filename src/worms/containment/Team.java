package worms.containment;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;
import worms.model.Worm;

/**
 * The Team class contains everything to group worms into teams with names.
 * @author Milan Sanders
 * @author Joren Vaes
 *
 */
public class Team {
	
	public Team(String targetName, World world) {
		System.out.println("Checking name \""+targetName+"\" ...");
		System.out.println("... For validity");
		if (!isValidName(targetName)) {
			System.out.println("Invalid name");
			throw new IllegalArgumentException("Invalid name");
		}
		System.out.println("Valid name. Checking for already existing teams...");
		for (Team subject: world.getAllTeams()) {
			String nameToCheck = subject.getName();
			System.out.println("...With \""+nameToCheck+"\"");
			if (nameToCheck.equals(targetName)) {
				System.out.println("Team already exists");
				throw new IllegalArgumentException("Team already exists");
				}
			System.out.println("OK, checking next team");
		}
		System.out.println("Name OK, moving on...");
		this.name = targetName;
		this.world = world;
		members = new ArrayList<Worm>();
	}
	
	private final String name;
	private final World world;
	private ArrayList<Worm> members;
	
	@Basic
	public String getName() {
		return this.name;
	}
	
	@Basic
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Return the worm of this team at the given index.
	 * 
	 * @param  index
	 *         The index of the worm to return.
	 * @throws IndexOutOfBoundsException
	 *         The given index is not positive or it exceeds the
	 *         number of members of this team.
	 *       | (index < 1) || (index > getNbWorms())
	 */
	@Basic
	public Worm getWormAt(int index) throws IndexOutOfBoundsException {
		return members.get(index);
	}
	
	/**
	 * Return the number of members of this team.
	 */
	@Basic
	public int getNbWorms() {
		return members.size();
	}
	
	/**
	 * Check whether this team can have the given worm
	 * as one of its members.
	 * 
	 * @param  worm
	 *         The worm to check.
	 * @return True if and only if the given worm is effective, and
	 *         if that worm can have this team as its team.
	 *       | result ==
	 *       |   (worm != null) &&
	 *       |   worm.canHaveAsTeam(this)
	 */
	public boolean canHaveAsWorm(Worm worm) {
		return (worm != null);
	}
	
	/**
	 * Check whether this team can have the given worm
	 * as one of its members at the given index.
	 * 
	 * @param  worm
	 *         The worm to check.
	 * @param  index
	 *         The index to check.
	 * @return False if the given index is not positive or exceeds
	 *         the number of members of this team + 1.
	 *       | if ( (index < 1) || (index > getNbWorms()+1) )
	 *       |   then result == false
	 *         Otherwise, false if this team cannot have the
	 *         given worm as one of its members.
	 *       | else if (! canHaveAsWorm(worm))
	 *       |   then result == false
	 *         Otherwise, true if and only if the given worm is
	 *         not already registered at another index.
	 *       | else result ==
	 *       |   for each I in 1..getNbWorms():
	 *       |     ( (I == index) || (getWormAt(I) != worm) )
	 */
	public boolean canHaveAsWormAt(Worm worm, int index) {
		if ((index < 1) || (index > getNbWorms() + 1))
			return false;
		if (!canHaveAsWorm(worm))
			return false;
		for (int pos = 1; pos <= getNbWorms(); pos++)
			if ((pos != index) && (getWormAt(pos) == worm))
				return false;
		return true;
	}
	
	/**
	 * Check whether this team has a proper arraylist of worms.
	 * 
	 * @return True if and only if this team can have each of its
	 *         members at their index, and if each of these members
	 *         references this team as their owner.
	 *       | for each index in 1..getNbWorms():
	 *       |   canHaveAsWormAt(getWormAt(index),index) &&
	 *       |   (getWormAt(index).getWorm() == this)
	 */
	public boolean hasProperWorms() {
		for (int index = 1; index <= getNbWorms(); index++) {
			if (!canHaveAsWormAt(getWormAt(index), index))
				return false;
			if (getWormAt(index).getTeam() != this)
				return false;
		}
		return true;
	}
	
	/**
	 * Check whether this team has the given worm as one of
	 * its worm.
	 *
	 * @param  worm
	 *         The worm to check.
	 * @return True if and only if this team has the given worm
	 *         as one of its members at some index.
	 *       | result ==
	 *       |   for some index in 1..getNbWorms():
	 *       |     getWormAt(index).equals(worm)
	 */
	public boolean hasAsWorm(Worm worm) {
		return members.contains(worm);
	}
	
	/**
	 * Return the index at which the given worm is registered
	 * in the list of worms for this team.
	 *  
	 * @param  worm
	 *         The worm to search for.
	 * @return If this team has the given worm as one of its
	 *         members, that worm is registered at the resulting
	 *         index. Otherwise, the resulting value is -1.
	 *       | if (hasAsWorm(worm))
	 *       |    then getWormAt(result) == worm
	 *       |    else result == -1
	 */
	public int getIndexOfWorm(Worm worm) {
		return members.indexOf(worm);
	}
	
	/**
	 * Return a list of all the members of this team.
	 * 
	 * @return The size of the resulting list is equal to the number of
	 *         members of this team.
	 *       | result.size() == getNbWorms()
	 * @return Each element in the resulting list is the same as the
	 *         worm of this team at the corresponding index.
	 *       | for each index in 0..result-size()-1 :
	 *       |   result.get(index) == getWormAt(index+1)
	 */
	public ArrayList<Worm> getAllWorms() {
		return new ArrayList<Worm>(members);
	}
	
	/**
	 * Add the given worm at the end of the arraylist of
	 * members of this team.
	 * 
	 * @param  worm
	 *         The worm to be added.
	 * @pre    The given worm is effective and already references
	 *         this team as its team.
	 *       | (worm != null) && (worm.getTeam() == this)
	 * @pre    This team does not not yet have the given worm
	 *         as one of its members.
	 *       | ! hasAsWorm(worm)
	 * @post   The number of members of this team is incremented
	 *         by 1.
	 *       | new.getNbWorms() == getNbWorms() + 1
	 * @post   This team has the given worm as its new last
	 *         worm.
	 *       | new.getWormAt(getNbWorms()+1) == worm
	 */
	public void addAsWorm(Worm worm) {
		assert (worm != null);
		assert !hasAsWorm(worm);
		//System.out.println("Adding worm: "+worm.getName());
		members.add(worm);
	}
	
	/**
	 * Remove the given worm from the members of this team.
	 * 
	 * @param  worm
	 *         The worm to be removed.
	 * @pre    The given worm is effective and does not have any
	 *         team.
	 *       | (worm != null) && (worm.getTeam() == null)
	 * @pre    This team has the given worm as one of
	 *         its members.
	 *       | hasAsWorm(worm)
	 * @post   The number of members of this team is decremented
	 *         by 1.
	 *       | new.getNbWorms() == getNbWorms() - 1
	 * @post   This team no longer has the given worm as
	 *         one of its members.
	 *       | (! new.hasAsWorm(worm))
	 * @post   All members registered beyond the removed worm
	 *         shift one position to the left.
	 *       | for each index in getIndexOfWorm(worm)+1..getNbWorms():
	 *       |   new.getWormAt(index-1) == getWormAt(index) 
	 */
	public void removeAsWorm(Worm worm) {
		assert (worm != null) && (worm.getTeam() == null);
		assert (hasAsWorm(worm));
		members.remove(worm);
	}
	
	public static boolean isValidName(String name) {
		return name.matches("[A-Z][a-zA-Z0-9\\s'\"]+");
	}
}
