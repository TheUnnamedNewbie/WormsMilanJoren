package worms.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import worms.CoordinateOutOfBoundsException;
import worms.containment.Team;
import worms.containment.World;
import worms.entities.Entity;
import worms.entities.Food;
import worms.entities.Movable;
import worms.gui.game.IActionHandler;
import worms.model.programs.ProgramFactory;
import worms.programs.Program;
import worms.programs.Expressions.Expression;
import worms.programs.Expressions.ExpressionKind;
import worms.programs.Expressions.SubExpression;
import worms.programs.statements.Statement;
import worms.programs.types.*;

/**
 * The programmed worm also gets a program associated with it. It executes the commands as per the parser -> factory
 * Vars and such are kept on this side.
 * @author Milan 'clickity-click' Sanders
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
		this.counter = 0;
	}
	
	private Program program;
	private IActionHandler handler;
	private Map<String, Type> vars = new HashMap<String, Type>();
	private ArrayList<Type> emptyTypes = new ArrayList<Type>();
	private int counter;
	private boolean hasPerformedAction;
	
	/**
	 * Makes the turn cfr the program (runs the program)
	 * 
	 * DEV NOTE: First figure the Statement end out before attempting linking to it. The winds of change are coming...
	 */
	public void takeTurn() {
		//Make sure the program is compiled. Must we compile if not (total)?
		this.vars = this.program.globals;
		hasPerformedAction = false;
		try {
			doStatement(program.main);
		} catch (Exception e) {
			program.setFailed();
		}
	}
	
	/**
	 * Base of our recursive call. Switch on StatementType and do stuff if required. and boy is it required
	 * @param statement
	 */
	public void doStatement(Statement statement) {
		if (this.program.hasFailed() || this.hasPerformedAction) {
			return;
		} else {
			if(getCounter() < 1000){
				incrementCounter();
			}
			else {return;}
			//System.out.println("executing statement of type: "+statement.getSubStatement().getType());
			switch (statement.getSubStatement().getType()) {
			case ACTION:
				//Casting is OK because we know it's an actionstatement, in Python this wouldn't be a problem. Just sayin'...
				Statement.ActionStatement subStatement = (Statement.ActionStatement)statement.getSubStatement();
				Object executionValue = null;
				if (subStatement.value != null)
					executionValue = evaluateExpression((Expression)subStatement.value);
				execute(subStatement.commandName, executionValue);
				endTurn();
				break;
			case SEQ:
				//again, casting is OK because of switch on StatementType
				for (Statement currentStatement: ((Statement.SequenceStatement)statement.getSubStatement()).getSequence()) {
					doStatement(currentStatement);
				}
				break;
			case ASSIGN:
				Statement.AssignStatement assignSubState = (Statement.AssignStatement)statement.getSubStatement();
				//make a new literal containing the value. Loosing all links to possible variables and uncertainties
				Expression expressionToAssign = assignSubState.expr; // so that if getvar we can replace with the gotten.
				TypeType typeType = expressionToAssign.getReturnType();
				if (Expression.VariableAccessExpression.class.isInstance(expressionToAssign.subExpression)) {
					expressionToAssign = vars.get(((Expression.VariableAccessExpression)assignSubState.expr.getSubExpression()).varname).getExpression();
					typeType = expressionToAssign.getReturnType();
				}
				Expression newExpression = new Expression(0, 0); //bogus wrapper expression
				SubExpression.LiteralType literalType = SubExpression.LiteralType.getCorrespondingType(typeType);
				//System.out.println("new literalType: "+literalType);
				Object newValue;
				if (!(expressionToAssign.getSubExpression().getKind() == ExpressionKind.LITERAL)) {
					//System.out.println("getting value for new literal");
					newValue = evaluateExpression(expressionToAssign);
				} else {
					newValue = ((Expression.LiteralExpression)expressionToAssign.getSubExpression()).getValue();
				}
				newExpression.createSubExpressionXLiteral(literalType, newValue);
				if (vars.containsKey(assignSubState.varName)) {
					//assert the types match
					vars.get(assignSubState.varName).setExpression(newExpression);
				} else {
					TypeType typeName = expressionToAssign.getReturnType();
					Type targetType = popNextType(typeName);
					targetType.setExpression(newExpression);
					createVar(assignSubState.varName, targetType);
				}
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
	}
	
	/**
	 * Recursive evaluation for expressions. This is gonna be a big one (similar to doStatement, but for expressions)
	 * Turns out: NOT THAT BIG. Just makes you wonder if there isn't a God because perfection like this exists.
	 * @param expression The expression to evaluate
	 * @return the evaluation
	 */
	private Object evaluateExpression(Expression expression) {
		//System.out.println("evaluating expression of type: "+expression.getSubExpression().getKind()+" "+expression.getSubExpression().getType());
		switch (expression.getSubExpression().getKind()) {
		
		case LITERAL:
			//trivial case, return this if type==THIS, otherwise return the value.
			if (((Expression.LiteralExpression)expression.getSubExpression()).getType() == SubExpression.LiteralType.THIS) {
				return this;
			}
			//either null or a literal
			return ((Expression.LiteralExpression)expression.getSubExpression()).getValue();
			
		case VARACCESS:
			//trivial case, just get the type, extract expression and evaluate.
			Type sourceType = getVar(((Expression.VariableAccessExpression)expression.getSubExpression()).varname);
			Expression sourceExpression = sourceType.getExpression();
			return evaluateExpression(sourceExpression);
			
		case EQUALITY:
			//still pretty darn easy
			Expression.EqualityExpression equalitySubExpr = ((Expression.EqualityExpression)expression.getSubExpression());
			//Assert things of types
			return (evaluateExpression(equalitySubExpr.left) == evaluateExpression(equalitySubExpr.right));
			
		case BOOLOP:
			Expression.BoolOpExpression boolOpSubExpr = ((Expression.BoolOpExpression)expression.getSubExpression());
			switch (boolOpSubExpr.getType()) {
			case OR:
				return ((Boolean)evaluateExpression(boolOpSubExpr.left) || (Boolean)evaluateExpression(boolOpSubExpr.right));
			case AND:
				return ((Boolean)evaluateExpression(boolOpSubExpr.left) && (Boolean)evaluateExpression(boolOpSubExpr.right));
			case NOT:
				return !((Boolean)evaluateExpression(boolOpSubExpr.left));
			}
			
		case DOUBLEOP:
			Expression.DoubleOpExpression doubleOpSubExpr = ((Expression.DoubleOpExpression)expression.getSubExpression());
			switch (doubleOpSubExpr.getType()) {
			case GREATER:
				return ((Double)evaluateExpression(doubleOpSubExpr.left) > (Double)evaluateExpression(doubleOpSubExpr.right));
			case GREATEREQUAL:
				return ((Double)evaluateExpression(doubleOpSubExpr.left) >= (Double)evaluateExpression(doubleOpSubExpr.right));
			case LESSER:
				return ((Double)evaluateExpression(doubleOpSubExpr.left) < (Double)evaluateExpression(doubleOpSubExpr.right));
			case LESSEREQUAL:
				return ((Double)evaluateExpression(doubleOpSubExpr.left) <= (Double)evaluateExpression(doubleOpSubExpr.right));
			case ADD:
				return ((Double)evaluateExpression(doubleOpSubExpr.left) + (Double)evaluateExpression(doubleOpSubExpr.right));
			case SUB:
				return ((Double)evaluateExpression(doubleOpSubExpr.left) - (Double)evaluateExpression(doubleOpSubExpr.right));
			case MUL:
				return ((Double)evaluateExpression(doubleOpSubExpr.left) * (Double)evaluateExpression(doubleOpSubExpr.right));
			case DIV:
				return ((Double)evaluateExpression(doubleOpSubExpr.left) / (Double)evaluateExpression(doubleOpSubExpr.right));
			case SQRT:
				return Math.sqrt((Double)evaluateExpression(doubleOpSubExpr.left));
			case SIN:
				return Math.sin((Double)evaluateExpression(doubleOpSubExpr.left));
			case COS:
				return Math.cos((Double)evaluateExpression(doubleOpSubExpr.left));
			}
			
		case ENTITYOP:
			//System.out.println("getting into case EntityOP");
			Expression.EntityOpExpression entityOpSubExpr = ((Expression.EntityOpExpression)expression.getSubExpression());
			switch (entityOpSubExpr.getType()) {
			case GETX:
				return ((Entity)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)).getPosX();
			case GETY:
				return ((Entity)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)).getPosY();
			case GETRAD:
				return ((Entity)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)).getRadius();
			case GETDIR:
				return ((Movable)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)).getOrientation();
			case GETAP:
				return ((Worm)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)).getActionPoints();
			case GETMAXAP:
				return ((Worm)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)).getMaxActionPoints();
			case GETHP:
				return ((Worm)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)).getHitPoints();
			case GETMAXHP:
				return ((Worm)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)).getMaxHitPoints();
			case SAMETEAM:
				Team teamToCheck = ((Worm)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)).getTeam();
				if ((teamToCheck == null) || (this.getTeam() == null))
					return false; //free-for-all
				return teamToCheck == this.getTeam();
			case ISWORM:
				return Worm.class.isInstance(((Entity)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)));
			case ISFOOD:
				return Food.class.isInstance(((Entity)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content)));
			case SEARCHOBJ:
				return searchObject((Double)evaluateExpression(((Expression.EntityOpExpression)expression.subExpression).content));
			}
			
		default: return null; //because errors otherwise
		}
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
		if (output == null) System.out.println("No types found"); //monitoring/debug line!
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
			System.out.println("jumping");
			this.handler.jump(this);
		} else if (commandName == "move") {
			System.out.println("moving");
			this.handler.move(this);
		} else if (commandName == "toggleweap") {
			System.out.println("toggling weapon");
			this.handler.toggleWeapon(this);
		} else if (commandName == "fire") {
			System.out.println("firing");
			this.handler.fire(this, (int)Math.round((Double)value));
		} else if (commandName == "turn") {
			System.out.println("turning");
			this.handler.turn(this, (Double)value);
		} else if (commandName == "skip") {
			System.out.println("skipping");
		} else throw new IllegalArgumentException("Illegal command name");
	}
	
	/**
	 * Iterative search for an object in a given direction starting from this.
	 * @param direction The direction in which you look for an entity.
	 * @return The Entity, if any. Null otherwise.
	 */
	public Entity searchObject(Double direction) {
		double startingX = getPosX()+Math.cos(getOrientation())*getRadius();
		double startingY = getPosY()+Math.sin(getOrientation())*getRadius();
		double deltaD = 0.1;
		double currentX = startingX + Math.cos(getOrientation())*deltaD;
		double currentY = startingY + Math.sin(getOrientation())*deltaD;
		boolean hasEnded = false;
		//while true and return null when hasEnded is also possible
		while(!hasEnded) {
			//search for object in currentXY
			Entity onLocation = getWorld().getEntityOn(currentX, currentY);
			//if found, return it
			if (onLocation != null)
				return onLocation;
			//if not, keep looking
			currentX += Math.cos(getOrientation())*deltaD;
			currentY += Math.sin(getOrientation())*deltaD;
			//It's gotta end sometime.
			if (!(getWorld().isValidX(currentX) && getWorld().isValidY(currentY)))
				hasEnded = true;
		} return null;
	}
	
	/**
	 * Stuff to do at the end of the turn, if any
	 * 
	 * NOTE: Do we need to select the next turn?
	 */
	private void endTurn() {
		this.hasPerformedAction = true;
	}
	
	/**
	 * error-generator for checking if we reach certain points (why not a printline? uhh... ?)
	 */
	@SuppressWarnings("null") //the warning was bugging me
	public void generateError() {
		Entity error = null; error.getPosX();
	}
	
	public int getCounter(){
		return this.counter;
	}
	
	public void incrementCounter(){
		this.counter++;
	}
}
