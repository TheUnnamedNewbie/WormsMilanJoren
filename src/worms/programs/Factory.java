package worms.programs;

import java.util.List;

import worms.model.programs.ProgramFactory;
import worms.programs.Expressions.Expression;
import worms.programs.statements.Statement;
import worms.programs.types.Type;
import worms.programs.types.TypeType;

public class Factory implements ProgramFactory<Expression, Statement, Type> {
	
	public Factory() {
	}
	

	/**
	 * Method to create doubleLiteral.
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @param d
	 * 		The value to be "stored" in the double literal
	 * @return
	 * 		An expression with subtype doubleliteral
	 * 	
	 */
	public Expression createDoubleLiteral(int line, int column, double d) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionDoubleLiteral(d);
		return temporary;
	}
	

	/**
	 * Method to create BooleanLiteral
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
	 * 		A ex[ression with subtype booleanliteral
	 */
	public Expression createBooleanLiteral(int line, int column, boolean b) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionBooleanLiteral(b);
		return temporary;
	}
	
	/**
	 * Method to create a logical And expression
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
	 * 		An expression with subExpressiontype AND
	 */
	public Expression createAnd(int line, int column, Expression e1, Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionAnd(e1, e2);
		return temporary;
	}
	
	
	/**
	 * Method to create a logical Or
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
	 * 		An expression with subexpressiontype Or
	 */
	public Expression createOr(int line, int column, Expression e1, Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionOr(e1, e2);
		return temporary;
	}
	
	//DONE
	/**
	 * Method to create a logical inversion
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 */
	public Expression createNot(int line, int column, Expression e) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionNot(e);
		return temporary;
	}
	
	/**
	 * Method to create Null
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 */
	public Expression createNull(int line, int column) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionNullLiteral();
		return temporary;
	}
	
	/**
	 * Method to create Self expression (IE a reference to the current active worm
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createSelf(int line, int column){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionThisLiteral();
		return temporary;
	}
	
	/**
	 * Method for creating an Expression containing the X value of the given expression (if the given expression is an Entity type)
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createGetX(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetX(exp);
		return temporary;
	}
	
	/**
	 * Method for creating an expression containing the y value of the given expression (if the given expression is an Entity type)
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createGetY(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetY(exp);
		return temporary;
	}

	/**
	  * Method for creating an expression containing the value of the radius of the given expression (if the given expression is an Entity type)
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createGetRadius(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetRadius(exp);
		return temporary;
	}
	
	/**
	 *  Method for creating an expression containing the value of the direction of the given expression 
	 *  (if the given expression has a direction, ie, is a worm)
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createGetDir(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetOrientation(exp);
		return temporary;
	}

	/**
	 *  * Method for creating an expression containing the actionpoints of the given expression 
	 *  (if the given expression is a worm)	
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createGetAP(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetAP(exp);
		return temporary;
	}
	
	/**
	 *  * Method for creating an expression containing the maximum value of actionpoints of the given expression 
	 *  (if the given expression is a worm)	
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createGetMaxAP(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetMaxAP(exp);
		return temporary;
	}
	
	/**
	 *  * Method for creating an expression containing the current hitpoints of the given expression
	 *  (if the given expression is a worm)	
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createGetHP(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetHP(exp);
		return temporary;
	}
	
	/**
	 *  * Method for creating an expression containing the maximum amount of hitpoints of the given expression 
	 *  (if the given expression is a worm)	
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createGetMaxHP(int line, int column, Expression exp) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetMaxHP(exp);
		return temporary;
	}

	/**
	 * Method that creates a expression which gives whether or not the expression stores a worm of the same team as the worm executing the current program
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createSameTeam(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSameTeam(exp);
		return temporary;
	}
	
	/**
	 * Method that returns a searchObject 
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createSearchObj(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSearchObject(exp);
		return temporary;
	}
	
	/**
	 * Method that creates a expression storing if the given expression exp is a worm or not
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createIsWorm(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionIsWorm(exp);
		return temporary;
	}
	
	/**
	 * Method for creating a expression storing if the given expression exp is food or not
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	public Expression createIsFood(int line, int column, Expression exp) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionIsFood(exp);
		return temporary;
	}
	
	/**
	 * Method creating a variable acces expression with name "name"
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	v* @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createVariableAccess(int line, int column, String name) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionVaribleAccess(name);
		return temporary;
	}
	
	/**
	 * Method for creating an expression representing a lessthan operator.
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createLessThan(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionLessThan(e1, e2);
		return temporary;
	}

	/**
	 * Method for creatnig an expression representing a greater than operator
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createGreaterThan(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGreaterThan(e1, e2);
		return temporary;
	}

	/**
	 * Method for creatnig an expression representing a less than or equal to operator
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createLessThanOrEqualTo(int line, int column,
			Expression e1, Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionLessThanOrEqual(e1, e2);
		return temporary;
	}

	/**
	 * Method for creatnig an expression representing a greater than or equal to operator
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createGreaterThanOrEqualTo(int line, int column,
			Expression e1, Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGreaterThanOrEqual(e1, e2);
		return temporary;
	}

	/**
	 *
	 * Method for creatnig an expression representing a equal to operator
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createEquality(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		if ((e1.getReturnType()==TypeType.DOUBLE) && (e2.getReturnType()==TypeType.DOUBLE))
			temporary.createSubExpressionDoubleEquality(e1, e2);
		else if ((e1.getReturnType()==TypeType.BOOLEAN) && (e2.getReturnType()==TypeType.BOOLEAN))
			temporary.createSubExpressionBooleanEquality(e1, e2);
		else if ((e1.getReturnType()==TypeType.ENTITY) && (e2.getReturnType()==TypeType.ENTITY))
			temporary.createSubExpressionEntityEquality(e1, e2);
		else if ((e1.getReturnType()==null) || (e2.getReturnType()==null))
			temporary.createSubExpressionUncheckedEquality(e1, e2);
		return temporary;
	}
	 
	/**
	 * Method for creatnig an expression representing a not equal to operator
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createInequality(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary1, temporary2;
		temporary1 = new Expression(line, column);
		temporary2 = new Expression(line, column);
		if ((e1.getReturnType()==TypeType.DOUBLE) && (e2.getReturnType()==TypeType.DOUBLE)) {
			temporary1.createSubExpressionDoubleEquality(e1, e2);
			temporary2.createSubExpressionNot(temporary1);
		} else if ((e1.getReturnType()==TypeType.BOOLEAN) && (e2.getReturnType()==TypeType.BOOLEAN)) {
			temporary1.createSubExpressionBooleanEquality(e1, e2);
			temporary2.createSubExpressionNot(temporary1);
		} else if ((e1.getReturnType()==TypeType.ENTITY) && (e2.getReturnType()==TypeType.ENTITY)) {
			temporary1.createSubExpressionEntityEquality(e1, e2);
			temporary2.createSubExpressionNot(temporary1);
		} else if ((e1.getReturnType()==null) || (e2.getReturnType()==null)) {
			temporary1.createSubExpressionUncheckedEquality(e1, e2);
			temporary2.createSubExpressionNot(temporary1);
		}
		return temporary2;
	}

	/**
	 * Method for creatnig an expression representing logical and operation
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createAdd(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionAddition(e1, e2);
		return temporary;
	}

	/**
	 * Method for creating an expression representing a subtraction of two expressions
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createSubtraction(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSubtraction(e1, e2);
		return temporary;
	}

	/**
	 * Method for creating an expression representing a multiplication of two expressions
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createMul(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionMultiplication(e1, e2);
		return temporary;
	}
	
	/**
	 * Method for creating an expression representing a division of two expressoins.
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createDivision(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionDivision(e1, e2);
		return temporary;
	}

	/**
	 * Method for creating an expression representing a square root of an expression
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createSqrt(int line, int column, Expression e) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSquareRoot(e);
		return temporary;
	}
	
	/**
	 * Method for creating an expression representing the sine of an expression
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createSin(int line, int column, Expression e) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSine(e);
		return temporary;
	}

	/**
	 * Method for creating an expression representing the cosine of an expression
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return	
	 * 		The requested expression
	 */
	@Override
	public Expression createCos(int line, int column, Expression e) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionCosine(e);
		return temporary;
	}

	/**
	 * Method for creating a statement representing a turn, of which the angle is given by the given expression.
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createTurn(int line, int column, Expression angle) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("turn", angle);
		return temporary;
	}
	
	/**
	 * Method for creating a statement representing a move command
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createMove(int line, int column) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("move", null);
		return temporary;
	}

	/**
	 * Method for creating a statement representing a Jump command
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createJump(int line, int column) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("jump", null);
		return temporary;
	}

	/**
	 * Method for creating a statement repressenting the toggeling of weapons
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createToggleWeap(int line, int column) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("toggleweap", null);
		return temporary;
	}

	/**
	 * Method for creating a statement representing the fire, of which the yield is given throught the expression
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createFire(int line, int column, Expression yield) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("fire", yield);
		return temporary;
	}

	/**
	 * Method for creating a statement representing a skip turn command
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createSkip(int line, int column) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("skip", null);
		return temporary;
	}

	/**
	 * Method for creating a statement that will assign a name to an expression
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createAssignment(int line, int column,
			String variableName, Expression rhs) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAssign(variableName, rhs);
		return temporary;
	}

	/**
	 * Method for creating a statement representing an if-then-else
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createIf(int line, int column, Expression condition,
			Statement then, Statement otherwise) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementIf(condition, then, otherwise);
		return temporary;
	}

	/**
	 * Method for creating a statement representing a while loop
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createWhile(int line, int column, Expression condition,
			Statement body) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementWhile(condition, body);
		return temporary;
	}
	
	/**
	 * Method for creating a statement representing a For-each-in loop
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createForeach(int line, int column,
			worms.model.programs.ProgramFactory.ForeachType type,
			String variableName, Statement body) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementFor(type, variableName, body);
		return temporary;
	}
	
	/**
	 * Method for creating a statement representing a sequence
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createSequence(int line, int column,
			List<Statement> statements) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementSequence(statements);
		return temporary;
	}
	
	/**
	 * Method for creating a statement that will execute a print
	 * @param line
	 * 		The line of the original code that creates the method call through the parser
	 * @param column
	 * 		The column of the original code that creates the method call through the parser	
	 * @return
 	 * 		The requested Statement
	 */
	@Override
	public Statement createPrint(int line, int column, Expression e) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementPrint(e);
		return temporary;
	}
	
	/**
	 * Method for creating a double type
	 * @return
	 * 		The requested Type
	 */
	@Override
	public Type createDoubleType() {
		return new Type(TypeType.DOUBLE);
	}

	/**
	 * Method for creating a boolean type
	 * @return
	 * 		The requested Type
	 */
	@Override
	public Type createBooleanType() {
		return new Type(TypeType.BOOLEAN);
	}
	
	/**
	 * Method for creating an entity type
	 * @return
	 * 		The requested Type
	 */
	@Override
	public Type createEntityType() {
		return new Type(TypeType.ENTITY);
	}
}