package worms.programs.expressions;

import worms.programs.expressions.Expression;

public abstract class SubExpression {
	
	protected final Expression master;
	
	public String getType(){
		return "SubExpression";
	}

}
