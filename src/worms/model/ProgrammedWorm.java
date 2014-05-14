package worms.model;

import java.util.HashMap;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;
import worms.programs.Program;
import worms.programs.expressions.Expression;
import worms.programs.statements.Statement;

/**
 * The programmed worm also gets a program associated with it. It executes the commands as per the parser -> factory
 * Vars and such are kept on this side.
 * @author Milan
 *
 */
public class ProgrammedWorm extends Worm {
	
	/**
	 * Yay for auto-generated code!
	 * cfr. super constructor
	 * @param commands The commands this worm should execute in its turn
	 * It's a statement (probably a sequence)
	 */
	public ProgrammedWorm(String name, double posX, double posY, double radius,
			double direction, World world, Program commands)
			throws CoordinateOutOfBoundsException, IllegalArgumentException {
		super(name, posX, posY, radius, direction, world);
		this.commands = commands;
	}
	
	private Program commands;
	private HashMap<String, Expression> vars = new HashMap<String, Expression>();
	
	/**
	 * Makes the worm take its turn. Dependent on this.commands
	 * Basically runs through the main statement and creates 'variables' (entries) in vars/executes commands/evaluates expressions
	 */
	public void takeTurn() {
		//Distill a statement from program (I think? Can we call the parser now? what?)
		//Run the statement and get stuff called from factory
	}
	
	/**
	 * Creates access to a Expression through a string
	 * @param name the name that points to the var
	 * @param content the content of the var
	 */
	public void createVar(String name, Expression content) {
		vars.put(name, content);
	}
	
	public Expression getVar(String name) {
		return vars.get(name);
	}
	
	/**
	 * This called upon by the factory if a execute statement is encountered.
	 * @param commandName The String that represents the command. NOTE: What with shoot(yield)?
	 */
	public void execute(String commandName) {
		//if-cascade or switch statements
	}
}