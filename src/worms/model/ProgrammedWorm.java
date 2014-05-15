package worms.model;

import java.util.HashMap;
import java.util.Map;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;
import worms.gui.game.IActionHandler;
import worms.programs.Program;
import worms.programs.statements.Statement;
import worms.programs.types.Type;

/**
 * The programmed worm also gets a program associated with it. It executes the commands as per the parser -> factory
 * Vars and such are kept on this side.
 * @author Milan
 *
 */
public class ProgrammedWorm extends Worm {
	
	/**
	 * Yay for auto-generated code!
	 * cfr. super constructor (Isn't there like a '@effect' thing or smth)
	 * @param program The commands this worm should execute in its turn
	 */
	public ProgrammedWorm(String name, double posX, double posY, double radius,
			double direction, World world, Program program)
			throws CoordinateOutOfBoundsException, IllegalArgumentException {
		super(name, posX, posY, radius, direction, world);
		this.program = program;
		this.handler = program.handler;
	}
	
	private Program program;
	private IActionHandler handler;
	private Map<String, Type> vars = new HashMap<String, Type>();
	
	/**
	 * Makes the turn cfr the program (runs the program)
	 * 
	 * DEV NOTE: First figure the Statement end out before attempting linking to it. The winds of change are coming...
	 */
	public void takeTurn() {
		//TODO Make sure the program is compiled. Must we compile if not (total)?
		this.vars = this.program.globals;
	}
	
	/**
	 * Base of our recursive call. Switch on StatementType and do stuff if required.
	 * @param statement
	 */
	public void doStatement(Statement statement) {
		
	}
	
	/**
	 * Creates access to a Expression through a string
	 * @param name the name that points to the var
	 * @param content the content of the var
	 */
	public void createVar(String name, Type content) {
		vars.put(name, content);
	}
	
	public Type getVar(String name) {
		return vars.get(name);
	}
	
	/**
	 * This called upon by the factory if a execute statement is encountered.
	 * @param commandName The String that represents the command. NOTE: What with shoot(yield)?
	 * @param value for use with fire and turn. Anything can be placed if not these (prefer null)
	 * @throws when commandname is illegal. This is an internal error and should not be thrown. Debugging purposes only.
	 */
	public void execute(String commandName, Object value) {
		if (commandName == "jump") {
			this.handler.jump(this);
		} else if (commandName == "move") {
			this.handler.move(this);
		} else if (commandName == "toggleweap") {
			this.handler.toggleWeapon(this);
		} else if (commandName == "skip") {
			endTurn();
		} else if (commandName == "fire") {
			this.handler.fire(this, (Integer)value); //annoying...
		} else if (commandName == "turn") {
			this.handler.turn(this, (Double)value);
		} else throw new IllegalAccessError("Illegal command name");
	}
	
	/**
	 * Stuff to do at the end of the turn, if any
	 */
	public void endTurn() {
		//stuff!
	}
}