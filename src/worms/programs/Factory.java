package worms.programs;

import worms.model.programs.ProgramFactory;
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
	
	
	
	
}
