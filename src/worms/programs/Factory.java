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
	
	//DONE
	public Expression createDoubleLiteral(int line, int column, double d) {
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
		temporary.createSubExpressionAnd(e1, e2);
		return temporary;
	}
	
	//DONE
	public Expression createOr(int line, int column, Expression e1, Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionOr(e1, e2);
		return temporary;
	}
	
	//DONE
	public Expression createNot(int line, int column, Expression e) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionNot(e);
		return temporary;
	}
	
	public Expression createNull(int line, int column) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionNullLiteral();
		return temporary;
	}
	
	public Expression createSelf(int line, int column){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionThisLiteral();
		return temporary;
	}
	
	public Expression createGetX(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetX(exp);
		return temporary;
	}
	
	public Expression createGetY(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetY(exp);
		return temporary;
	}

	public Expression createGetRadius(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetRadius(exp);
		return temporary;
	}
	
	public Expression createGetDir(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetOrientation(exp);
		return temporary;
	}

	public Expression createGetAP(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetAP(exp);
		return temporary;
	}
	
	public Expression createGetMaxAP(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetMaxAP(exp);
		return temporary;
	}
	
	public Expression createGetHP(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetHP(exp);
		return temporary;
	}
	
	public Expression createGetMaxHP(int line, int column, Expression exp) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGetMaxHP(exp);
		return temporary;
	}

	public Expression createSameTeam(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSameTeam(exp);
		return temporary;
	}
	
	public Expression createSearchObj(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSearchObject(exp);
		return temporary;
	}
	
	public Expression createIsWorm(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionIsWorm(exp);
		return temporary;
	}
	
	public Expression createIsFood(int line, int column, Expression exp) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionIsFood(exp);
		return temporary;
	}

	@Override
	public Expression createVariableAccess(int line, int column, String name) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionVaribleAccess(name);
		return temporary;
	}

	@Override
	public Expression createLessThan(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionLessThan(e1, e2);
		return temporary;
	}

	@Override
	public Expression createGreaterThan(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGreaterThan(e1, e2);
		return temporary;
	}

	@Override
	public Expression createLessThanOrEqualTo(int line, int column,
			Expression e1, Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionLessThanOrEqual(e1, e2);
		return temporary;
	}

	@Override
	public Expression createGreaterThanOrEqualTo(int line, int column,
			Expression e1, Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionGreaterThanOrEqual(e1, e2);
		return temporary;
	}

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

	@Override
	public Expression createAdd(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionAddition(e1, e2);
		return temporary;
	}

	@Override
	public Expression createSubtraction(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSubtraction(e1, e2);
		return temporary;
	}

	@Override
	public Expression createMul(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionMultiplication(e1, e2);
		return temporary;
	}

	@Override
	public Expression createDivision(int line, int column, Expression e1,
			Expression e2) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionDivision(e1, e2);
		return temporary;
	}

	@Override
	public Expression createSqrt(int line, int column, Expression e) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSquareRoot(e);
		return temporary;
	}

	@Override
	public Expression createSin(int line, int column, Expression e) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionSine(e);
		return temporary;
	}

	@Override
	public Expression createCos(int line, int column, Expression e) {
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionCosine(e);
		return temporary;
	}

	@Override
	public Statement createTurn(int line, int column, Expression angle) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("turn", angle);
		return temporary;
	}

	@Override
	public Statement createMove(int line, int column) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("move", null);
		return temporary;
	}

	@Override
	public Statement createJump(int line, int column) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("jump", null);
		return temporary;
	}

	@Override
	public Statement createToggleWeap(int line, int column) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("toggleweap", null);
		return temporary;
	}

	@Override
	public Statement createFire(int line, int column, Expression yield) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("fire", yield);
		return temporary;
	}

	@Override
	public Statement createSkip(int line, int column) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAction("skip", null);
		return temporary;
	}

	@Override
	public Statement createAssignment(int line, int column,
			String variableName, Expression rhs) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementAssign(variableName, rhs);
		return temporary;
	}

	@Override
	public Statement createIf(int line, int column, Expression condition,
			Statement then, Statement otherwise) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementIf(condition, then, otherwise);
		return temporary;
	}

	@Override
	public Statement createWhile(int line, int column, Expression condition,
			Statement body) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementWhile(condition, body);
		return temporary;
	}

	@Override
	public Statement createForeach(int line, int column,
			worms.model.programs.ProgramFactory.ForeachType type,
			String variableName, Statement body) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementFor(type, variableName, body);
		return temporary;
	}

	@Override
	public Statement createSequence(int line, int column,
			List<Statement> statements) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementSequence(statements);
		return temporary;
	}

	@Override
	public Statement createPrint(int line, int column, Expression e) {
		Statement temporary;
		temporary = new Statement(line, column);
		temporary.createSubStatementPrint(e);
		return temporary;
	}

	@Override
	public Type createDoubleType() {
		return new Type(TypeType.DOUBLE);
	}

	@Override
	public Type createBooleanType() {
		return new Type(TypeType.BOOLEAN);
	}

	@Override
	public Type createEntityType() {
		return new Type(TypeType.ENTITY);
	}
}