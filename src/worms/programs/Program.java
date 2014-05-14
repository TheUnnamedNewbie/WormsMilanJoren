package worms.programs;

import worms.gui.game.IActionHandler;
import worms.model.programs.ParseOutcome;
import worms.programs.statements.Statement;

/**
 * Program contains a main Statement (which contains more statements, ...)
 * I think compiling it on creation might be good
 * @author Milan
 *
 */
public class Program {
	
	public Program(String raw, IActionHandler handler) {
		this.raw = raw;
		this.handler = handler;
		compile();
	}
	
	/**
	 * Compiles the raw text file and puts it in Statement. The return is cfr. Facade to reflect success/failure
	 * @param input
	 * @param handler
	 * @return
	 */
	private ParseOutcome<?> compile() {
		// Create an instance of Factory? Because handler is already given.
		return null;
	}
	
	private String raw; //The raw text file
	private Statement main; //The compiled version
	private IActionHandler handler; //Our link to the actual gameworld has been made!
}