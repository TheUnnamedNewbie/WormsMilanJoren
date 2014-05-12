package worms.programs;

import java.util.HashMap;
import java.util.List;

import worms.containment.Team;
import worms.entities.Entity;
import worms.entities.Food;
import worms.entities.Movable;
import worms.model.Worm;
import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramFactory.ForeachType;
import worms.programs.expressions.Expression;
import worms.programs.statements.Statement;
import worms.programs.types.Type;

public class Factory implements ProgramFactory<Expression, Statement, Type> {
	
	private HashMap<String, Expression> vars = new HashMap<String, Expression>();
	
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
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionNull();
		return temporary;
	}
	
	public Expression createSelf(int line, int column){
		Expression temporary;
		temporary = new Expression(line, column);
		Entity target = DefaultActionHandler.getFacade().getCurrentWorm(); //HOW!?
		target = null; //TODO pointer goes here
		temporary.createSubExpressionEntity(target);
		return temporary;
	}
	
	public Expression createGetX(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		//TODO Assert things
		Entity value = (Entity) exp.getSubExpression().getValue();
		temporary.createSubExpressionDoubleLiteral(value.getPosX());
		return temporary;
	}
	
	public Expression createGetY(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		//TODO Assert things
		Entity value = (Entity) exp.getSubExpression().getValue();
		temporary.createSubExpressionDoubleLiteral(value.getPosY());
		return temporary;
	}

	public Expression createGetRadius(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		//TODO Assert things
		Entity value = (Entity) exp.getSubExpression().getValue();
		temporary.createSubExpressionDoubleLiteral(value.getRadius());
		return temporary;
	}
	
	public Expression createGetDir(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		//TODO Assert things
		Movable value = (Movable) exp.getSubExpression().getValue();
		temporary.createSubExpressionDoubleLiteral(value.getOrientation());
		return temporary;
	}

	public Expression createGetAP(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		//TODO Assert things
		Worm value = (Worm) exp.getSubExpression().getValue();
		temporary.createSubExpressionDoubleLiteral(value.getActionPoints()); //auto-convert from long to double? probably!
		return temporary;
	}
	
	public Expression createGetMaxAP(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		//TODO Assert things
		Worm value = (Worm) exp.getSubExpression().getValue();
		temporary.createSubExpressionDoubleLiteral(value.getMaxActionPoints()); //auto-convert from long to double? probably!
		return temporary;
	}
	
	public Expression createGetHP(int line, int column, Expression exp){
		Expression temporary;
		temporary = new Expression(line, column);
		//TODO Assert things
		Worm value = (Worm) exp.getSubExpression().getValue();
		temporary.createSubExpressionDoubleLiteral(value.getHitPoints()); //auto-convert from long to double? probably!
		return temporary;
	}
	
	public Expression createGetMaxHP(int line, int column, Expression exp) {
		Expression temporary;
		temporary = new Expression(line, column);
		//TODO Assert things
		Worm value = (Worm) exp.getSubExpression().getValue();
		temporary.createSubExpressionDoubleLiteral(value.getMaxHitPoints()); //auto-convert from long to double? probably!
		return temporary;
	}

	public Expression createSameTeam(int line, int column, Expression exp){
		//getting team from given
		Team team1 = ((Worm) exp.getSubExpression().getValue()).getTeam();
		// Get team from currently executing
		Team team2 = null; //... again with the 'how do wet get to the game?'
		boolean target_value = (team1.getName()==team2.getName());
		//create and return expression
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionBooleanLiteral(target_value);
		return temporary;
	}
	
	public Expression createSearchObj(int line, int column, Expression exp){
		return null;
	}
	
	public Expression createIsWorm(int line, int column, Expression exp){
		boolean target_value = Worm.class.isInstance(exp.getSubExpression().getValue());
		//create and return expression
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionBooleanLiteral(target_value);
		return temporary;
	}
	
	public Expression createIsFood(int line, int column, Expression exp) {
		boolean target_value = Food.class.isInstance(exp.getSubExpression().getValue());
		//create and return expression
		Expression temporary;
		temporary = new Expression(line, column);
		temporary.createSubExpressionBooleanLiteral(target_value);
		return temporary;
	}

	@Override
	public Expression createVariableAccess(int line, int column, String name) {
		return vars.get(name); //right?
	}

	@Override
	public Expression createLessThan(int line, int column, Expression e1,
			Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGreaterThan(int line, int column, Expression e1,
			Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createLessThanOrEqualTo(int line, int column,
			Expression e1, Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGreaterThanOrEqualTo(int line, int column,
			Expression e1, Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createEquality(int line, int column, Expression e1,
			Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createInequality(int line, int column, Expression e1,
			Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createAdd(int line, int column, Expression e1,
			Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createSubtraction(int line, int column, Expression e1,
			Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createMul(int line, int column, Expression e1,
			Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createDivision(int line, int column, Expression e1,
			Expression e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createSqrt(int line, int column, Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createSin(int line, int column, Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createCos(int line, int column, Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createTurn(int line, int column, Expression angle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createMove(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createJump(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createToggleWeap(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createFire(int line, int column, Expression yield) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSkip(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createAssignment(int line, int column,
			String variableName, Expression rhs) {
		vars.put(variableName, rhs);
		//how the hell do we represent this thing that has happened by a statement
		//(and not void, as would be custom)!?
		return null;
	}

	@Override
	public Statement createIf(int line, int column, Expression condition,
			Statement then, Statement otherwise) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWhile(int line, int column, Expression condition,
			Statement body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createForeach(int line, int column,
			worms.model.programs.ProgramFactory.ForeachType type,
			String variableName, Statement body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSequence(int line, int column,
			List<Statement> statements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(int line, int column, Expression e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type createDoubleType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type createBooleanType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type createEntityType() {
		// TODO Auto-generated method stub
		return null;
	}
}