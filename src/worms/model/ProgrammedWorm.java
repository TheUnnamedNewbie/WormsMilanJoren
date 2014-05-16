package worms.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import worms.CoordinateOutOfBoundsException;
import worms.containment.World;
import worms.entities.Entity;
import worms.entities.Food;
import worms.gui.game.IActionHandler;
import worms.model.programs.ProgramFactory;
import worms.programs.Program;
import worms.programs.Expressions.Expression;
import worms.programs.statements.Statement;
import worms.programs.types.*;

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
	private ArrayList<Type> emptyTypes = new ArrayList<Type>();
	
	/**
	 * Makes the turn cfr the program (runs the program)
	 * 
	 * DEV NOTE: First figure the Statement end out before attempting linking to it. The winds of change are coming...
	 */
	public void takeTurn() {
		//Make sure the program is compiled. Must we compile if not (total)?
		this.vars = this.program.globals;
		doStatement(program.main);
		endTurn();
	}
	
	/**
	 * Base of our recursive call. Switch on StatementType and do stuff if required. and boy is it required
	 * @param statement
	 */
	public void doStatement(Statement statement) {
		switch (statement.getSubStatement().getType()) {
		case ACTION:
			//Casting is OK because we know it's an actionstatement, in Python this wouldn't be a problem. Just sayin'...
			Statement.ActionStatement subStatement = (Statement.ActionStatement)statement.getSubStatement();
			execute(subStatement.commandName, subStatement.value);
			endTurn();
			break;
		case SEQ:
			//again, casting is OK because of switch on StatementType
			for (Statement currentStatement: ((Statement.SequenceStatement)statement.getSubStatement()).getSequence()) {
				doStatement(currentStatement);
			}
			break;
		case ASSIGN:
			Statement.AssignStatement assignSubState = (Statement.AssignStatement)statement.getSubStatement(); //believe me, you want it this way
			TypeType typeName = assignSubState.expr.getReturnType();
			Type targetType = popNextType(typeName);
			targetType.setExpression(assignSubState.expr);
			createVar(assignSubState.varName, targetType);
			break;
		case PRINT:
			String temp = toString(((Statement.PrintStatement)statement.getSubStatement()).output);
			this.handler.print(temp);
			break;
		case IF:
			Statement.IfStatement ifSubState = (Statement.IfStatement)statement.getSubStatement();
			boolean ifcondition = (Boolean)evaluateExpression(ifSubState.condition);
			//work with a temp to check types or smth
			if (ifcondition)
				doStatement(ifSubState.ifthen);
			else
				doStatement(ifSubState.ifelse);
			break;
		case WHILE:
			Statement.WhileStatement whileSubState = (Statement.WhileStatement)statement.getSubStatement();
			while ((Boolean)evaluateExpression(whileSubState.condition)) {
				doStatement(whileSubState.body);
			}
			break;
		case FOR:
			Statement.ForStatement forSubState = (Statement.ForStatement)statement.getSubStatement();
			ArrayList<Entity> theList = new ArrayList<Entity>();
			if (forSubState.fortype == ProgramFactory.ForeachType.WORM) {
				for (Worm worm: getWorld().getAllWorms())
					theList.add(worm);
			} else if (forSubState.fortype == ProgramFactory.ForeachType.FOOD) {
				for (Food food: getWorld().getAllFoods())
					theList.add(food);
			} else if (forSubState.fortype == ProgramFactory.ForeachType.ANY) {
				for (Worm worm: getWorld().getAllWorms())
					theList.add(worm);
				for (Food food: getWorld().getAllFoods())
					theList.add(food);
			}
			Type bogusType = new Type(TypeType.ENTITY);
			for (Entity targetEntity: theList) {
				Expression targetExpression = new Expression(-1, -1);
				targetExpression.createSubExpressionEntityLiteral(targetEntity);
				bogusType.setExpression(targetExpression);
				createVar(forSubState.variableName, bogusType);
				
				doStatement(forSubState.body);
			}
			vars.remove(forSubState.variableName);
		}
	}
	
	/**
	 * TODO implement
	 * Recursive evaluation for expressions. This is gonna be a big one (similar to doStatement, but for expressions)
	 * @param expression The expression to evaluate
	 * @return the evaluation
	 */
	private Object evaluateExpression(Expression expression) {
		return null;
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
	 * Returns the next empty type and removes it from the emptyType list
	 * ! Put it somewhere when calling this method because otherwise it's lost!
	 * @return
	 */
	private Type popNextType(TypeType typeType) {
		Type output = null;
		for (int index = 0; index < emptyTypes.size(); index++) {
			Type targetType = emptyTypes.get(index);
			if (targetType.getType() == typeType) {
				output = targetType;
				emptyTypes.remove(index);
				break;
			}
		}
		return output;
	}
	
	/**
	 * Turns the input into a string representation
	 * possible input-types: Double, Boolean, Worm, Food
	 * @param input
	 * @return
	 */
	private String toString(Expression input) {
		Object beforeConvert = evaluateExpression(input); //We get actual thing
		String output = "null";
		if (Boolean.class.isInstance(beforeConvert)) {
			output = ((Boolean)beforeConvert).toString();
		} else if (Double.class.isInstance(beforeConvert)) {
			output = ((Double)beforeConvert).toString();
		} else if (Entity.class.isInstance(beforeConvert)) {
			if (Worm.class.isInstance(beforeConvert)) {
				output = ((Worm)beforeConvert).getName();
			} else if (Food.class.isInstance(beforeConvert)) {
				output = "Food";
			}
		} return output;
	}
	
	/**
	 * This called upon by the factory if a execute statement is encountered.
	 * @param commandName The String that represents the command. NOTE: What with shoot(yield)?
	 * @param value for use with fire and turn. Anything can be placed if not these (prefer null)
	 * @throws when commandname is illegal. This is an internal error and should not be thrown. Debugging purposes only.
	 */
	private void execute(String commandName, Object value) {
		if (commandName == "jump") {
			this.handler.jump(this);
		} else if (commandName == "move") {
			this.handler.move(this);
		} else if (commandName == "toggleweap") {
			this.handler.toggleWeapon(this);
		} else if (commandName == "skip") {
			//do absolutely nothing this turn onward.
		} else if (commandName == "fire") {
			this.handler.fire(this, (Integer)value); //annoying...
		} else if (commandName == "turn") {
			this.handler.turn(this, (Double)value);
		} else throw new IllegalArgumentException("Illegal command name");
	}
	
	/**
	 * Stuff to do at the end of the turn, if any
	 */
	private void endTurn() {
		//stuff!
	}
}