package worms.programs;

import java.util.List;

import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramFactory.ForeachType;
import worms.programs.expressions.Expression;
import worms.programs.statements.Statement;
import worms.programs.types.Type;

public class Factory implements ProgramFactory<Expression, Statement, Type> {
	
	//DONE
	public Expression createnDoubleLiteral(int line, int column, double d) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionDoubleLiteral(d);
		return temporary;
	}
	
	//DONE
	public Expression createBooleanLiteral(int line, int column, boolean b) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionBooleanLiteral(b);
		return temporary;
	}
	
	//DONE
	public Expression createAnd(int line, int column, Expression e1, Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionLogicAnd(e1, e2);
		return temporary;
	}
	
	//DONE
	public Expression createOr(int line, int column, Expression e1, Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionLogicOr(e1, e2);
		return temporary;
	}
	
	//DONE
	public Expression createNot(int line, int column, Expression e) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionLogicNot(e);
		return temporary;
	}
	
	public Expression createNull(int line, int column) {
		return null;
	}
	
	public Expression createSelf(int line, int column){
		return null;
	}
	
	public Expression createGetX(int line, int column, Expression exp){
		return null;
	}
	
	public Expression createGetY(int line, int column, Expression exp){
		return null;
	}

	public Expression createGetRadius(int line, int column, Expression exp){
		return null;
	}
	
	public Expression createGetDir(int line, int column, Expression exp){
		return null;
	}

	public Expression createGetAP(int line, int column, Expression exp){
		return null;
	}
	
	public Expression createGetMaxAP(int line, int column, Expression exp){
		return null;
	}
	
	public Expression creategetHP(int line, int column, Expression exp){
		return null;
	}
	
	public Expression createGetMaxHP(int line, int column, Expression exp) {
		return null;
	}

	public Expression createSameTeam(int line, int column, Expression exp){
		return null;
	}
	
	public Expression createSearchObj(int line, int column, Expression exp){
		return null;
	}
	
	public Expression createIsWorm(int line, int column, Expression exp){
		return null;
	}
	
	public Expression createIsFood(int line, int column, Expression e);

	/**
	 * Create an expression that evaluates to the value of the variable with the
	 * given name
	 */
	public Expression createVariableAccess(int line, int column, String name);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * less than the value of the expression e2
	 */
	public Expression createLessThan(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * greater than the value of the expression e2
	 */
	public Expression createGreaterThan(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * less than or equal to the value of the expression e2
	 */
	public Expression createLessThanOrEqualTo(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * greater than or equal to the value of the expression e2
	 */
	public Expression createGreaterThanOrEqualTo(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * equal to the value of the expression e2
	 */
	public Expression createEquality(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that checks whether the value of expression e1 is
	 * not equal to the value of the expression e2
	 */
	public Expression createInequality(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that represents the addition of the value of
	 * expression e1 and the value of the expression e2
	 */
	public Expression createAdd(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that represents the subtraction of the value of
	 * expression e1 and the value of the expression e2
	 */
	public Expression createSubtraction(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that represents the multiplication of the value of
	 * expression e1 and the value of the expression e2
	 */
	public Expression createMul(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that represents the division of the value of
	 * expression e1 and the value of the expression e2
	 */
	public Expression createDivision(int line, int column, Expression e1, Expression e2);

	/**
	 * Create an expression that represents the square root of the value of
	 * expression e1 and the value of the expression e2
	 */
	public Expression createSqrt(int line, int column, Expression e);

	/**
	 * Create an expression that represents the sine of the value of expression
	 * e1 and the value of the expression e2
	 */
	public Expression createSin(int line, int column, Expression e);

	/**
	 * Create an expression that represents the cosine of the value of
	 * expression e1 and the value of the expression e2
	 */
	public Expression createCos(int line, int column, Expression e);

	/* actions */

	/**
	 * Create a statement that represents a turn of the worm executing the
	 * program by the value of the angle expression
	 */
	public S createTurn(int line, int column, Expression angle);

	/**
	 * Create a statement that represents a move of the worm executing the
	 * program
	 */
	public S createMove(int line, int column);

	/**
	 * Create a statement that represents a jump of the worm executing the
	 * program
	 */
	public S createJump(int line, int column);

	/**
	 * Create a statement that represents toggling the weapon of the worm
	 * executing the program
	 */
	public S createToggleWeap(int line, int column);

	/**
	 * Create a statement that represents firing the current weapon of the worm
	 * executing the program, where the propulsion yield is given by the yield
	 * expression
	 */
	public S createFire(int line, int column, Expression yield);

	/**
	 * Create a statement that represents no action of a worm
	 */
	public S createSkip(int line, int column);

	/* other statements */

	/**
	 * Create a statement that represents the assignment of the value of the rhs
	 * expression to a variable with the given name
	 */
	public S createAssignment(int line, int column, String variableName, Expression rhs);

	/**
	 * Create a statement that represents the conditional execution of the
	 * statements then or otherwise, depending on the value of the condition
	 * expression
	 */
	public S createIf(int line, int column, Expression condition, S then, S otherwise);

	/**
	 * Create a statement that represents the repeated execution of the body
	 * statement, as long as the value of the condition expression evaluates to
	 * true
	 */
	public S createWhile(int line, int column, Expression condition, S body);

	/**
	 * Create a statement that represents the repeated execution of the body
	 * statement, where for each execution the value of the variable with the
	 * given name is set to a different object of the given type.
	 */
	public S createForeach(int line, int column, ForeachType type,
			String variableName, S body);

	/**
	 * Create a statement that represents the sequential execution of the given
	 * statements
	 */
	public S createSequence(int line, int column, List<S> statements);

	/**
	 * Create a statement that represents printing out the value of the
	 * expression e
	 */
	public S createPrint(int line, int column, Expression e);

	/* types */

	/**
	 * Returns an object that represents the type of a global variable with
	 * declared type 'double'.
	 */
	public T createDoubleType();

	/**
	 * Returns an object that represents the type of a global variable with
	 * declared type 'boolean'.
	 */
	public T createBooleanType();

	/**
	 * Returns an object that represents the type of a global variable with
	 * declared type 'entity'.
	 */
	public T createEntityType();
}
