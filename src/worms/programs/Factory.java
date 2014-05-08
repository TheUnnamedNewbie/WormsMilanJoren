package worms.programs;

import worms.model.programs.ProgramFactory;
import worms.programs.expressions.Expression;
import worms.programs.statements.Statement;
import worms.programs.types.Type;

public class Factory implements ProgramFactory<Expression, Statement, Type> {
	
	//DONE
	public Expression createnDoubleLiteral(int line, int column, double d) {
		return null;
	}
	
	//DONE
	public Expression createBooleanLiteral(int line, int column, boolean b) {
		return null;
	}
	
	//DONE
	public Expression createAnd(int line, int column, Expression e1, Expression e2) {
		return null;
	}
	
	//DONE
	public Expression createOr(int line, int column, Expression e1, Expression e2) {
		return null;
	}
	
	//DONE
	public Expression createNot(int line, int column, Expression e) {
		return null;
	}
	
	
}
