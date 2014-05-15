package worms.programs;

import java.util.Map;

import worms.gui.game.IActionHandler;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ProgramParser;
import worms.programs.expressions.Expression;
import worms.programs.statements.Statement;
import worms.programs.types.Type;

/**
 * Program contains a main Statement (which contains more statements, ...)
 * @author Milan
 *
 */
public class Program {
	
	/**
	 * 
	 * @param raw The text file to be parsed/executed
	 * @param handler The handler upon which to call for actions
	 */
	public Program(String raw, IActionHandler handler) {
		this.raw = raw;
		this.handler = handler;
	}
	
	private String raw; //The raw text file
	public Statement main; //The compiled version
	public Map<String, Type> globals;
	public IActionHandler handler;
	
	
	/**
	 * Compiles the raw text file and puts it in Statement. The return is cfr. Facade to reflect success/failure and contains this if success.
	 * @param input
	 * @param handler
	 * @return
	 */
	public ParseOutcome<?> compile() {
		ProgramParser<Expression, Statement, Type> parser = new ProgramParser<Expression, Statement, Type>(new Factory());
		parser.parse(this.raw);
		//at this point we can even delete the raw text file
		if (parser.getErrors().size() > 0) { //You were... un successful? - Obviously...
			return ParseOutcome.failure(parser.getErrors());
		}
		this.main = parser.getStatement();
		globals = parser.getGlobals(); //flush this to the worm on start of turn
		return ParseOutcome.success(this);
	}
}